package xyz.up123.test.akka.search;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.search
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/15 20:09
 * @Version: 1.0
 */
public class Test {
    /**
     * <p>Title:通过多个搜索引擎查询多个条件，并返回第一条查询结果 </p>
     *
     * @author 韩超 2018/3/6 14:15
     */
    public static void main(String[] args) {
        //通过工具类获取搜索引擎列表
        List<String> engines = EngineUtils.getEngineList();
        //通过 Actor 进行并发查询，获取最先查到的答案
        String result = new Test().getFirstResult("今天你吃了吗？", engines);
        System.out.println(result);
        //打印结果
    }

    /**
     * 通过多个搜索引擎查询，并返回第一条查询结果
     *
     * @param question 查询问题
     * @param engines  查询条件数组
     * @return 最先查出的结果
     */
    public String getFirstResult(String question, List<String> engines) {
        //创建一个Actor系统
        ActorSystem system = ActorSystem.create("search-system");
        //创建一个原子引用用于保存查询结果
        AtomicReference<String> result = new AtomicReference<>();
        //通过静态方法，调用Props的构造器，创建Props对象
        Props props = Props.create(QuestionQuerier.class, question, engines, result);
        //通过system.actorOf(props,name)创建一个 问题查询器Actor
        final ActorRef querier = system.actorOf(props, "master");
        //告诉问题查询器开始查询
        querier.tell(new Object(), ActorRef.noSender());

        //通过while无限循环 等待actor进行查询，知道产生结果
        while (null == result.get()){

        }
        //关闭 Actor系统
        system.terminate();
        //返回结果
        return result.get();
    }
}
