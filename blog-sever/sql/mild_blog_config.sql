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

COPY public.blog_config (id, config_code, config_name, config_value, create_time, update_time) FROM stdin;
1	sysAuthor	开发者	飞雪	2019-08-24 20:33:17	2019-08-30 03:27:35
2	sysUrl	服务器url	localhost:80	2019-08-24 14:03:23	2019-08-24 14:03:26
3	sysVersion	当前版本号	1.1.0	2019-08-24 20:33:23	2019-08-24 11:58:06
4	websiteName	博客名	七月飞雪	2018-11-11 20:33:01	2021-08-10 09:54:45
5	sysCopyRight	版权所	yzqdev	2019-08-24 20:33:31	2022-05-14 20:42:11.383
init	init	初始化	init	2019-08-24 20:33:17	2019-08-30 03:27:35
8	filing	备案	豫ICP备2022004109号	2022-06-18 13:11:12	2022-06-18 13:11:15
6	sysAuthorImg	开发者头像	http://localhost:2801/upload/20220908_233437.png	2019-08-24 20:33:14	2019-08-24 21:56:23
7	sysUpdateTime	最后修改时间	2023-02-26 18:27:42	2019-08-24 20:33:20	2022-05-14 20:43:17.239
\.


--
-- PostgreSQL database dump complete
--

