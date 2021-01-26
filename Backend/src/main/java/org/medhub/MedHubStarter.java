package org.medhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.medhub")
public class MedHubStarter {
	public static void main(String[] args) {
		SpringApplication.run(MedHubStarter.class, args);
	}

}
