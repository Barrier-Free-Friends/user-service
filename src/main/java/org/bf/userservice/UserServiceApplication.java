package org.bf.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "org.bf.userservice",
        "org.bf.global"
})
public class UserServiceApplication {

	public static void main(String[] args) {
		String dbUrl = System.getenv("DB_URL");
		System.out.println("현재 읽어온 DB_URL: " + dbUrl);
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
