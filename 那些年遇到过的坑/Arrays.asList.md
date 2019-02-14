- [摘自](https://www.cnblogs.com/wang-meng/p/f1532cf23ce049ce63b4bdd62d53659d.html)

### 前言
最近在项目上线的时候发现一个问题，从后台报错日志看：java.lang.UnsupportedOperationException异常。
从代码定位来看，原来是使用了Arrays.asList()方法时把一个数组转化成List列表时，对得到的List列表进行add()和remove()操作, 所以导致了这个问题。

对于这个问题，现在来总结下，当然会总结Arrays下面的一些坑。

### 源代码分析
Arrays.asList返回的是同样的ArrayList，为什么就不能使用add和remove方法呢？

1.查看Arrays.asList 源码 
```java
public static <T> List<T> asList(T... a) {
    return new ArrayList<>(a);
}
```

2.查看此ArrayList结构： 
```java
private static class ArrayList<E> extends AbstractList<E>
        implements RandomAccess, java.io.Serializable
    {
        private static final long serialVersionUID = -2764017481108945198L;
        private final E[] a;

        ArrayList(E[] array) {
            a = Objects.requireNonNull(array);
        }

        @Override
        public int size() {
            return a.length;
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(a, a.length, Object[].class);
        }
```
最主要的是：这个ArrayList类是Arrays的内部静态类，而不是java.util.ArrayList。\
并且这个ArrayList类并没有实现add、remove等方法。

3.再查看AbstractList结构： 
```java
public void add(int index, E element) {
    throw new UnsupportedOperationException();
}

public E remove(int index) {
    throw new UnsupportedOperationException();
}
```
可以发现AbstractList类中add、remove直接抛出了异常。

由此可知Arrays.asList 返回的 List 是一个不可变长度的列表，此列表不再具备原 List 的很多特性，因此慎用 Arrays.asList 方法。

### Arrays.asList的其他坑

1.基本类型的数组和应用类型的数组
```java
int[] intArr = {1,2,3,4,5};
Integer[] integerArr = {1,2,3,4,5};
List<int[]> ints = Arrays.asList(intArr);
List<Integer> integers = Arrays.asList(integerArr);
System.out.println(ints.size()); //输出1
System.out.println(integers.size());  //输出5
```

由上面asList源码`asList(T... a)`我们可以看到返回的 Arrays 的内部类 ArrayList 构造方法接收的是一个类型为 T 的数组，而基本类型是不能作为泛型参数的，所以这里参数 a 只能接收引用类型，自然为了编译通过编译器就把上面的 int[] 数组当做了一个引用参数，所以 size 为 1，要想修改这个问题很简单，将 int[] 换成 Integer[] 即可。所以原始类型不能作为 Arrays.asList 方法的参数，否则会被当做一个参数。

2.Collections.toArray报错问题 
```java
List<String> strs = Arrays.asList("string");
Object[] strArray = strs.toArray();
System.out.println(strArray.getClass());
strArray[0] = new Object(); //错误
strArray[0] = "str"; //正确
```
报错：
```java
class [Ljava.lang.String;
Exception in thread "main" java.lang.ArrayStoreException: java.lang.Object
```
查看Arrays.ArrayList可以发现：
```java
public Object[] toArray() {
    return a.clone();
}
```

因为asList返回的是一个String数组，所以这里toArray返回的其实是String[]类型，只不过是这里做了一个向上转型，将String[]类型转为Object[]类型罢了。\
但是注意，虽然返回的引用为Object[]，但实际的类型还是String[],当你往一个引用类型和实际类型不匹配的对象中添加元素时，就是报错。JDK11已经没有了这个错误。\
具体大家可以参考Java向上转型和向下转型的相关知识点。

### 用Arrays.asList生成普通ArrayList
```java
Integer[] integerArr = {1,2,3,4,5};
List<Integer> integers = new ArrayList(Arrays.asList(integerArr));
integers.add(6);
```
其实就是用Arrays.asList生成的集合重新生成一个集合。

java.util.ArrayList:
```java
public ArrayList(Collection<? extends E> c)
```

