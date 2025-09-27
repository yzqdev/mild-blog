--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: blog_config; Type: TABLE DATA; Schema: public; Owner: postgres
--
INSERT INTO "public"."admin_user"VALUES ('myid', 'admin', '$2a$10$o4H/p1qsjV71Ubr7Bw0Pv.LoVJCFNnL8U4f9Y/RtiDguySD4ryeH2', '啊啊啊', 'f', 1, 'http://localhost:2801/upload/20220615_163021.jpg');



INSERT INTO "public"."blog_config" VALUES ('1', 'sysAuthor', '开发者', '飞雪', '2019-08-24 20:33:17', '2019-08-30 03:27:35');
INSERT INTO "public"."blog_config" VALUES ('2', 'sysUrl', '服务器url', 'localhost:80', '2019-08-24 14:03:23', '2019-08-24 14:03:26');
INSERT INTO "public"."blog_config" VALUES ('4', 'websiteName', '博客名', '七月飞雪', '2018-11-11 20:33:01', '2021-08-10 09:54:45');
INSERT INTO "public"."blog_config" VALUES ('5', 'sysCopyRight', '版权所', 'yzqdev', '2019-08-24 20:33:31', '2022-05-14 20:42:11.383');
INSERT INTO "public"."blog_config" VALUES ('init', 'init', '初始化', 'init', '2019-08-24 20:33:17', '2019-08-30 03:27:35');
INSERT INTO "public"."blog_config" VALUES ('8', 'filing', '备案', '豫ICP备2022004109号', '2022-06-18 13:11:12', '2022-06-18 13:11:15');
INSERT INTO "public"."blog_config" VALUES ('6', 'sysAuthorImg', '开发者头像', 'http://localhost:2801/upload/20220908_233437.png', '2019-08-24 20:33:14', '2019-08-24 21:56:23');
INSERT INTO "public"."blog_config" VALUES ('3', 'sysVersion', '当前版本号', '1.2.0', '2019-08-24 20:33:23', '2024-06-24 09:57:40.638419');
INSERT INTO "public"."blog_config" VALUES ('7', 'sysUpdateTime', '最后修改时间', '2024-06-24 11:49:32', '2019-08-24 20:33:20', '2022-05-14 20:43:17.239');

INSERT INTO "public"."link" VALUES ('4', 0, '饿了吗', 'https://element-plus.gitee.io/#/zh-CN/component/link#tu-biao', '222222', 123, 't', '2022-09-08 22:32:18.395', '2022-09-08 22:32:18.395158');
INSERT INTO "public"."link" VALUES ('1', 2, '百度', 'https://www.baidu.com', '这是百度', 0, 't', '2022-09-02 23:52:14', '2022-09-02 23:52:14');
INSERT INTO "public"."link" VALUES ('2', 1, 'github', 'https://github.com', '这是github', 0, 'f', '2022-09-02 23:52:21', '2022-09-02 23:52:21');

INSERT INTO "public"."category" VALUES ('1', '默认分类', '', 1, '2019-08-30 15:07:02', 't', NULL);

INSERT INTO "public"."sys_dict_type" ("id", "name", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537673136371220481', '邮件类型', 'email_type', 0, '啊啊', 't', NULL, NULL);
INSERT INTO "public"."sys_dict_type" ("id", "name", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537672535637835778', '性别', 'sex', 0, '', 't', '2022-06-17 15:14:00', '2022-06-17 15:14:03');


INSERT INTO "public"."sys_dict_data" ("id", "type_id", "value", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537686611881369602', 'sex', '男', 'man', 0, '', 't', '2022-06-17 15:14:14', '2022-06-17 15:14:16');
INSERT INTO "public"."sys_dict_data" ("id", "type_id", "value", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537687111485890562', 'sex', '女', 'girl', 0, '啊', 't', '2022-06-17 14:42:30.883645', '2022-06-17 14:42:30.883645');

--
-- PostgreSQL database dump complete
--

