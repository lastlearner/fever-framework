package com.github.fanfever.fever.condition.wrapperHandler;

import com.github.fanfever.fever.condition.request.BaseConditionRequest;

/**
 * Created by fanfever on 2017/5/6.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */

@FunctionalInterface
public interface ConditionWrapperHandle<T> {

    T exec(BaseConditionRequest condition);

}
