package com.github.fanfever.fever.web.controller.response;

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
public class ResponseIdDTO<S extends BaseModel> implements Converter<S, ResponseIdDTO> {

    /**
     * ID
     */
    private Integer id;

    @Override
    public ResponseIdDTO convert(S s) {
        if (null == s) {
            return null;
        }
        ResponseIdDTO responseIdDTO = new ResponseIdDTO();
        responseIdDTO.setId(s.getId());
        return responseIdDTO;
    }

    public static ResponseIdDTO converts(BaseModel s) {
        return new ResponseIdDTO().convert(s);
    }

    public static List converts(List s) {
        return new ResponseIdDTO().convertToList(s);
    }

}
