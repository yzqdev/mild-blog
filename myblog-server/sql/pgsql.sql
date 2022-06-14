/*
 Navicat Premium Data Transfer

 Source Server         : localpg
 Source Server Type    : PostgreSQL
 Source Server Version : 140003
 Source Host           : localhost:5432
 Source Catalog        : my_blog_db
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140003
 File Encoding         : 65001

 Date: 15/06/2022 01:20:24
*/


-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_admin_user";
CREATE TABLE "public"."tb_admin_user" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "login_user_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "login_password" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "nick_name" varchar(50) COLLATE "pg_catalog"."default",
  "locked" int2,
  "role" int2,
  "avatar" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_admin_user"."id" IS '管理员id';
COMMENT ON COLUMN "public"."tb_admin_user"."login_user_name" IS '管理员登陆名称';
COMMENT ON COLUMN "public"."tb_admin_user"."login_password" IS '管理员登陆密码';
COMMENT ON COLUMN "public"."tb_admin_user"."nick_name" IS '管理员显示昵称';
COMMENT ON COLUMN "public"."tb_admin_user"."locked" IS '是否锁定 0未锁定 1已锁定无法登陆';
COMMENT ON COLUMN "public"."tb_admin_user"."role" IS '0普通用户,1管理员';
COMMENT ON COLUMN "public"."tb_admin_user"."avatar" IS '用户头像';
COMMENT ON TABLE "public"."tb_admin_user" IS '后台管理员信息表';

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_category";
CREATE TABLE "public"."tb_blog_category" (
  "relation_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_id" int8,
  "category_id" int8,
  "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for tb_blog_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_config";
CREATE TABLE "public"."tb_blog_config" (
  "config_field" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "config_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "config_value" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_blog_config"."config_field" IS '字段名';
COMMENT ON COLUMN "public"."tb_blog_config"."config_name" IS '配置名';
COMMENT ON COLUMN "public"."tb_blog_config"."config_value" IS '配置项的值';
COMMENT ON COLUMN "public"."tb_blog_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_blog_config"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."tb_blog_config"."id" IS 'id';

-- ----------------------------
-- Table structure for tb_blog_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_info";
CREATE TABLE "public"."tb_blog_info" (
  "blog_id" int8 NOT NULL,
  "blog_title" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_sub_url" varchar(200) COLLATE "pg_catalog"."default",
  "blog_preface" varchar(255) COLLATE "pg_catalog"."default",
  "blog_content" text COLLATE "pg_catalog"."default" NOT NULL,
  "blog_status" int2 NOT NULL,
  "blog_views" int8,
  "enable_comment" int2 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6),
  "show" bool NOT NULL
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
COMMENT ON COLUMN "public"."tb_blog_info"."create_time" IS '添加时间';
COMMENT ON COLUMN "public"."tb_blog_info"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."tb_blog_info"."show" IS '是否删除 0=否 1=是';
COMMENT ON TABLE "public"."tb_blog_info" IS '博客信息表';

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_tag";
CREATE TABLE "public"."tb_blog_tag" (
  "relation_id" int8 NOT NULL,
  "blog_id" int8 NOT NULL,
  "tag_id" int8 NOT NULL,
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."tb_blog_tag"."relation_id" IS '关系表id';
COMMENT ON COLUMN "public"."tb_blog_tag"."blog_id" IS '博客id';
COMMENT ON COLUMN "public"."tb_blog_tag"."tag_id" IS '标签id';
COMMENT ON COLUMN "public"."tb_blog_tag"."create_time" IS '添加时间';
COMMENT ON TABLE "public"."tb_blog_tag" IS '博客跟标签的关系表';

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_category";
CREATE TABLE "public"."tb_category" (
  "category_id" int8 NOT NULL,
  "category_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "category_icon" varchar(50) COLLATE "pg_catalog"."default",
  "category_rank" int4 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "show" bool NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_category"."category_id" IS '分类表主键';
COMMENT ON COLUMN "public"."tb_category"."category_name" IS '分类的名称';
COMMENT ON COLUMN "public"."tb_category"."category_icon" IS '分类的图标';
COMMENT ON COLUMN "public"."tb_category"."category_rank" IS '分类的排序值 被使用的越多数值越大';
COMMENT ON COLUMN "public"."tb_category"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_category"."show" IS '是否删除 0=否 1=是';
COMMENT ON TABLE "public"."tb_category" IS '博客分类';

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_comment";
CREATE TABLE "public"."tb_comment" (
  "comment_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
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
  "user_agent" varchar(255) COLLATE "pg_catalog"."default",
  "os" varchar(255) COLLATE "pg_catalog"."default",
  "show" bool NOT NULL
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
COMMENT ON COLUMN "public"."tb_comment"."user_agent" IS '用户使用的浏览器';
COMMENT ON COLUMN "public"."tb_comment"."os" IS '用户的系统';
COMMENT ON COLUMN "public"."tb_comment"."show" IS '是否删除 0-未删除 1-已删除';
COMMENT ON TABLE "public"."tb_comment" IS '评论信息表';

-- ----------------------------
-- Table structure for tb_img
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_img";
CREATE TABLE "public"."tb_img" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "img_name" varchar(255) COLLATE "pg_catalog"."default",
  "img_size" int4,
  "img_path" varchar(255) COLLATE "pg_catalog"."default",
  "img_url" varchar(255) COLLATE "pg_catalog"."default",
  "img_type" varchar(255) COLLATE "pg_catalog"."default",
  "media_type" varchar(255) COLLATE "pg_catalog"."default",
  "upload_time" timestamp(6),
  "thumbnail_path" varchar(255) COLLATE "pg_catalog"."default",
  "md5" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_link";
CREATE TABLE "public"."tb_link" (
  "link_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
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
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_tag";
CREATE TABLE "public"."tb_tag" (
  "tag_id" int8 NOT NULL,
  "tag_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "show" bool NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_tag"."tag_id" IS '标签表主键id';
COMMENT ON COLUMN "public"."tb_tag"."tag_name" IS '标签名称';
COMMENT ON COLUMN "public"."tb_tag"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_tag"."show" IS '是否删除 0=否 1=是';
COMMENT ON TABLE "public"."tb_tag" IS '标签表';

-- ----------------------------
-- Primary Key structure for table tb_admin_user
-- ----------------------------
ALTER TABLE "public"."tb_admin_user" ADD CONSTRAINT "tb_admin_user_pkey" PRIMARY KEY ("id", "login_user_name");

-- ----------------------------
-- Primary Key structure for table tb_blog_category
-- ----------------------------
ALTER TABLE "public"."tb_blog_category" ADD CONSTRAINT "tb_blog_category_pkey" PRIMARY KEY ("relation_id");

-- ----------------------------
-- Primary Key structure for table tb_blog_config
-- ----------------------------
ALTER TABLE "public"."tb_blog_config" ADD CONSTRAINT "tb_blog_config_pkey" PRIMARY KEY ("id");

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
