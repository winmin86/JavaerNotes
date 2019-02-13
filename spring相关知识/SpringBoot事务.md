- [摘自]("https://blog.csdn.net/happydecai/article/details/80338053"")

```
ServiceA {
    //自定义的transactionManager
    @Transactional(transactionManager = "primaryTransactionManager", propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=2000,rollbackFor=Exception.class)
    void methodA() {  
        ServiceB.methodB();  
    }  
}      
ServiceB {
    @Transactional(transactionManager = "primaryTransactionManager", propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=2000,rollbackFor=Exception.class)
    void methodB() {  
    }           
}  
```
### 〇.事务的嵌套概念
所谓事务的嵌套就是两个事务方法之间相互调用。spring事务开启 ，或者是基于接口的或者是基于类的代理被创建
（注意一定要是代理，不能手动new 一个对象，并且此类（有无接口都行）一定要被代理——spring中的bean只要纳入了IOC管理都是被代理的）。
所以<font color=#0000ff size=18>在同一个类中一个方法调用另一个方法有事务的方法，事务是不会起作用的。</font>
Spring默认情况下会对运行期例外(RunTimeException)，即uncheck异常，进行事务回滚。
如果遇到checked异常就不回滚。
如何改变默认规则：

1 让checked例外也回滚：在整个方法前加上 @Transactional(rollbackFor=Exception.class)

2 让unchecked例外不回滚： @Transactional(notRollbackFor=RunTimeException.class)

3 不需要事务管理的(只查询的)方法：@Transactional(propagation=Propagation.NOT_SUPPORTED)
上面三种方式也可在xml配置

PS:将派生于Error或者RuntimeException的异常称为unchecked异常，所有其他的异常成为checked异常。\
![img](img/exception.jpg)
---
### 一.spring事务传播属性propagation 
在枚举Propagation（TransactionDefinition接口类似）中一共定义了7种事务传播属性：
0. REQUIRED(0),支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
1. SUPPORTS(1),支持当前事务，如果当前没有事务，就以非事务方式执行。 
2. MANDATORY(2),支持当前事务，如果当前没有事务，就抛出异常。 
3. REQUIRES_NEW(3),新建事务，如果当前存在事务，把当前事务挂起。 
4. NOT_SUPPORTED(4),以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
5. NEVER(5),以非事务方式执行，如果当前存在事务，则抛出异常。 
6. NESTED(6),如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。

##### (0).Propagation.REQUIRED
假如当前正要执行的事务不在另外一个事务里，那么就起一个新的事务\ 
- 1、如果ServiceA.methodA已经起了事务，这时调用ServiceB.methodB，
ServiceB.methodB看到自己已经运行在ServiceA.methodA的事务内部，就不再起新的事务。
这时只有外部事务并且他们是共用的，所以这时**ServiceA.methodA或者ServiceB.methodB
无论哪个发生异常methodA和methodB作为一个整体都将一起回滚。**
- 2、如果ServiceA.methodA没有事务，ServiceB.methodB就会为自己分配一个事务。
这样，在ServiceA.methodA中是没有事务控制的。**只是在ServiceB.methodB内的任何地方出现异常，
ServiceB.methodB将会被回滚，不会引起ServiceA.methodA的回滚。**
##### (1).Propagation.SUPPORTS
如果当前在事务中，即以事务的形式运行，如果当前不再一个事务中，那么就以非事务的形式运行。
##### (2).Propagation.MANDATORY
必须在一个事务中运行。也就是说，他只能被一个父事务调用。否则，他就要抛出异常。
##### (3).Propagation.REQUIRES_NEW
启动一个新的, 不依赖于环境的 "内部" 事务. 这个事务将被完全 commited 或 rolled back 而不依赖于外部事务, 它拥有自己的隔离范围, 自己的锁, 等等. 当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行. 
比如我们设计ServiceA.methodA的事务级别为Propagation.REQUIRED，ServiceB.methodB的事务级别为Propagation.REQUIRES_NEW，那么当执行到ServiceB.methodB的时候，
ServiceA.methodA所在的事务就会挂起，ServiceB.methodB会起一个新的事务，等待ServiceB.methodB的事务完成以后，他才继续执行。他与Propagation.REQUIRED 的事务区别在于事务的回滚程度了。
因为ServiceB.methodB是新起一个事务，那么就是存在两个不同的事务。\
1、如果ServiceB.methodB已经提交，那么ServiceA.methodA失败回滚，ServiceB.methodB是不会回滚的。\
2、如果ServiceB.methodB失败回滚，如果他抛出的异常被ServiceA.methodA的try..catch捕获并处理，
ServiceA.methodA事务仍然可能提交；如果他抛出的异常未被ServiceA.methodA捕获处理，ServiceA.methodA事务将回滚。

使用场景：
不管业务逻辑的service是否有异常，Log Service都应该能够记录成功，所以Log Service的传播属性可以配为此属性。最下面将会贴出配置代码。
##### (4).Propagation.NOT_SUPPORTED
当前不支持事务。比如ServiceA.methodA的事务级别是Propagation.REQUIRED ，而ServiceB.methodB的事务级别是Propagation.SUPPORTED ，
那么当执行到ServiceB.methodB时，ServiceA.methodA的事务挂起，而他以非事务的状态运行完，再继续ServiceA.methodA的事务。
##### (5).Propagation.NEVER
不能在事务中运行。假设ServiceA.methodA的事务级别是Propagation.REQUIRED， 而ServiceB.methodB的事务级别是Propagation.NEVER ，那么ServiceB.methodB就要抛出异常了。     

##### (6).Propagation.NESTED
开始一个 "嵌套的" 事务,  它是已经存在事务的一个真正的子事务. 潜套事务开始执行时,  它将取得一个 savepoint. 如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分, 只有外部事务结束后它才会被提交. 

比如我们设计ServiceA.methodA的事务级别为Propagation.REQUIRED，ServiceB.methodB的事务级别为Propagation.NESTED，那么当执行到ServiceB.methodB的时候，ServiceA.methodA所在的事务就会挂起，ServiceB.methodB会起一个新的子事务并设置savepoint，等待ServiceB.methodB的事务完成以后，他才继续执行。。因为ServiceB.methodB是外部事务的子事务，那么\
1、如果ServiceB.methodB已经提交，那么ServiceA.methodA失败回滚，ServiceB.methodB也将回滚。\
2、如果ServiceB.methodB失败回滚，如果他抛出的异常被ServiceA.methodA的try..catch捕获并处理，ServiceA.methodA事务仍然可能提交；如果他抛出的异常未被ServiceA.methodA捕获处理，ServiceA.methodA事务将回滚。

理解Nested的关键是savepoint。他与Propagation.REQUIRES_NEW的区别是：
Propagation.REQUIRES_NEW 完全是一个新的事务,它与外部事务相互独立； 而 Propagation.NESTED 则是外部事务的子事务, 如果外部事务 commit, 嵌套事务也会被 commit, 这个规则同样适用于 roll back. 

在 spring 中使用 Propagation.NESTED的前提：
1. 我们要设置 transactionManager 的 nestedTransactionAllowed 属性为 true(Spring Boot 2.X 默认是true), 注意, 此属性默认为 false!!! 
2. java.sql.Savepoint 必须存在, 即 jdk 版本要 1.4+ 
3. Connection.getMetaData().supportsSavepoints() 必须为 true, 即 jdbc drive 必须支持 JDBC 3.0 
确保以上条件都满足后, 你就可以尝试使用 PROPAGATION_NESTED 了. 

---
### 二.spring事务隔离级别Isolation

0. DEFAULT(-1),使用数据库默认的隔离级别。 
1. READ_UNCOMMITTED(1),它允许另外一个事务可以看到这个事务未提交的数据，可能导致脏读、不可重复读和幻读。 
2. READ_COMMITTED(2),保证一个事物提交后才能被另外一个事务读取，另外一个事务不能读取该事物未提交的数据，可能导致重复读和幻读。
3. REPEATABLE_READ(4),该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。
4. SERIALIZABLE(8),完全服从ACID的原则，确保不发生脏读、不可重复读和幻读。 

> 什么是脏数据，脏读，不可重复读，幻觉读？
  脏读: 指当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，这时，
       另外一个事务也访问这个数据，然后使用了这个数据。因为这个数据是还没有提交的数据， 那么另外一
       个事务读到的这个数据是脏数据，依据脏数据所做的操作可能是不正确的。    
> 不可重复读: 指在一个事务内，多次读同一数据。在这个事务还没有结束时，另外一个事务也访问该同一数据。
               那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的数据
               可能是不一样的。这样就发生了在一个事务内两次读到的数据是不一样的，因此称为是不可重复读。            
> 幻觉读: 指当事务不是独立执行时发生的一种现象，例如第一个事务对一个表中的数据进行了修改，这种修改涉及
           到表中的全部数据行。同时，第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据。那么，
           以后就会发生操作第一个事务的用户发现表中还有没有修改的数据行，就好象发生了幻觉一样。

---

### 三.spring事务其他属性
		
属性 | 类型 | 描述
---|---|---
value | String | 可选的限定描述符，指定使用的事务管理器
propagation | enum: Propagation | 可选的事务传播行为设置
isolation | enum: Isolation | 可选的事务隔离级别设置
readOnly | boolean | 读写或只读事务，默认读写
timeout | int (in seconds granularity) | 事务超时时间设置
rollbackFor | Class对象数组，必须继承自Throwable | 导致事务回滚的异常类数组
rollbackForClassName | 类名数组，必须继承自Throwable | 导致事务回滚的异常类名字数组
noRollbackFor | Class对象数组，必须继承自Throwable | 不会导致事务回滚的异常类数组
noRollbackForClassName | 类名数组，必须继承自Throwable | 不会导致事务回滚的异常类名字数组

---
### 四.用法注意
@Transactional 可以作用于接口、接口方法、类以及类方法上。当作用于类上时，
该类的所有 public 方法将都具有该类型的事务属性，同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。

虽然 @Transactional 注解可以作用于接口、接口方法、类以及类方法上，
但是 Spring 建议不要在接口或者接口方法上使用该注解，因为这只有在使用基于接口的代理时它才会生效。
另外， `@Transactional 注解应该只被应用到 public 方法上，这是由 Spring AOP 的本质决定的。`如果你在 protected、private 或者默认可见性的方法上使用 @Transactional 注解，这将被忽略，也不会抛出任何异常。

默认情况下，只有来自外部的方法调用才会被AOP代理捕获，也就是，
**类内部方法调用本类内部的其他方法并不会引起事务行为**，即使被调用方法使用@Transactional注解进行修饰。

示例：
```
@Service
public class UserServerImpl implements UserServer {

    @Autowired
    private UserRoleServer userRoleServer;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(transactionManager = "primaryTransactionManager", propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=2000,rollbackFor=Exception.class)
    public int insert(User user) {
        int rs = userDao.insert(user);
        List<Role> roles = user.getRoles();
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUid(user.getUid());
            userRoles.add(userRole);
        }
        userRoleServer.insert(userRoles);
        return rs;
    }
}

@Service
public class UserRoleServerImpl implements UserRoleServer {

    @Autowired
    private UserRoleDao userRoleDao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=2000,rollbackFor=Exception.class)
    public int insert(List<UserRole> userRoles) {
        return userRoleDao.insert(userRoles);
    }
}
```


