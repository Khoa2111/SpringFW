package com.study.springFW.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.springFW.DTO.CreateStudentRequest;
import com.study.springFW.DTO.StudentDetailResponse;
import com.study.springFW.DTO.StudentSummaryResponse;
import com.study.springFW.DTO.UpdateStudentRequest;
import com.study.springFW.model.Student;
import com.study.springFW.service.StudentService;
import com.study.springFW.service.StudentServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody CreateStudentRequest st) {
           System.out.println("active = " + st.isActive());
        try {
            StudentDetailResponse savedStudent = service.createStudent(st);
            URI uri = URI.create("/api/students/" + savedStudent.getId());
            return ResponseEntity.created(uri).body(savedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<StudentSummaryResponse>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable long id) {
        return service.getStudentById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable long id, @RequestBody UpdateStudentRequest st) {
        try {
            return ResponseEntity.ok(service.updateStudent(id, st));
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT;
            return ResponseEntity.status(status).body(e.getMessage());
        }

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        if (service.deleteStudent(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("gpa-above-average")
    public ResponseEntity<List<StudentSummaryResponse>> getStudentsWithGpaGreaterThanAverage() {
        return ResponseEntity.ok(service.findStudentsWithGpaGreaterThanAverage());
    }

    @GetMapping("/count-active")
    public ResponseEntity<Long> countActiveStudents() {
        return ResponseEntity.ok(service.countActiveStudents());
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<StudentSummaryResponse>> getStudentsCreatedInLast7Days() {
        return ResponseEntity.ok(service.findStudentsCreatedInLast7Day());
    }
    

        @GetMapping("/test")
    public String test() {
        return "ok";
}
    
}
