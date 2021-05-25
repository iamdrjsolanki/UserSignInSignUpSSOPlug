package com.signinsignup.plug.sso.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final ObjectMapper objectMapper;
	private final TokenStore tokenStore;
	private final TokenFilter tokenFilter;
	
	public SecurityConfig(ObjectMapper objectMapper, TokenStore tokenStore, TokenFilter tokenFilter) {
		super();
		this.objectMapper = objectMapper;
		this.tokenStore = tokenStore;
		this.tokenFilter = tokenFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable().cors()
			.and()
				.authorizeRequests()
		        .antMatchers( "/sso-api/" ).permitAll()
		        .anyRequest().authenticated()
		    .and()
		    	.oauth2Login()
		    	.authorizationEndpoint()
		    	.authorizationRequestRepository( new InMemoryRequestRepository() )
		    .and()
		    	.successHandler( this::successHandler )
		    .and()
		    	.exceptionHandling()
		    	.authenticationEntryPoint( this::authenticationEntryPoint );
		
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	private void successHandler(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication) throws IOException {
		
		String token = tokenStore.generateToken(authentication);
		response.getWriter().write(
				objectMapper.writeValueAsString(Collections.singletonMap("accessToken", token))
			);
	
	}
	
	private void authenticationEntryPoint( HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException ) throws IOException {
	
		response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		response.getWriter().write( objectMapper.writeValueAsString( Collections.singletonMap( "error", "Unauthenticated" ) ) );
	
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	public WebMvcConfigurer cors() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String allowedOrigins = 
						"http://localhost:4200";
				
				registry.addMapping("/**")
					.allowedOrigins(allowedOrigins)
					.allowCredentials(true)
					.allowedHeaders("*");
			}
		};
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
