package com.study.springFW.DTO;

import com.study.springFW.model.Student;

public class StudentMapper {

    // Student -> StudentSummaryResponse (db -> DTO)
    public static StudentSummaryResponse toStudentSummaryResponse(Student student) {
        return new StudentSummaryResponse(student);
    }
    
    // CreateStudentRequest(client - dto) -> Student 
    public static Student toStudent(CreateStudentRequest request) {
        return new Student(
            request.getName(),
            request.getEmail(),
            request.getAge(),
            request.getGpa(),
            request.isActive(),
            null // createdAt sẽ được set trong service
        );
    }
}
