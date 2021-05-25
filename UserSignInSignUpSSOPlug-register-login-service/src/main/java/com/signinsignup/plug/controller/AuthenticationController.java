package com.signinsignup.plug.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.signinsignup.plug.model.AuthenticationReponse;
import com.signinsignup.plug.model.AuthenticationRequest;
import com.signinsignup.plug.model.RegisteredUser;
import com.signinsignup.plug.model.Users;
import com.signinsignup.plug.service.impl.MyUserDetailsService;
import com.signinsignup.plug.service.impl.UsersService;
import com.signinsignup.plug.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UsersService userService;
	
	@GetMapping("/")
	public String helloWorld() {
		return "Hello World. \nThis is the landing page of the application. \nPlease put /login at the end of the url to get authenticated.";
	}
	
	@GetMapping("/restricted")
	public String restricted() {
		return "You are now accessing the restricted API with the use of JWT.";
	}
	
	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		Users currentUser = userService.findByUsername(request.getUsername());
		
		return ResponseEntity
				.ok(new AuthenticationReponse(
						currentUser.getUsername(), currentUser.getDisplayName(), 
						currentUser.getRole(), jwt
					)
				);
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal prin) {
		return prin;
	}
	
	@PostMapping("/register-user")
	public ResponseEntity<?> registerUser(@RequestBody Users user) throws Exception {
		user.setUsername(user.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setCreatedDate(LocalDateTime.now());
		user.setModifiedDate(LocalDateTime.now());
		
		Users newUser = userService.saveUser(user);
		
		RegisteredUser regUser = new RegisteredUser(newUser.getEmail(), newUser.getUsername(), newUser.getRole(), newUser.getDisplayName());
					
		return ResponseEntity.ok(regUser);
	}
	
	@RequestMapping(path = "/get-jwt/{username}", method = RequestMethod.POST)
	public ResponseEntity<?> getJWTForUser(@PathVariable("username") String username) throws Exception {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		final String jwt = jwtUtil.generateToken(userDetails);
		
		Users currentUser = userService.findByUsername(username);
		
		return ResponseEntity
				.ok(new AuthenticationReponse(
						currentUser.getUsername(), currentUser.getDisplayName(), 
						currentUser.getRole(), jwt
					)
				);
	}

}
