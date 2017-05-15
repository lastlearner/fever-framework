package com.github.fanfever.fever.command.request;

import com.github.fanfever.fever.command.enums.IndexCommandType;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2017年5月3日
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public
class IndexCommandRequest {

    @NonNull private IndexCommandType indexCommandType;
    @NonNull private String index;
    @NonNull private String type;
    private List<Field> fieldList;

    public IndexCommandRequest addField(@NonNull final Field field){
        if(null == fieldList){
            fieldList = Lists.newArrayList();
        }
        fieldList.add(field);
        return this;
    }

}
