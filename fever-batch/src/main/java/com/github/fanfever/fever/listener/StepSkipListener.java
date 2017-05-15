package com.github.fanfever.fever.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.SkipListenerSupport;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年8月10日
 */
public class StepSkipListener<T, S> extends SkipListenerSupport<T, S> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onSkipInRead(Throwable t) {
		logger.error("onSkipInRead", t);
		super.onSkipInRead(t);
	}

	@Override
	public void onSkipInWrite(S item, Throwable t) {
		logger.error("onSkipInWrite item:{}", item);
		logger.error("onSkipInWrite", t);
		super.onSkipInWrite(item, t);
	}

	@Override
	public void onSkipInProcess(T item, Throwable t) {
		logger.error("onSkipInProcess item:{}", item);
		logger.error("onSkipInProcess", t);
		super.onSkipInProcess(item, t);
	}

}
