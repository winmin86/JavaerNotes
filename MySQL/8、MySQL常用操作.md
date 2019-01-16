### 1、 查看InnoDB系统级别的事务隔离级别：
```sql
mysql> SELECT @@global.tx_isolation;
+-----------------------+
| @@global.tx_isolation |
+-----------------------+
| REPEATABLE-READ       |
+-----------------------+
1 row in set (0.01 sec)
```

### 2、查看InnoDB会话级别的事务隔离级别：
```sql
 mysql> SELECT @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set (0.00 sec)
```

### 3、How to find Engine version
```sql
mysql> SELECT VERSION();
+------------+
| VERSION()  |
+------------+
| 5.7.18-log |
+------------+
1 row in set (0.00 sec)
```

### 4、创建账号root@111111并授予所有库所有表的全部权限
```sql
grant all privileges on *.* to root@"%" identified by "111111";
flush privileges;
```

### 5、slaves节点同步master节点
```sql
change master to master_host='192.168.30.230',
master_port=3306,
master_user='repl',
master_password='111111',
master_log_file='master-bin.000002',
master_log_pos=0;
```

### 6、查看binlog日志格式
```sql
mysql> SHOW GLOBAL VARIABLES LIKE '%binlog_format%';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| binlog_format | ROW   |
+---------------+-------+
1 row in set (0.01 sec)
设置binlog日志格式
SET SESSION binlog_format = 'ROW';  #会话级别
SET GLOBAL binlog_format = 'ROW';  #系统级别
```

### 7、查看binlog日志文件
```sql
mysql> SHOW MASTER LOGS;
mysql> SHOW BINARY LOGS;
+-------------------+-----------+
| Log_name          | File_size |
+-------------------+-----------+
| master-bin.000001 |       360 |
| master-bin.000002 |      1874 |
+-------------------+-----------+
2 rows in set (0.00 sec)
```

### 8、另起一个binlog文件
```sql
mysql> FLUSH LOGS;
Query OK, 0 rows affected (0.09 sec)
```

### 9、查看binlog列表
```sql
mysql> SHOW BINARY LOGS;
mysql> SHOW MASTER LOGS;
+-------------------+-----------+
| Log_name          | File_size |
+-------------------+-----------+
| master-bin.000001 |       360 |
| master-bin.000002 |      1922 |
| master-bin.000003 |       154 |
+-------------------+-----------+
3 rows in set (0.00 sec)
```

