package com.signinsignup.plug.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserSignInSignUpSsoPlugSsoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSignInSignUpSsoPlugSsoServiceApplication.class, args);
	}

}
