package com.github.fanfever.fever.config.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.fanfever.fever.config.process.SimpleProcessorConfiguration.SimpleProcessor;
import com.github.fanfever.fever.item.User;
import com.github.fanfever.fever.listener.JobCompletionNotificationListener;
import com.github.fanfever.fever.listener.StepRetryListener;
import com.github.fanfever.fever.listener.StepSkipListener;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月10日
 */
@Configuration
public class SimpleJobConfiguration extends BaseJobConfiguration {

	@Autowired
	@Qualifier("simpleReader")
	private JdbcPagingItemReader<User> reader;

	@Autowired
	private SimpleProcessor processor;

	@Autowired
	@Qualifier("simpleWriter")
	private JdbcBatchItemWriter<User> writer;

	@Bean
	public Job simpleJob() {
		return jobBuilderFactory.get("simpleJob").incrementer(new RunIdIncrementer())
				.listener(new JobCompletionNotificationListener()).flow(simpleJobStep1()).end().build();
	}

	@Bean
	public Step simpleJobStep1() {
		return stepBuilderFactory.get("simpleJobStep1").<User, User>chunk(10).faultTolerant().retry(Exception.class)
				.retryLimit(2).listener(new StepRetryListener()).skip(Exception.class).skipLimit(100)
				.listener(new StepSkipListener<>()).reader(reader).processor(processor).writer(writer).build();
	}

}
