- resource 下的 schema.sql 是表结构初始化脚本
- resource 下的 data.sql 是数据初始化脚本

主要用法
---
- junit 单元测试（jvm单机模式，用完就释放数据，无数据污染）
- 作为缓存中间件使用 （服务器模式，jvm停止数据不释放），缓存热点数据，项目启动预热数据。多个项目都可连接H2使用热点数据。

项目集成
---
- Mybatis
- pagehelper
- 通用Mapper(tk Mapper)

缓存用法
---
mysq数据预热到 -> h2, 减少查询时间，使用h2服务模式，作为redis的补充

1. 新建表结构
2. 项目启动数据预热（使用spring监听器）
3. 注入mapper 使用
