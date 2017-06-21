package com.github.fanfever.fever.writer;

import com.github.fanfever.fever.command.DocumentCommand;
import com.github.fanfever.fever.command.enums.DocumentCommandType;
import com.github.fanfever.fever.command.request.BaseDocument;
import com.github.fanfever.fever.command.request.DocumentCommandRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fanfever on 2017/6/22.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@AllArgsConstructor
public class ElasticSearchItemWriter<T extends BaseDocument> implements ItemWriter<T> {

    private final String INDEX;
    private final String TYPE;
    private final DocumentCommand documentCommand;

    @Override
    public void write(List<? extends T> itemList) throws Exception {
        documentCommand.execute(itemList.stream().map(i -> DocumentCommandRequest.of(DocumentCommandType.UPSERT,INDEX,TYPE, i.getId())).collect(Collectors.toList()));
    }
}
