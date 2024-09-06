package com.seminar.querydsl_sql.user.repository;

import static com.seminar.querydsl_sql.user.entity.QUserEntity.userEntity;
import static com.seminar.querydsl_sql.post.entity.QPostEntity.postEntity;
import static com.seminar.querydslsql.Tables.POST;
import static com.seminar.querydslsql.Tables.USER;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.QueryHandler;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.seminar.querydsl_sql.post.dto.QPostDTO;
import com.seminar.querydsl_sql.user.dto.QUserDTO;
import com.seminar.querydsl_sql.user.dto.UserDTO;
import com.seminar.querydsl_sql.post.dto.PostDTO;
import com.seminar.querydsl_sql.user.entity.UserEntity;
import com.seminar.querydslsql.Tables;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Records;
import org.jooq.Table;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository {
  private final JPAQueryFactory queryFactory;
  private final com.querydsl.sql.Configuration configuration;
  private final QueryHandler queryHandler;
  private final DSLContext dsl;

  @PersistenceContext
  private EntityManager entityManager;

  public UserCustomRepositoryImpl(JPAQueryFactory queryFactory, com.querydsl.sql.Configuration configuration, QueryHandler queryHandler,
      DSLContext dsl) {
    super(UserEntity.class);
    this.queryFactory = queryFactory;
    this.configuration = configuration;
    this.queryHandler = queryHandler;
    this.dsl = dsl;
  }

  @Override
  public List<UserEntity> findUserAndPostsV1() {
    return queryFactory.selectFrom(userEntity)
        .join(userEntity.posts, postEntity).fetchJoin()
        .where(userEntity.id.eq(1L))
        .offset(0)
        .limit(5)
        .fetch();
  }

  @Override
  public List<UserDTO> findUserAndPostsV2() {
    return queryFactory.from(postEntity)
        .join(postEntity.user, userEntity)
        .where(userEntity.id.eq(1L))
        .offset(0)
        .limit(5)
        .transform(GroupBy.groupBy(userEntity.id).list(
            new QUserDTO(
                userEntity.id,
                userEntity.username,
                GroupBy.list(
                    new QPostDTO(
                        postEntity.id,
                        postEntity.content
                    )
                )
            )
        ));
  }

  @Override
  public List<UserEntity> findAllUsersV1() {
    JPASQLQuery<?> jpasqlQuery = new JPASQLQuery<>(entityManager, configuration, queryHandler);

    return jpasqlQuery
        .select(userEntity)
        .from(userEntity)
        .fetch();
  }

  @Override
  public List<UserDTO> findAllUsersV2() {
    JPASQLQuery<?> jpasqlQuery = new JPASQLQuery<>(entityManager, configuration, queryHandler);

    return jpasqlQuery
        .from(userEntity)
        .join(postEntity)
        .on(userEntity.id.eq(Expressions.numberPath(Long.class, postEntity, "user_id")))
        .transform(GroupBy.groupBy(userEntity.id).list(
            new QUserDTO(
                userEntity.id,
                userEntity.username,
                GroupBy.list(
                    new QPostDTO(
                        postEntity.id,
                        postEntity.content
                    )
                )
            )
        ));
  }

  @Override
  public List<UserDTO> findAllUsersV3() {
    return dsl
        .select(
            USER.ID,
            USER.USERNAME,
            multiset(
                select(POST.ID, POST.CONTENT)
                    .from(POST)
                    .where(POST.USER_ID.eq(USER.ID))
            ).convertFrom(r -> r.map(Records.mapping(PostDTO::new)))
        )
        .from(USER)
        .groupBy(USER.ID)
        .fetch(Records.mapping(UserDTO::new));
  }

  @Override
  public List<UserDTO> findTop5UsersV1() {
    JPASQLQuery<?> jpasqlQuery = new JPASQLQuery<>(entityManager, configuration, queryHandler);
    StringPath userView = Expressions.stringPath("user_view");
    NumberPath<Long> userViewId = Expressions.numberPath(Long.class, userView, "id");
    StringPath userViewUsername = Expressions.stringPath(userView, "username");

    return jpasqlQuery
        .from(
            JPAExpressions
                .select(userEntity.id, userEntity.username)
                .from(userEntity)
                .limit(5), userView
        )
        .join(postEntity)
        .on(userViewId.eq(Expressions.numberPath(Long.class, postEntity, "user_id")))
        .transform(GroupBy.groupBy(userViewId).list(
            new QUserDTO(
                userViewId,
                userViewUsername,
                GroupBy.list(
                    new QPostDTO(
                        postEntity.id,
                        postEntity.content
                    )
                )
            )
        ));
  }


  @Override
  public List<UserDTO> findTop5UsersV2() {
    String userViewTableName = "user_view";
    Table<?> userView = dsl
        .selectFrom(USER)
        .limit(5)
        .asTable(userViewTableName);

    return dsl
        .select(
            userView.field(USER.ID),
            userView.field(USER.USERNAME),
            multiset(
                select(POST.ID, POST.CONTENT)
                    .from(POST)
                    .where(POST.USER_ID.eq(userView.field(USER.ID)))
            ).convertFrom(r -> r.map(Records.mapping(PostDTO::new)))
        )
        .from(userView)
        .groupBy(userView.field(USER.ID))
        .fetch(Records.mapping(UserDTO::new));
  }
}
