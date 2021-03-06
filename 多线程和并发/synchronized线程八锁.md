- [链接](https://www.cnblogs.com/wq3435/p/6366913.html)
- [链接](https://www.cnblogs.com/lixuwu/p/5676143.html)
```java
/**
 * 题目：判断打印 "one" or "two"
 * 
 * 1.两个普通同步方法，两个线程 ，标准打印，打印？// one two
 * 2.新增Thread.sleep(3000) 给getOne() 打印？ // 3s 后打印 one two  同一对象，对象锁影响所有的锁成员(非static锁)
 * 3.新增普通方法 getThreee 打印？// 先打印three 三秒后打印 one two 同一对象，对象锁不影响非锁成员
 * 4.两个普通同步方法，两个number对象，打印？ // two 3s后打印 one   不同对象，对象锁没有影响
 * 5.修改getOne()为静态同步方法，一个number对象，打印？     // two 3s后打印 one  对象锁和类锁互不影响
 * 6.修改两个方法均为静态同步方法，一个number对象，打印？// 3s 后打印 one two  类锁 所有对象互斥
 * 7.修改getOne()为静态同步方法，getTwo()为非静态同步方法 ，两个number，一个调用one，一个调用two //two 3s后打印 one  对象锁和类锁互不影响
 * 8.两个都改为静态同步方法，两个number 一个调用getOne(),一个调用getTwo() //3s 后打印 one two  类锁 所有对象互斥  同6
 *
 */
public class TestThread8Monitor {

    public static void main(String[] args) {

        final Number number = new Number();
        final Number number2 = new Number();
        new Thread(new Runnable(){
            @Override
            public void run() {
                number.getOne();
            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                number2.getTwo();
            }
        }).start();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                number.getThree();
            }
        }).start();*/
    }

}

class Number{
    
    public static synchronized void getOne(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }
    
    public static synchronized void getTwo(){
        System.out.println("two");
    }
    
    public void getThree(){
        System.out.println("three");
    }
}
```
---
**Synchronized和Static Synchronized区别**

一个是对象锁（也即实例锁，锁在某一个实例对象上，如果该类是单例，那么该锁也具有全局锁的概念）\
一个是全局锁（也即类锁，该锁针对的是类，无论实例多少个对象，那么线程都共享该锁）

对象锁对应的就是synchronized关键字，而类锁（全局锁）对应的就是static synchronized（或者是锁在该类的class或者classloader对象上）。

**注： static 说明了该类的一个静态资源，不管new了多少个对象，只有一份，所以对该类的所有对象都加了锁！**

**实例锁是锁特定的实例（只要有synchronized就会去锁该实例），全局锁是锁所有的实例。**

synchronized是对类的当前实例（当前对象）进行加锁，防止其他线程同时访问该类的该实例的所有synchronized块（注：**是所有**），注意这里是“类的当前实例”， 类的两个不同实例就没有这种约束了。

static synchronized恰好就是要控制类的所有实例的并发访问，static synchronized是限制多线程中该类的所有实例同时访问jvm中该类所对应的代码块。\
也就是说synchronized相当于 this.synchronized，而static synchronized相当于Something.synchronized.

主要看是 this.synchronized 还是something.synchronized，加锁不区分锁的位置！！！！！  \
this.synchronized 还是something.synchronized是两种不同的锁，互不影响！！！！
多线程中，只要是同一个对象，synchronized不管锁多少方法，对象锁都起作用。\
多线程中，不是同一个对象，对象锁没有约束\
多线程中，不同的对象，类锁具有约束性\
对象锁与类锁互不干扰，与对象无关！

其实总结起来很简单：
1. 一个锁的是类对象，一个锁的是实例对象。
2. 若类对象被lock，则类对象的所有同步方法全被lock；
3. 若实例对象被lock，则该实例对象的所有同步方法全被lock

synchronized methods(){} 与synchronized（this）{}之间没有什么区别，都是对象锁。
只是synchronized methods(){} 便于阅读理解，而synchronized（this）{}可以更精确的控制冲突限制访问区域，有时候表现更高效率。

**面试题**
1.当一个线程进入一个对象的synchronized方法A之后，其它线程是否可进入此对象的synchronized方法B？\
答：不能。其它线程只能访问该对象的非同步方法，同步方法则不能进入。因为非静态方法上的synchronized修饰符要求执行方法时要获得对象的锁，如果已经进入A方法说明对象锁已经被取走，那么试图进入B方法的线程就只能在等锁池（注意不是等待池哦）中等待对象的锁。

2.synchronized关键字的用法？\
答：synchronized关键字可以将对象或者方法标记为同步，以实现对对象和方法的互斥访问，可以用synchronized(对象) { … }定义同步代码块，或者在声明方法时将synchronized作为方法的修饰符。

3.简述synchronized 和java.util.concurrent.locks.Lock的异同？\
答：Lock是Java 5以后引入的新的API，和关键字synchronized相比主要相同点：Lock 能完成synchronized所实现的所有功能；主要不同点：Lock有比synchronized更精确的线程语义和更好的性能，而且不强制性的要求一定要获得锁。synchronized会自动释放锁，而Lock一定要求程序员手工释放，并且最好在finally 块中释放（这是释放外部资源的最好的地方）