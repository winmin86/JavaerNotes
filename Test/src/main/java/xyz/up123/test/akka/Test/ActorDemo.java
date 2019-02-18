package xyz.up123.test.akka.Test;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.Test
 * @ClassName: ActorDemo
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/16 15:08
 * @Version: 1.0
 */
public class ActorDemo extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger( this .getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            log.info(message.toString());
        } else {
            unhandled(message);
        }
    }
}
