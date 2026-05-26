package com.study.springFW.support;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class StudentSortSupport {

    //field để lưu các trường cho phép sort
    private static final Set<String> ALLOWED_FIELDS = Set.of("id", "name", "age", "email", "createdAt", "gpa",
            "active");
    
    // cons private để không cho tạo instance
    private StudentSortSupport() {
    }

    // static method 
    public static Pageable normalizePageable(Pageable pageable, Sort defaultSort) {
        List<Sort.Order> validSorts = pageable.getSort().stream()
                .filter(order -> ALLOWED_FIELDS.contains(order.getProperty()))
                .toList();


        Sort finalSort = validSorts.isEmpty() ? defaultSort : Sort.by(validSorts);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), finalSort);
    }
    
}
