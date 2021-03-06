# Metrics
```json
##集群健康度
curl 'localhost:9200/_cat/health?v'
##获取所有节点列表
curl 'localhost:9200/_cat/nodes?v'
```
# Index
## QUERY
```json
##GET ALL INDEX
curl 'localhost:9200/_cat/indices?v'

##GET ONE INDEX
curl 'localhost:9200/myindex/_mapping?pretty'

##DELETE
curl -XDELETE 'localhost:9200/myindex'
```
## Index Templates
```json
##GET
curl localhost:9200/_template

##DELETE
curl -XDELETE 'localhost:9200/_template/template_1'

##POST
curl -XPUT 'localhost:9200/_template/template_1
{
  "template": "test*", #匹配索引名称
  "settings": {
    "analysis": {
        "analyzer": {
          "ik_smart": {
            "tokenizer": "ik_smart"
          },
          "ik_max_word": {
            "tokenizer": "ik_max_word"
          },
          "pinyin": {
            "tokenizer": "pinyin_analyzer"
          },
          "ik_max_word_pinyin": {
            "tokenizer": "ik_smart",
            "filter": ["pinyin", "word_delimiter"]
          },
          "chinese_sort": {
            "tokenizer": "keyword",
            "filter": "chinese_collator"
          },
          "comma": {
            "type": "custom",
            "tokenizer": "comma"
          }
        },
        "tokenizer": {
          "pinyin_analyzer": {
            "type": "pinyin",
            "keep_full_pinyin" : false,
            "keep_joined_full_pinyin" : true
          },
          "comma": {
            "type": "pattern",
            "pattern": ","
          }
        },
        "filter": {
          "pinyin": {
            "type": "pinyin",
            "padding_char": " ",
            "keep_full_pinyin" : false,
            "keep_joined_full_pinyin" : true
          },
          "chinese_collator": {
            "type": "icu_collation",
            "language": "zh-cn"
          }
        }
    }
  },
  "aliases": {

  },
  "mappings":{
    "_default_": {
      "_all": {
        "enabled": false #搜索不包含属性时是否包含所有字段
      }
    }
  }
}'
```
## Mapping
```json
##GET
curl -XDELETE 'localhost:9200'
dynamic: false
```

## Document
```json
curl -XPOST 'localhost:9200/index/_search?pretty' -d ' { "query": { "match_all": {} } }'
```

## Analyzer(分析器) flow
1. 文本流 <p>Small is Big<p>
2. charFilter 0～1个 字符级别过滤HTML标签
3. tokenizer 1个 基于规则或语义切分 Small, is, Big
4. tokenfilter 0到n个 禁用词，同义词，大小写 small,young,big,old

Character Filter(字符过滤器)去掉里面的HTML标记等 -》
Tokenizer(分词器)提取多个(Token)词源 -》
Token Filter(词元处理器)转成小写等处理为Term(词)，文档中包含了几个这样的Term被称为Frequency(词频)。 引擎会建立Term和原文档的Inverted Index(倒排索引)， 这样就能根据Term很快到找到源文档了 -》
