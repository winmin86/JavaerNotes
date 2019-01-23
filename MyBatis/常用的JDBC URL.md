- [参考](https://blog.csdn.net/tolcf/article/details/52102849)
```sql
jdbc:mysql://127.0..0.1:3306/test?useSSL=true&createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&rewriteBatchedStatements=true
```

键 | 默认值 | 描述
---|---|---
user | | 数据库用户名(用于连接数据库)
password | | 用户密码(用于连接数据库)
useSSL  | false |   MySQL 5.5.x+ 要求使用SSL
createDatabaseIfNotExist | false | 当JDBC连接指定数据库,如果此数据库不存在,此参数值为true时，则自动创建此数据库
useUnicode  | false | 是否使用Unicode字符集，如果参数characterEncoding设置为gb2312、gbk或utf8,本参数的值必须设置为true
characterEncoding | 无 | 如果useUnicode,该参数指定encoding类型，建议使用utf8
autoReconnect | false | 当数据库连接丢失时是否自动连接
autoReconnectForPools | false | 是否使用针对数据库连接池的重连策略
failOverReadOnly | true | 自动重连成功后，连接是否设置为只读？
maxReconnects | 3 | autoReconnect设置为true时，重试连接的次数
initialTimeout | 2 | autoReconnect设置为true时，两次重连之间的时间间隔，单位：秒
connectTimeout | 0 | 和数据库服务器建立socket连接时的超时，单位：毫秒。 0表示永不超时，适用于JDK 1.4及更高版本
scoketTimeout | 0 | socket操作（读写）超时，单位：毫秒。 0表示永不超时
allowMultiQueries | false | 在一条语句中，允许使用“;”来分隔多条查询
rewriteBatchedStatements | false | 是否允许批量insert update delete
useTimezone | false | 是否在客户端和服务器时区间转换时间／日期类型
serverTimezone | | 覆盖时区的检测/映射。当服务器的时区为映射到Java时区时使用。
useOldAliasMetadataBehavior | false | 这个代表数据库里面允许有别名




