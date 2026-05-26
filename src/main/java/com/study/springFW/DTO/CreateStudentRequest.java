package com.study.springFW.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateStudentRequest { // này để nhận dữ liệu khi tạo student
    // phải ghi đủ message để client biết lỗi gì, chứ không thì nó sẽ trả về lỗi mặc định rất khó hiểu
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;
    @Min(value = 16, message = "Age must be at least 16")
    private int age;
    @DecimalMax(value = "4.0", message = "GPA must be between 0.0 and 4.0") @DecimalMin(value = "0.0", message = "GPA must be at least 0.0")
    private double gpa;
    @NotNull(message = "Active status must not be null")
    private Boolean active;
    
    //cons
    public CreateStudentRequest() {
    }

    public CreateStudentRequest(String name, String email, int age, double gpa, Boolean active) {
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
   
    
    
}
