package org.pah_monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.pah_monitoring.main")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
