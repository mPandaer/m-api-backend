create table if not exists api_info
(
    api_id          varchar(255)                          not null comment '唯一ID'
        primary key,
    api_name        varchar(256)                          not null comment '接口名字',
    api_desc        varchar(512)                          not null comment '接口描述',
    api_url         varchar(256)                          not null comment '接口地址',
    api_resp_type   varchar(256)                          not null comment '响应体类型（响应头 Content-Type的值）',
    api_req_method  varchar(8)                            not null comment '接口的请求方式',
    api_status      varchar(20) default 'disable'         not null comment '接口的状态',
    api_req_header  json                                  not null comment '接口请求头信息 具体格式 [{name:xxxx,value:xxx}]',
    api_req_params  json                                  not null comment '接口请求参数信息 具体格式 [{name:xxx,type:xxx,required:true,desc:xxxx}]',
    api_resp_desc   json                                  not null comment '接口响应体说明 [{name:xxxx,type:xxx,desc:xxx}]',
    api_resp_sample text                                  not null comment '响应体结果示例',
    is_delete       tinyint     default 0                 not null comment '逻辑删除 0表示未删除 1表示删除',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint check_api_req_method
        check ((`api_req_method` = _utf8mb4'GET') or (`api_req_method` = _utf8mb4'POST')),
    constraint check_api_status
        check ((`api_status` = _utf8mb4'disable') or (`api_status` = _utf8mb4'enable'))
);

create index idx_api_name
    on api_info (api_name);

create table if not exists users
(
    user_id         varchar(256)                          not null comment '用户ID'
        primary key,
    user_account    varchar(255)                          not null comment '账户',
    user_name       varchar(255)                          not null comment '用户名',
    user_password   varchar(255)                          not null comment '用户密码',
    access_key      varchar(256)                          null comment 'AccessKey',
    secret_key      varchar(256)                          null comment 'SecretKey',
    user_avatar_url varchar(255)                          not null comment '用户头像链接',
    user_role       varchar(32) default 'user'            not null,
    is_delete       tinyint     default 0                 not null comment '用户删除标识 0--表示未删除 1--已删除',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint idx_account
        unique (user_account),
    constraint check_role
        check ((`user_role` = _utf8mb4'user') or (`user_role` = _utf8mb4'admin'))
)
    comment '用户表' collate=utf8mb4_unicode_ci;

create index idx_create_time
    on users (create_time);

create index idx_user_name
    on users (user_name);



INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1800348827556278272', '我的名字', '本服务也支持私有化部署', '未知', 'json/xml', 'GET', 'enable', '[{"name": "Content-Type", "value": "application/x-www-form-urlencoded"}]', '[{"desc": "需要查询的手机号或手机号前7位", "name": "phone", "type": "int", "require": true}, {"desc": "在个人中心->我的数据,接口名称上方查看", "name": "key", "type": "string", "require": true}, {"desc": "返回数据的格式,xml或json，默认json", "name": "dtype", "type": "string", "require": true}]', '[{"desc": "返回码", "name": "error_code", "type": "int"}, {"desc": "返回说明", "name": "reason", "type": "string"}, {"desc": "返回结果集", "name": "result", "type": "string"}]', '{
    "resultcode":"200",
    "reason":"Return Successd!",
    "result":{
        "province":"浙江",
        "city":"杭州",
        "areacode":"0571",
        "zip":"310000",
        "company":"中国移动",
        "card":""
        }
}
', 0, '2024-06-11 10:06:24', '2024-06-11 15:08:15');
INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1800358988136181760', '手机归属地查询', '本服务也支持私有化部署', 'http://apis.juhe.cn/mobile/get', 'json/xml', 'GET', 'disable', '[{"name": "Content-Type", "value": "application/x-www-form-urlencoded"}]', '[{"desc": "需要查询的手机号或手机号前7位", "name": "phone", "type": "int", "require": true}, {"desc": "在个人中心->我的数据,接口名称上方查看", "name": "key", "type": "string", "require": true}, {"desc": "返回数据的格式,xml或json，默认json", "name": "dtype", "type": "string", "require": false}]', '[{"desc": "返回码", "name": "error_code", "type": "int"}]', '{
    "resultcode":"200",
    "reason":"Return Successd!",
    "result":{
        "province":"浙江",
        "city":"杭州",
        "areacode":"0571",
        "zip":"310000",
        "company":"中国移动",
        "card":""
        }
}
', 0, '2024-06-11 10:46:46', '2024-06-11 15:08:15');
INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1800466169158189056', 'fdafasdf', 'dfafdas', 'fdafs', 'fdfas', 'POST', 'disable', '[{"name": "fdafasd", "value": "dsfasd"}]', '[{"desc": "afsdadsf", "name": "fdasfasd", "type": "dfsafasd", "require": [true]}]', '[{"desc": "fdsaas", "name": "fasdfs", "type": "afsddasf"}]', 'asdfdas', 0, '2024-06-11 17:52:40', '2024-06-11 17:52:40');
INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1800467264152551424', 'fasfasdf', 'dasfdasfsd', 'dfasfdsafas', 'dasfasd', 'GET', 'disable', '[{"name": "adsfasd", "value": "dsfasfd"}]', '[{"desc": "fdafds", "name": "fdsaaf", "type": "dfas", "require": true}, {"desc": "asdfasd", "name": "dfadf", "type": "asdfas", "require": "true"}]', '[{"desc": "dasfas", "name": "asdfasd", "type": "fasdfads"}]', 'fasfdsfasfdsfasd', 1, '2024-06-11 17:57:01', '2024-06-11 19:00:15');
INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1800467669964046336', 'jasdfjklasdj;als', 'asdfgjdkl;agfhjl;asgh;sda', 'fdasgdsaasdgfas', 'asdgdsaGASDGASD', 'POST', 'disable', '[{"name": "ASDGASD", "value": "ASDGASDG"}]', '[{"desc": "ASDGASDG", "name": "ASGDSA", "type": "ASDGASD", "require": "true"}, {"desc": "ASFGASD", "name": "ASGDSDA", "type": "ASDGASDG", "require": false}]', '[{"desc": "ASDGAS", "name": "GASDGDSA", "type": "ASDGASD"}]', 'DASGASDASDGASDG DASHRADFA3WRHBNERHBHBEA', 1, '2024-06-11 17:58:38', '2024-06-11 19:00:15');
INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1800491727325773824', 'fasdfasda', 'dasfasd', 'asdfasf', 'dafasdf', 'POST', 'disable', '[{"name": "afdas", "value": "dasffasd"}, {"name": "dddd", "value": "ddddd"}]', '[{"desc": "adfas", "name": "dasfasd", "type": "adfas", "require": true}, {"desc": "dasfasd", "name": "asdfdas", "type": "dsfaa", "require": true}]', '[]', 'asdfdasgasdghbasdfgasdfgasdgasdfhasdfgasdgasdffg', 0, '2024-06-11 19:34:13', '2024-06-11 19:34:13');
INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1801575879756328960', 'GET-hello', '测试接口', 'http://api.pandaer.com/api/mock/hello', 'application/json', 'GET', 'disable', '[{"name": "Content-Type", "value": "application/json"}]', '[{"desc": "一个名字", "name": "name", "type": "string", "require": true}]', '[{"desc": "返回类型", "name": "Content-Type", "type": "application/json"}]', '{name:"xxxx"}', 0, '2024-06-14 19:22:16', '2024-06-14 19:22:16');
INSERT INTO m_api.api_info (api_id, api_name, api_desc, api_url, api_resp_type, api_req_method, api_status, api_req_header, api_req_params, api_resp_desc, api_resp_sample, is_delete, create_time, update_time) VALUES ('1801576337891766272', 'POST-hello', '测试接口POST', 'http://api.pandaer.com/api/mock/hello/user', 'application/json', 'POST', 'disable', '[{"name": "Content-Type", "value": "application/json"}]', '[{"desc": "一个名字", "name": "name", "type": "string", "require": true}]', '[{"desc": "一个名字", "name": "name", "type": "string"}]', '{name:xxxx}', 0, '2024-06-14 19:24:05', '2024-06-14 19:24:05');


INSERT INTO m_api.users (user_id, user_account, user_name, user_password, access_key, secret_key, user_avatar_url, user_role, is_delete, create_time, update_time) VALUES ('1799645162625417216', 'user001', 'user001', '7kyoSy$NTCKQpMTBjxMa0sOHdFzpi7y5YD+Ux2z79JEXR8qftM=', null, null, '', 'admin', 0, '2024-06-09 11:30:17', '2024-06-09 15:19:52');
INSERT INTO m_api.users (user_id, user_account, user_name, user_password, access_key, secret_key, user_avatar_url, user_role, is_delete, create_time, update_time) VALUES ('1801509311395405824', 'user002', 'user002', 'ACVNHO$gqdeX+y36z6jpNAE2S13DpC+0qqVfbgWL7frbiC1daM=', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOiIyMDI0LTA2LTE0IDE1OjIyOjQ0IiwidXNlcklkIjoiMTgwMTUwOTMxMTM5NTQwNTgyNCJ9.SxJqJwruPu1nU7OB9MUztAZX5tRBv_7whRaRnaPHOSw', 'LiCNP7mRBT0fvHL8', '', 'admin', 0, '2024-06-14 14:57:44', '2024-06-14 15:01:18');
