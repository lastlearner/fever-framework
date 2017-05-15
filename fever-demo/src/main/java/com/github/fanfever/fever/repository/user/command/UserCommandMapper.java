package com.github.fanfever.fever.repository.user.command;

import com.github.fanfever.fever.model.user.User;
import com.github.fanfever.fever.repository.BaseCommandRepository;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public interface UserCommandMapper extends BaseCommandRepository<Integer, User> {

	void save(User user);

	void update(User user);
}
