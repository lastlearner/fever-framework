package com.github.fanfever.fever.web.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.fanfever.fever.bean.Converter;
import com.github.fanfever.fever.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by fanfever on 2017/4/10.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true, value = "clazz")
@Data
@NoArgsConstructor
public class RequestIdDTO<T extends BaseModel> implements Converter<RequestIdDTO, T> {

    /**
     * ID
     */
    private Integer id;

    private Class<T> clazz;

    public RequestIdDTO(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convert(final RequestIdDTO s) {
        if (null == s) {
            return null;
        }
        T target = null;
        try {
            target = null == clazz ? (T) new BaseModel() : clazz.newInstance();
            if (null != s.getId()) {
                target.setId(s.getId());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("exception:{}", e);
        }
        return target;
    }

    public static <T> T convert(RequestIdDTO s, Class<T> targetClass) {
        return (T) new RequestIdDTO(targetClass).convert(s);
    }

    public static List convert(List<RequestIdDTO> s) {
        return new RequestIdDTO().convertToList(s);
    }

}
