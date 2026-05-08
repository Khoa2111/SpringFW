package com.study.springFW.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.springFW.DTO.CreateStudentRequest;
import com.study.springFW.DTO.StudentMapper;
import com.study.springFW.DTO.StudentSummaryResponse;
import com.study.springFW.model.Student;
import com.study.springFW.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository repo;

    @Autowired
    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public long countActiveStudents() {
    
        return repo.countActiveStudents();
    }

    @Override
    public StudentSummaryResponse createStudent(CreateStudentRequest request) {

        // check email tồn tại chưa
        if (repo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exist: " + request.getEmail());
        }
        Student student = repo.save(StudentMapper.toStudent(request));
        return StudentMapper.toStudentSummaryResponse(student);
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
        return repo.findStudentsCreatedInLast7Day(LocalDateTime.now().minusDays(7)).stream().map(StudentMapper::toStudentSummaryResponse).toList();
    }

    @Override
    public List<StudentSummaryResponse> findStudentsWithGpaGreaterThanAverage() {
        return repo.findStudentsWithGpaGreaterThanAverage().stream().map(StudentMapper::toStudentSummaryResponse).toList();
    }


    @Override
    public List<StudentSummaryResponse> getAllStudents() {
        return repo.findAll().stream().map(StudentMapper::toStudentSummaryResponse).toList();
    }

    @Override
    public Optional<StudentSummaryResponse> getStudentById(Long id) {
        return repo.findById(id).map(StudentMapper::toStudentSummaryResponse);
    }

    @Override
    public StudentSummaryResponse updateStudent(Long id, CreateStudentRequest request) {
        // lấy sv theo id để check tồn tại, nếu không có sẽ throw exception
        Student existStudent = repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        // nếu trùng email thì cũng lỗi (ngoại trừ trường hợp email không đổi)
        if (repo.existsByEmail(request.getEmail()) && !existStudent.getEmail().equals(request.getEmail())) {
            throw new RuntimeException("Email already exist: " + request.getEmail());
        }

        // cập nhật thông tin sv
        existStudent.setName(request.getName());
        existStudent.setEmail(request.getEmail());
        existStudent.setAge(request.getAge());
        existStudent.setGpa(request.getGpa());
        existStudent.setActive(request.isActive());

        return StudentMapper.toStudentSummaryResponse(repo.save(existStudent));
    }
}
