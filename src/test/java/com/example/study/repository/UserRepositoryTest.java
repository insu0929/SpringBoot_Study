package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired //직접 객체를 생성하지 않음 dependency injection (DI)
    private UserRepository userRepository;

    @Test
    public void create(){
        // Sting sql = insert into user (%s. %s, %d) value (account, email, age);
        //라고 했던것을 jpa는 바로 매칭을 시켜줌
        User user = new User();
        user.setAccount("TestUser03");
        user.setEmail("TestUser03@gmail.com");
        user.setPhoneNumber("010-3333-3333");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser3");

        User newUser = userRepository.save(user);
        System.out.println("newUser : " + newUser);
    }

    @Test
    @Transactional
    public void read(){

        //select * from user where id = ?
        //Optional<User> user = userRepository.findById(7L);

        Optional<User> user = userRepository.findByAccount("TestUser03");

        user.ifPresent(selectUser -> {

            selectUser.getOrderDetailList().stream().forEach(detail->{
                Item item = detail.getItem();
                System.out.println(detail.getItem());
            });
        });

    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(3L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("PPPP");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional //db에서 실제로 지워지진 않음
    public void delete(){
        Optional<User> user = userRepository.findById(4L);

        Assertions.assertTrue(user.isPresent());
        user.ifPresent(selectUser ->{

            userRepository.delete(selectUser);

        });

        Optional<User> deleteUser = userRepository.findById(4L);

        Assertions.assertFalse(deleteUser.isPresent());


    }
}
