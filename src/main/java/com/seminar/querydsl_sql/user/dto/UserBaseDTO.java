package com.seminar.querydsl_sql.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBaseDTO {
  private Long id;
  private String username;
}
