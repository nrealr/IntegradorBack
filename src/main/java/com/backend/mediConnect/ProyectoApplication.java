package com.backend.mediConnect;

import com.backend.mediConnect.utils.FileTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
@SpringBootApplication
public class ProyectoApplication {

	private static Logger logger = LoggerFactory.getLogger(ProyectoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
		logger.info("MediConnect is now running...");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();

	}

	@Bean
	public Gson gson() {
		return new GsonBuilder()
				.registerTypeAdapter(File.class, new FileTypeAdapter())
				.create();	
	}

}

