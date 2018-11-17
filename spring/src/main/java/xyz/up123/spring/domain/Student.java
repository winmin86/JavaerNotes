package xyz.up123.spring.domain;

/**
 * @ClassName: Student
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/15 19:34
 **/
public class Student {
    private String name;

    private Teacher teacher;

    public Student() {

    }

    public Student(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
