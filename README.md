# fever
This is a basic framework depend on springsource and maven

## module info
```
.fever
├── fever-parent 指定依赖包版本
├── fever-common 基类及工具类
├── fever-web web项目基类及工具类
├── fever-migration 库表脚本管理
├── fever-batch 批量任务
├── fever-elasticsearch ES基础包
├── fever-upload 上传下载基础包
├── fever-demo DEMO
├── fever-metrics web项目监控
└── fever-batch-metrics 批量任务监控

则新项目启动时遵循此风格
.proj
├── proj-parent 继承fever-parent,指定依赖包版本
├── proj-common 引入fever-common
├── proj-web 引入fever-web

```
## maven
+ [nexus](http://awsjenkins.flyudesk.com/nexus)
+ deploy
```
vim $MAVEN_HOME/conf/setting.xml
<servers>
    <server>
      <id>releases</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
    <server>
      <id>snapshots</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
</servers>
mvn deploy
```
## code quality
+ [sonarQube](http://awsjenkins.flyudesk.com/sonar)

```bash
#安装sonarlint插件
mvn clean install
mvn sonar:sonar -Dsonar.host.url=http://awsjenkins.flyudesk.com/sonar/
```
## framework library
+ SDK
    + JDK8
+ VCS
    + Git
+ Build Tools
    + Maven
+ Basic Library
    + SpringBoot
+ Job Library
    + Spring Batch
+ Commons Library
    + Google Guava
    + Lombok
    + Apache Commons
+ DB
    + MySql
+ DB Pool
    + Druid
    + HikariCP
+ Migration
    + Flyway
+ ORM
    + MyBatis
+ Security
    + Shiro
+ Cache
    + EhCache
    + Redis
+ Serializer
    + Jackson
+ Web
    + Spring Restful Service
    + Spring RestTemplate
+ Doc
    + Swagger
+ Log
    + Logback
    + GrayLog
+ Validation
    + URI -> Regular Expression
    + urlParams -> Spring Validated
    + Bean -> Hibernate Validation, Fluent-validator
+ Environment
    + Ali
    + AWS
+ Metrics
    + Spring Boot Admin
    + Spring Batch Admin
    + NewRelic
    + DataDog
+ IT Test
    + Cucumber
+ Search
    + ElasticSearch
+ Code Quality
    + SonarQube
+ Code Coverage
    + jacoco
+ UML
    + PlantUml

## 管理说明
    + 保证JIRA中时刻有一个任务处于开发中或RD测试中

## 分支说明
    + 分支说明: master: 线上版本, develop: 回归测试版本, feature: 开发分支, hotfix: 线上紧急BUG分支, bugfix: 普通BUG分支
    + 创建分支命名规范: feature_JIRAXX_simpleTitle, eg. feature_SALES_001_init
    + 创建分支及合并说明: 新建任务分支源头为develop, 采用rebase操作合并冲突, 即feature rebase develop, 反之则使用merge  

## 开发规范
    + mapper接口约定符合[JPA规范](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
    + 数据库table按照 module_table, eg. sys_user, 同时项目包名按照module划分
    + 普通业务按照三层架构, service层处理业务, repository及mapper读写分离, 涉及到分布式事务可以考虑加sys层处理事务同步问题