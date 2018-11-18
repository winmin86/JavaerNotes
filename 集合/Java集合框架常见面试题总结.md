- [摘自](https://github.com/Snailclimb/JavaGuide/blob/master/Java%E7%9B%B8%E5%85%B3/Java%E9%9B%86%E5%90%88%E6%A1%86%E6%9E%B6%E5%B8%B8%E8%A7%81%E9%9D%A2%E8%AF%95%E9%A2%98%E6%80%BB%E7%BB%93.md)这个GitHub的面试题很不错，强烈推荐

#### 1.List，Set,Map三者的区别及总结
- List：对付顺序的好帮手
List接口存储一组不唯一（可以有多个元素引用相同的对象），有序的对象
- Set:注重独一无二的性质
不允许重复的集合。不会有多个元素引用相同的对象。
- Map:用Key来搜索的专家
使用键值对存储。Map会维护与Key有关联的值。两个Key可以引用相同的对象，但Key不能重复，典型的Key是String类型，但也可以是任何对象。

#### 2.Arraylist、LinkedList、Vector的区别
- Arraylist底层使用的是`数组`（**存读数据效率高，插入删除特定位置效率低**），不是同步的。
- LinkedList底层使用的是`双向循环链表`数据结构（**插入，删除效率特别高**），不是同步的。
- Vector类的所有方法都是`同步`的。可以由多个线程安全地访问一个Vector对象，但是一个线程访问Vector ，代码要在同步操作上耗费大量的时间。

#### 3.HashMap、Hashtable、ConcurrentHashMap的区别
- HashMap是非线程安全的，HashTable、ConCurrentHashMap是线程安全的；HashTable内部的方法基本都经过synchronized修饰。
- 因为线程安全的问题，HashMap要比HashTable效率高一点，HashTable基本被淘汰。
- HashMap的键值对允许有null，但是HashTable、ConCurrentHashMap都不允许。
- Hashtable和HashMap有几个主要的不同：线程安全以及速度。仅在你需要完全的线程安全的时候使用Hashtable，而如果你使用Java5或以上的话，请使用ConcurrentHashMap吧
- ConcurrentHashMap对整个桶数组进行了分割分段(Segment)，然后在每一个分段上都用lock锁进行保护，相对于HashTable的synchronized锁的粒度更精细了一些，
并发性能更好，而HashMap没有锁机制，不是线程安全的。（JDK1.8之后ConcurrentHashMap启用了一种全新的方式实现,利用CAS算法。）

#### 4.HashSet如何检查重复
当你把对象加入HashSet时，HashSet会先计算对象的hashcode值来判断对象加入的位置，同时也会与其他加入的对象的hashcode值作比较，如果没有相符的hashcode，HashSet会假设对象没有重复出现。\
但是如果发现有相同hashcode值的对象，这时会调用equals（）方法来检查hashcode相等的对象是否真的相同。如果两者相同，HashSet就不会让加入操作成功。（摘自我的Java启蒙书《Head fist java》第二版）

##### hashCode（）与equals（）的相关规定：
- 如果两个对象相等，则hashcode一定也是相同的
- 两个对象相等,对两个equals方法返回true
- **两个对象有相同的hashcode值，它们也不一定是相等的**
- 综上，equals方法被覆盖过，则hashCode方法也必须被覆盖
- hashCode()的默认行为是对堆上的对象产生独特值。**如果没有重写hashCode()，则该class的两个对象无论如何都不会相等**（即使这两个对象指向相同的数据）。

##### ==与equals的区别
1. ==是判断两个变量或实例是不是指向同一个**内存空间** equals是判断两个变量或实例所指向的内存空间的**值**是不是相同
2. ==是指**对内存地址进行比较**，equals()是对字符串的**内容**进行比较
3. ==指引用是否相同，equals()指的是值是否相同

#### 5.comparable 和 comparator的区别
- comparable接口实际上是出自java.lang包 它有一个 compareTo(Object obj)方法用来排序
- comparator接口实际上是出自 java.util 包它有一个compare(Object obj1, Object obj2)方法用来排序
一般我们需要对一个集合使用自定义排序时，我们就要重写compareTo方法或compare方法，当我们需要对某一个集合实现两种排序方式，比如一个song对象中的歌名和歌手名分别采用一种排序方法的话，我们可以重写compareTo方法和使用自制的Comparator方法或者以两个Comparator来实现歌名排序和歌星名排序，第二种代表我们只能使用两个参数版的Collections.sort().

```java
//必须实现Comparable
public class User implements Comparable<User>{
    private Integer id;
    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //可以设置多种排序
    public int compareTo(User o) {
        return this.id.compareTo(o.id);
    }
}
```

```java
// 定制排序的用法
Collections.sort(arrayList, new Comparator<Integer>() {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o2.compareTo(o1);
    }
});
```
**对objects数组进行排序，我们可以用Arrays.sort()方法**\
**对objects的集合进行排序，需要使用Collections.sort()方法**


