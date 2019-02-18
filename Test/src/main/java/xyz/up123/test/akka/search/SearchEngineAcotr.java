package xyz.up123.test.akka.search;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.search
 * @ClassName: SearchEngineAcotr
 * @Description: 搜索引擎Actor
 * @Author: ershixiong
 * @CreateDate: 2019/2/15 20:05
 * @Version: 1.0
 */
public class SearchEngineAcotr extends UntypedAbstractActor {
    //定义Actor日志
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
    /**
     * <p>Title: Actor都需要重写消息接收处理方法</p>
     *
     * @author 韩超 2018/3/6 14:42
     */
    @Override
    public void onReceive(Object message) throws Throwable {
        //如果消息是指定的类型Message，则进行处理，否则不处理
        if (message instanceof QueryTerms) {
            log.info("接收到搜索条件：" + ((QueryTerms) message).getQuestion());
            //通过工具类进行一次搜索引擎查询
            String result = EngineUtils.searchByEngine(((QueryTerms) message).getQuestion(), ((QueryTerms) message).getEngine());
            //通过getSender().tell(result,actor)将actor的 处理结果[result] 发送消息的发送者[getSender()]
            //通过getSender获取消息的发送方
            //通过getSelf()获取当前Actor
            getSender().tell(new QueryResult(result), getSelf());
        } else {
            unhandled(message);
        }
    }

}
