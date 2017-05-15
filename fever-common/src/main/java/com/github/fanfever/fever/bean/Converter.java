package com.github.fanfever.fever.bean;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by fanfever on 2017/4/1.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */

@FunctionalInterface
public interface Converter<S, T>{

    T convert(S s);

    default List<T> convertToList(final List<S> input) {
        if(CollectionUtils.isEmpty(input)){
            return Collections.emptyList();
        }
        return input.stream().map(this::convert).collect(toList());
    }

}
