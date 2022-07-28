package com.example.restservice.exception;

import com.example.restservice.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice //이 어노테이션을 추가하면 모든 컨트롤러가 실행되기 전에 이 클래스가 실행되게 할 수 있다.
//프로그램 실행중에 error가 났을 때 사용할 수 있는 공통 error handling class
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));
        // request.getDescription(false)를 하면 trace 부분의 상세 내용이 보이지 않고 , 전달하고자 하는 메세지만 볼 수 있음!
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException unfe, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), unfe.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
