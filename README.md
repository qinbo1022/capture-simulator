# Capture Simulator

## 简介

Capture Simulator 是一套用于模拟设备抓拍、库管理、任务推送、Kafka集成的综合系统，支持设备、任务、库、配置、日志等多模块管理，适合测试和演示推送流程。

## 主要功能
- 设备管理：支持设备缓存、分页、搜索、只读展示。
- 任务管理：支持任务创建、编辑、定时推送、独立队列、异步加载设备缓存。
- 库管理：支持库的增删改查、人员管理、图片分组。
- 推送配置：支持Kafka、检测服务等推送参数配置。
- 任务日志：记录任务操作与Kafka推送，支持筛选、分页、内容弹窗。
- 定时清理：自动定时清理3天前的任务日志。

## 技术栈
- Spring Boot 2.3
- Spring Cloud Alibaba Nacos
- MyBatis-Plus
- MySQL
- Kafka
- Fastjson2、Hutool、Guava
- 前端：HTML + TailwindCSS + Bootstrap5 + 原生JS

## 快速启动

### 1. 环境准备
- JDK 8+
- Maven 3.6+
- MySQL 5.7/8.0
- Kafka（如需推送）
- Nacos（注册中心/配置中心）

### 2. 配置
编辑 `src/main/resources/bootstrap.yml`，主要环境变量：
- `REGISTER_HOST`/`REGISTER_PORT`：Nacos地址
- `REGISTER_USERNAME`/`REGISTER_PASSWORD`：Nacos账号
- `REGISTER_GROUP`：Nacos分组

数据库、Kafka等参数可在Nacos配置中心或本地配置文件中调整。

### 3. 编译与运行
```bash
# 编译
mvn clean package
# 启动
java -jar target/capture-simulator-0.0.1-SNAPSHOT.jar
# 或
./mvnw spring-boot:run
```

### 4. 访问系统
默认端口：`http://localhost:8080/`

## 常见问题
- **设备缓存数据量大**：已支持分页和异步加载，前端不卡顿。
- **任务日志过多**：系统自动每天清理3天前日志。
- **Kafka/检测服务配置**：可在"推送配置"页面或Nacos中调整。
- **注册中心/配置中心**：需保证Nacos可用。

## 目录结构
- `src/main/java/com/tsing/` 业务代码
- `src/main/resources/templates/` 前端页面
- `src/main/resources/mapper/` MyBatis XML
- `src/main/resources/bootstrap.yml` 主要配置

## 贡献&反馈
如有建议或问题，请联系项目维护者。 