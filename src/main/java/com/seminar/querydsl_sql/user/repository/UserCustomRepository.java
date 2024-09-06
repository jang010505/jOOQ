package com.seminar.querydsl_sql.user.repository;

import com.seminar.querydsl_sql.user.dto.UserDTO;
import com.seminar.querydsl_sql.user.entity.UserEntity;
import java.util.List;

public interface UserCustomRepository {
  List<UserEntity> findUserAndPostsV1();
  List<UserDTO> findUserAndPostsV2();
  List<UserEntity> findAllUsersV1();
  List<UserDTO> findAllUsersV2();
  List<UserDTO> findAllUsersV3();
  List<UserDTO> findTop5UsersV1();
  List<UserDTO> findTop5UsersV2();
}
