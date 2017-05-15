package com.github.fanfever.fever.web.controller.response;

import lombok.Data;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Data
public class BaseResponse {

	protected boolean isSuccess = false;
	protected String message;
	protected String msg;
	protected String showMsg;

}
