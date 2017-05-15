package com.github.fanfever.fever.repository.user.query;

import java.util.List;

import com.github.fanfever.fever.model.user.User;
import com.github.fanfever.fever.repository.BaseQueryRepository;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public interface UserQueryMapper extends BaseQueryRepository<Integer, User> {

	User findById(Integer id);

	List<User> findAll();
}
