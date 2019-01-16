- [转自](https://blog.csdn.net/timchen525/article/details/75268151)

MySQL中的日志包括：错误日志、二进制日志、通用查询日志、慢查询日志等等。这里主要介绍下比较常用的两个功能：通用查询日志和慢查询日志。

1）通用查询日志：记录建立的客户端连接和执行的语句。

2）慢查询日志：记录所有执行时间超过long_query_time秒的所有查询或者不使用索引的查询

### 1. 通用查询日志

在学习通用日志查询时，需要知道两个数据库中的常用命令：

1) show variables like '%version%';

效果如下：
```sql
mysql> show variables like '%version%';
+-------------------------+------------------------------+
| Variable_name           | Value                        |
+-------------------------+------------------------------+
| innodb_version          | 5.7.18                       |
| protocol_version        | 10                           |
| slave_type_conversions  |                              |
| tls_version             | TLSv1,TLSv1.1                |
| version                 | 5.7.18-log                   |
| version_comment         | MySQL Community Server (GPL) |
| version_compile_machine | x86_64                       |
| version_compile_os      | Win64                        |
+-------------------------+------------------------------+
```
显示当前数据库中与版本号相关的东西。

2) show variables like '%general%';
```sql
mysql> show variables like '%general%';
+------------------+----------------+
| Variable_name    | Value          |
+------------------+----------------+
| general_log      | ON             |
| general_log_file | PC-9GKQSR2.log |
+------------------+----------------+
```
可以查看，当前的通用日志查询是否开启，如果general_log的值为ON则为开启，为OFF则为关闭（默认情况下是关闭的）。

3) show variables like '%log_output%';
```sql
mysql> show variables like '%log_output%';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| log_output    | TABLE |
+---------------+-------+
```
查看当前慢查询日志输出的格式，可以是FILE（存储在数数据库的数据文件中的hostname.log），也可以是TABLE（存储在数据库中的mysql.general_log）。

**问题：如何开启MySQL通用查询日志，以及如何设置要输出的通用日志输出格式呢？**
- 开启通用日志查询： set global general_log=on;
- 关闭通用日志查询： set globalgeneral_log=off;
- 设置通用日志输出为表方式： set globallog_output='TABLE';
- 设置通用日志输出为文件方式： set globallog_output='FILE';
- 设置通用日志输出为表和文件方式：set global log_output='FILE,TABLE';

（注意：上述命令只对当前生效，当MySQL重启失效，如果要永久生效，需要配置my.cnf）\
记录到mysql.general_log表中的数据如下：
```sql
mysql> select * from mysql.general_log order by event_time desc limit 3;
+----------------------------+------------------------------+-----------+-----------+--------------+------------------------------------------------------------------+
| event_time                 | user_host                    | thread_id | server_id | command_type | argument                                                         |
+----------------------------+------------------------------+-----------+-----------+--------------+------------------------------------------------------------------+
| 2019-01-16 15:10:14.484231 | root[root] @ localhost [::1] |         9 |         1 | Query        | select * from mysql.general_log order by event_time desc limit 3 |
| 2019-01-16 15:08:25.414037 | root[root] @ localhost [::1] |         9 |         1 | Query        | select * from mysql.general_log                                  |
| 2019-01-16 15:06:48.710757 | root[root] @ localhost [::1] |         9 |         1 | Query        | show variables like '%log_output%'                               |
+----------------------------+------------------------------+-----------+-----------+--------------+------------------------------------------------------------------+
```
**my.cnf文件的配置如下：**
- general_log=1  #为1表示开启通用日志查询，值为0表示关闭通用日志查询
- log_output=FILE,TABLE#设置通用日志的输出格式为文件和表


### 2. 慢查询日志

MySQL的慢查询日志是MySQL提供的一种日志记录，用来记录在MySQL中响应时间超过阈值的语句，具体指运行时间超过long_query_time值的SQL，则会被记录到慢查询日志中（日志可以写入文件或者数据库表，如果对性能要求高的话，建议写文件）。默认情况下，MySQL数据库是不开启慢查询日志的，long_query_time的默认值为10（即10秒，通常设置为1秒），即运行10秒以上的语句是慢查询语句。

一般来说，慢查询发生在大表（比如：一个表的数据量有几百万），且查询条件的字段没有建立索引，此时，要匹配查询条件的字段会进行全表扫描，耗时查过long_query_time，则为慢查询语句。

**问题：如何查看当前慢查询日志的开启情况？**

