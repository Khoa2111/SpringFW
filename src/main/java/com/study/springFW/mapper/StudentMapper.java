package com.study.springFW.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.study.springFW.DTO.CreateStudentRequest;
import com.study.springFW.DTO.StudentSummaryResponse;
import com.study.springFW.DTO.StudentDetailResponse;
import com.study.springFW.DTO.UpdateStudentRequest;
import com.study.springFW.model.Student;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface StudentMapper {
    
    //to student
    @Mapping(target = "id", ignore = true)
    Student toStudent(CreateStudentRequest request);
    
    // Response for detail
    StudentDetailResponse toStudentDetailResponse(Student student);

    // Response for List
    List<StudentSummaryResponse> toStudentSummaryResponses(List<Student> students);
    
    // hàm này còn chứa cả logic để update, nên phải ignore id và createdAt, còn lại thì lấy từ request
    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "gpa", source = "gpa")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "createdAt", ignore = true)
    void updateStudentFromRequest(UpdateStudentRequest request, @MappingTarget Student student);
}
