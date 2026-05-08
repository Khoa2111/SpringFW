package com.study.springFW.DTO;

public class CreateStudentRequest { // này để nhận dữ liệu khi tạo student
    private String name;
    private String email;
    private int age;
    private double gpa;
    private boolean active;

    //cons
    public CreateStudentRequest() {
    }

    public CreateStudentRequest(String name, String email, int age, double gpa, boolean active) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.active = active;
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
