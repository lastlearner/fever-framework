package com.github.fanfever.fever.writer;

import com.github.fanfever.fever.command.enums.DocumentCommandType;
import com.github.fanfever.fever.command.request.DocumentCommandRequest;
import com.github.fanfever.fever.dto.BaseDocument;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by fanfever on 2017/6/22.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
public class ElasticSearchItemWriter<T> implements ItemWriter<T>, InitializingBean {

    private String INDEX;
    private String TYPE;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void write(List<? extends T> itemList) throws Exception {
//        itemList.stream().map(i -> DocumentCommandRequest.of(DocumentCommandType.UPSERT,INDEX,TYPE,i.getId()));
    }
}
