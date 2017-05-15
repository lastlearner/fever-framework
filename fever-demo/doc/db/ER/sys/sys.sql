SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Indexes */

DROP INDEX sys_mdict_parent_id ON sys_dict;
DROP INDEX sys_mdict_parent_ids ON sys_dict;
DROP INDEX sys_mdict_del_flag ON sys_dict;
DROP INDEX sys_menu_parent_id ON sys_menu;
DROP INDEX sys_menu_parent_ids ON sys_menu;
DROP INDEX sys_menu_del_flag ON sys_menu;



/* Drop Tables */

DROP TABLE IF EXISTS sys_area;
DROP TABLE IF EXISTS sys_dict;
DROP TABLE IF EXISTS sys_menu;
DROP TABLE IF EXISTS sys_office;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_role_menu;
DROP TABLE IF EXISTS sys_role_office;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_user_role;




/* Create Tables */

-- 区域表
CREATE TABLE sys_area
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	parent_id int(11) COMMENT '父级ID',
	parent_ids varchar(2000) COMMENT '所有父级ID',
	name varchar(32) COMMENT '名称',
	orders tinyint(2) COMMENT '排序顺序',
	-- 创建用户ID
	create_user_id int(11) NOT NULL COMMENT '创建用户ID : 创建用户ID',
	-- 创建时间
	create_time datetime NOT NULL COMMENT '创建时间 : 创建时间',
	update_user_id int(11) COMMENT '更新用户ID',
	-- 更新时间
	update_time datetime COMMENT '更新时间 : 更新时间',
	-- 备注
	remark varchar(255) COMMENT '备注 : 备注',
	delete_flag tinyint(1) NOT NULL COMMENT '删除标签(0未删除,1已删除)',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '区域表';


