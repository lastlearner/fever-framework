package com.github.fanfever.fever.model.sys;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.github.fanfever.fever.model.BaseModel;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public class Menu extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Menu parent;
	private String parentIds;
	private String name;
	private Integer orders;
	private String href;
	private String target;
	private Integer isShow;
	private String permission;

	public Integer getParentId() {
		return null != parent && null != parent.getId() ? parent.getId() : 0;
	}

	@NotNull(message = "validate.sys.menu.parent.notNull")
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@Length(min = 1, max = 2000, message = "validate.sys.menu.parentIds.size")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Length(min = 1, max = 100, message = "validate.sys.menu.name.size")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@Length(min = 0, max = 2000, message = "validate.sys.menu.href.size")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min = 0, max = 20, message = "validate.sys.menu.target.size")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@NotNull(message = "validate.sys.menu.isShow.notNull")
    @Min(value = 0, message = "validate.sys.menu.isShow.size")
    @Max(value = 1, message = "validate.sys.menu.isShow.size")
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Length(min = 0, max = 200, message = "validate.sys.menu.permission.size")
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