在MySQL中输入命令：\
show variables like '%quer%';
```sql
mysql> show variables like '%quer%';
+----------------------------------------+---------------------+
| Variable_name                          | Value               |
+----------------------------------------+---------------------+
| binlog_rows_query_log_events           | OFF                 |
| ft_query_expansion_limit               | 20                  |
| have_query_cache                       | YES                 |
| log_queries_not_using_indexes          | OFF                 |
| log_throttle_queries_not_using_indexes | 0                   |
| long_query_time                        | 2.000000            |
| query_alloc_block_size                 | 8192                |
| query_cache_limit                      | 1048576             |
| query_cache_min_res_unit               | 4096                |
| query_cache_size                       | 0                   |
| query_cache_type                       | OFF                 |
| query_cache_wlock_invalidate           | OFF                 |
| query_prealloc_size                    | 8192                |
| slow_query_log                         | ON                  |
| slow_query_log_file                    | PC-9GKQSR2-slow.log |
+----------------------------------------+---------------------+
```

**主要掌握以下的几个参数：**\
（1）slow_query_log的值为ON为开启慢查询日志，OFF则为关闭慢查询日志。\
（2）slow_query_log_file 的值是记录的慢查询日志到文件中（注意：默认名为主机名.log，慢查询日志是否写入指定文件中，需要指定慢查询的输出日志格式为文件，相关命令为：show variables like '%log_output%'；去查看输出的格式）。\
（3）long_query_time 指定了慢查询的阈值，即如果执行语句的时间超过该阈值则为慢查询语句，默认值为10秒。\
（4）log_queries_not_using_indexes 如果值设置为ON，则会记录所有没有利用索引的查询（注意：如果只是将log_queries_not_using_indexes设置为ON，而将slow_query_log设置为OFF，此时该设置也不会生效，即该设置生效的前提是slow_query_log的值设置为ON），一般在性能调优的时候会暂时开启。

**问题：设置MySQL慢查询的输出日志格式为文件还是表，或者两者都有？**

通过命令：show variables like '%log_output%';
```sql
mysql> show variables like '%log_output%';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| log_output    | TABLE |
+---------------+-------+
```
通过log_output的值可以查看到输出的格式，上面的值为TABLE。当然，我们也可以设置输出的格式为文本，或者同时记录文本和数据库表中，设置的命令如下：

- 慢查询日志输出到表中（即mysql.slow_log）
set global log_output='TABLE';

- 慢查询日志仅输出到文本中(即：slow_query_log_file指定的文件)
set global log_output='FILE';

- 慢查询日志同时输出到文本和表中
set global log_output='FILE,TABLE';

关于慢查询日志的表中的数据个文本中的数据格式分析：

慢查询的日志记录myql.slow_log表中，格式如下：
```sql
mysql> select * from mysql.slow_log;
+----------------------------+------------------------------+-----------------+-----------------+-----------+---------------+-----+----------------+-----------+-----------+-----------------+-----------+
| start_time                 | user_host                    | query_time      | lock_time       | rows_sent | rows_examined | db  | last_insert_id | insert_id | server_id | sql_text        | thread_id |
+----------------------------+------------------------------+-----------------+-----------------+-----------+---------------+-----+----------------+-----------+-----------+-----------------+-----------+
| 2019-01-16 15:35:29.580231 | root[root] @ localhost [::1] | 00:00:03.000971 | 00:00:00.000000 |         1 |             0 | sys |              0 |         0 |         1 | select sleep(3) |         9 |
| 2019-01-16 15:35:49.276535 | root[root] @ localhost [::1] | 00:00:03.000970 | 00:00:00.000000 |         1 |             0 | sys |              0 |         0 |         1 | select sleep(3) |         9 |
| 2019-01-16 15:35:53.004561 | root[root] @ localhost [::1] | 00:00:03.000971 | 00:00:00.000000 |         1 |             0 | sys |              0 |         0 |         1 | select sleep(3) |         9 |
+----------------------------+------------------------------+-----------------+-----------------+-----------+---------------+-----+----------------+-----------+-----------+-----------------+-----------+
```
具体记录了：是那条语句导致慢查询（sql_text），该慢查询语句的查询时间（query_time），锁表时间（Lock_time），以及扫描过的行数（rows_examined）等信息。

**问题：如何查询当前慢查询的语句的个数？**

在MySQL中有一个变量专门记录当前慢查询语句的个数：

输入命令：\
show global status like '%slow%';
```sql
mysql> show global status like '%slow%';
+---------------------+-------+
| Variable_name       | Value |
+---------------------+-------+
| Slow_launch_threads | 0     |
| Slow_queries        | 0     |
+---------------------+-------+
```

（注意：上述所有命令，如果都是通过MySQL的shell将参数设置进去，如果重启MySQL，所有设置好的参数将失效，如果想要永久的生效，需要将配置参数写入my.cnf文件中）。

补充知识点：如何利用MySQL自带的慢查询日志分析工具mysqldumpslow分析日志？
mysqldumpslow是用perl开发，所以必须安装perl。
perl mysqldumpslow –s c –t 10 slow-query.log

具体参数设置如下：

-s 表示按何种方式排序，c、t、l、r分别是按照记录次数、时间、查询时间、返回的记录数来排序，ac、at、al、ar，表示相应的倒叙；

-t 表示top的意思，后面跟着的数据表示返回前面多少条；

-g 后面可以写正则表达式匹配，大小写不敏感。


