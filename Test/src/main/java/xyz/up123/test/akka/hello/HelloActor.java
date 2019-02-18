package xyz.up123.test.akka.hello;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka
 * @ClassName: HelloActor
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/15 19:44
 * @Version: 1.0
 */
public class HelloActor extends UntypedAbstractActor {
    //定义日志，很重要
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
    /**
     * @method  onReceive
     * @description 重写接收方法
     * @date: 2019/2/15 19:45
     * @author:
     * @param message
     * @return void
     */
    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("HelloActor receive message : " + message + "=========" + (message instanceof HelloMessage));
        //如果消息类型是HelloMessage，则进行处理
        if (message instanceof HelloMessage){
            log.info("Hello " + ((HelloMessage) message).getName() + "!");
        }
    }
}
