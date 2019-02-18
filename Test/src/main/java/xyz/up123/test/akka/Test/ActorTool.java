package xyz.up123.test.akka.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.Test
 * @ClassName: ActorTool
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/16 15:13
 * @Version: 1.0
 */
public class ActorTool {
    private static ActorSystem actor = null;

    public static void create() {
        actor = ActorSystem.create();
    }

    public static void create(String name) {
        actor = ActorSystem.create(name);
    }

    public static ActorRef actorOf(Class clazz) {
        return actor.actorOf(Props.create(clazz));
    }

    public static ActorRef actorOf(Class clazz, String name) {
        return actor.actorOf(Props.create(clazz), name);
    }

    public static void shutdown() {
        actor.terminate();
    }
}
