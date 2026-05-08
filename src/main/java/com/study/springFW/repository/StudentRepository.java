package com.study.springFW.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.study.springFW.DTO.StudentSummaryResponse;
import com.study.springFW.model.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    
    // query method 
    //     Tìm theo email
    Optional<Student> findByEmail(String email);

    // Kiểm tra email tồn tại chưa
    boolean existsByEmail(String email);

    // Lấy danh sách sinh viên active
    List<Student> findByActiveTrue();
    
    // Tìm sinh viên có age > X
    List<Student> findByAgeGreaterThan(int age);

    // Tìm sinh viên có GPA từ A → B
    List<Student> findByGpaBetween(double minGpa, double maxGpa);

    // Tìm sinh viên theo name chứa keyword
    List<Student> findByNameContaining(String keyword);
    
    // Lấy 3 sinh viên GPA cao nhất
    List<Student> findTop3ByOrderByGpaDesc();

    // Lấy sinh viên GPA > trung bình
    @Query("SELECT s FROM Student s WHERE s.gpa > (SELECT AVG(s2.gpa) FROM Student s2)")
    List<StudentSummaryResponse> findStudentsWithGpaGreaterThanAverage();

    // Đếm số sinh viên active
    @Query("select count(s) from Student s where s.active = true")
    long countActiveStudents();

    // Lấy sinh viên tạo trong 7 ngày gần nhất
    @Query("select s from Student s where s.createdAt >= :fromDate")
    List<StudentSummaryResponse> findStudentsCreatedInLast7Day(@Param("fromDate") LocalDateTime fromDate);

}
