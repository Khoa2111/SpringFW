package com.study.springFW.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.study.springFW.DTO.CreateStudentRequest;
import com.study.springFW.DTO.StudentSummaryResponse;
import com.study.springFW.model.Student;
import com.study.springFW.repository.StudentRepository;

@Service
public interface StudentService {

    // tạo sv
    StudentSummaryResponse createStudent(CreateStudentRequest request);
    
    // lấy all sv
    List<StudentSummaryResponse> getAllStudents();

    // lấy sv theo id
    Optional<Student> getStudentById(Long id);

    // update sv
    StudentSummaryResponse updateStudent(Long id, CreateStudentRequest request);

    // delete sv
    boolean deleteStudent(Long id);

    //JPQL
    List<StudentSummaryResponse> findStudentsWithGpaGreaterThanAverage();
    
    long countActiveStudents();

    List<StudentSummaryResponse> findStudentsCreatedInLast7Day();


}
