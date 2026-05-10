package com.study.springFW.DTO;

import java.time.LocalDateTime;

import com.study.springFW.model.Student;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;

public class StudentDetailResponse  {   // này để show ra chi tiết cho detail

    private long id;    
    private String name;
    private String email;
    private int age;
    private double gpa;
    private boolean active;
    

    //cons
    public StudentDetailResponse () {
    }
    public StudentDetailResponse (Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.age = student.getAge();
        this.gpa = student.getGpa();
        this.active = student.isActive();
    }
    public Long getId() {
        return id;
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
