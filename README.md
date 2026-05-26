# ljw-project

这是一个 Spring Boot 多模块基础项目，用于学习后端项目分层结构、MyBatis-Plus 数据库访问以及接口开发。

目前项目已经完成 Spring Boot 启动、MySQL 数据库连接，并实现了用户列表查询接口。

## 技术栈

- JDK 21
- Maven 3.9.15
- Spring Boot 3.3.5
- MyBatis-Plus 3.5.15
- MySQL 8.0

## 项目结构

```text
ljw-project
├─ ljw-common      公共模块
├─ ljw-dao         数据实体模块
├─ ljw-service     业务服务模块
├─ ljw-dispatch    调度/接口编排模块
├─ ljw-vo          视图对象模块
└─ ljw-web         Web 启动模块和 Controller 接口
