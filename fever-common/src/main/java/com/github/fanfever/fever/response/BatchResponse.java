package com.github.fanfever.fever.response;

import com.github.fanfever.fever.exception.BadRequestException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BatchResponse {

	private List<Integer> successList = Lists.newArrayList();
	private Map<String, String> failMap = Maps.newLinkedHashMap();

	public void successListAdd(int id) {
		successList.add(id);
	}

	public void failMapPut(String id, Exception e) {
		if(StringUtils.isNoneBlank(e.getMessage())){
			failMap.put(id, e.getMessage());
		}else if(e instanceof BadRequestException){
			failMap.put(id, ((BadRequestException) e).getMessageList().get(0));
		}
	}

	public void transMessage(MessageSource messageSource, Locale locale){
		failMap.forEach((k, v) -> {
			failMap.put(k, messageSource.getMessage(v, null, locale));
		});
	}

	public Map<String, String> getFailMap() {
		return failMap;
	}

	public int getTotal() {
		return successList.size() + failMap.size();
	}

	public int getSuccessTotal() {
		return successList.size();
	}

	public int getFailTotal() {
		return failMap.size();
	}

	public List<Integer> getSuccessList() {
		return successList;
	}

	public void setSuccessList(List<Integer> successList) {
		this.successList = successList;
	}

	public void setFailMap(Map<String, String> failMap) {
		this.failMap = failMap;
	}

}
