package com.study.springFW.dto;

import jakarta.validation.constraints.NotBlank;

public class StudentSummaryResponse {

    
    private long id;
    private String name;
    private String email;
    private double gpa;
    
    // không cần cons, chỉ cần getters và setters
    
    //getters
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public double getGpa() {
        return gpa;
    }
    
    //setters
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
