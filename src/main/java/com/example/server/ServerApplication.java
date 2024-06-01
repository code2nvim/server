package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
        // clear
        System.out.print("\033c");
		SpringApplication.run(ServerApplication.class, args);
	}

}
