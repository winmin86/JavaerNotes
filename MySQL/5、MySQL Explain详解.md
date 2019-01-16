
### 1、创建表及插入数据
```sql
DROP TABLE IF EXISTS `actor`;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `actor` (`id`, `name`, `update_time`) VALUES (1,'a','2017-12-22 15:27:18'), (2,'b','2017-12-22 15:27:18'), (3,'c','2017-12-22 15:27:18');

DROP TABLE IF EXISTS `film`;
CREATE TABLE `film` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `film` (`id`, `name`) VALUES (3,'film0'),(1,'film1'),(2,'film2');

DROP TABLE IF EXISTS `film_actor`;
CREATE TABLE `film_actor` (
  `id` int(11) NOT NULL,
  `film_id` int(11) NOT NULL,
  `actor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_film_actor_id` (`film_id`,`actor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `film_actor` (`id`, `film_id`, `actor_id`) VALUES (1,1,1),(2,1,2),(3,2,1);
```

### 2、Explain简介
在 select 语句之前增加 explain 关键字，MySQL 会在查询上设置一个标记，执行查询时，会返回执行计划的信息，而不是执行这条SQL（如果 from 中包含子查询，仍会执行该子查询，将结果放入临时表中）。
```sql
mysql> explain select * from actor;
+----+-------------+-------+------+---------------+------+---------+------+------+-------+
| id | select_type | table | type | possible_keys | key  | key_len | ref  | rows | Extra |
+----+-------------+-------+------+---------------+------+---------+------+------+-------+
|  1 | SIMPLE      | actor | ALL  | NULL          | NULL | NULL    | NULL |    2 | NULL  |
+----+-------------+-------+------+---------------+------+---------+------+------+-------+
```
在查询中的每个表会输出一行，如果有两个表通过 join 连接查询，那么会输出两行。表的意义相当广泛：可以是子查询、一个 union 结果等。

explain 有两个变种：\
**1）explain extended**：会在 explain  的基础上额外提供一些查询优化的信息。紧随其后通过 show warnings 命令可以 得到优化后的查询语句，从而看出优化器优化了什么。额外还有 filtered 列，是一个半分比的值，rows * filtered/100 可以估算出将要和 explain 中前一个表进行连接的行数（前一个表指 explain 中的id值比当前表id值小的表）。
```sql
mysql> explain extended select * from film where id = 1;
+----+-------------+-------+-------+---------------+---------+---------+-------+------+----------+-------+
| id | select_type | table | type  | possible_keys | key     | key_len | ref   | rows | filtered | Extra |
+----+-------------+-------+-------+---------------+---------+---------+-------+------+----------+-------+
|  1 | SIMPLE      | film  | const | PRIMARY       | PRIMARY | 4       | const |    1 |   100.00 | NULL  |
+----+-------------+-------+-------+---------------+---------+---------+-------+------+----------+-------+

mysql> show warnings;
+-------+------+--------------------------------------------------------------------------------+
| Level | Code | Message                                                                        |
+-------+------+--------------------------------------------------------------------------------+
| Note  | 1003 | /* select#1 */ select '1' AS `id`,'film1' AS `name` from `test`.`film` where 1 |
+-------+------+--------------------------------------------------------------------------------+
```
**2）explain partitions**：相比 explain 多了个 partitions 字段，如果查询是基于分区表的话，会显示查询将访问的分区。

### 3、explain 各列的含义

#### 1. id列
id列的编号是 select 的序列号，有几个 select 就有几个id，并且id的顺序是按 select 出现的顺序增长的。\
MySQL将 select 查询分为简单查询和复杂查询。复杂查询分为三类：**简单子查询**、**派生表（from语句中的子查询）**、**union 查询**。\
我的理解是SQL执行的顺序的标识,SQL从大到小的执行
1. id相同时，执行顺序由上至下
2. 如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
3. id如果相同，可以认为是一组，从上往下顺序执行；在所有组中，id值越大，优先级越高，越先执行

#### 2. select_type
示查询中每个select子句的类型

(1) SIMPLE(简单SELECT,不使用UNION或子查询等)\
(2) PRIMARY(查询中若包含任何复杂的子部分,最外层的select被标记为PRIMARY)\
(3) UNION(UNION中的第二个或后面的SELECT语句)\
(4) DEPENDENT UNION(UNION中的第二个或后面的SELECT语句，取决于外面的查询)\
(5) UNION RESULT(UNION的结果)\
(6) SUBQUERY(子查询中的第一个SELECT)\
(7) DEPENDENT SUBQUERY(子查询中的第一个SELECT，取决于外面的查询)\
(8) DERIVED(派生表的SELECT, FROM子句的子查询)\
(9) UNCACHEABLE SUBQUERY(一个子查询的结果不能被缓存，必须重新评估外链接的第一行)
```sql
mysql> explain select (select 1 from actor where id = 1) from (select * from film where id = 1) der;
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------------+
| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref   | rows | filtered | Extra       |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------------+
|  1 | PRIMARY     | film  | NULL       | const | PRIMARY       | PRIMARY | 4       | const |    1 |   100.00 | Using index |
|  2 | SUBQUERY    | actor | NULL       | const | PRIMARY       | PRIMARY | 4       | const |    1 |   100.00 | Using index |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------------+

