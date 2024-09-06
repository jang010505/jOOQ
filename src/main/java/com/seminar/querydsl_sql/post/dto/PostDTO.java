package com.seminar.querydsl_sql.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PostDTO {
  public Long id;
  public String content;

  @QueryProjection
  public PostDTO(Long id, String content) {
    this.id = id;
    this.content = content;
  }
}
