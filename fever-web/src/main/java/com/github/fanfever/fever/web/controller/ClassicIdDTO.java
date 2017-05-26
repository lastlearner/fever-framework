package com.github.fanfever.fever.web.controller;

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
public class ClassicIdDTO<T extends BaseModel> implements Converter<ClassicIdDTO, T> {

    private Integer id;

    private Class<T> clazz;

    public ClassicIdDTO(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convert(final ClassicIdDTO s) {
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

    public static <T> T convert(ClassicIdDTO s, Class<T> targetClass) {
        return (T) new ClassicIdDTO(targetClass).convert(s);
    }

    public static List convert(List<ClassicIdDTO> s) {
        return new ClassicIdDTO().convertToList(s);
    }

}
