package com.github.fanfever.fever.command.request;

import com.github.fanfever.fever.command.enums.DocumentCommandType;
import lombok.*;
import lombok.experimental.Accessors;

/**
 *
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
class DocumentCommandRequest {

    @NonNull private DocumentCommandType commandType;
    @NonNull private String index;
    @NonNull private String type;
    @NonNull private Integer id;
    private Object document;

    public String getId(){
        return String.valueOf(id);
    }

}
