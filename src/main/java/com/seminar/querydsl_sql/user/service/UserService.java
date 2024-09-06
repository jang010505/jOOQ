package com.seminar.querydsl_sql.user.service;

import com.seminar.querydsl_sql.post.entity.PostEntity;
import com.seminar.querydsl_sql.post.repository.PostRepository;
import com.seminar.querydsl_sql.user.dto.UserDTO;
import com.seminar.querydsl_sql.user.entity.UserEntity;
import com.seminar.querydsl_sql.user.mapper.UserMapper;
import com.seminar.querydsl_sql.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final UserMapper userMapper;

  public void insert() {
    for (int i = 1; i <= 10; i++) {
      UserEntity user = UserEntity.builder()
          .username("username" + i)
          .build();

      userRepository.save(user);

      for (int j = 1; j <= 10; j++) {
        postRepository.save(PostEntity.builder()
            .content("content" + i)
            .user(user)
            .build());
      }
    }
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findUserAndPostsV1() {
    return userRepository.findUserAndPostsV1()
        .stream()
        .map(userMapper::toUserDTO)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findUserAndPostsV2() {
    return userRepository.findUserAndPostsV2();
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findAllV1() {
    return userRepository.findAllUsersV1()
        .stream()
        .map(userMapper::toUserDTO)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findAllV2() {
    return userRepository.findAllUsersV2();
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findAllV3() {
    return userRepository.findAllUsersV3();
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findTop5V1() {
    return userRepository.findTop5UsersV1();
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findTop5V2() {
    return userRepository.findTop5UsersV2();
  }
}
