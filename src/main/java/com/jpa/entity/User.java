package com.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User {

    // Id 는 DB에서 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id를 제외한 생성자 생성
    @Column(length = 50, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String address;

    public User (String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // 업데이트 생성자
    public void update(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

}