### 10、查看binlog具体信息
```sql
mysql> SHOW BINLOG EVENTS IN 'master-bin.000002';
+-------------------+------+----------------+-----------+-------------+-----------------------------------------------+
| Log_name          | Pos  | Event_type     | Server_id | End_log_pos | Info                                       |
+-------------------+------+----------------+-----------+-------------+-----------------------------------------------+                                        
| master-bin.000002 |  123 | Previous_gtids |         1 |         154 |
| master-bin.000002 |    4 | Format_desc    |         1 |         123 | Server ver: 5.7.18-log, Binlog ver: 4|

```
Pos/End_log_pos 开始/结束的位置\
SHOW BINLOG EVENTS IN 'mysql-bin.000005' FROM pos LIMIT 10; #从pos开始查看10条记录
### 11、查看正在使用的binlog
```sql
mysql> show master status;
+-------------------+----------+--------------+------------------+-------------------+
| File              | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+-------------------+----------+--------------+------------------+-------------------+
| master-bin.000003 |      154 |              |                  |                   |
+-------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

### 12、本地查看binlog
注意\
a、不要查看当前正在写入的binlog文件\
b、不要加--force参数强制访问\
c、如果binlog格式是行模式的,请加 -vv参数\
如果报编码错误则加：--no-defaults\
(1)基于开始/结束时间\
./mysqlbinlog --start-datetime='2013-09-10 00:00:00' --stop-datetime='2013-09-10 01:01:01' -d 库名 二进制文件\
(2)基于pos值\
./mysqlbinlog --start-postion=107 --stop-position=1000 -d 库名 二进制文件\

### 13、远程查看binlog
指定开始/结束时间,并把结果重定向到本地t.binlog文件中.\
./mysqlbinlog -u username -p password -hl-db1.dba.beta.cn6.qunar.com -P3306 \
--read-from-remote-server --start-datetime='2013-09-10 23:00:00' --stop-datetime='2013-09-10 23:30:00' mysql-bin.000001 > t.binlog

### 14、查看wait_timeout
```sql
mysql> SHOW VARIABLES LIKE 'wait_timeout';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| wait_timeout  | 28800 |
+---------------+-------+
1 row in set (0.01 sec)
```

### 15、设置wait_timeout
mysql> SET GLOBAL/SESSION wait_timeout=2880000;

### 16、创建临时表
```sql
CREATE TEMPORARY TABLE tmp_stu (
  `id` INT (4) NOT NULL AUTO_INCREMENT,
  `names` VARCHAR (20),
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 ;
```
临时表将在你连接MySQL期间存在。当你断开时，MySQL将自动删除表并释放所用的空间。

### 17、锁表
读锁\
mysql>LOCK TABLES tbl_name READ;\
写锁\
mysql>LOCK TABLES tbl_name WRITE;\

### 18、解锁
a、mysql> SHOW PROCESSLIST;\
找到锁进程，kill id ;\
b、mysql>UNLOCK TABLES;

### 19、查看哪些表被锁了
mysql> SHOW OPEN TABLES WHERE In_use > 0;

### 20、存储过程
```sql
mysql> DELIMITER &&
mysql> CREATE PROCEDURE proce (IN num INT, OUT total INT)
    -> BEGIN
    -> SELECT
    ->   COUNT(id) INTO total
    -> FROM
    ->   esx.stu
    -> WHERE id < num;
    -> END &&
Query OK, 0 rows affected (0.03 sec)

mysql> DELIMITER ;
mysql> CALL proce(100,@total);
Query OK, 1 row affected (0.00 sec)

mysql> SELECT @total;
+--------+
| @total |
+--------+
|      2 |
+--------+
1 row in set (0.00 sec)
mysql> SHOW PROCEDURE STATUS;  #显示当期的存储过程
mysql> DROP PROCEDURE pro;           #删除指定存储过程
```

### 21、查看整个系统的最大连接数
```sql
mysql> select @@global.max_connections;
+--------------------------+
| @@global.max_connections |
+--------------------------+
|                     2048 |
+--------------------------+
1 row in set (0.00 sec)
mysql> select @@max_user_connections;   #个人
mysql> select @@max_user_connections;   #设置
```

### 22、查看slave节点的状态
mysql> SHOW slave STATUS\G;

### 23、修改和查看AUTO_INCREMENT
```sql
ALTER TABLE `xn_realtime`.`realtime_userrole` AUTO_INCREMENT=0;

SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_schema='xn_realtime' AND table_name='realtime_metrics_group';
```

### 24、查看锁表
```sql
mysql> show status like 'Table%';
+----------------------------+----------+
| Variable_name        | Value |
+----------------------------+----------+
| Table_locks_immediate | 105         |
| Table_locks_waited   | 3           |
+----------------------------+----------+
```

Table_locks_immediate  指的是能够立即获得表级锁的次数\
Table_locks_waited  指的是不能立即获取表级锁而需要等待的次数
 
查看正在被锁定的的表\
show OPEN TABLES where In_use > 0;

### 25、查看正在占用的连接列表
SHOW PROCESSLIST;




备份数据库\
mysqldump -h 10.150.148.138 -uttshop_reader -pMhxzKhl123 ttshop --lock-tables=false > /home/zhuwenming/test.sql

查看INNODB存储引擎的状态\
SHOW ENGINE INNODB STATUS\G;

查看缓冲池大小\
show variables like 'innodb_buffer_pool_size'\G;

查看日志缓冲池\
show variables like 'innodb_log_buffer_size'\G;

查看额外内存缓冲池\
show variables like 'innodb_additional_mem_pool_size'\G;

寻找mysql配置文件\
mysql --help | grep my.cnf

查找错误日志路径\
show variables like 'log_error';

查看慢查询long_query_time设置\
show variables like '%long%';

查询慢查询是否开启\
show variables like 'log_slow_queries';

查询慢查询时长\
show variables like 'long_query_time';

查询慢查询是否使用索引\
show variables like 'log_queries_not_using_indexes';

OPTIMIZE TABLE tabTest;

flush privileges;修改数据库之后的刷新

set global time_zone = '+8:00'; ##修改mysql全局时区为北京时间，即我们所在的东8区\
set time_zone = '+8:00'; ##修改当前会话时区\
flush privileges; #立即生效