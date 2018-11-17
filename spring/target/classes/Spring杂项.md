###1.什么是Spring?
Spring是一个容器框架，用来装javabean（java对象），中间层框架（万能胶）可以起一个连接作用，比如说把Struts和hibernate粘合在一起运用。简单来说，**Spring是一个轻量级的控制反转(IoC)和面向切面(AOP)的容器框架**。

###2.使用Spring的好处

####1. 轻量级框架 
Spring是轻量级框架,基本的版本大约2M
####2. 控制反转   
Spring通过控制反转实现松散耦合,对象们给它们的依赖,而不是创建或者查找依赖的对象们,方便解耦,简化开发
####3. 面相切面的编程 AOP
Spring支持面相切面的编程,并且把应用业务逻辑和系统分开
####4. 容器 
Spring 包含并管理应用中对象的生命周期和配置
####5. MVC框架 
Spring的WEB框架是个精心设计的框架,是WEB框架的一个很好的替代品
####6. 事务管理 
Spring 提供一个持续的事务管理接口,可以扩展到上至本地事务下至全局事务(JTA)
####7. 异常处理 
Spring 提供方便的API把具体技术的相关异常(比如由JDBC Hibernate or JDO 抛出的) 转化为一致的 unchecked异常

###IOC控制反转
说的是创建对象实例的控制权从代码控制剥离到IOC容器控制，实际就是你在xml文件控制，侧重于原理。

###DI依赖注入
说的是创建对象实例时，为这个对象注入属性值或其他对象实例后，侧重于实现。

###Context
####ClassPathXmlApplicationContext
ClassPathXmlApplicationContext 默认会去 **classPath 路径**下找。classPath 路径指的就是编译后的 classes 目录。
```
//单配置文件方式一
BeanFactory beanFactory=new ClassPathXmlApplicationContext("application.xml");

//单配置文件方式二
BeanFactory beanFactory=new ClassPathXmlApplicationContext("classpath:application.xml");

//多个配置文件
BeanFactory beanFactory=new ClassPathXmlApplicationContext(new String[]{"application.xml"});

//绝对路径需加“file:”前缀
BeanFactory beanFactory = new ClassPathXmlApplicationContext("file:D:\work\springtest\src\main\resources\application.xml");
```
“classpath:” 是可以缺省的。\
**如果是绝对路径，必须加上 “file:” 前缀，不可缺省。**

####FileSystemXmlApplicationContext
FileSystemXmlApplicationContext 默认是去**项目的路径**下加载，可以是相对路径，也可以是绝对路径，若是绝对路径，**“file:” 前缀可以缺省**。

```
//classes目录
BeanFactory beanFactory=new FileSystemXmlApplicationContext("classpath:application.xml");

//项目路径相对路径
BeanFactory beanFactory=new FileSystemXmlApplicationContext("src\main\resources\application.xml");

//多配置文件
BeanFactory beanFactory=new FileSystemXmlApplicationContext(new String[]{"src\main\resources\application.xml"});

//绝对目录
BeanFactory beanFactory=new FileSystemXmlApplicationContext(new String[]{"D:\work\springtest\src\main\resources\application.xml"});
```

####BeanFactory和ApplicationContext有什么区别

- 相同点\
上述两者都是通过加载XMl配置文件的方式加载Bean,而后者是前者的扩展,提供了更多的功能,即**ApplicationContext拥有BeanFactory的全部功能**,在绝大多数的"典型的"企业应用和系统,ApplicationContext都优先于BeanFactory.

- 不同点\
**BeanFactory是延迟加载**,如果一个Bean当中存在属性没有加载,会在第一次调用getBean()方法的时候报错,而ApplicationContext会在读取Xml文件后,如果配置文件没有错误,就会将所有的Bean加载到内存中,缺点就是在Bean较多的时候比较占内存,程序启动较慢.

ApplicationContext的功能：
- 提供了支持国际化的文本消息
- 统一的资源文件读取方式
- 已在监听器中注册的bean的事件

####Bean的生命周期活动
    
    1.Bean的建立， 由BeanFactory读取Bean定义文件，并生成各个实例
    2.Setter注入，执行Bean的属性依赖注入
    3.BeanNameAware的setBeanName(), 如果实现该接口，则执行其setBeanName方法
    4.BeanFactoryAware的setBeanFactory()，如果实现该接口，则执行其setBeanFactory方法
    5.BeanPostProcessor的processBeforeInitialization()，如果有关联的processor，则在Bean初始化之前都会执行这个实例的processBeforeInitialization()方法
    6.InitializingBean的afterPropertiesSet()，如果实现了该接口，则执行其afterPropertiesSet()方法
    7.Bean定义文件中定义init-method
    8.BeanPostProcessors的processAfterInitialization()，如果有关联的processor，则在Bean初始化之前都会执行这个实例的processAfterInitialization()方法
    9.DisposableBean的destroy()，在容器关闭时，如果Bean类实现了该接口，则执行它的destroy()方法
    10.Bean定义文件中定义destroy-method，在容器关闭时，可以在Bean定义文件中使用“destory-method”定义的方法

