package com.seminar.querydsl_sql.user.repository;

import com.seminar.querydsl_sql.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserCustomRepository {

}
