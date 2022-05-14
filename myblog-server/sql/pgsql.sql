/*
 Navicat Premium Data Transfer

 Source Server         : localpg
 Source Server Type    : PostgreSQL
 Source Server Version : 140002
 Source Host           : localhost:5432
 Source Catalog        : my_blog_db
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140002
 File Encoding         : 65001

 Date: 14/05/2022 18:04:32
*/


-- ----------------------------
-- Sequence structure for blog_info_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."blog_info_seq";
CREATE SEQUENCE "public"."blog_info_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 60
CACHE 1;

-- ----------------------------
-- Sequence structure for blog_tag
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."blog_tag";
CREATE SEQUENCE "public"."blog_tag" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 482
CACHE 1;

-- ----------------------------
-- Sequence structure for sequence_name
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sequence_name";
CREATE SEQUENCE "public"."sequence_name" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 48
CACHE 1;

-- ----------------------------
-- Sequence structure for upms_log_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."upms_log_id_seq";
CREATE SEQUENCE "public"."upms_log_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_admin_user";
CREATE TABLE "public"."tb_admin_user" (
  "admin_user_id" int8 NOT NULL,
  "login_user_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "login_password" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "nick_name" varchar(50) COLLATE "pg_catalog"."default",
  "locked" int2,
  "role" int2,
  "avatar" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_admin_user"."admin_user_id" IS '管理员id';
COMMENT ON COLUMN "public"."tb_admin_user"."login_user_name" IS '管理员登陆名称';
COMMENT ON COLUMN "public"."tb_admin_user"."login_password" IS '管理员登陆密码';
COMMENT ON COLUMN "public"."tb_admin_user"."nick_name" IS '管理员显示昵称';
COMMENT ON COLUMN "public"."tb_admin_user"."locked" IS '是否锁定 0未锁定 1已锁定无法登陆';
COMMENT ON COLUMN "public"."tb_admin_user"."role" IS '0普通用户,1管理员';
COMMENT ON COLUMN "public"."tb_admin_user"."avatar" IS '用户头像';
COMMENT ON TABLE "public"."tb_admin_user" IS '后台管理员信息表';

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO "public"."tb_admin_user" VALUES (4, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 0, 1, 'https://i0.hdslb.com/bfs/album/0a75a254e639e7ce606099e1d6c2b75582dc4e8a.jpg');

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_category";
CREATE TABLE "public"."tb_blog_category" (
  "relation_id" int8 NOT NULL,
  "blog_id" int8,
  "category_id" int8,
  "create_time" timestamp(6)
)
;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------

-- ----------------------------
-- Table structure for tb_blog_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_config";
CREATE TABLE "public"."tb_blog_config" (
  "config_field" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "config_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "config_value" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_blog_config"."config_field" IS '字段名';
COMMENT ON COLUMN "public"."tb_blog_config"."config_name" IS '配置名';
COMMENT ON COLUMN "public"."tb_blog_config"."config_value" IS '配置项的值';
COMMENT ON COLUMN "public"."tb_blog_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_blog_config"."update_time" IS '修改时间';

-- ----------------------------
-- Records of tb_blog_config
-- ----------------------------
INSERT INTO "public"."tb_blog_config" VALUES ('sysAuthor', '开发者', '飞雪', '2019-08-24 20:33:17', '2019-08-30 03:27:35');
INSERT INTO "public"."tb_blog_config" VALUES ('sysAuthorImg', '开发者头像', 'http://localhost/authorImg/20190906_18162846.jpg', '2019-08-24 20:33:14', '2019-08-24 21:56:23');
INSERT INTO "public"."tb_blog_config" VALUES ('sysCopyRight', '版权所', 'xuebingsi(xuebingsi) 访问官网', '2019-08-24 20:33:31', '2021-08-25 20:25:04');
INSERT INTO "public"."tb_blog_config" VALUES ('sysEmail', '开发者邮箱', '1320291471@qq.com', '2019-08-24 14:06:48', '2019-08-24 14:06:51');
INSERT INTO "public"."tb_blog_config" VALUES ('sysUpdateTime', '最后修改时间', '2021-10-24 20:33:23', '2019-08-24 20:33:20', '2021-08-10 09:16:49');
INSERT INTO "public"."tb_blog_config" VALUES ('sysUrl', '服务器url', 'localhost:80', '2019-08-24 14:03:23', '2019-08-24 14:03:26');
INSERT INTO "public"."tb_blog_config" VALUES ('sysVersion', '当前版本号', '1.1.0', '2019-08-24 20:33:23', '2019-08-24 11:58:06');
INSERT INTO "public"."tb_blog_config" VALUES ('websiteName', '博客名', '七月飞雪', '2018-11-11 20:33:01', '2021-08-10 09:54:45');

-- ----------------------------
-- Table structure for tb_blog_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_info";
CREATE TABLE "public"."tb_blog_info" (
  "blog_id" int8 NOT NULL,
  "blog_title" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_sub_url" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_preface" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_content" text COLLATE "pg_catalog"."default" NOT NULL,
  "blog_status" int2 NOT NULL,
  "blog_views" int8,
  "enable_comment" int2 NOT NULL,
  "is_deleted" int2 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."tb_blog_info"."blog_id" IS '博客表主键id';
COMMENT ON COLUMN "public"."tb_blog_info"."blog_title" IS '博客标题';
COMMENT ON COLUMN "public"."tb_blog_info"."blog_sub_url" IS '博客自定义路径url';
COMMENT ON COLUMN "public"."tb_blog_info"."blog_preface" IS '博客前言';
COMMENT ON COLUMN "public"."tb_blog_info"."blog_content" IS '博客内容';
COMMENT ON COLUMN "public"."tb_blog_info"."blog_status" IS '0-草稿 1-发布';
COMMENT ON COLUMN "public"."tb_blog_info"."blog_views" IS '阅读量';
COMMENT ON COLUMN "public"."tb_blog_info"."enable_comment" IS '0-允许评论 1-不允许评论';
COMMENT ON COLUMN "public"."tb_blog_info"."is_deleted" IS '是否删除 0=否 1=是';
COMMENT ON COLUMN "public"."tb_blog_info"."create_time" IS '添加时间';
COMMENT ON COLUMN "public"."tb_blog_info"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."tb_blog_info" IS '博客信息表';

-- ----------------------------
-- Records of tb_blog_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_tag";
CREATE TABLE "public"."tb_blog_tag" (
  "relation_id" int8 NOT NULL,
  "blog_id" int8 NOT NULL,
  "tag_id" int4 NOT NULL,
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."tb_blog_tag"."relation_id" IS '关系表id';
COMMENT ON COLUMN "public"."tb_blog_tag"."blog_id" IS '博客id';
COMMENT ON COLUMN "public"."tb_blog_tag"."tag_id" IS '标签id';
COMMENT ON COLUMN "public"."tb_blog_tag"."create_time" IS '添加时间';
COMMENT ON TABLE "public"."tb_blog_tag" IS '博客跟标签的关系表';

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_category";
CREATE TABLE "public"."tb_category" (
  "category_id" int8 NOT NULL,
  "category_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "category_icon" varchar(50) COLLATE "pg_catalog"."default",
  "category_rank" int4 NOT NULL,
  "is_deleted" int2 NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_category"."category_id" IS '分类表主键';
COMMENT ON COLUMN "public"."tb_category"."category_name" IS '分类的名称';
COMMENT ON COLUMN "public"."tb_category"."category_icon" IS '分类的图标';
COMMENT ON COLUMN "public"."tb_category"."category_rank" IS '分类的排序值 被使用的越多数值越大';
COMMENT ON COLUMN "public"."tb_category"."is_deleted" IS '是否删除 0=否 1=是';
COMMENT ON COLUMN "public"."tb_category"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."tb_category" IS '博客分类';

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO "public"."tb_category" VALUES (1, '默认分类', '', 1, 0, '2019-08-30 15:07:02');
INSERT INTO "public"."tb_category" VALUES (22, 'Java进阶', '', 22, 0, '2018-11-12 10:42:25');
INSERT INTO "public"."tb_category" VALUES (33, '前端', NULL, 1, 0, '2021-08-26 13:23:23');

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_comment";
CREATE TABLE "public"."tb_comment" (
  "comment_id" int8 NOT NULL,
  "blog_id" int8 NOT NULL,
  "commentator" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "email" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "website_url" varchar(50) COLLATE "pg_catalog"."default",
  "comment_body" text COLLATE "pg_catalog"."default" NOT NULL,
  "comment_create_time" timestamp(6) NOT NULL,
  "commentator_ip" varchar(20) COLLATE "pg_catalog"."default",
  "reply_body" varchar(200) COLLATE "pg_catalog"."default",
  "reply_create_time" timestamp(6),
  "comment_status" int2 NOT NULL,
  "is_deleted" int2 NOT NULL,
  "user_agent" varchar(255) COLLATE "pg_catalog"."default",
  "os" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_comment"."comment_id" IS '主键id';
COMMENT ON COLUMN "public"."tb_comment"."blog_id" IS '关联的blog主键';
COMMENT ON COLUMN "public"."tb_comment"."commentator" IS '评论者名称';
COMMENT ON COLUMN "public"."tb_comment"."email" IS '评论人的邮箱';
COMMENT ON COLUMN "public"."tb_comment"."website_url" IS '网址';
COMMENT ON COLUMN "public"."tb_comment"."comment_body" IS '评论内容';
COMMENT ON COLUMN "public"."tb_comment"."comment_create_time" IS '评论提交时间';
COMMENT ON COLUMN "public"."tb_comment"."commentator_ip" IS '评论时的ip地址';
COMMENT ON COLUMN "public"."tb_comment"."reply_body" IS '回复内容';
COMMENT ON COLUMN "public"."tb_comment"."reply_create_time" IS '回复时间';
COMMENT ON COLUMN "public"."tb_comment"."comment_status" IS '是否审核通过 0-未审核 1-审核通过';
COMMENT ON COLUMN "public"."tb_comment"."is_deleted" IS '是否删除 0-未删除 1-已删除';
COMMENT ON COLUMN "public"."tb_comment"."user_agent" IS '用户使用的浏览器';
COMMENT ON COLUMN "public"."tb_comment"."os" IS '用户的系统';
COMMENT ON TABLE "public"."tb_comment" IS '评论信息表';

-- ----------------------------
-- Records of tb_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_img
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_img";
CREATE TABLE "public"."tb_img" (
  "id" int4 NOT NULL,
  "img_name" varchar(255) COLLATE "pg_catalog"."default",
  "img_size" int4,
  "img_path" varchar(255) COLLATE "pg_catalog"."default",
  "img_url" varchar(255) COLLATE "pg_catalog"."default",
  "img_type" varchar(255) COLLATE "pg_catalog"."default",
  "md5" varchar(255) COLLATE "pg_catalog"."default",
  "upload_time" timestamp(6)
)
;

-- ----------------------------
-- Records of tb_img
-- ----------------------------

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_link";
CREATE TABLE "public"."tb_link" (
  "link_id" int8 NOT NULL,
  "link_type" int2 NOT NULL,
  "link_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "link_url" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "link_description" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "link_rank" int4 NOT NULL,
  "is_deleted" int2 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."tb_link"."link_id" IS '友链表主键id';
COMMENT ON COLUMN "public"."tb_link"."link_type" IS '友链类别 0-友链 1-推荐 2-个人网站';
COMMENT ON COLUMN "public"."tb_link"."link_name" IS '网站名称';
COMMENT ON COLUMN "public"."tb_link"."link_url" IS '网站链接';
COMMENT ON COLUMN "public"."tb_link"."link_description" IS '网站描述';
COMMENT ON COLUMN "public"."tb_link"."link_rank" IS '用于列表排序';
COMMENT ON COLUMN "public"."tb_link"."is_deleted" IS '是否删除 0-未删除 1-已删除';
COMMENT ON COLUMN "public"."tb_link"."create_time" IS '添加时间';
COMMENT ON TABLE "public"."tb_link" IS '友情链接表';

-- ----------------------------
-- Records of tb_link
-- ----------------------------
INSERT INTO "public"."tb_link" VALUES (1, 0, '百度', 'https://www.baidu.com', '这是百度', 0, 1, '2021-08-25 20:17:06', '2021-08-25 20:17:06');
INSERT INTO "public"."tb_link" VALUES (2, 0, 'github', 'https://github.com', '这是github', 0, 1, '2019-09-02 21:24:44', NULL);
INSERT INTO "public"."tb_link" VALUES (4, 1, '饿了吗', 'https://element-plus.gitee.io/#/zh-CN/component/link#tu-biao', '222222', 123, 0, '2019-09-03 14:47:21', NULL);
INSERT INTO "public"."tb_link" VALUES (5, 0, 'name', 'field', 'value', 0, 1, '2021-08-25 06:28:56', NULL);

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_tag";
CREATE TABLE "public"."tb_tag" (
  "tag_id" int8 NOT NULL,
  "tag_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "is_deleted" int2 NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_tag"."tag_id" IS '标签表主键id';
COMMENT ON COLUMN "public"."tb_tag"."tag_name" IS '标签名称';
COMMENT ON COLUMN "public"."tb_tag"."is_deleted" IS '是否删除 0=否 1=是';
COMMENT ON COLUMN "public"."tb_tag"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."tb_tag" IS '标签表';

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
INSERT INTO "public"."tb_tag" VALUES (1, '默认tag', 0, '2021-08-26 13:05:01');
INSERT INTO "public"."tb_tag" VALUES (152, '333', 0, '2021-10-30 09:05:04');
INSERT INTO "public"."tb_tag" VALUES (154, '444', 0, '2021-10-30 09:06:53');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."blog_info_seq"', 60, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."blog_tag"', 492, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."sequence_name"', 49, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."upms_log_id_seq"', 3, true);

-- ----------------------------
-- Primary Key structure for table tb_admin_user
-- ----------------------------
ALTER TABLE "public"."tb_admin_user" ADD CONSTRAINT "tb_admin_user_pkey" PRIMARY KEY ("admin_user_id", "login_user_name");

-- ----------------------------
-- Primary Key structure for table tb_blog_category
-- ----------------------------
ALTER TABLE "public"."tb_blog_category" ADD CONSTRAINT "tb_blog_category_pkey" PRIMARY KEY ("relation_id");

-- ----------------------------
-- Primary Key structure for table tb_blog_config
-- ----------------------------
ALTER TABLE "public"."tb_blog_config" ADD CONSTRAINT "tb_blog_config_pkey" PRIMARY KEY ("config_field");

-- ----------------------------
-- Primary Key structure for table tb_blog_info
-- ----------------------------
ALTER TABLE "public"."tb_blog_info" ADD CONSTRAINT "tb_blog_info_pkey" PRIMARY KEY ("blog_id");

-- ----------------------------
-- Primary Key structure for table tb_blog_tag
-- ----------------------------
ALTER TABLE "public"."tb_blog_tag" ADD CONSTRAINT "tb_blog_tag_pkey" PRIMARY KEY ("relation_id");

-- ----------------------------
-- Primary Key structure for table tb_category
-- ----------------------------
ALTER TABLE "public"."tb_category" ADD CONSTRAINT "tb_category_pkey" PRIMARY KEY ("category_id");

-- ----------------------------
-- Primary Key structure for table tb_comment
-- ----------------------------
ALTER TABLE "public"."tb_comment" ADD CONSTRAINT "tb_comment_pkey" PRIMARY KEY ("comment_id");

-- ----------------------------
-- Primary Key structure for table tb_img
-- ----------------------------
ALTER TABLE "public"."tb_img" ADD CONSTRAINT "tb_img_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_link
-- ----------------------------
ALTER TABLE "public"."tb_link" ADD CONSTRAINT "tb_link_pkey" PRIMARY KEY ("link_id");

-- ----------------------------
-- Primary Key structure for table tb_tag
-- ----------------------------
ALTER TABLE "public"."tb_tag" ADD CONSTRAINT "tb_tag_pkey" PRIMARY KEY ("tag_id");
