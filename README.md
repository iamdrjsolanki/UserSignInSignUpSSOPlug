# UserSignInSignUpSSOPlug
 - User SignUp and SignIn and SSO pluggable application.
 - Tech Stack
	- Java 8
	- Maven
	- Spring Boot 2.4.5
	- Spring Security, JPA, Cloud
	- Netflix Eureka
	- MySQL
	- Angular
 

## Google Console Configuration
1. Go to console.google.com
2. Create a project and then go to the "Credentials" tab to configure.
3. Go to "+ CREATE CREDENTIALS" and select "OAuth Client ID".
4. Select applicaton type, and then enter the name and also provide "Authorised redirect URIs".
5. After creating the credentials, a prompt appears, copy the "Client ID" & "Client Secret".


## Sping Security Configuration
 - Use the Client ID & Client Secret in the application.properties file to configure.
 - Added database details for storing users.
 - Added Eureka Sevice Discovery server. Runs on 8761 port.
 - Created 2 micro-services.
	- UserSignInSignUpSSOPlug-register-login-service 
		- User registration & login. Runs on 8080 port.
		- Registers the user and stores into the database.
		- Authenticates the user. Also creates a JWToken for the user and accepts it with every request.
		
	- UserSignInSignUpSSOPlug-sso-service
		- One for User SSO login through Google & Github credentials. Runs on 8081 port.
		- Uses SSO to authenticate users. Also stores the authentication object in a cache in TokenStore.java file.
		- A random UUID string is created and stored in the cache as key and Authentication object as its value.
		- The randin UUID string is now sent as response, and to be treated as a JWToken.
		- Every request has the token with it which is extracted and evaluated from the TokenStore cache.
		
	- Both services authenticate the user and share a JWToken for intercepting further requests.
 

## User Inteface
 - UI has been created using Angular.
 - The Angular appliaction runs on 4200 port.
 - The application saves the JWToken on the local storage and retrieves it from there to intercept every request with that JWToken.
 - The application handles the User Registration & User Login part and User SSO part separately.
  

## Future applications
 - New micro-services can be added behind the above two micro-services.
 - The above micro-services will authenticate the user and send requests to different micro-services for different purpose. 


