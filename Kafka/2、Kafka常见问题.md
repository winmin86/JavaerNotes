1、
2、
3、

### 4、Kafka如何保证高可用性?
Kafka 一个最基本的架构认识：由多个 broker 组成，每个 broker 是一个节点；你创建一个 topic，这个 topic 可以划分为多个 partition，每个 partition 可以存在于不同的 broker 上，每个 partition 就放一部分数据。\
这就是天然的分布式消息队列，**就是说一个 topic 的数据，是分散放在多个机器上的，每个机器就放一部分数据**。\
实际上 RabbmitMQ 之类的，并不是分布式消息队列，它就是传统的消息队列，只不过提供了一些集群、HA(High Availability, 高可用性) 的机制而已，因为无论怎么玩儿，RabbitMQ 一个 queue 的数据都是放在一个节点里的，镜像集群下，也是每个节点都放这个 queue 的完整数据。

Kafka就是 replica（复制品） 副本机制。每个 partition 的数据都会同步到其它机器上，形成自己的多个 replica 副本。所有 replica 会选举一个 leader 出来，那么生产和消费都跟这个 leader 打交道，然后其他 replica 就是 follower。写的时候，leader 会负责把数据同步到所有 follower 上去，读的时候就直接读 leader 上的数据即可。只能读写 leader？很简单，要是你可以随意读写每个 follower，那么就要 care 数据一致性的问题，系统复杂度太高，很容易出问题。Kafka 会均匀地将一个 partition 的所有 replica 分布在不同的机器上，这样才可以提高容错性。

如果某个 broker 宕机了，该broker上面的 partition 在其他机器上都有副本的，如果这上面有某个 partition 的 leader，那么此时会从 follower 中重新选举一个新的 leader 出来，大家继续读写那个新的 leader 即可。这就有所谓的高可用性了。\
`写数据的时候`，生产者就写 leader，然后 leader 将数据落地写本地磁盘，接着其他 follower 自己主动从 leader 来 pull 数据。一旦所有 follower 同步好数据了，就会发送 ack 给 leader，leader 收到所有 follower 的 ack 之后，就会返回写成功的消息给生产者。\
`消费的时候`，只会从 leader 去读，但是只有当一个消息已经被所有 follower 都同步成功返回 ack 的时候，这个消息才会被消费者读到。

### 5、如何保证消息不被重复消费？
即如何保证消息消费时的幂等性

![image](img/kafka_offset.png)

**底层根本原因：已经消费了数据，但是offset没提交。** \
- 原因1：强行kill线程，导致消费后的数据，offset没有提交。 
- 原因2：设置offset为自动提交，关闭kafka时，如果在close之前，调用 consumer.unsubscribe() 则有可能部分offset没提交，下次重启会重复消费。
```java
try {
    consumer.unsubscribe();
} catch (Exception e) {
}

try {
    consumer.close();
} catch (Exception e) {
}
```
上面代码会导致部分offset没提交，下次启动时会重复消费。 
- 原因3（重复消费最常见的原因）：消费后的数据，当offset还没有提交时，partition就断开连接。比如，通常会遇到消费的数据，处理很耗时，导致超过了Kafka的session timeout时间（0.10.x版本默认是30秒），那么就会re-blance重平衡，此时有一定几率offset没提交，会导致重平衡后重复消费。 
- 原因4：当消费者重新分配partition的时候，可能出现从头开始消费的情况，导致重发问题。 
- 原因5：当消费者消费的速度很慢的时候，可能在一个session周期内还未完成，导致心跳机制检测报告出问题。

**解决方案:**\
**1、维护offset**\
记录offset和恢复offset的方案。理论上记录offset，下一个group consumer可以接着记录的offset位置继续消费。 
简单offset记录方案： 
每次消费时更新每个topic+partition位置的offset在Redis中，

> Map<key, value>，key=topic+'-'+partition，value=offset

下一次启动consumer，需要读取上一次的offset信息，方法是 以当前的topic+partition为key，从上次的Map中去寻找offset。 然后使用consumer.seek()方法指定到上次的offset位置。\ 
说明：为了确保consumer消费的数据一定是接着上一次consumer消费的数据，consumer消费时，记录第一次取出的数据，将其offset和上次consumer最后消费的offset进行对比，如果相同则继续消费。如果不同，则停止消费，检查原因。

**2、数据缓存+维护offset**\
对于离线接入，设置较大的缓存；对于近实时接入，设置较小缓存。\
缓存好的数据写入hive中，就相当于改文件名字，很快（可以看成不会出错）。\
对于同时处理离线和实时的数据接入任务，可以“记录实时接入已读取偏移量”、“实时接入已提交偏移量”、“实时接入消息队列输出偏移量”；\
已读取偏移量肯定是大于已提交偏移量和消息队列输出偏移量，当数据写入出错，那么缓存的数据就会丢失，没有写入到hive,这时根据记录实时接入已提交偏移量和实时接入消息队列输出偏移量重新从kafka拉取数据。这样主要是保证两条线路的数据一致性。

### 6、如何保证消息的可靠性传输？
即如何处理消息丢失的问题

**猜测**：设置offset为自动定时提交，当offset被自动定时提交时，数据还在内存中未处理，此时刚好把线程kill掉，那么offset已经提交，但是数据未处理，导致这部分内存中的数据丢失。\ 
消息推动服务，每天早上，手机上各终端都会给用户推送消息，这时候流量剧增，可能会出现kafka发送数据过快，导致服务器网卡爆满，或者磁盘处于繁忙状态，可能会出现丢包现象。\
总结起来就是来得快，去得慢，然后offset又提交了。\
**解决方案**：**首先对kafka进行限速，其次启用重试机制，重试间隔时间设置长一些，最后Kafka设置acks=all**。\
**检测方法**：使用重试机制，查看问题所在。\
kafka配置如下：
```java
props.put("compression.type", "gzip");
props.put("linger.ms", "50");//按照linger.ms的时间间隔批量发送消息
props.put("acks", "all");
props.put("retries ", 30);
props.put("reconnect.backoff.ms ", 20000);
props.put("retry.backoff.ms", 20000);
```




