package com.study.springFW.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.springFW.dto.CreateStudentRequest;
import com.study.springFW.dto.PageResponse;
import com.study.springFW.dto.StudentDetailResponse;
import com.study.springFW.dto.StudentSummaryResponse;
import com.study.springFW.dto.UpdateStudentRequest;
import com.study.springFW.service.StudentService;

import jakarta.validation.Valid;
import tools.jackson.core.ObjectReadContext.Base;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/students")
public class StudentController  {
    
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<StudentDetailResponse> createStudent(@Valid @RequestBody CreateStudentRequest st) {
        StudentDetailResponse savedStudent = service.createStudent(st);
        URI uri = URI.create("/api/students/" + savedStudent.getId());
        return ResponseEntity.created(uri).body(savedStudent);
    }
    
    @GetMapping
    public ResponseEntity<PageResponse<StudentSummaryResponse>> getAllStudents(
            @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.getAllStudents(pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailResponse> getStudentById(@PathVariable long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<StudentDetailResponse> updateStudent(@PathVariable long id, @Valid @RequestBody UpdateStudentRequest st) {
        return ResponseEntity.ok(service.updateStudent(id, st));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("gpa-above-average")
    public ResponseEntity<PageResponse<StudentSummaryResponse>> getStudentsWithGpaGreaterThanAverage(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return ResponseEntity.ok(service.findStudentsWithGpaGreaterThanAverage(pageable));
    }

    @GetMapping("/count-active")
    public ResponseEntity<Long> countActiveStudents() {
        return ResponseEntity.ok(service.countActiveStudents());
    }
    
    @GetMapping("/recent")
    public ResponseEntity<PageResponse<StudentSummaryResponse>> getStudentsCreatedInLast7Days(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return ResponseEntity.ok(service.findStudentsCreatedInLast7Day(pageable));
    }
    

    // tìm sv có tên trong keyword
    @GetMapping("/keyword")
    public ResponseEntity<PageResponse<StudentSummaryResponse>> searchStudentByName(
            @RequestParam String keyword,
            @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return ResponseEntity.ok(service.searchStudentByName(keyword, pageable));
    }

    @GetMapping("/top-gpa")
    public ResponseEntity<PageResponse<StudentSummaryResponse>> getTopStudentsByGpa(
            @PageableDefault(page = 0, size = 3, sort = "gpa", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.getTopStudentsByGpa(pageable));
    }
    

    @GetMapping("/test")
    public String test() {
        return "ok";
    }
    
}
