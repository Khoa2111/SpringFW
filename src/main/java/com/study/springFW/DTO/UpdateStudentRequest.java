package com.study.springFW.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UpdateStudentRequest {
    
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank") @Email(message = "Invalid email format")
    private String email;
    @Min(value = 16, message = "Age must be at least 16") 
    private Integer age;
    @DecimalMax(value = "4.0", message = "GPA must be between 0.0 and 4.0") @DecimalMin(value = "0.0", message = "GPA must be at least 0.0")
    private Double gpa;
    private Boolean active;

    //cons
    public UpdateStudentRequest() {
    }

    public UpdateStudentRequest(String name, String email, Integer age, Double gpa, Boolean active) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
}
