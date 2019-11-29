package com.tieto.springbootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class SpringbootadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootadminApplication.class, args);
	}

}
