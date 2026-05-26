package com.study.springFW.dto;
import com.study.springFW.model.Student;

public class StudentDetailResponse  {   // này để show ra chi tiết cho detail

    private long id;    
    private String name;
    private String email;
    private int age;
    private double gpa;
    private boolean active;
    
    // không cần cons, vì sẽ dùng mapstruct để map từ entity sang, nên chỉ cần getters và setters là đủ

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public double getGpa() {
        return gpa;
    }
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    
}
