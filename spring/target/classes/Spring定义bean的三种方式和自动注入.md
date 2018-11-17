###方法一：基于XML的bean定义（需要提供setter方法）

类参考Student和Teacher
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
 
    <bean id="student" class="test.Student">
        <property name="name" value="张三"/>
        <property name="teacher" ref="teacher"/>
    </bean>
 
    <bean id="teacher" class="test.Teacher">
        <property name="name" value="李四"/>
    </bean>
 
</beans>
```
使用：
```
//在 ClassPath 中寻找 xml 配置文件，根据 xml 文件内容来构建 ApplicationContext
ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
Student student= (Student) context.getBean("student");
Teacher teacher= (Teacher) context.getBean("teacher");

```
###方法二：基于注解的bean定义（不需要提供setter方法）
Spring为此提供了四个注解，这些注解的作用与上面的XML定义bean效果一致，在于将组件交给Spring容器管理。组件的名称默认是类名（首字母变小写），也可以自己修改：\
@Component：当对组件的层次难以定位的时候使用这个注解\
@Controller：表示控制层的组件\
@Service：表示业务逻辑层的组件\
@Repository：表示数据访问层的组件\
使用这些注解的时候还有一个地方需要注意，就是需要在applicationContext.xml中声明
<contex:component-scan...>一项，指明Spring容器扫描组件的包目录。

```
@Component("user")
public class User {
    @Value("1")
    private Integer id;

    @Value("Ershixiong")
    private String name;

    @Value("18")
    private Integer age;
    ...
}
```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
 
    <!--扫描组件的包目录-->
    <context:component-scan base-package="test"/>
 
</beans>
```
使用：
```
//在 ClassPath 中寻找 xml 配置文件，根据 xml 文件内容来构建 ApplicationContext
ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
User user= (Student) context.getBean("User");
```

###方法三：基于Java类的bean定义（需要提供setter方法）
```
@Configuration
public class Beans {
    @Bean
    public Student student(){
        Student student=new Student();
        student.setName("张三");
        student.setTeacher(teacher());
        return student;
    }

    @Bean
    public Teacher teacher(){
        Teacher teacher=new Teacher();
        teacher.setName("李四");
        return teacher;
    }

}
```

使用
```
AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(Beans.class);
Student student= (Student) context.getBean("student");
Teacher teacher= (Teacher) context.getBean("teacher");
```

###两种注入方式
- **构造器注入** （constructor-arg：通过构造函数注入。）
```
<bean id="students" class="xyz.up123.spring.domain.Student">
    <constructor-arg name="name" value="小明"></constructor-arg>
    <constructor-arg name="teacher" ref="teachers"></constructor-arg>
</bean>
``` 
- **Setter方法注入** （property：通过setter对应的方法注入。）
```
<bean id="teacher" class="xyz.up123.spring.domain.Teacher">
    <property name="name" value="李四"/>
</bean>
```
- **接口注入**

```
构造函数注入理由：

构造函数保证重要属性预先设置；
无需提供每个属性的setter方法，减少类的方法个数；
可以更好地封装类变量，避免外部错误调用。

属性注入理由：

属性过多时，构造函数变的臃肿；
构造函数注入灵活性不强，有时需要为属性注入null值；
多个构造函数时，配置上产生歧义，复杂度升高；
构造函数不利于类的继承和扩展；
构造函数注入会引起循环依赖的问题。 
```
###Bean的作用域scope
- singleton(单例模式)：这种bean范围是**默认**的，这种范围确保不管接受到多少个请求，每个容器中只有一个bean的实例，单例的模式由bean factory自身来维护。
- prototype(原型模式/多例模式)：一个bean（对象）定义对应多个对象实例
- request：在请求bean范围内会每一个来自客户端的网络请求创建一个实例，在请求完成以后，bean会失效并被垃圾回收器回收。
- Session：与request范围类似，确保每个session中有一个bean的实例，在session过期后，bean会随之失效。
- global-session：global-session和Portlet应用相关。当你的应用部署在Portlet容器中工作时，它包含很多portlet。如果你想要声明让所有的portlet共用全局的存储变量的话，那么这全局变量需要存储在global-session中。
全局作用域与Servlet中的session作用域效果相同。


###Spring的自动注入

####1.基于XML的自动装配
Spring提供了五种自动装配的类型
- no：顾名思义， 显式指明不使用Spring的自动装配功能
- byName：根据属性和组件的名称匹配关系来实现bean的自动装配
- byType：根据属性和组件的类型匹配关系来实现bean的自动装配，有多个适合类型的对象时装配失败
- constructor：与byType类似是根据类型进行自动装配，但是要求待装配的bean有相应的构造函数
- autodetect：利用Spring的自省机制判断使用byType或是constructor装配

示例
```
<bean id="student" class="test.Student" autowire="byName">
    <property name="name" value="张三"/>
</bean>
```
####2.基于注解的自动装配

- @Resource默认是使用byName进行装配\
- @Autowired默认使用byType进行装配

示例
```
@Resource
private Teacher teacher;
```



