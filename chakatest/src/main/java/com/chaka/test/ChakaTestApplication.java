package com.chaka.test;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ChakaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChakaTestApplication.class, args);
	}

}