mysql> explain select 1 union all select 1;
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra          |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
|  1 | PRIMARY     | NULL  | NULL       | NULL | NULL          | NULL | NULL    | NULL | NULL |     NULL | No tables used |
|  2 | UNION       | NULL  | NULL       | NULL | NULL          | NULL | NULL    | NULL | NULL |     NULL | No tables used |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+

```
#### 3. table   
这一列表示 explain 的一行正在访问哪个表。\
当 from 子句中有子查询时，table列是 <derivenN> 格式，表示当前查询依赖 id=N 的查询，于是先执行 id=N 的查询。当有 union 时，UNION RESULT 的 table 列的值为 <union1,2>，1和2表示参与 union 的 select 行id。

```sql
mysql> explain select * from (select * from film union select * from film union select * from film) a;
+----+--------------+--------------+------------+-------+---------------+----------+---------+------+------+----------+-----------------+
| id | select_type  | table        | partitions | type  | possible_keys | key      | key_len | ref  | rows | filtered | Extra           |
+----+--------------+--------------+------------+-------+---------------+----------+---------+------+------+----------+-----------------+
|  1 | PRIMARY      | <derived2>   | NULL       | ALL   | NULL          | NULL     | NULL    | NULL |    9 |   100.00 | NULL            |
|  2 | DERIVED      | film         | NULL       | index | NULL          | idx_name | 33      | NULL |    3 |   100.00 | Using index     |
|  3 | UNION        | film         | NULL       | index | NULL          | idx_name | 33      | NULL |    3 |   100.00 | Using index     |
|  4 | UNION        | film         | NULL       | index | NULL          | idx_name | 33      | NULL |    3 |   100.00 | Using index     |
| NULL | UNION RESULT | <union2,3,4> | NULL       | ALL   | NULL          | NULL     | NULL    | NULL | NULL |     NULL | Using temporary |
+----+--------------+--------------+------------+-------+---------------+----------+---------+------+------+----------+-----------------+
```

#### 4. partitions (JSON name: partitions)
分片命中，如果表没有分片则值为NULL。

#### 5. type
这一列表示关联类型或访问类型，即MySQL决定如何查找表中的行。\
依次从最优到最差分别为：\
system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL

- ALL：Full Table Scan， MySQL将遍历全表以找到匹配的行
- index: Full Index Scan，index与ALL区别为index类型只遍历索引树
- range:范围扫描通常出现在 in(), between ,> ,<, >= 等操作中。使用一个索引来检索给定范围的行。
- ref: 相比 eq_ref，不使用唯一索引，而是使用普通索引或者唯一性索引的部分前缀，索引要和某个值相比较，可能会找到多个符合条件的行。
- eq_ref: 类似ref，区别就在使用的索引是唯一索引，对于每个索引键值，表中只有一条记录匹配，简单来说，就是多表连接中使用primary key或者 unique key作为关联条件
- const、system: 当MySQL对查询某部分进行优化，并转换为一个常量时，使用这些类型访问。如将主键置于where列表中，MySQL就能将该查询转换为一个常量,system是const类型的特例，当查询的表只有一行的情况下，使用system
- NULL: MySQL在优化过程中分解语句，执行时甚至不用访问表或索引，例如从一个索引列里选取最小值可以通过单独索引查找完成。

```sql
mysql> explain extended select * from (select * from film where id = 1) tmp;
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref   | rows | filtered | Extra |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
|  1 | SIMPLE      | film  | NULL       | const | PRIMARY       | PRIMARY | 4       | const |    1 |   100.00 | NULL  |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
1 row in set, 2 warnings (0.00 sec)

mysql> explain select * from film_actor left join film on film_actor.film_id = film.id;
+----+-------------+------------+------------+--------+---------------+-------------------+---------+------------------------+------+----------+-------------+
| id | select_type | table      | partitions | type   | possible_keys | key               | key_len | ref                    | rows | filtered | Extra       |
+----+-------------+------------+------------+--------+---------------+-------------------+---------+------------------------+------+----------+-------------+
|  1 | SIMPLE      | film_actor | NULL       | index  | NULL          | idx_film_actor_id | 8       | NULL                   |    3 |   100.00 | Using index |
|  1 | SIMPLE      | film       | NULL       | eq_ref | PRIMARY       | PRIMARY           | 4       | sys.film_actor.film_id |    1 |   100.00 | NULL        |
+----+-------------+------------+------------+--------+---------------+-------------------+---------+------------------------+------+----------+-------------+
2 rows in set, 1 warning (0.03 sec)

