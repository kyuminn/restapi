package com.example.restservice.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {

    // Integer vs int
    private Integer Id;
    private String name;
    private Date joinDate;
}
