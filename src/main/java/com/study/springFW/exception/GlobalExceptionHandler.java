package com.study.springFW.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // xử lý exception khi không tìm thấy sinh viên
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex, HttpServletRequest request) {
        // trả về exception message cho client
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }
    

    // xử lí exception khi trùng lặp tài nguyên
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), null);
    }

    
    // xử lí validateion exception khi dữ liệu không hợp lệ
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handlerValidationException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, List<String>> validationErrors = new LinkedHashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {

            validationErrors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>()).add(fieldError.getDefaultMessage());

        }
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                request.getRequestURI(),
                validationErrors);
    }

    // xử lí exception về request body không hợp lệ, ví dụ khi client gửi dữ liệu không đúng định dạng JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Request body is missing or has invalid JSON format",
                request.getRequestURI(), null);
    }

    // bắt exception chung, nếu có lỗi nào không được xử lý ở trên thì sẽ vào đây, và là tấm lưới cuối cùng để tránh lỗi không được xử lý
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "an unexcepted error occurred", request.getRequestURI(),
                null);
    }
    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, String path, Map<String, List<String>> validationErrors) {
        ApiErrorResponse response = new ApiErrorResponse(
            status.value(),
            status.getReasonPhrase(),
            message,
            path,
            validationErrors
        );
        return ResponseEntity.status(status).body(response);
    }
}   
