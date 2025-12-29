package com.jpa.service;

import com.jpa.dto.*;
import com.jpa.entity.User;
import com.jpa.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    // 저장
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {

        // 생성자 userController 에서 getter 불러옴
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getAddress()
        ); //User을 생성
        User savedUser = userRepository.save(user); // User을 save

        CreateUserResponse response = new CreateUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getAddress()
        );
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getAddress()
        );
    }
    //단 건 조회
    @Transactional (readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
       User user = userRepository.findById(userId).orElseThrow(
               () -> new IllegalStateException("해당 유저는 없는 유저입니다.")
       );

        return new GetOneUserResponse(
               user.getId(),
               user.getName(),
               user.getEmail(),
               user.getAddress()
       );
    }

    // 다多 건 조회
    @Transactional (readOnly = true)
    public List<GetOneUserResponse> getAll() {
        List<User> users = userRepository.findAll();

        List<GetOneUserResponse> dtos = new ArrayList<>();
        for(User user : users) {

            GetOneUserResponse dto = new GetOneUserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // 업데이트
    @Transactional (readOnly = true)
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저는 없는 유저입니다.")
        );

        user.update(
                request.getName(),
                request.getEmail(),
                request.getAddress()
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress()
        );
    }

    //삭제
    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);

        // 유저가 없는 경우
        if (!existence) {
            throw new IllegalStateException("없는 유저입니다");
        }

        // 유저가 있는 경우 -> 삭제 가능
        userRepository.deleteById(userId);
    }
 }
