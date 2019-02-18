package xyz.up123.test.akka.hello;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka
 * @ClassName: HelloMessage
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/15 19:43
 * @Version: 1.0
 */
public class HelloMessage {
    private String name;

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloMessage{" +
                "name='" + name + '\'' +
                '}';
    }
}
