- [转自](https://www.cnblogs.com/tanwei81/p/6814022.html)

### 一、注解(annotations)列表 
- @SpringBootApplication：包含了@ComponentScan、@Configuration和@EnableAutoConfiguration注解。其中@ComponentScan让spring Boot扫描到Configuration类并把它加入到程序上下文。
- @Configuration 等同于spring的XML配置文件；使用Java代码可以检查类型安全。
- @EnableAutoConfiguration 自动配置。
- @ComponentScan 组件扫描，可自动发现和装配一些Bean。
- @Component可配合CommandLineRunner使用，在程序启动后执行一些基础任务。
- @RestController注解是@Controller和@ResponseBody的合集,表示这是个控制器bean,并且是将函数的返回值直接填入HTTP响应体中,是REST风格的控制器。
- @Autowired自动导入。
- @PathVariable获取参数。
- @JsonBackReference解决嵌套外链问题。
- @RepositoryRestResourcepublic配合spring-boot-starter-data-rest使用。

### 二、注解(annotations)详解
- @SpringBootApplication：申明让spring boot自动给程序进行必要的配置，这个配置等同于：@Configuration ，@EnableAutoConfiguration 和 @ComponentScan 三个配置。
- @ResponseBody：表示该方法的返回结果直接写入HTTP response body中，一般在异步获取数据时使用，用于构建RESTful的api。在使用@RequestMapping后，返回值通常解析为跳转路径，加上@responsebody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中。比如异步获取json数据，加上@responsebody后，会直接返回json数据。该注解一般会配合@RequestMapping一起使用。
- @Controller：用于定义控制器类，在spring 项目中由控制器负责将用户发来的URL请求转发到对应的服务接口（service层），一般这个注解在类中，通常方法需要配合注解@RequestMapping。
- @RestController：用于标注控制层组件(如struts中的action)，@ResponseBody和@Controller的合集。
- @RequestMapping：提供路由信息，负责URL到Controller中的具体函数的映射。
- @EnableAutoConfiguration：Spring Boot自动配置（auto-configuration）：尝试根据你添加的jar依赖自动配置你的Spring应用。例如，如果你的classpath下存在HSQLDB，并且你没有手动配置任何数据库连接beans，那么我们将自动配置一个内存型（in-memory）数据库”。你可以将@EnableAutoConfiguration或者@SpringBootApplication注解添加到一个@Configuration类上来选择自动配置。如果发现应用了你不想要的特定自动配置类，你可以使用@EnableAutoConfiguration注解的排除属性来禁用它们。
- @ComponentScan：表示将该类自动发现扫描组件。个人理解相当于，如果扫描到有@Component、@Controller、@Service等这些注解的类，并注册为Bean，可以自动收集所有的Spring组件，包括@Configuration类。我们经常使用@ComponentScan注解搜索beans，并结合@Autowired注解导入。可以自动收集所有的Spring组件，包括@Configuration类。我们经常使用@ComponentScan注解搜索beans，并结合@Autowired注解导入。如果没有配置的话，Spring Boot会扫描启动类所在包下以及子包下的使用了@Service,@Repository等注解的类。
- @Import：用来导入其他配置类。
- @ImportResource：用来加载xml配置文件。
- @Service：一般用于修饰service层的组件
- @Repository：使用@Repository注解可以确保DAO或者repositories提供异常转译，这个注解修饰的DAO或者repositories类会被ComponetScan发现并配置，同时也不需要为它们提供XML配置项。
- @Value：注入Spring boot application.properties配置的属性的值。
示例代码：
```java
@Value(value = "#{message}")
private String message;
```

- @Inject：等价于默认的@Autowired，只是没有required属性；
- @Bean:相当于XML中的,放在方法的上面，而不是类，意思是产生一个bean,并交给spring管理。
- @Qualifier：当有多个同一类型的Bean时，可以用@Qualifier(“name”)来指定。与@Autowired配合使用。@Qualifier限定描述符除了能根据名字进行注入，但能进行更细粒度的控制如何选择候选者，具体使用方式如下：
```java
@Autowired 
@Qualifier(value = "demoInfoService") 
private DemoInfoService demoInfoService;
```

- @Service: 注解在类上，表示这是一个业务层bean
- @Controller：注解在类上，表示这是一个控制层bean
- @Repository: 注解在类上，表示这是一个数据访问层bean
- @Component： 注解在类上，表示通用bean ，value不写默认就是类名首字母小写
- @Autowired：按类型注入.默认属性required= true;当不能确定 Spring 容器中一定拥有某个类的Bean 时， 可以在需要自动注入该类 Bean 的地方可以使用 @Autowired(required = false)， 这等于告诉Spring：在找不到匹配Bean时也不抛出BeanCreationException 异常。@Autowired 和 @Qualifier 结合使用时，自动注入的策略就从 byType 转变byName 了。@Autowired可以对成员变量、方法以及构造函数进行注释，而 @Qualifier 的标注对象是成员变量、方法入参、构造函数入参。正是由于注释对象的不同，所以 Spring 不将 @Autowired 和 @Qualifier 统一成一个注释类。
- @Resource(name="name",type="type")：没有括号内内容的话，默认byName。与@Autowired干类似的事。
```markdown
// 下面两种@Resource只要使用一种即可
@Resource(name="userDao")
private UserDao userDao; // 用于字段上

@Resource(name="userDao")
public void setUserDao(UserDao userDao) { // 用于属性的setter方法上
    this.userDao = userDao;
}
```
注：最好是将@Resource放在setter方法上，因为这样更符合面向对象的思想，通过set、get去操作属性，而不是直接去操作属性。

>@Resource装配顺序：\
①如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常。\
②如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常。\
③如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或是找到多个，都会抛出异常。\
④如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配。\
@Resource的作用相当于@Autowired，只不过@Autowired按照byType自动注入。

**区别：**
> @Resource默认按照名称方式进行bean匹配，@Autowired默认按照类型方式进行bean匹配\
  @Resource(import javax.annotation.Resource;)是J2EE的注解，@Autowired(import org.springframework.beans.factory.annotation.Autowired;)是Spring的注解

- @Configuration：注解在类上，表示这是一个IOC容器，相当于spring的配置文件，java配置的方式。 IOC容器的配置类一般与 @Bean 注解配合使用，用 @Configuration 注解类等价与 XML 中配置 beans，用@Bean 注解方法等价于 XML 中配置 bean。
- @Bean： 注解在方法上，声明当前方法返回一个Bean
- @Scope：注解在类上，描述spring容器如何创建Bean实例。它有一下几个值：
```markdown
（1）singleton： 表示在spring容器中的单例，通过spring容器获得该bean时总是返回唯一的实例
（2）prototype：表示每次获得bean都会生成一个新的对象
（3）request：表示在一次http请求内有效（只适用于web应用）
（4）session：表示在一个用户会话内有效（只适用于web应用）
（5）globalSession：表示在全局会话内有效（只适用于web应用）
```

### 三、springMVC相关注解
- @RequestMapping：@RequestMapping("/path")表示该控制器处理所有"/path"的UR L请求。RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。\
用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。该注解有六个属性： 
>params:指定request中必须包含某些参数值是，才让该方法处理。 
 headers:指定request中必须包含某些指定的header值，才能让该方法处理请求。 
 value:指定请求的实际地址，指定的地址可以是URI Template 模式 
 method:指定请求的method类型， GET、POST、PUT、DELETE等 
 consumes:指定处理请求的提交内容类型（Content-Type），如application/json,text/html; 
 produces:指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回

示例：
> @RequestMapping( value = "/url" , method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_UTF8_VALUE )

- @RequestParam：用在方法的参数前面。 
- @PathVariable:路径变量。
>RequestMapping("user/get/mac/{macAddress}") 
 public String getByMacAddress(@PathVariable String macAddress){  
 }

参数与大括号里的名字一样要相同。

### 四、全局异常处理
- @ControllerAdvice：包含@Component。可以被扫描到。统一处理异常。
- @ExceptionHandler（Exception.class）：用在方法上面表示遇到这个异常就执行以下方法。

