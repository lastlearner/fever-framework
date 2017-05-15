package com.github.fanfever.fever.config.reader;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.fanfever.fever.item.User;
import com.google.common.collect.Maps;

/**
 * 
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月5日
 * 
 *       JpaPagingItemReader
 * @see https://github.com/muumin/spring-boot-batch-sample
 */
@Configuration
public class SimpleReaderConfiguration {

	@Autowired
	public DataSource dataSource;

	@Bean
	@StepScope
	public JdbcPagingItemReader<User> simpleReader() {
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, username, sex, create_time, version");
		queryProvider.setFromClause("user");
		queryProvider.setWhereClause("sex = 1");
		Map<String, Order> sortKeyMap = Maps.newHashMap();
		sortKeyMap.put("id", Order.ASCENDING);
		queryProvider.setSortKeys(sortKeyMap);

		JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<User>();
		reader.setDataSource(dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper((rs, rowNum) -> User.build(rs.getInt("id"), rs.getString("username"), rs.getInt("sex"),
				rs.getTimestamp("create_time").getTime(), rs.getInt("version")));
		reader.setQueryProvider(queryProvider);
		return reader;
	}
}
