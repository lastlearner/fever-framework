/*CREATE DATABASE IF NOT EXISTS test DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
 Drop Tables */

DROP TABLE IF EXISTS user;


/* Create Tables */

-- 用户表
CREATE TABLE user
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	username varchar(32) COMMENT '名称',
	sex tinyint(1) COMMENT '编码',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	version int(5),
	PRIMARY KEY (id)
) COMMENT = '用户表';

INSERT INTO user VALUES (1, '张三', 1, NOW(), NULL);
INSERT INTO user VALUES (2, '李四', 1, NOW(), NULL);