-- 字典表 : 多级字典表
CREATE TABLE sys_dict
(
	-- 编号
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID : 编号',
	parent_id int(11) COMMENT '父级ID',
	parent_ids varchar(2000) COMMENT '所有父级ID',
	name varchar(32) COMMENT '名称',
	orders tinyint(2) COMMENT '排序顺序',
	company_id int(11) COMMENT '公司ID',
	label varchar(32) COMMENT '标签',
	value varchar(32) COMMENT '值',
	type varchar(32) COMMENT '类型',
	-- 描述
	description varchar(100) COMMENT '描述 : 描述',
	-- 创建用户ID
	create_user_id int(11) NOT NULL COMMENT '创建用户ID : 创建用户ID',
	-- 创建时间
	create_time datetime NOT NULL COMMENT '创建时间 : 创建时间',
	update_user_id int(11) COMMENT '更新用户ID',
	-- 更新时间
	update_time datetime COMMENT '更新时间 : 更新时间',
	-- 备注
	remark varchar(255) COMMENT '备注 : 备注',
	delete_flag tinyint(1) NOT NULL COMMENT '删除标签(0未删除,1已删除)',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '字典表 : 多级字典表';


-- 菜单表 : 菜单表
CREATE TABLE sys_menu
(
	-- 编号
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID : 编号',
	parent_id int(11) COMMENT '父级ID',
	parent_ids varchar(2000) COMMENT '所有父级ID',
	name varchar(32) COMMENT '名称',
	orders tinyint(2) COMMENT '排序顺序',
	-- 链接
	href varchar(2000) COMMENT '链接 : 链接',
	-- 目标（mainFrame、 _blank、_self、_parent、_top）
	target varchar(20) COMMENT '目标 : 目标（mainFrame、 _blank、_self、_parent、_top）',
	-- 是否在菜单中显示
	is_show tinyint NOT NULL COMMENT '是否在菜单中显示 : 是否在菜单中显示',
	-- 权限标识
	permission varchar(200) COMMENT '权限标识 : 权限标识',
	-- 创建用户ID
	create_user_id int(11) NOT NULL COMMENT '创建用户ID : 创建用户ID',
	-- 创建时间
	create_time datetime NOT NULL COMMENT '创建时间 : 创建时间',
	update_user_id int(11) COMMENT '更新用户ID',
	-- 更新时间
	update_time datetime COMMENT '更新时间 : 更新时间',
	-- 备注
	remark varchar(255) COMMENT '备注 : 备注',
	delete_flag tinyint(1) NOT NULL COMMENT '删除标签(0未删除,1已删除)',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '菜单表 : 菜单表';


-- 机构
CREATE TABLE sys_office
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	area_id int(11) COMMENT '区域ID',
	parent_id int(11) COMMENT '父级ID',
	parent_ids varchar(2000) COMMENT '所有父级ID',
	name varchar(32) COMMENT '名称',
	orders tinyint(2) COMMENT '排序顺序',
	-- 创建用户ID
	create_user_id int(11) NOT NULL COMMENT '创建用户ID : 创建用户ID',
	-- 创建时间
	create_time datetime NOT NULL COMMENT '创建时间 : 创建时间',
	update_user_id int(11) COMMENT '更新用户ID',
	-- 更新时间
	update_time datetime COMMENT '更新时间 : 更新时间',
	-- 备注
	remark varchar(255) COMMENT '备注 : 备注',
	delete_flag tinyint(1) NOT NULL COMMENT '删除标签(0未删除,1已删除)',
	PRIMARY KEY (id)
) COMMENT = '机构';


-- 角色表
CREATE TABLE sys_role
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	office_id int(11) COMMENT '机构ID',
	name varchar(32) COMMENT '角色名称',
	data_scope int(11) COMMENT '数据范围',
	-- 创建用户ID
	create_user_id int(11) NOT NULL COMMENT '创建用户ID : 创建用户ID',
	-- 创建时间
	create_time datetime NOT NULL COMMENT '创建时间 : 创建时间',
	update_user_id int(11) COMMENT '更新用户ID',
	-- 更新时间
	update_time datetime COMMENT '更新时间 : 更新时间',
	-- 备注
	remark varchar(255) COMMENT '备注 : 备注',
	delete_flag tinyint(1) NOT NULL COMMENT '删除标签(0未删除,1已删除)',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '角色表';


-- 角色菜单关系
CREATE TABLE sys_role_menu
(
	role_id int(11) NOT NULL COMMENT '角色ID',
	menu_id int(11) NOT NULL COMMENT '菜单ID',
	PRIMARY KEY (role_id, menu_id)
) COMMENT = '角色菜单关系';


-- 角色机构关系
CREATE TABLE sys_role_office
(
	role_id int(11) NOT NULL COMMENT '角色ID',
	menu_id int(11) NOT NULL COMMENT '菜单ID',
	PRIMARY KEY (role_id, menu_id)
) COMMENT = '角色机构关系';


-- 用户表
CREATE TABLE sys_user
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	-- 真实姓名
	realname varchar(32) COMMENT '真实姓名 : 真实姓名',
	-- 邮箱
	email varchar(32) COMMENT '电子邮箱 : 邮箱',
	mobile varchar(32) COMMENT '手机号',
	username varchar(32) COMMENT '用户名',
	password varchar(32) COMMENT '密码',
	company_id int(11) COMMENT '公司ID',
	office_id int(11) COMMENT '部门ID',
	-- 创建用户ID
	create_user_id int(11) NOT NULL COMMENT '创建用户ID : 创建用户ID',
	-- 创建时间
	create_time datetime NOT NULL COMMENT '创建时间 : 创建时间',
	update_user_id int(11) COMMENT '更新用户ID',
	-- 更新时间
	update_time datetime COMMENT '更新时间 : 更新时间',
	-- 备注
	remark varchar(255) COMMENT '备注 : 备注',
	delete_flag tinyint(1) NOT NULL COMMENT '删除标签(0未删除,1已删除)',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '用户表';


-- 用户角色关系
CREATE TABLE sys_user_role
(
	menu_id int(11) NOT NULL COMMENT '菜单ID',
	role_id int(11) NOT NULL COMMENT '角色ID',
	PRIMARY KEY (menu_id, role_id)
) COMMENT = '用户角色关系';



/* Create Indexes */

CREATE INDEX sys_mdict_parent_id ON sys_dict ();
CREATE INDEX sys_mdict_parent_ids ON sys_dict ();
CREATE INDEX sys_mdict_del_flag ON sys_dict ();
CREATE INDEX sys_menu_parent_id ON sys_menu ();
CREATE INDEX sys_menu_parent_ids ON sys_menu ();
CREATE INDEX sys_menu_del_flag ON sys_menu ();



