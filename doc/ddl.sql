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


create table m_api.api_info
(
    api_id          varchar(255)                       not null comment '唯一ID'
        primary key,
    api_name        varchar(256)                       null comment '接口名字',
    api_url         varchar(256)                       null comment '接口地址',
    api_req_method  varchar(8)                         null comment '接口的请求方式',
    api_resp_type   varchar(256)                       null comment '响应体类型（响应头 Content-Type的值）',
    api_desc        varchar(512)                       null comment '接口描述',
    api_req_header  json                               null comment '接口请求头信息 具体格式 [{name:xxxx,value:xxx}]',
    api_req_params  json                               null comment '接口请求参数信息 具体格式 [{name:xxx,type:xxx,required:true,desc:xxxx}]',
    api_resp_desc   json                               null comment '接口响应体说明 [{name:xxxx,type:xxx,desc:xxx}]',
    api_resp_sample text                               null comment '响应体结果示例',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint check_api_req_method
        check ((`api_req_method` = _utf8mb4'GET') or (`api_req_method` = _utf8mb4'POST'))
);

create index idx_api_name
    on m_api.api_info (api_name);

