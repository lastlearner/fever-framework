package com.github.fanfever.fever.bean;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
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
        if(input instanceof Page){
            PageInfo pageInfo = new PageInfo(input);
            List<T> collect = input.stream().map(this::convert).collect(toList());
            new PageInfo(input).setList(collect);
            return pageInfo.getList();
        }else{
            return input.stream().map(this::convert).collect(toList());
        }
    }
}
