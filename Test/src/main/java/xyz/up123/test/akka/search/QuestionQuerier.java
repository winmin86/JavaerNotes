package xyz.up123.test.akka.search;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.search
 * @ClassName: QuestionQuerier
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/15 20:07
 * @Version: 1.0
 */
public class QuestionQuerier extends UntypedAbstractActor {

    //定义Actor日志
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
    /**
     * 搜索引擎列表
     */
    private List<String> engines;
    /**
     * 搜索结果
     */
    private AtomicReference<String> result;
    /**
     * 问题
     */
    private String question;

    public QuestionQuerier(String question, List<String> engines, AtomicReference<String> result) {
        this.question = question;
        this.engines = engines;
        this.result = result;
    }

    /**
     * <p>Title: Actor都需要重写消息接收处理方法</p>
     *
     * @author 韩超 2018/3/6 16:35
     */
    @Override
    public void onReceive(Object message) throws Throwable {
        //如果收到查询结果，则对查询结果进行处理
        if (message instanceof QueryResult) {//如果消息是指定的类型Result，则进行处理，否则不处理
            log.info("接收到搜索结果：" + ((QueryResult) message).getResult());
            //通过CAS设置原子引用的值
            result.compareAndSet(null, ((QueryResult) message).getResult());
            //如果已经查询到了结果，则停止Actor
            //通过getContext()获取ActorSystem的上下文环境
            //通过getContext().stop(self())停止当前Actor
            getContext().stop(self());
        } else {//如果没有收到处理结果，则创建搜索引擎Actor进行查询
            log.info("开始创建搜索引擎进行查询");

            //使用原子变量去测试Actor的创建是否有序
            AtomicInteger count = new AtomicInteger(1);

            //针对每一个搜索引擎，都创建一个Actor
            for (String engine : engines) {
                log.info("为" + engine + "创建第" + count + "个搜索引擎Actor....");
                count.getAndIncrement();

                //通过actorOf(Props,name)创建Actor
                //通过Props.create(Actor.class)创建Props
                ActorRef fetcher = this.getContext().actorOf(Props.create(SearchEngineAcotr.class), "fetcher-" + engine.hashCode());
                //创建查询条件
                QueryTerms msg = new QueryTerms(question, engine);
                //将查询条件告诉Actor
                fetcher.tell(msg, self());
            }
        }
    }

}
