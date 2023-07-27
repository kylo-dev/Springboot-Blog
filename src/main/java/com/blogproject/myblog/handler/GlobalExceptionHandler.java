package com.blogproject.myblog.handler;

import com.blogproject.myblog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice// 모든 exception이 여기 클래스로 들어옴.
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

//    @ExceptionHandler(value = IllegalArgumentException.class) // IllegalArgumentException 에러를 관리
//    public String handleArgumentException(IllegalArgumentException e) {
//        return "<h1>" + e.getMessage() + "</h1>";
//    }

}
