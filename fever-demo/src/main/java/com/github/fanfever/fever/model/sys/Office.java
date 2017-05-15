package com.github.fanfever.fever.model.sys;

import com.github.fanfever.fever.model.BaseModel;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public class Office extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Area area;
	private Office parent;
	private String parentIds;
	private Integer masterUserId;
	private String name;
	private Integer type;
	private Integer grade;
	private Integer orders;

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Office getParent() {
		return parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Integer getMasterUserId() {
		return masterUserId;
	}

	public void setMasterUserId(Integer masterUserId) {
		this.masterUserId = masterUserId;
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

}
