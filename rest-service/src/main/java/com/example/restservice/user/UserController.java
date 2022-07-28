package com.example.restservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    // final 붙은 변수에 한해 자동으로 생성자 주입을 해주는 @RequiredArgsContructor 이용, 의존주입!
    private final UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }
    // 숫자형이여도 pathvariable 의 숫자는 모두 String으로 들어온다.
    // 하지만 받을때 int로 자동으로 매핑됨.
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userDaoService.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build(); // response header에 상세정보 볼 수 있는 uri를 응답해줌.(postman에서 확인)


        // 서버로부터 적절한 응답 code를 반환하는 것이 좋은 rest api의 설계임
        // created : 201

        // restful api를 구축할 때 모두 post로 받고 return 되는 response code 를 모두 200번 ok로 보내는것 절대 지양.
        // 상황에 맞게 get , post , put ,deletemapping 시키고 response code도 다양하게 사용하여 예외처리 하면 좋음.
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = userDaoService.deleteById(id);
        if(user==null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable int id){
        user.setId(id);
        User updatedUserInfo = userDaoService.update(user);
        return new ResponseEntity<User>(updatedUserInfo, HttpStatus.OK);
//        return ResponseEntity.ok().body(updatedUserInfo);
        // 둘다 동일하게 쓸 수 있음..


    }


}
