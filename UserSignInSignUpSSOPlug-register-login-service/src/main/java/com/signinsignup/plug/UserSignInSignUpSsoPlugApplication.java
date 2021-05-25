package com.signinsignup.plug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserSignInSignUpSsoPlugApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSignInSignUpSsoPlugApplication.class, args);
	}

}
