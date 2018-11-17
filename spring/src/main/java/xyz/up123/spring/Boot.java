package xyz.up123.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.up123.spring.domain.Student;
import xyz.up123.spring.domain.Teacher;
import xyz.up123.spring.domain.User;
import xyz.up123.spring.server.MessageService;

/**
 * @ClassName: Boot
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/15 19:24
 **/
public class Boot {
    public static void main(String[] args) {
        //在 ClassPath 中寻找 xml 配置文件，根据 xml 文件内容来构建 ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        System.out.println("context 启动成功");
        // 从 context 中取出我们的 Bean，而不是用 new MessageServiceImpl() 这种方式
        //方法一：基于XML的bean定义（需要提供setter方法）
        /*MessageService messageService = context.getBean(MessageService.class);
        Student student = context.getBean(Student.class);
        System.out.println(messageService.getMessage());
        System.out.println(student.toString());
        student.setName("张山");
        System.out.println(student.toString());*/

        //方法二：基于注解的bean定义（不需要提供setter方法）
        //User user = context.getBean(User.class);
        //System.out.println(user.toString());


        Student student= (Student) context.getBean("students");
        Teacher teacher= (Teacher) context.getBean("teachers");
        System.out.println(student.toString());
        System.out.println(teacher.toString());

    }



}
