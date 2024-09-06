package com.seminar.querydsl_sql.post.mapper;

import com.seminar.querydsl_sql.post.dto.PostDTO;
import com.seminar.querydsl_sql.post.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
  public PostDTO toPostDTO(PostEntity postEntity) {
    return PostDTO.builder()
        .id(postEntity.getId())
        .content(postEntity.getContent())
        .build();
  }
}
