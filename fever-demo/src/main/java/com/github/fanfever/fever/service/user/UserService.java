package com.github.fanfever.fever.service.user;

import java.util.List;

import com.github.fanfever.fever.model.user.User;
import com.github.fanfever.fever.service.BaseService;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public interface UserService extends BaseService<Integer, User> {

	List<User> findAll();

}
