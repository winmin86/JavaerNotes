package xyz.up123.test.akka.hello;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/15 19:46
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        //创建actor系统
        ActorSystem system = ActorSystem.create("hello-system");
        //定义Actor引用
        ActorRef helloActor = system.actorOf(Props.create(HelloActor.class),"hello-actor");

        //向HelloActor发送消息
        helloActor.tell(new HelloMessage("World"),null);
        helloActor.tell(new HelloMessage("Akka Actor"),null);

        //终止Actor系统
        //system.terminate();
    }
}
