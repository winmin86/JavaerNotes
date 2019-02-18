package xyz.up123.test.akka.Test;

import akka.actor.ActorRef;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.Future;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.Test
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/16 15:24
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        ActorTool.create();
        ActorRef ref = ActorTool.actorOf(ActorDemo.class);
        ref.tell("Hello, Ershixiong", ActorRef.noSender());
        ActorTool.shutdown();
    }
}
