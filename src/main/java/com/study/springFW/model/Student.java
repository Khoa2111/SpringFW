package com.study.springFW.model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    //attributes
    @Id     
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment in mysql
    private long id;    //primary key 

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double gpa;

    private boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //cons
    public Student() {
    }
    public Student( String name, String email, int age, double gpa, boolean active, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.active = active;
        this.createdAt = createdAt;
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
     
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    
}
