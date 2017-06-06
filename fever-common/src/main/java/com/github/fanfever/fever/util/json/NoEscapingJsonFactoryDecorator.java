package com.github.fanfever.fever.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import net.logstash.logback.decorate.JsonFactoryDecorator;

/**
 * <p>reference:
 * <a href="https://github.com/logstash/logstash-logback-encoder/issues/197">logstash-logback-encoder</a>
 *
 * @author scott he
 * @date 2017/6/3
 */
public class NoEscapingJsonFactoryDecorator implements JsonFactoryDecorator {
  @Override
  public MappingJsonFactory decorate(MappingJsonFactory factory) {
    return (MappingJsonFactory) factory.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
  }
}
