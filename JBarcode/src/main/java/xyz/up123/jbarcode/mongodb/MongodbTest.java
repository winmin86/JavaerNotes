package xyz.up123.jbarcode.mongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/1 16:23
 * @Description：TODO
 * @Version: V1.0.0
 */
public class MongodbTest {
    public static void main(String[] args) {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("runoob");
            System.out.println("Connect to database successfully");

            //创建集合
            //mongoDatabase.createCollection("user");
            MongoCollection<Document> collection = mongoDatabase.getCollection("user");


            /*FindIterable<Document> documents = collection.find();
            for (Document document : documents) {
                System.out.println(document.getString("site") + "==============");
            }*/

            //插入文档
            /**
             * 1. 创建文档 org.bson.Document 参数为key-value的格式
             * 2. 创建文档集合List<Document>
             * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
             * */
            /*for (int i = 100; i < 120; i++) {
                Document document = new Document("title", "MongoDB").
                    append("description", "database").
                    append("likes", i).
                    append("by", "Fly");
                List<Document> documents = new ArrayList<Document>();
                documents.add(document);
                collection.insertMany(documents);
            }*/
            //collection.deleteOne(Filters.eq("likes", 200));
            //更新文档   将文档中likes=100的文档修改为likes=200
            //collection.updateMany(Filters.eq("likes", 100), new Document("$set",new Document("likes",200)));

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
