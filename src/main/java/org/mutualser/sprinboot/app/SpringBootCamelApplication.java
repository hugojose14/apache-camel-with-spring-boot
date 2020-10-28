package org.mutualser.sprinboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SpringBootCamelApplication {

	public static void main(String[] args) throws Exception {
	  SpringApplication.run(SpringBootCamelApplication.class, args);

	}

}
