package com.github.fanfever.fever.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.fanfever.fever.model.user.User;
import com.github.fanfever.fever.repository.user.command.UserCommandMapper;
import com.github.fanfever.fever.repository.user.query.UserQueryMapper;
import com.github.fanfever.fever.service.user.UserService;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Override
	public User findById(Integer id) {
		return null;

	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Transactional
	public User save(User user) {
		return null;
	}

	@Transactional
	public User update(User user) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		System.out.println(id);
	}

}
