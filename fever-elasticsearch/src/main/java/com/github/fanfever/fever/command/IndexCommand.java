package com.github.fanfever.fever.command;

import com.github.fanfever.fever.command.enums.IndexCommandType;
import com.github.fanfever.fever.command.request.Field;
import com.github.fanfever.fever.command.request.IndexCommandRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * elasticsearch index operation
 * <p>
 * best choose is create index template
 * elasticsearch version 5.2.1
 * <pre>curl -XPUT 'localhost:9200/_template/template_1
 * {@code
 *  "template" : "*", #匹配索引名称
 *  "settings" : {
 *      "analysis": {
 *          "analyzer": {
 *              "ngram_filter": {
 *                  "tokenizer": "ngram_filter"
 *              },
 *              "ik_smart": {
 *                  "tokenizer": "ik_smart"
 *              },
 *              "ik_max_word": {
 *                  "tokenizer": "ik_max_word"
 *              },
 *              "pinyin": {
 *                  "tokenizer": "pinyin_analyzer"
 *              },
 *              "ik_max_word_pinyin": {
 *                  "tokenizer": "ik_smart",
 *                  "filter": ["pinyin", "word_delimiter"]
 *              },
 *              "chinese_sort": {
 *                  "tokenizer": "keyword",
 *                  "filter": "chinese_collator"
 *              },
 *              "comma": {
 *                  "type": "custom",
 *                  "tokenizer": "comma"
 *              }
 *          },
 *          "tokenizer": {
 *              "ngram_filter": {
 *                  "type": "ngram",
 *                  "min_gram": 1,
 *                  "max_gram": 1,
 *                  "token_chars": [
 *                  "letter",
 *                  "digit"
 *              ]
 *              },
 *              "pinyin_analyzer": {
 *                  "type": "pinyin",
 *                  "keep_full_pinyin" : false,
 *                  "keep_joined_full_pinyin" : true
 *              },
 *              "comma": {
 *                  "type": "pattern",
 *                  "pattern": ","
 *              }
 *          },
 *          "filter": {
 *              "pinyin": {
 *                  "type": "pinyin",
 *                  "padding_char": " ",
 *                  "keep_full_pinyin" : false,
 *                  "keep_joined_full_pinyin" : true
 *              },
 *              "chinese_collator": {
 *                  "type": "icu_collation",
 *                  "language": "zh-cn"
 *              }
 *          }
 *      }
 *  },
 *  "aliases" : {
 *
 *  },
 *  "mappings" : {
 *      "_default_" : {
 *          "_all": {
 *              "enabled": false #搜索不包含属性时是否包含所有字段
 *          }
 *      }
 *  }
 * }'</pre>
 *
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2017年5月3日
 */
@Slf4j
@Component
public class IndexCommand {

    private final Client elasticsearchClient;

