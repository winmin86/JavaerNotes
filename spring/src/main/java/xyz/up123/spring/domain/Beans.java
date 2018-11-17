package xyz.up123.spring.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: Beans
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/15 20:08
 **/
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
