package com.example.restservice.helloworld;

import com.example.restservice.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
// rest api - resources(자원)의 상태에 따라 개발 / api 는 프로그램간 다리(약속).. get api의 경우 get 으로 요청하는 방식이 정해져 있기 때문에 client도,server도 정해진 약속에 맞게 데이터를 주고받아야 함.
// restful : rest api로 개발하는 방식
// rest api 방식을 채택하면 data를 주고받을때 json을 이용한다
// json format이 아닌 xml 형태로 값을 주고받고 싶으면 xml 관련 라이브러리를 추가하면 됨. (강의 Section 3)
// json 관련 어노테이션 (@RestController, @ResponseBody, @RequestBody)


/*
[restful 하게 개발한다는 것은 ?]
    사용자의 요청에 따라 controller가 view (.jsp, .thymeleaf)를 반환하는 것이 아니라
    xml, json 포맷의 데이터를 반환하는 것
[@RestController]
    @Controller + @ResponseBody
    view를 갖지 않는 Rest Data (json or xml)을 반환


 */

@RestController
public class HelloController {
    // GET
    // uri : /hello-world (endpoint)

    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        User myUser = new User(1, "myName", new Date());
        System.out.println(myUser);
        return "Hello World!!";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello world");
        // 요청했을때 {"message":"Hello world"} 로 옴
        // java bean 형태로 return 할 경우 spring에서 json 형태로 변환해서 반환해준다 (rest controller 이기 때문에)
    }

    // 가변 변수 사용해보기
    // http://localhost:9999/hello-world-bean/path-variable/kyumin
    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean pathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello, %s",name));
//        {
//            "message": "Hello, kyumin"
//        }
    }

}
