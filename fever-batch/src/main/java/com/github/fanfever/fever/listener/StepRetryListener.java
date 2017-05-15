package com.github.fanfever.fever.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月10日
 */
public class StepRetryListener extends RetryListenerSupport {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		logger.error("onError retryCount:{}", context.getRetryCount());
		logger.error("onError", throwable);
		super.onError(context, callback, throwable);
	}

}
