package com.github.fanfever.fever.web.controller.user.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.fanfever.fever.model.user.User;
import com.github.fanfever.fever.service.user.UserService;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{id:^[1-9]\\d*$}", method = RequestMethod.GET)
	public ResponseEntity<User> findById(@PathVariable(value = "id") int id) {
		User user = userService.findById(id);
		if (null == user) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAll() {
		List<User> userList = userService.findAll();
		if (userList.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody User user) {
		if (null != user.getId() && userService.isExist(user.getId())) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		userService.save(user);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build().toUri());
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id:^[1-9]\\d*$}", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable(value = "id") int id, @RequestBody User user) {
		if (!userService.isExist(user.getId())) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		userService.update(user);
		user = userService.findById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id:^[1-9]\\d*$}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable(value = "id") int id) {
		if (!userService.isExist(id)) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
