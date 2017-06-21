package com.github.fanfever.fever.writer;

import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by fanfever on 2017/6/22.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
public class ElasticSearchItemWriter<T> implements ItemWriter<T>{

    @Override
    public void write(List<? extends T> list) throws Exception {
        List<DocumentCommandRequest> requests = Lists.newArrayList();
        for (DataData dataData : list) {
            DocumentCommandRequest commandRequest =
                    DocumentCommandRequest.of(DocumentCommandType.UPDATE,"dataDatas","dataData",dataData.getId());
            commandRequest.setDocument(dataData);
            requests.add(commandRequest);
        }
        documentCommand.execute(requests);
    }
}
