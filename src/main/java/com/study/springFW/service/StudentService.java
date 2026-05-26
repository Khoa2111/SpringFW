package com.study.springFW.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.study.springFW.dto.CreateStudentRequest;
import com.study.springFW.dto.PageResponse;
import com.study.springFW.dto.StudentDetailResponse;
import com.study.springFW.dto.StudentSummaryResponse;
import com.study.springFW.dto.UpdateStudentRequest;


public interface StudentService {

    // tạo sv
    StudentDetailResponse createStudent(CreateStudentRequest request);
    
    // lấy all sv
    PageResponse<StudentSummaryResponse> getAllStudents(Pageable pageable);

    // lấy sv theo id
    StudentDetailResponse getStudentById(Long id);

    // update sv
    StudentDetailResponse updateStudent(Long id, UpdateStudentRequest request);

    // delete sv
    void deleteStudent(Long id);

    PageResponse<StudentSummaryResponse> findByNameContaining(String keywword, Pageable pageable);

    //JPQL
    PageResponse<StudentSummaryResponse> findStudentsWithGpaGreaterThanAverage(Pageable pageable);
    
    long countActiveStudents();

    PageResponse<StudentSummaryResponse> findStudentsCreatedInLast7Day(Pageable pageable);

    // tìm sv có tên trong keyword
    PageResponse<StudentSummaryResponse> searchStudentByName(String keyword, Pageable pageable);

    PageResponse<StudentSummaryResponse> getTopStudentsByGpa(Pageable pageable);

}
