package xyz.up123.test.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.kafka
 * @ClassName: KafkaProducers
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/19 16:34
 * @Version: 1.0
 */
public class KafkaProducers{
    private final static String BOOTSTRAP_SERVERS = "localhost:8092";

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            onlyProduceInTransaction(i + "", i + "");
        }

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    onlyConsumeInTransaction();
                }
            }
        }).start();*/

    }

    /**
     * 在一个事务只有生产消息操作
     */
    public static void onlyProduceInTransaction(String key, String msg) {
        Producer producer = buildProducer();
        // 1.初始化事务
        producer.initTransactions();
        // 2.开启事务
        producer.beginTransaction();
        try {
            // 3.kafka写操作集合
            // 3.1 do业务逻辑
            // 3.2 发送消息
            producer.send(new ProducerRecord<String, String>("test", key, "MESSAGE_" + msg));
            producer.flush();
            System.out.println("发送过...");
            // 3.3 do其他业务逻辑,还可以发送其他topic的消息。
            // 4.事务提交
            producer.commitTransaction();
        } catch (Exception e) {
            // 5.放弃事务
            producer.abortTransaction();
        }
    }

    /**
     * 需要:
     * 1、设置transactional.id
     * 2、设置enable.idempotence
     *
     * @return
     */
    private static Producer buildProducer() {
        // create instance for properties to access producer configs
        Properties props = new Properties();
        // bootstrap.servers是Kafka集群的IP地址。多个时,使用逗号隔开
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        // 设置事务id
        props.put("transactional.id", "first-transactional");
        // 设置幂等性
        props.put("enable.idempotence", true);

        props.put("compression.type", "gzip");
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 10);
        //Specify buffer size in config,这里不进行设置这个属性,如果设置了,还需要执行producer.flush()来把缓存中消息发送出去
        //props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 30);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put("reconnect.backoff.ms ", 20000);
        props.put("retry.backoff.ms", 20000);

        // Kafka消息是以键值对的形式发送,需要设置key和value类型序列化器
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        return producer;
    }

    /**
     * 在一个事务只有消息操作
     */
    public static void onlyConsumeInTransaction() {
        Producer producer = buildProducer();
        // 1.初始化事务
        producer.initTransactions();
        // 2.开启事务
        producer.beginTransaction();
        // 3.kafka读消息的操作集合
        Consumer consumer = buildConsumer();

        while (true) {
            // 3.1 接受消息
            ConsumerRecords<String, String> records = consumer.poll(500);
            try {
                // 3.2 do业务逻辑;
                System.out.println("customer Message---");
                Map<TopicPartition, OffsetAndMetadata> commits = new HashMap<>();
                for (ConsumerRecord<String, String> record : records) {
                    // 3.2.1 处理消息 print the offset,key and value for the consumer records.
                    System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                    // 3.2.2 记录提交偏移量
                    commits.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()));
                }
                // 4.提交偏移量
                producer.sendOffsetsToTransaction(commits, "group001");
                // 5.事务提交
                producer.commitTransaction();
            } catch (Exception e) {
                // 6.放弃事务
                producer.abortTransaction();
            }
        }
    }

    /**
     * 需要:
     * 1、关闭自动提交 enable.auto.commit
     * 2、isolation.level为read_committed
     */
    public static Consumer buildConsumer() {
        Properties props = new Properties();
        // bootstrap.servers是Kafka集群的IP地址。多个时,使用逗号隔开
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        // 消费者群组
        props.put("group.id", "group001");
        // 设置隔离级别
        props.put("isolation.level", "read_committed");
        // 关闭自动提交
        props.put("enable.auto.commit", "false");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        return consumer;

    }
}