    @Autowired
    public IndexCommand(@NonNull Client elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    /**
     * 创建更新删除index/mapping
     *
     * @param indexCommandRequest {@link IndexCommandRequest}
     * @return true if the execute should be success; otherwise false
     */
    public boolean execute(final IndexCommandRequest indexCommandRequest) {
        List<IndexCommandRequest> indexCommandRequestList = Lists.newArrayListWithCapacity(1);
        indexCommandRequestList.add(indexCommandRequest);
        return execute(indexCommandRequestList);
    }

    /**
     * 批量创建更新删除index/mapping
     *
     * @param indexCommandRequestList {@link IndexCommandRequest}
     * @return true if the execute should be success; otherwise false
     */
    public boolean execute(final List<IndexCommandRequest> indexCommandRequestList) {
        Assert.notEmpty(indexCommandRequestList, "indexCommandRequestList is null!");
        Set<String> successResultIndexSet = Sets.newHashSetWithExpectedSize(indexCommandRequestList.size());
        boolean result = false;
        for (IndexCommandRequest indexCommandRequest : indexCommandRequestList) {
            switch (indexCommandRequest.getIndexCommandType()) {
                case SAVE_INDEX:
                    result = createIndex(indexCommandRequest);
                    break;
                case SAVE_MAPPING:
                case UPDATE_MAPPING:
                    result = updateMapping(indexCommandRequest);
                    break;
                case DELETE_INDEX:
                    result = deleteIndex(indexCommandRequest);
                    break;
                default:
                    throw new AssertionError("operation is not exists!");
            }
            if (result) {
                successResultIndexSet.add(indexCommandRequest.getIndex());
            }
        }

        if (CollectionUtils.isNotEmpty(successResultIndexSet)) {
            result = 0 == getIndicesAdminClient().prepareRefresh(successResultIndexSet.toArray(new String[successResultIndexSet.size()])).get().getFailedShards();
        }
        return result;
    }

    /**
     * <pre>PUT twitter
     * {@code
     *   "settings" : {
     *     "index" : {
     *       "number_of_shards" : 5, #default
     *       "number_of_replicas" : 1 #default
     *     }
     *   }
     * }</pre>
     *
     * @param indexCommandRequest {@link IndexCommandRequest}
     * @return true if the execute should be success; otherwise false
     */
    private boolean createIndex(final IndexCommandRequest indexCommandRequest) {
        return getIndicesAdminClient().prepareCreate(indexCommandRequest.getIndex()).get().isAcknowledged();
    }

    /**
     * <pre>PUT twitter
     * {@code
     *   "settings" : {
     *     "index" : {
     *       "number_of_shards" : 5, #default
     *       "number_of_replicas" : 1 #default
     *     }
     *   }
     * }</pre>
     *
     * @param indexCommandRequest {@link IndexCommandRequest}
     * @return true if the execute should be success; otherwise false
     */
    private boolean updateMapping(final IndexCommandRequest indexCommandRequest) {
        Assert.notNull(indexCommandRequest, "indexCommandRequest is not null!");
        XContentBuilder source = mappingsWrapper(indexCommandRequest);
        try {
            log.debug("updateMapping index:{}, type:{}, source:{}", indexCommandRequest.getIndex(), indexCommandRequest.getType(), source.string());
            return getIndicesAdminClient().preparePutMapping(indexCommandRequest.getIndex()).setType(indexCommandRequest.getType()).setSource(source).get().isAcknowledged();
        } catch (IOException e) {
            log.error("updateMapping exception:{}", e);
            return false;
        }
    }

    private boolean deleteIndex(final IndexCommandRequest indexCommandRequest) {
        return elasticsearchClient.admin().indices().prepareDelete(indexCommandRequest.getIndex()).get().isAcknowledged();
    }

    private static XContentBuilder mappingsWrapper(final IndexCommandRequest indexCommandRequest) {
        return mappingsPropertiesWrapper(indexCommandRequest);
    }

    /**
     * <pre>PUT twitter
     * {@code
     *   "properties" : {
     *     "id" : {
     *       "type" : "integer"
     *     },
     *     "date" : {
     *       "type" : "date",
     *       "format":"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
     *     },
     *     "name" : {
     *       "type" : "keyword",
     *       "index" : false,
     *       "fields" : {
     *           "raw" : {
     *              "type" : "keyword" #未经处理，可用于排序分组
     *           },
     *           "ngram_filter" : {
     *              "type" : "text",
     *              "analyzer": "ngram_filter" #用于精确短语过滤，不用考虑评分
     *           }
     *           "ik_max_word" : {
     *               "type" : "text",
     *               "analyzer": "ik_max_word" #中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”
     *           },
     *           "pinyin" : { #BETA
     *               "type": "text",
     *               "analyzer": "pinyin_analyzer" #用于拼音搜索
     *           },
     *           "ik_max_word_pinyin": { #BETA
     *               "type": "text",
     *               "analyzer": "ik_pinyin_analyzer" #用于ik拼音搜索
     *           },
     *           "chinese_sort": { #BETA
     *               "type": "text",
     *               "fielddata": true,
     *               "analyzer": "keyword_chinese_collator" #用于中文排序
     *           }
     *       }
     *     },
     *   }
     * }</pre>
     *
     * @param indexCommandRequest {@link IndexCommandRequest}
     * @return a reference to this {@code XContentBuilder} object
     */
    private static XContentBuilder mappingsPropertiesWrapper(final IndexCommandRequest indexCommandRequest) {
        try (XContentBuilder root = XContentFactory.jsonBuilder()) {
            XContentBuilder properties = root.startObject("properties");
            for (int i = 0; i < indexCommandRequest.getFieldList().size(); i++) {
                Field field = indexCommandRequest.getFieldList().get(i);
                XContentBuilder name = properties.startObject(field.getName());
                switch (field.getType()) {
                    case STRING:
                        name.field("type", "keyword");
                        name.field("index", false);
                        XContentBuilder fields = name.startObject("fields");
                        fields.startObject("raw")
                                .field("type", "keyword").endObject();
                        fields.startObject("index_analyzer")
                                .field("type", "text")
                                .field("analyzer", "ngram_index_analyzer").endObject();
                        if (field.isAnalysis()) {
                            fields.startObject("analyzer")
                                    .field("type", "text")
                                    .field("analyzer", "ik_max_word").endObject();
                        } else if (field.isNeedPinyinSearch()) {
                            fields.startObject("ik_max_word_pinyin")
                                    .field("type", "text")
                                    .field("analyzer", "ik_max_word_pinyin").endObject();
                        } else if (field.isSort()) {
                            fields.startObject("chinese_sort")
                                    .field("type", "text")
                                    .field("fielddata", true)
                                    .field("analyzer", "keyword_chinese_collator").endObject();
                        }
                        name.endObject();
                        break;
                    case DATE:
                        name.field("type", "date")
                                .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
                        break;
                    case COMMA:
                        name.field("type", "keyword");
                        name.field("index", false);
                        fields = name.startObject("fields");
                        fields.startObject("raw")
                                .field("type", "keyword").endObject();
                        fields.startObject("index_analyzer")
                                .field("type", "text")
                                .field("analyzer", "comma").endObject();
                        name.endObject();
                        break;
                    default:
                        name.field("type", field.getType().toString().toLowerCase());

                }
                name.endObject();
                properties.endObject().endObject();
            }
            return root;
        } catch (IOException e) {
            log.error("mappingsPropertiesWrapper exception:{}", e);
        }
        return null;
    }

    private IndicesAdminClient getIndicesAdminClient() {
        return elasticsearchClient.admin().indices();
    }

    public static void main(String[] args) {
        List<Field> fieldList = Lists.newArrayListWithCapacity(3);
        final Field nameField = Field.builder().name("name").type(Field.DataType.STRING).build();
        final Field ageField = Field.builder().name("age").type(Field.DataType.INTEGER).build();
        final Field createTimeField = Field.builder().name("createTime").type(Field.DataType.DATE).build();
        fieldList.add(nameField);
        fieldList.add(ageField);
        fieldList.add(createTimeField);
        IndexCommandRequest.of(IndexCommandType.UPDATE_MAPPING, "myIndex", "myType").setFieldList(fieldList);
    }
}
