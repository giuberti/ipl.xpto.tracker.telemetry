package com.ipl.xpto.trackingTelemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.ipl.xpto.trackingTelemetry"})
@EntityScan("com.ipl.xpto.trackingTelemetry")
@EnableJpaRepositories("com.ipl.xpto.trackingTelemetry")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
