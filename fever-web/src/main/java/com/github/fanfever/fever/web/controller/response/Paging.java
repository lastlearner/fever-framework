package com.github.fanfever.fever.web.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by fanfever on 2017/5/26.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@AllArgsConstructor
@Data
public class Paging {
    private int pageNum;
    private int pageSize;
    private long total;
}
