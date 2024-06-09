create table users
(
    user_id         varchar(256)                       not null comment '用户ID'
        primary key,
    user_account    varchar(255)                       not null comment '账户',
    user_name       varchar(255)                       not null comment '用户名',
    user_password   varchar(255)                       not null comment '用户密码',
    user_avatar_url varchar(255)                       not null comment '用户头像链接',
    is_delete       tinyint  default 0                 not null comment '用户删除标识 0--表示未删除 1--已删除',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint idx_account
        unique (user_account)
)
    comment '用户表' collate = utf8mb4_unicode_ci;

create index idx_create_time
    on users (create_time);

create index idx_user_name
    on users (user_name);

