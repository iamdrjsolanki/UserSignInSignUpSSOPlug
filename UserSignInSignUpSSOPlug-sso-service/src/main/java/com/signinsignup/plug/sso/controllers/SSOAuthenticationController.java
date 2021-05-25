package com.signinsignup.plug.sso.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sso-api")
public class SSOAuthenticationController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/")
	public String helloWorld() {
		return "Hello World. \nThis is the landing page of the application. \nPlease put /login at the end of the url to get authenticated.";
	}
	
	@GetMapping("/restricted")
	public String restricted() {
		return "You are now accessing the restricted API with the use of SSO & JWT.";
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal prin) {
		return prin;
	}
	
	@GetMapping("/user/me")
	public Map<String, Object> userDetails(@AuthenticationPrincipal OAuth2User user) {
		System.out.println(user.getAttributes().get("email"));
	    return user.getAttributes();
	}

}
