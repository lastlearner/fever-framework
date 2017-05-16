package com.github.fanfever.fever.config.process;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.github.fanfever.fever.item.User;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月10日
 */
@Configuration
public class SimpleProcessorConfiguration {

	@Bean
	@StepScope
	public SimpleProcessor simpleProcessor() {
		return new SimpleProcessor();
	}

	public class SimpleProcessor implements ItemProcessor<User, User> {

		@Autowired
		RestTemplate restTemplate;

		@Override
		public User process(User user) throws Exception {
			String message = restTemplate.exchange("http://staging1.udeska1.com/api/hello", HttpMethod.GET,
					new HttpEntity<>(null), String.class).getBody();
			user.setUsername(message);
			return user;
		}

	}
}
