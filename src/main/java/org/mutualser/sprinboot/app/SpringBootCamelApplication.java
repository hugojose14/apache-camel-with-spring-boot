package org.mutualser.sprinboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableAutoConfiguration
@SpringBootApplication
@EnableJms
public class SpringBootCamelApplication {

	public static void main(String[] args) throws Exception {
	  SpringApplication.run(SpringBootCamelApplication.class, args);

	}

}
