package com.study.springFW.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UpdateStudentRequest {
    
    @NotBlank
    private String name;

    @NotBlank @Email
    private String email;
    @Min(16) @Max(20)
    private int age;
    @DecimalMax("4.0") @DecimalMin("0.0")
    private double gpa;
    private boolean active;

    //cons
    public UpdateStudentRequest() {
    }

    public UpdateStudentRequest(String name, String email, int age, double gpa, boolean active) {
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
