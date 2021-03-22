package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import jdk.vm.ci.meta.Local;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
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
        String account = "Test03";
        String password = "Test03";
        String status = "REGISTERED";
        String email = "Test03@gmail.com";
        String phoneNumber = "010-1111-3333";
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

        User u = User.builder().account(account).password(password).status(status).email(email).build();
        User newUser = userRepository.save(user);

        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-1111");

        if(user!=null){
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("----------주문 묶음----------");
                System.out.println("수령인: " +orderGroup.getRevName());
                System.out.println("수령지: " +orderGroup.getRevAddress());
                System.out.println("총금액: " +orderGroup.getTotalPrice());
                System.out.println("총수량: " +orderGroup.getTotalQuantity());

                System.out.println("----------주문상세----------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름: " + orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리: " + orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문상품: " + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호" + orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문상태: " + orderDetail.getStatus());
                    System.out.println("도착예정일자: " + orderDetail.getArrivalDate());




                });
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
