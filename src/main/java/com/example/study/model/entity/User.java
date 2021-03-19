package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // == table;
@ToString(exclude = {"orderGroupList"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "account") <-이거 db column과 이름 같으면 할 필요 없음
    private String account;
    private String password;
    private String status;
    private String email;
    private String phoneNumber; //자동으로 jpa에서 매칭시켜줌
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    //User 1 : N OrderGroup
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderGroup> orderGroupList;

}
