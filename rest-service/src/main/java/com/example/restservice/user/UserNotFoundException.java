package com.example.restservice.user;
// HTTP Status code
// 2xx -> ok
// 4xx -> client 문제
// 5xx -> server 문제

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// 존재하지 않는 회원을 요청한 경우 404 (NOT_FOUND) status를 반환하기 위한 어노테이션
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
