package com.github.fanfever.fever.model.sys;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.github.fanfever.fever.model.BaseModel;
import com.google.common.collect.Lists;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public class User extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Office company;
	private Office office;
	private String username;
	private String password;
	private Integer type;
	private String realname;
	private String email;
	private String mobile;
	private String loginIp;
	private LocalDateTime loginTime;
	private Integer loginFlag;

	private List<Role> roleList = Lists.newArrayList();

	public boolean isAdmin() {
		return null != this.getId() && 1 == this.getId().intValue();
	}

	@NotNull(message = "validate.sys.user.realname.notNull")
	@Pattern(regexp = "[\u4e00-\u9fa5]{2,4}", message = "validate.sys.user.realname.format")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@NotNull(message = "validate.sys.user.email.notNull")
	@Email(message = "validate.sys.user.email.format")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull(message = "validate.sys.user.mobile.notNull")
	@Pattern(regexp = "^1[3-9]\\d{9}$", message = "validate.sys.user.mobile.format")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@NotNull(message = "validate.sys.user.username.notNull")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull(message = "validate.sys.user.password.notNull")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(message = "validate.sys.user.company.notNull")
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	@NotNull(message = "validate.sys.user.office.notNull")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

}
