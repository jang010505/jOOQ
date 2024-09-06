package com.seminar.querydsl_sql.user.controller;

import com.seminar.querydsl_sql.user.dto.UserDTO;
import com.seminar.querydsl_sql.user.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/users")
  public void insert() {
    userService.insert();
  }

  @GetMapping("/v1/users/posts")
  public List<UserDTO> findUserAndPostsV1() {
    return userService.findUserAndPostsV1();
  }

  @GetMapping("/v2/users/posts")
  public List<UserDTO> findUserAndPostsV2() {
    return userService.findUserAndPostsV2();
  }

  @GetMapping("/v1/users")
  public List<UserDTO> findAllV1() {
    return userService.findAllV1();
  }

  @GetMapping("/v2/users")
  public List<UserDTO> findAllV2() {
    return userService.findAllV2();
  }

  @GetMapping("/v3/users")
  public List<UserDTO> findAllV3() {
    return userService.findAllV3();
  }

  @GetMapping("/v1/users/top-5")
  public List<UserDTO> findTop5V1() {
    return userService.findTop5V1();
  }

  @GetMapping("/v2/users/top-5")
  public List<UserDTO> findTop5V2() {
    return userService.findTop5V2();
  }
}
