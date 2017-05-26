package com.github.fanfever.fever.mail.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author scott.he
 * @date 2017/4/14
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.github.fanfever.fever"})
@PropertySource({"classpath:application.yml"})
@EnableConfigurationProperties
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
