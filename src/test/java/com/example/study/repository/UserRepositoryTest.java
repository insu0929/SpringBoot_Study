package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import jdk.vm.ci.meta.Local;
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
        String account = "Test01";
        String password = "Test01";
        String status = "REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-1111";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);

        User newUser = userRepository.save(user);

        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-1111");

        if(user!=null){
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println(orderGroup.getRevName());
                System.out.println(orderGroup.getRevAddress());
                System.out.println(orderGroup.getTotalPrice());
                System.out.println(orderGroup.getTotalQuantity());
            });

        }
        Assertions.assertNotNull(user);

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
