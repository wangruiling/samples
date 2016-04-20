package org.wrl.jpa.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: wangrl
 * @Date: 2016-03-16 10:24
 */
public class Student {
    /** 学生ID **/
    private Integer studentid;
    /** 学生姓名 **/
    private String name;
    private Set<Teacher> teachers=new HashSet<Teacher>();

    public Student() {
        super();
    }

    public Student(String name) {
        super();
        this.name = name;
    }

    @Id
    @GeneratedValue
    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    @Column(nullable=false, length=32)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //@ManyToMany注释表示Student是多对多关系的一边，mappedBy属性定义了Student为双向关系的维护端
    //Teacher表是关系的维护者，owner side，有主导权，它有个外键指向Student表。
    @ManyToMany(mappedBy = "students")
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
