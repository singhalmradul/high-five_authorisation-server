package io.github.singhalmradul.authorizationserver;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {

		run(AuthorizationServerApplication.class, args);
	}

}
