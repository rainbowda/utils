package com.utils.demo.bean;

import java.util.Date;
import java.util.List;

public class DemoBean {

    //姓名
    private String name;

    //性别，0->女,1->男
    private Integer sex;

    //出生日期
    private Date birthday;

    //爱好
    private List<String> hobbies;

    public String getName() {
        return name;
    }

    public DemoBean setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSex() {
        return sex;
    }

    public DemoBean setSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public DemoBean setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public DemoBean setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
        return this;
    }
    
}
