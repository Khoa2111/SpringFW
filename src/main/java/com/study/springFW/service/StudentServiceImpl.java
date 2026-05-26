package com.study.springFW.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.study.springFW.dto.CreateStudentRequest;
import com.study.springFW.dto.PageResponse;
import com.study.springFW.dto.StudentDetailResponse;
import com.study.springFW.dto.StudentSummaryResponse;
import com.study.springFW.dto.UpdateStudentRequest;
import com.study.springFW.exception.DuplicateResourceException;
import com.study.springFW.exception.StudentNotFoundException;
import com.study.springFW.mapper.StudentMapper;
import com.study.springFW.model.Student;
import com.study.springFW.repository.StudentRepository;
import com.study.springFW.support.StudentSortSupport;

@Service
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository repo;
    private final StudentMapper mapper;

    @Autowired
    public StudentServiceImpl(StudentRepository repo, StudentMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public long countActiveStudents() {
    
        return repo.countActiveStudents();
    }

    @Override
    public StudentDetailResponse createStudent(CreateStudentRequest request) {

        // check email tồn tại chưa
        validateEmailForCreate(request.getEmail());
        Student student = repo.save(mapper.toStudent(request));
        return mapper.toStudentDetailResponse(student);
    }

    // xóa luôn không cần check null, có lỗi thì để controller xử lý
    @Override
    public void deleteStudent(Long id) {
        Student student = findStudentById(id);
        repo.delete(student);
        
    }

    @Override
    public PageResponse<StudentSummaryResponse> findStudentsCreatedInLast7Day(Pageable pageable) {

        Pageable normalized = StudentSortSupport.normalizePageable(pageable, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<StudentSummaryResponse> studentPage = repo.findStudentsCreatedInLast7Day(LocalDateTime.now().minusDays(7), normalized).map(mapper::toStudentSummaryResponse);

        return PageResponse.from(studentPage);
    }

    @Override
    public PageResponse<StudentSummaryResponse> findStudentsWithGpaGreaterThanAverage(Pageable pageable) {

        Pageable normalized = StudentSortSupport.normalizePageable(pageable, Sort.by(Sort.Direction.DESC, "gpa"));

        Page<StudentSummaryResponse> studentPage = repo.findStudentsWithGpaGreaterThanAverage(normalized).map(mapper::toStudentSummaryResponse);
        return PageResponse.from(studentPage);
    }


    @Override
    public PageResponse<StudentSummaryResponse> getAllStudents(Pageable pageable) {
        Pageable normalized = StudentSortSupport.normalizePageable(pageable, Sort.by(Sort.Direction.ASC, "id"));
        Page<StudentSummaryResponse> studentPage = repo.findAll(normalized)
                .map(mapper::toStudentSummaryResponse);

        return PageResponse.from(studentPage);
        
    }


    @Override
    public StudentDetailResponse getStudentById(Long id) {
        Student student = findStudentById(id); // check tồn tại, nếu không có sẽ throw exception
        return mapper.toStudentDetailResponse(student);
    }

    @Override
    public StudentDetailResponse updateStudent(Long id, UpdateStudentRequest request) {
        // lấy sv theo id để check tồn tại, nếu không có sẽ throw exception
        Student existStudent = findStudentById(id);
        // nếu trùng email thì cũng lỗi (ngoại trừ trường hợp email không đổi)
        validateEmailForUpdate(existStudent.getEmail(), request.getEmail());

        mapper.updateStudentFromRequest(request, existStudent);

        // cập nhật thông tin sv
        return mapper.toStudentDetailResponse(repo.save(existStudent));
    }
    
    
    
    @Override
    public PageResponse<StudentSummaryResponse> findByNameContaining(String keywword, Pageable pageable) {
        return null;
    }

    @Override
    public PageResponse<StudentSummaryResponse> searchStudentByName(String keyword, Pageable pageable) {
        Page<StudentSummaryResponse> studentPage = repo.findByNameContaining(keyword, pageable)
                .map(mapper::toStudentSummaryResponse);

        return PageResponse.from(studentPage);
    }

    @Override
    public PageResponse<StudentSummaryResponse> getTopStudentsByGpa(Pageable pageable) {
        Pageable fixed = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "gpa"));
        Page<StudentSummaryResponse> studentPage = repo.findAll(fixed)
                .map(mapper::toStudentSummaryResponse);

        return PageResponse.from(studentPage);
    }

    // untils method
    private Student findStudentById(Long id) {
        return repo.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    private void validateEmailForCreate(String email) {
        if (repo.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already exist: " + email);
        }
    }

    private void validateEmailForUpdate(String email, String emailRequest) {
        if (repo.existsByEmail(emailRequest) && !email.equals(emailRequest)) {
            throw new DuplicateResourceException("Email already exist: " + emailRequest);
        }
    }

}
