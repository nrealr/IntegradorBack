package com.backend.mediConnect;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProyectoApplication {

	private static Logger logger = LoggerFactory.getLogger(ProyectoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
		logger.info("MedicCare is now running...");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();

	}

}

