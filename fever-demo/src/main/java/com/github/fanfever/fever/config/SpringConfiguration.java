package com.github.fanfever.fever.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Configuration
public class SpringConfiguration {

	@Bean
	public com.github.fanfever.fever.util.SpringContextHolder SpringContextHolder() {
		return new com.github.fanfever.fever.util.SpringContextHolder();

	}
}
