package com.github.fanfever.fever.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Configuration
public class RestConfiguration {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
