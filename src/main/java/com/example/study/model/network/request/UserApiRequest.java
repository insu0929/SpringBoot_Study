package com.example.study.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiRequest {

    private Long id;

    private String account;

    private String password;

    private String status;

    private String email;

    private String phoneNumber;

    //가입 일자, 해지 일자는 클라이언트가 아니라 서버에서 생성
}
