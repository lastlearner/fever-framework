SET SESSION FOREIGN_KEY_CHECKS=0;
DROP TABLE batch_job_execution;
DROP TABLE batch_job_execution_context;
DROP TABLE batch_job_execution_params;
DROP TABLE batch_job_execution_seq;
DROP TABLE batch_job_instance;
DROP TABLE batch_job_seq;
DROP TABLE batch_step_execution;
DROP TABLE batch_step_execution_context;
DROP TABLE batch_step_execution_seq;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	sex tinyint(1) COMMENT '性别',
	username varchar(32) COMMENT '姓名',
	create_time datetime COMMENT '创建时间',
	version int(11) COMMENT '版本',
	PRIMARY KEY (id)
) COMMENT = '用户测试表';

INSERT INTO user VALUES (1, 1, 'tiger', NOW(), 1);
INSERT INTO user VALUES (2, 1, 'cat', NOW(), 1);
INSERT INTO user VALUES (3, 1, 'dog', NOW(), 1);
INSERT INTO user VALUES (4, 1, 'parda', NOW(), 1);
INSERT INTO user VALUES (5, 2, 'bid', NOW(), 1);
INSERT INTO user VALUES (6, 2, 'other', NOW(), 1);
