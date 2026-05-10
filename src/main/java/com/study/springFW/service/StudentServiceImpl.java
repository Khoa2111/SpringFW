package com.study.springFW.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.springFW.DTO.CreateStudentRequest;
import com.study.springFW.DTO.StudentDetailResponse;
import com.study.springFW.DTO.StudentSummaryResponse;
import com.study.springFW.DTO.UpdateStudentRequest;
import com.study.springFW.mapper.StudentMapper;
import com.study.springFW.model.Student;
import com.study.springFW.repository.StudentRepository;

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
        if (repo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exist: " + request.getEmail());
        }
        Student student = repo.save(mapper.toStudent(request));
        return mapper.toStudentDetailResponse(student);
    }

    // xóa luôn không cần check null, có lỗi thì để controller xử lý
    @Override
    public boolean deleteStudent(Long id) {   
        // repo.deleteById(id);
        Optional<Student> st = repo.findById(id);
        if (st.isEmpty()) {
            return false;
        }
        repo.delete(st.get());
        return true;
        
    }

    @Override
    public List<StudentSummaryResponse> findStudentsCreatedInLast7Day() {
        return mapper.toStudentSummaryResponses(repo.findStudentsCreatedInLast7Day(null));
    }

    @Override
    public List<StudentSummaryResponse> findStudentsWithGpaGreaterThanAverage() {
        return mapper.toStudentSummaryResponses(repo.findStudentsWithGpaGreaterThanAverage());
    }


    @Override
    public List<StudentSummaryResponse> getAllStudents() {
        return mapper.toStudentSummaryResponses(repo.findAll());
    }

    @Override
    public Optional<StudentDetailResponse> getStudentById(Long id) {
        return repo.findById(id).map(mapper::toStudentDetailResponse);
    }

    @Override
    public StudentDetailResponse updateStudent(Long id, UpdateStudentRequest request) {
        // lấy sv theo id để check tồn tại, nếu không có sẽ throw exception
        Student existStudent = repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        // nếu trùng email thì cũng lỗi (ngoại trừ trường hợp email không đổi)
        if (repo.existsByEmail(request.getEmail()) && !existStudent.getEmail().equals(request.getEmail())) {
            throw new RuntimeException("Email already exist: " + request.getEmail());
        }

        mapper.updateStudentFromRequest(request, existStudent);

        // cập nhật thông tin sv
        return mapper.toStudentDetailResponse(repo.save(existStudent));
    }
}
