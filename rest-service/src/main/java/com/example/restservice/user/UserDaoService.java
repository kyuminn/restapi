package com.example.restservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount =3 ;

    // 클래스 초기화 블럭 : 클래스가 로딩될 때 최초 한번만 수행, 주로 클래스 변수 초기화에 사용, 위의 List가 static 변수니까 static 블럭에서 초기화 할 수있음!
    // (만약 static 키워드 없이 {}만 있으면 인스턴스 초기화 블럭, 인스턴스가 생성될 때 생성자보다 먼저 호출됨, 주로 인스턴스 변수 초기화에 사용)
    static {
        users.add(new User(1,"Kenneth", new Date()));
        users.add(new User(2,"Alice", new Date()));
        users.add(new User(3,"Elena", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if (user.getId()==null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for (User user : users) {
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }


    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId()==id){
             iterator.remove();
             return user;
            }
        }
        return null;
    }

    public User update(User user){
        for (User user1 : users) {
            if(user.getId()==user1.getId()){
                user1.setName(user.getName());
                user1.setJoinDate(user.getJoinDate());
                return user1;
            }
        }

        return null;
    }
}
