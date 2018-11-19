保证同步的方法

```java
Collection<T> synchronizedCollection(Collection<T> c);
List<T> synchronizedList(List<T> list);    
Map<K,V> synchronizedMap(Map<K,V> m);
Set<T> synchronizedSet(Set<T> s)
SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m)
SortedSet<T> synchronizedSortedSet(SortedSet<T> s);
NavigableMap<K,V> synchronizedNavigableMap(NavigableMap<K,V> m);
NavigableSet<T> synchronizedNavigableSet(NavigableSet<T> s);
```
Collections中分别定义了SynchronizedCollection、SynchronizedList、SynchronizedSet、SynchronizedMap等内部类，在构造函数里面弄了一个 Object mutx = this,让后所有的方法在调用的时候都synchronized(mutex)。

```java

final Object mutex;     // Object on which to synchronize

SynchronizedCollection(Collection<E> c) {
    this.c = Objects.requireNonNull(c);
    mutex = this;
}

SynchronizedCollection(Collection<E> c, Object mutex) {
    this.c = Objects.requireNonNull(c);
    this.mutex = Objects.requireNonNull(mutex);
}

public int size() {
    synchronized (mutex) {return c.size();}
}
public boolean isEmpty() {
    synchronized (mutex) {return c.isEmpty();}
}
public boolean contains(Object o) {
    synchronized (mutex) {return c.contains(o);}
}
public Object[] toArray() {
    synchronized (mutex) {return c.toArray();}
}
public <T> T[] toArray(T[] a) {
    synchronized (mutex) {return c.toArray(a);}
}

public Iterator<E> iterator() {
    return c.iterator(); // Must be manually synched by user!
}

public boolean add(E e) {
    synchronized (mutex) {return c.add(e);}
}
public boolean remove(Object o) {
    synchronized (mutex) {return c.remove(o);}
}

```

Collections其他方法

```java
List<Integer> list = new ArrayList<Integer>();
list.add(34);
list.add(55);
list.add(56);
list.add(89);
list.add(12);
list.add(23);
list.add(126);
System.out.println(list);

//对集合进行排序
Collections.sort(list);
System.out.println(list);

//对集合进行随机排序
Collections.shuffle(list);
System.out.println(list);

//获取集合最大值、最小值
int max = Collections.max(list);
int min = Collections.min(list);
System.out.println("Max:" + max + " Min: " + min);

List<String> list2 = Arrays.asList("Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday".split(","));
System.out.println(list2);

//查找集合指定元素，返回元素所在索引
//若元素不存在，n表示该元素最有可能存在的位置索引
int index1 = Collections.binarySearch(list2, "Thursday");
int index2 = Collections.binarySearch(list2, "TTTTTT");
System.out.println(index1);
int n = -index2 - 1;

//查找子串在集合中首次出现的位置
List<String> subList = Arrays.asList("Friday,Saturday".split(","));
int index3 = Collections.indexOfSubList(list2, subList);
System.out.println(index3);
int index4 = Collections.lastIndexOfSubList(list2, subList);
System.out.println(index4);

//替换集合中指定的元素，若元素存在返回true，否则返回false
boolean flag = Collections.replaceAll(list2, "Sunday", "tttttt");
System.out.println(flag);
System.out.println(list2);

//反转集合中的元素的顺序
Collections.reverse(list2);
System.out.println(list2);

//集合中的元素向后移动k位置，后面的元素出现在集合开始的位置
Collections.rotate(list2, 3);
System.out.println(list2);

//将集合list3中的元素复制到list2中，并覆盖相应索引位置的元素
List<String> list3 = Arrays.asList("copy1,copy2,copy3".split(","));
Collections.copy(list2, list3);
System.out.println(list2);

//交换集合中指定元素的位置
Collections.swap(list2, 0, 3);
System.out.println(list2);

//替换集合中的所有元素，用对象object
Collections.fill(list2, "替换");
System.out.println(list2);

//生成一个指定大小与内容的集合
List<String> list4 = Collections.nCopies(5, "哈哈");
System.out.println(list4);

//为集合生成一个Enumeration
List<String> list5 = Arrays.asList("I love my country!".split(" "));
System.out.println(list5);
Enumeration<String> e = Collections.enumeration(list5);
while (e.hasMoreElements()) {
System.out.println(e.nextElement());
}
```