package com.seminar.querydsl_sql.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seminar.querydsl_sql.post.dto.PostDTO;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
  private Long id;
  private String username;
  private List<PostDTO> postDTOS;

  @QueryProjection
  public UserDTO(Long id, String username, List<PostDTO> postDTOS) {
    this.postDTOS = postDTOS;
    this.username = username;
    this.id = id;
  }
}
