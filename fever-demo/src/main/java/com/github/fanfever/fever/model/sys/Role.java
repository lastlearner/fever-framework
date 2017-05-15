package com.github.fanfever.fever.model.sys;

import java.util.List;

import com.github.fanfever.fever.model.BaseModel;
import com.google.common.collect.Lists;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public class Role extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Office office;
	private String name;
	private Integer type;
	private Integer dataScope;
	private User user;
	private List<Menu> menuList = Lists.newArrayList();

	// 数据范围（1:所有数据, 2:所在公司及以下数据, 3:所在公司数据, 4:所在部门及以下数据, 5:所在部门数据, 8:仅本人数据）
	public static final Integer DATA_SCOPE_ALL = 1;
	public static final Integer DATA_SCOPE_COMPANY_AND_CHILD = 2;
	public static final Integer DATA_SCOPE_COMPANY = 3;
	public static final Integer DATA_SCOPE_OFFICE_AND_CHILD = 4;
	public static final Integer DATA_SCOPE_OFFICE = 5;
	public static final Integer DATA_SCOPE_SELF = 8;

	public Role() {
		super();
		this.dataScope = DATA_SCOPE_SELF;
	}

	public Role(User user) {
		this();
		this.user = user;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDataScope() {
		return dataScope;
	}

	public void setDataScope(Integer dataScope) {
		this.dataScope = dataScope;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

}
