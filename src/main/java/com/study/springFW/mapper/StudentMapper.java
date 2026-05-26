package com.study.springFW.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.study.springFW.dto.CreateStudentRequest;
import com.study.springFW.dto.StudentDetailResponse;
import com.study.springFW.dto.StudentSummaryResponse;
import com.study.springFW.dto.UpdateStudentRequest;
import com.study.springFW.model.Student;

//  use mapstruct để chuyển đổi giữa các lớp DTO và entity, giúp code sạch hơn và dễ bảo trì hơn
@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface StudentMapper {
    
    //to student
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Student toStudent(CreateStudentRequest request);
    
    // Response for detail
    StudentDetailResponse toStudentDetailResponse(Student student);

    // Response for List
    List<StudentSummaryResponse> toStudentSummaryResponses(List<Student> students);

    StudentSummaryResponse toStudentSummaryResponse(Student student);

    
    // hàm này còn chứa cả logic để update, nên phải ignore id và createdAt, còn lại thì lấy từ request
    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "gpa", source = "gpa")
    @Mapping(source = "active", target = "active")  
    @Mapping(target = "createdAt", ignore = true)
    void updateStudentFromRequest(UpdateStudentRequest request, @MappingTarget Student student);
}
