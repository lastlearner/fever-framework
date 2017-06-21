package com.github.fanfever.fever.dto;

import lombok.Data;

/**
 * Created by fanfever on 2017/6/22.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Data
public abstract class BaseDocument {

    private Integer id;
    private String index;
    private String type;
}
