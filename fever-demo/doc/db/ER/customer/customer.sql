SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS cust_customer;




/* Create Tables */

-- 客户表
CREATE TABLE cust_customer
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	realname varchar(32) COMMENT '客户姓名',
	telephone int(11) COMMENT '电话',
	mobile varchar(32) COMMENT '手机',
	email varchar(32) COMMENT '邮箱',
	charge_user_id int(11) COMMENT '负责人',
	classification int(2) COMMENT '客户级别',
	province int(11) COMMENT '省',
	city int(11) COMMENT '市',
	district int(11) COMMENT '区',
	address varchar(128) COMMENT '地址',
	url varchar(32) COMMENT '网址',
	sld varchar(32) COMMENT '二级域名',
	agent_count int(32) COMMENT '座席数',
	focus varchar(128) COMMENT '关注点IDs',
	source_channel varchar(128) COMMENT '来源渠道IDs',
	qq_id varchar(32) COMMENT 'QQID',
	weibo_id varchar(32) COMMENT '微博ID',
	weixin_id varchar(32) COMMENT '微信ID',
	sign_date date COMMENT '成单日期',
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
) COMMENT = '客户表';



