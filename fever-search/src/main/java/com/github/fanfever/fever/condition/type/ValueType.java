package com.github.fanfever.fever.condition.type;

import lombok.NonNull;

/**
 * Created by fanfever on 2017/6/19.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
public enum ValueType {
    TEXT, LONG_TEXT, RICH_TEXT, NUMERIC, TIME, ARRAY;

    public static boolean isText(@NonNull ValueType valueType){
        return valueType.equals(TEXT) || valueType.equals(LONG_TEXT) || valueType.equals(RICH_TEXT);
    }
}
