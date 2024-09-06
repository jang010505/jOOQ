package com.seminar.querydsl_sql.user.mapper;

import com.seminar.querydsl_sql.post.mapper.PostMapper;
import com.seminar.querydsl_sql.user.dto.UserDTO;
import com.seminar.querydsl_sql.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final PostMapper postMapper;

  public UserDTO toUserDTO(UserEntity userEntity) {
    return UserDTO.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .postDTOS(userEntity.getPosts().stream()
            .map(postMapper::toPostDTO)
            .toList()
        )
        .build();
  }
}
