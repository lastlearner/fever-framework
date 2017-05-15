package com.github.fanfever.fever.item;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月10日
 */
public class User {

	private Integer id;
	private String username;
	private Integer sex;
	private LocalDateTime createTime;
	private Integer version;

	public User(Integer id, String username, Integer sex, long createTime, Integer version) {
		super();
		this.id = id;
		this.username = username;
		this.sex = sex;
		this.createTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(createTime), ZoneId.systemDefault());
		this.version = version;
	}

	public static User build(Integer id, String username, Integer sex, long createTime, Integer version) {
		return new User(id, username, sex, createTime, version);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
