package com.github.fanfever.fever.command.request;


import lombok.Builder;
import lombok.Data;

/**
 * Created by user on 2017/2/28.
 */
@Data
@Builder
public
class Field {

    private String name;
    private DataType type;
    private boolean isAnalysis = false;
    private boolean isNeedPinyinSearch = false;
    private boolean isSort = false;

    public enum DataType {
        STRING, INTEGER, FLOAT, DATE, ARRAY, COMMA, DOUBLE;
    }
}
