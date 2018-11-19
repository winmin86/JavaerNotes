- [摘自](http://www.cnblogs.com/chengxiao/p/6842045.html)
- [HashMap实现原理及源码分析](http://www.cnblogs.com/chengxiao/p/6059914.html)
- [集合框架源码学习之HashMap(JDK1.8)](https://juejin.im/post/5ab0568b5188255580020e56)

### 0.前奏
HashMap ：HashMap是**线程不安全**的，在并发环境下，可能会**形成环状链表**（扩容时可能造成），导致get操作时，cpu空转，所以，在并发环境中使用HashMap是非常危险的。

HashTable ： HashTable和HashMap的实现原理几乎一样，差别无非是
**1.HashTable不允许key和value为null；
2.HashTable是线程安全的。**
但是HashTable线程安全的策略实现代价却太大了，简单粗暴，**get/put所有相关操作都是synchronized的**，这相当于给整个哈希表加了一把大锁，多线程访问时候，只要有一个线程访问或操作该对象，那其他线程只能阻塞，相当于将所有的操作串行化，在竞争激烈的并发场景中性能就会非常差。
![imgae](img/hashtable.png)

### 1.ConcurrentHashMap实现原理
ConcurrentHashMap采用了非常精妙的"分段锁"策略，ConcurrentHashMap的主干是个Segment数组。
![imgae](img/concurrenthashmap.png)







