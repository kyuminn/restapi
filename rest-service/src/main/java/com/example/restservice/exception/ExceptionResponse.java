package com.example.restservice.exception;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ExceptionResponse {

    //aop = 프로그램에서 사용하는 공통 로직을 다루는 방법
    private Date timestamp;
    private String message;
    private String details;

}
