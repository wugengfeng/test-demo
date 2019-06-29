[seata](https://github.com/seata/seata) 是一个分布式事务解决方案

使用 seata 需要下载相应版本的 [seata-server](https://github.com/seata/seata/tags)

目前此项目使用的是0.6.1版本

seata-server 使用
---
解压后在目录 conf 中有两个文件
* registry.conf     服务注册发现相关配置
* file.conf         seata-server连接数据库等参数配置

配置步骤
---
registry.conf
* registry模块 type属性 配置为eureka， seata提供多种注册策略
* eureka模块的 url 配置为你eureka的注册地址

启动
---
* 启动 eureka
* 在seata-server解压文件夹下的bin 运行 seata-server.bat 启动seata-server
* eureka 注册了名字为 DEFAULT 的 application就说明seata-server启动注册完成
* 依次启动 storage-server, order-server, account-server, business-server
* 访问 [http://localhost:8089/swagger-ui.html](http://localhost:8089/swagger-ui.html)

验证数据
---
1. 事务成功

   库存减1、订单加1、余额减5
2. 事务回滚

   数据无变化

