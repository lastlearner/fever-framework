SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS business_log;
DROP TABLE IF EXISTS niche_niche;




/* Create Tables */

-- 业务日志
CREATE TABLE business_log
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	company_id int(11) COMMENT '公司ID',
	business_type tinyint(2) COMMENT '业务类型',
	business_id int(11) COMMENT '业务ID',
	business_content varchar(128) COMMENT '业务内容',
	operation_user_id int(11) COMMENT '操作用户ID',
	operation_user_name varchar(32) COMMENT '操作用户姓名',
	message varchar(128) COMMENT '消息',
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
) COMMENT = '业务日志';


-- 商机
CREATE TABLE niche_niche
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	customer_id int(11) COMMENT '客户ID',
	charge_user_id int(11) COMMENT '负责人ID',
	name varchar(32) COMMENT '名称',
	sale_process tinyint(2) COMMENT '销售流程',
	pre_sale_amount decimal(10,2) COMMENT '预计销售金额',
	pre_sign_date date COMMENT '预计签单日期',
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
) COMMENT = '商机';



