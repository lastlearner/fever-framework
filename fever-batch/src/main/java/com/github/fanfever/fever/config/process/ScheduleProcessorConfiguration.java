package com.github.fanfever.fever.config.process;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.fanfever.fever.item.User;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月10日
 */
@Configuration
public class ScheduleProcessorConfiguration {

	@Bean
	@StepScope
	public ScheduleProcessor ScheduleProcessor() {
		return new ScheduleProcessor();
	}

	public class ScheduleProcessor implements ItemProcessor<User, User> {

		@Override
		public User process(User user) throws Exception {
			user.setVersion(user.getVersion() + 1);
			return user;
		}

	}
}
