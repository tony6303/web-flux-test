package com.cos.reactorex02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Reactorex02Application {

	public static void main(String[] args) {
		SpringApplication.run(Reactorex02Application.class, args);
		
		String response = "username=ssar&password=1234";
		String[] arrResponse = response.split("&");
		
		String[] usernameResp = arrResponse[0].split("username=");
		String[] passwordResp = arrResponse[1].split("password=");
		
		System.out.println("1. "+arrResponse[0]);
		System.out.println("2. "+arrResponse[1]);
		
		System.out.println("3. "+usernameResp[1]);
		System.out.println("4. "+passwordResp[1]);
		
		User user1 = new User(usernameResp[1], passwordResp[1] );
		System.out.println("5. "+user1);
	}

}
