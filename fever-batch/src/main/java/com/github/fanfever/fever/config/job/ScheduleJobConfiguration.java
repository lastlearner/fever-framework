package com.github.fanfever.fever.config.job;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.fanfever.fever.config.process.ScheduleProcessorConfiguration.ScheduleProcessor;
import com.github.fanfever.fever.item.User;
import com.github.fanfever.fever.listener.JobCompletionNotificationListener;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月10日
 */
//@Component
public class ScheduleJobConfiguration extends BaseJobConfiguration {

//	@Autowired
//	private JobLauncher jobLauncher;
//
//	@Autowired
//	@Qualifier("scheduleReader")
//	private JdbcPagingItemReader<User> reader;
//
//	@Autowired
//	private ScheduleProcessor processor;
//
//	@Autowired
//	@Qualifier("scheduleWriter")
//	private JdbcBatchItemWriter<User> writer;
//
////	@Scheduled(fixedDelay = 9999)
//	public void run() throws JobExecutionAlreadyRunningException, JobRestartException,
//			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//		JobParametersBuilder builder = new JobParametersBuilder();
//		builder.addDate("date", new Date());
//		jobLauncher.run(scheduleJob(), builder.toJobParameters());
//	}
//
//	private Job scheduleJob() {
//		return jobBuilderFactory.get("scheduleJob").incrementer(new RunIdIncrementer()).preventRestart()
//				.flow(scheduleJobStep1()).end().build();
//	}
//
//	private Step scheduleJobStep1() {
//		return stepBuilderFactory.get("simpleJobStep1").listener(new JobCompletionNotificationListener())
//				.<User, User>chunk(10).reader(reader).processor(processor).writer(writer).build();
//	}

}