mysql> explain select * from film where name = "film1" or name is null;
+----+-------------+-------+------------+-------------+---------------+----------+---------+-------+------+----------+--------------------------+
| id | select_type | table | partitions | type        | possible_keys | key      | key_len | ref   | rows | filtered | Extra                    |
+----+-------------+-------+------------+-------------+---------------+----------+---------+-------+------+----------+--------------------------+
|  1 | SIMPLE      | film  | NULL       | ref_or_null | idx_name      | idx_name | 33      | const |    2 |   100.00 | Using where; Using index |
+----+-------------+-------+------------+-------------+---------------+----------+---------+-------+------+----------+--------------------------+
1 row in set, 1 warning (0.01 sec)

mysql> explain select * from film where id = 1 or name = 'abc';
+----+-------------+-------+------------+-------+------------------+----------+---------+------+------+----------+--------------------------+
| id | select_type | table | partitions | type  | possible_keys    | key      | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+-------+------------+-------+------------------+----------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | film  | NULL       | index | PRIMARY,idx_name | idx_name | 33      | NULL |    3 |    55.56 | Using where; Using index |
+----+-------------+-------+------------+-------+------------------+----------+---------+------+------+----------+--------------------------+
1 row in set, 1 warning (0.00 sec)
```

#### 6. possible_keys   
**指出MySQL能使用哪个索引在表中找到记录，查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询使用**
该列完全独立于EXPLAIN输出所示的表的次序。这意味着在possible_keys中的某些键实际上不能按生成的表次序使用。\
如果该列是NULL，则没有相关的索引。在这种情况下，可以通过检查WHERE子句看是否它引用某些列或适合索引的列来提高你的查询性能。如果是这样，创造一个适当的索引并且再次用EXPLAIN检查查询

#### 7. Key
**key列显示MySQL实际决定使用的键（索引）**
如果没有选择索引，键是NULL。要想强制MySQL使用或忽视possible_keys列中的索引，在查询中使用FORCE INDEX、USE INDEX或者IGNORE INDEX。

#### 8. key_len
表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度（key_len显示的值为索引字段的最大可能长度，并非实际使用长度，即key_len是根据表定义计算而得，不是通过表内检索出的）\
**不损失精确性的情况下，长度越短越好** 
```markdown
key_len计算规则如下：

1. 字符串
 char(n)：n字节长度
 varchar(n)：2字节存储字符串长度，如果是utf-8，则长度 3n + 2
2. 数值类型
 tinyint：1字节
 smallint：2字节
 int：4字节
 bigint：8字节　　
3. 时间类型　
 date：3字节
 timestamp：4字节
 datetime：8字节
如果字段允许为 NULL，需要1字节记录是否为 NULL
索引最大长度是768字节，当字符串过长时，mysql会做一个类似左前缀索引的处理，将前半部分的字符提取出来做索引。
```
#### 9. ref
这一列显示了在key列记录的索引中，表查找值所用到的列或常量，常见的有：const（常量），func，NULL，字段名（例：film.id）

#### 10. rows
表示MySQL根据表统计信息及索引选用情况，估算的找到所需的记录所需要读取的行数，**注意**这个不是结果集里的行数。

#### 11. Extra
该列包含MySQL解决查询的详细信息,有以下几种情况：
- Using where: mysql服务器将在存储引擎检索行后再进行过滤。就是先读取整行数据，再按 where 条件进行检查，符合就留下，不符合就丢弃。列数据是从仅仅使用了索引中的信息而没有读取实际的行动的表返回的，这发生在对表的全部的请求列都是同一个索引的部分的时候，表示mysql服务器将在存储引擎检索行后再进行过滤
- Using temporary：表示MySQL需要使用临时表来存储结果集，常见于排序和分组查询。出现这种情况一般是要进行优化的，首先是想到用索引来优化。
- Using filesort：MySQL中无法利用索引完成的排序操作称为“文件排序”。mysql 会对结果使用一个外部索引排序，而不是按索引次序从表里读取行。此时mysql会根据联接类型浏览所有符合条件的记录，并保存排序关键字和行指针，然后排序关键字并按顺序检索行信息。这种情况下一般也是要考虑使用索引来优化的。
- Using join buffer：改值强调了在获取连接条件时没有使用索引，并且需要连接缓冲区来存储中间结果。如果出现了这个值，那应该注意，根据查询的具体情况可能需要添加索引来改进能。
- Impossible where：这个值强调了where语句会导致没有符合条件的行。
- Select tables optimized away：这个值意味着仅通过使用索引，优化器可能仅从聚合函数结果中返回一行
- distinct：一旦mysql找到了与行相联合匹配的行，就不再搜索了
- Using index：这发生在对表的请求列都是同一索引的部分的时候，返回的列数据只使用了索引中的信息，而没有再去访问表中的行记录。是性能高的表现。

### 总结：
- EXPLAIN不会告诉你关于触发器、存储过程的信息或用户自定义函数对查询的影响情况
- EXPLAIN不考虑各种Cache
- EXPLAIN不能显示MySQL在执行查询时所作的优化工作
- 部分统计信息是估算的，并非精确值
- EXPALIN只能解释SELECT操作，其他操作要重写为SELECT后查看执行计划。



