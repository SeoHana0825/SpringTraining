package com.jpa.dto;

import jakarta.persistence.Column;
import lombok.Getter;

// 클라이언트가 보내준 정보
@Getter
public class CreateUserRequest {

    private String name;
    private String email;
    private String address;
}
