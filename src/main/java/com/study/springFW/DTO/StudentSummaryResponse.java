package com.study.springFW.DTO;

import jakarta.validation.constraints.NotBlank;

public class StudentSummaryResponse {

    
    private long id;
    private String name;
    private String email;
    
    //cons
    public StudentSummaryResponse() {
    }

    public StudentSummaryResponse(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
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
}
