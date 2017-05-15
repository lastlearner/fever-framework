package com.github.fanfever.fever.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Configuration
public class JacksonConfiguration {

	@Bean
	public ObjectMapper JavaTimeModule() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.registerModule(new JavaTimeModule());
	}

}
