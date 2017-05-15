package com.github.fanfever.fever.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"deleteFlag", "sqlMap", "page"})
public class BaseModel implements Serializable {

//	@JsonView(View.Basic.class)
	protected Integer id;
	protected Integer createUserId;
	protected String createUserUsername;
	protected LocalDateTime createTime;
	protected Integer updateUserId;
	protected String updateUserUsername;
	protected LocalDateTime updateTime;
	protected String remark;
	protected Integer deleteFlag;

	protected transient Map<String, String> sqlMap;
	private transient String page;
	// 语言代码（查询参数）
	protected transient String languageCode;

	public BaseModel() {
		super();
		this.deleteFlag = DeleteFlag.NORMAL.getValue();
	}

	public <T extends BaseModel> T clean() {
		try {
			id = this.getId();
			T t = (T) this.getClass().newInstance();
			t.setId(id);
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("clean exception:{}", e);
		}
		return null;
	}

	public void addSqlMap(String key, String value) {
		this.addSqlMap(key, value, 1);
	}

	public void addSqlMap(String key, String value, Integer expectedSize) {
		if (null == sqlMap) {
			sqlMap = Maps.newHashMapWithExpectedSize(expectedSize);
		}
		sqlMap.put(key, value);
	}

	public enum DeleteFlag {
		NORMAL(0), DELETE(1);

		private int value;

		DeleteFlag(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
