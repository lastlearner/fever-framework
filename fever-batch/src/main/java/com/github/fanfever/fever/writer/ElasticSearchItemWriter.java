package com.github.fanfever.fever.writer;

import com.github.fanfever.fever.command.DocumentCommand;
import com.github.fanfever.fever.command.enums.DocumentCommandType;
import com.github.fanfever.fever.command.request.DocumentCommandRequest;
import com.github.fanfever.fever.dto.BaseDocument;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fanfever on 2017/6/22.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Component
@AllArgsConstructor
public class ElasticSearchItemWriter implements ItemWriter<BaseDocument> {

    private final DocumentCommand documentCommand;

    @Override
    public void write(List<? extends BaseDocument> itemList) throws Exception {
        documentCommand.execute(itemList.stream().map(i -> DocumentCommandRequest.of(DocumentCommandType.UPSERT,i.getIndex(),i.getType(),i.getId()).setDocument(i)).collect(Collectors.toList()));
    }
}
