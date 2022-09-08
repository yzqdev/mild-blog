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

 Date: 08/09/2022 23:56:44
*/


-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."admin_user";
CREATE TABLE "public"."admin_user" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "nickname" varchar(50) COLLATE "pg_catalog"."default",
  "locked" bool,
  "role" int2,
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "uuid" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."admin_user"."id" IS '管理员id';
COMMENT ON COLUMN "public"."admin_user"."username" IS '管理员登陆名称';
COMMENT ON COLUMN "public"."admin_user"."password" IS '管理员登陆密码';
COMMENT ON COLUMN "public"."admin_user"."nickname" IS '管理员显示昵称';
COMMENT ON COLUMN "public"."admin_user"."locked" IS '是否锁定 0未锁定 1已锁定无法登陆';
COMMENT ON COLUMN "public"."admin_user"."role" IS '0普通用户,1管理员';
COMMENT ON COLUMN "public"."admin_user"."avatar" IS '用户头像';
COMMENT ON COLUMN "public"."admin_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."admin_user"."uuid" IS 'uuid';
COMMENT ON TABLE "public"."admin_user" IS '后台管理员信息表';

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."blog_category";
CREATE TABLE "public"."blog_category" (
  "relation_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_id" varchar(64) COLLATE "pg_catalog"."default",
  "category_id" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for blog_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."blog_config";
CREATE TABLE "public"."blog_config" (
  "id" varchar(255) COLLATE "pg_catalog"."default",
  "config_code" varchar(255) COLLATE "pg_catalog"."default",
  "config_name" varchar(255) COLLATE "pg_catalog"."default",
  "config_value" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for blog_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."blog_info";
CREATE TABLE "public"."blog_info" (
  "blog_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_title" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "sub_url" varchar(200) COLLATE "pg_catalog"."default",
  "preface" varchar(255) COLLATE "pg_catalog"."default",
  "blog_content" text COLLATE "pg_catalog"."default" NOT NULL,
  "blog_views" int8,
  "enable_comment" bool,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6),
  "show" bool NOT NULL,
  "deleted" bool
)
;
COMMENT ON COLUMN "public"."blog_info"."blog_id" IS '博客表主键id';
COMMENT ON COLUMN "public"."blog_info"."blog_title" IS '博客标题';
COMMENT ON COLUMN "public"."blog_info"."sub_url" IS '博客自定义路径url';
COMMENT ON COLUMN "public"."blog_info"."preface" IS '博客前言';
COMMENT ON COLUMN "public"."blog_info"."blog_content" IS '博客内容';
COMMENT ON COLUMN "public"."blog_info"."blog_views" IS '阅读量';
COMMENT ON COLUMN "public"."blog_info"."enable_comment" IS '0-允许评论 1-不允许评论';
COMMENT ON COLUMN "public"."blog_info"."create_time" IS '添加时间';
COMMENT ON COLUMN "public"."blog_info"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."blog_info"."show" IS '是否发布';
COMMENT ON COLUMN "public"."blog_info"."deleted" IS '是否已经删除';
COMMENT ON TABLE "public"."blog_info" IS '博客信息表';

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."blog_tag";
CREATE TABLE "public"."blog_tag" (
  "relation_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "tag_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."blog_tag"."relation_id" IS '关系表id';
COMMENT ON COLUMN "public"."blog_tag"."blog_id" IS '博客id';
COMMENT ON COLUMN "public"."blog_tag"."tag_id" IS '标签id';
COMMENT ON COLUMN "public"."blog_tag"."create_time" IS '添加时间';
COMMENT ON TABLE "public"."blog_tag" IS '博客跟标签的关系表';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS "public"."category";
CREATE TABLE "public"."category" (
  "category_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "category_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "category_icon" varchar(50) COLLATE "pg_catalog"."default",
  "category_rank" int4 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "show" bool NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."category"."category_id" IS '分类表主键';
COMMENT ON COLUMN "public"."category"."category_name" IS '分类的名称';
COMMENT ON COLUMN "public"."category"."category_icon" IS '分类的图标';
COMMENT ON COLUMN "public"."category"."category_rank" IS '分类的排序值 被使用的越多数值越大';
COMMENT ON COLUMN "public"."category"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."category"."show" IS '是否删除 0=否 1=是';
COMMENT ON TABLE "public"."category" IS '博客分类';

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."comment";
CREATE TABLE "public"."comment" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "blog_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "commentator" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "email" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "website_url" varchar(50) COLLATE "pg_catalog"."default",
  "comment_body" text COLLATE "pg_catalog"."default" NOT NULL,
  "comment_create_time" timestamp(6) NOT NULL,
  "commentator_ip" varchar(20) COLLATE "pg_catalog"."default",
  "reply_body" varchar(200) COLLATE "pg_catalog"."default",
  "reply_create_time" timestamp(6),
  "comment_status" bool NOT NULL,
  "user_agent" varchar(255) COLLATE "pg_catalog"."default",
  "os" varchar(255) COLLATE "pg_catalog"."default",
  "deleted" bool NOT NULL
)
;
COMMENT ON COLUMN "public"."comment"."id" IS '主键id';
COMMENT ON COLUMN "public"."comment"."blog_id" IS '关联的blog主键';
COMMENT ON COLUMN "public"."comment"."commentator" IS '评论者名称';
COMMENT ON COLUMN "public"."comment"."email" IS '评论人的邮箱';
COMMENT ON COLUMN "public"."comment"."website_url" IS '网址';
COMMENT ON COLUMN "public"."comment"."comment_body" IS '评论内容';
COMMENT ON COLUMN "public"."comment"."comment_create_time" IS '评论提交时间';
COMMENT ON COLUMN "public"."comment"."commentator_ip" IS '评论时的ip地址';
COMMENT ON COLUMN "public"."comment"."reply_body" IS '回复内容';
COMMENT ON COLUMN "public"."comment"."reply_create_time" IS '回复时间';
COMMENT ON COLUMN "public"."comment"."comment_status" IS '是否审核通过 0-未审核 1-审核通过';
COMMENT ON COLUMN "public"."comment"."user_agent" IS '用户使用的浏览器';
COMMENT ON COLUMN "public"."comment"."os" IS '用户的系统';
COMMENT ON COLUMN "public"."comment"."deleted" IS '是否删除 0-未删除 1-已删除';
COMMENT ON TABLE "public"."comment" IS '评论信息表';

-- ----------------------------
-- Table structure for email_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."email_config";
CREATE TABLE "public"."email_config" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "email_key" varchar(255) COLLATE "pg_catalog"."default",
  "email_url" varchar(255) COLLATE "pg_catalog"."default",
  "port" varchar(255) COLLATE "pg_catalog"."default",
  "email_name" varchar(255) COLLATE "pg_catalog"."default",
  "enable" bool
)
;
COMMENT ON COLUMN "public"."email_config"."email" IS '邮箱';
COMMENT ON COLUMN "public"."email_config"."email_key" IS '授权码';
COMMENT ON COLUMN "public"."email_config"."email_url" IS '服务器';
COMMENT ON COLUMN "public"."email_config"."port" IS '端口';
COMMENT ON COLUMN "public"."email_config"."email_name" IS '用户名';
COMMENT ON COLUMN "public"."email_config"."enable" IS '1为可用，其他为不使用';

-- ----------------------------
-- Table structure for img
-- ----------------------------
DROP TABLE IF EXISTS "public"."img";
CREATE TABLE "public"."img" (
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
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS "public"."link";
CREATE TABLE "public"."link" (
  "link_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "link_type" int2 NOT NULL,
  "link_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "link_url" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "link_description" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "link_rank" int4 NOT NULL,
  "show" bool NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."link"."link_id" IS '友链表主键id';
COMMENT ON COLUMN "public"."link"."link_type" IS '友链类别 0-友链 1-推荐 2-个人网站';
COMMENT ON COLUMN "public"."link"."link_name" IS '网站名称';
COMMENT ON COLUMN "public"."link"."link_url" IS '网站链接';
COMMENT ON COLUMN "public"."link"."link_description" IS '网站描述';
COMMENT ON COLUMN "public"."link"."link_rank" IS '用于列表排序';
COMMENT ON COLUMN "public"."link"."show" IS '是否删除 0-未删除 1-已删除';
COMMENT ON COLUMN "public"."link"."create_time" IS '添加时间';
COMMENT ON TABLE "public"."link" IS '友情链接表';

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_data";
CREATE TABLE "public"."sys_dict_data" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "type_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "value" text COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "sort" int4 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" bool NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_dict_data"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_dict_data"."type_id" IS '字典类型id';
COMMENT ON COLUMN "public"."sys_dict_data"."value" IS '值';
COMMENT ON COLUMN "public"."sys_dict_data"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_dict_data"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dict_data"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict_data"."status" IS '状态（字典 0正常 1停用 2删除）';
COMMENT ON COLUMN "public"."sys_dict_data"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict_data"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_dict_data" IS '系统字典值表';

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_type";
CREATE TABLE "public"."sys_dict_type" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "sort" int4 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" bool NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_dict_type"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_dict_type"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_dict_type"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_dict_type"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dict_type"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict_type"."status" IS '状态（字典 0正常 1停用 2删除）';
COMMENT ON COLUMN "public"."sys_dict_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict_type"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_dict_type" IS '系统字典类型表';

-- ----------------------------
-- Table structure for sys_op_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_op_log";
CREATE TABLE "public"."sys_op_log" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(50) COLLATE "pg_catalog"."default",
  "op_type" varchar(16) COLLATE "pg_catalog"."default",
  "success" char(1) COLLATE "pg_catalog"."default",
  "message" text COLLATE "pg_catalog"."default",
  "ip" varchar(255) COLLATE "pg_catalog"."default",
  "location" varchar(255) COLLATE "pg_catalog"."default",
  "browser" varchar(255) COLLATE "pg_catalog"."default",
  "os" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(500) COLLATE "pg_catalog"."default",
  "class_name" varchar(500) COLLATE "pg_catalog"."default",
  "method_name" varchar(500) COLLATE "pg_catalog"."default",
  "req_method" varchar(255) COLLATE "pg_catalog"."default",
  "param" text COLLATE "pg_catalog"."default",
  "result" text COLLATE "pg_catalog"."default",
  "op_time" timestamp(6),
  "account" varchar(50) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for sys_timers
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_timers";
CREATE TABLE "public"."sys_timers" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "timer_name" varchar(255) COLLATE "pg_catalog"."default",
  "action_class" varchar(255) COLLATE "pg_catalog"."default",
  "cron" varchar(255) COLLATE "pg_catalog"."default",
  "status" bool,
  "remark" varchar(1000) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_timers"."id" IS '定时器id';
COMMENT ON COLUMN "public"."sys_timers"."timer_name" IS '任务名称';
COMMENT ON COLUMN "public"."sys_timers"."action_class" IS '执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）';
COMMENT ON COLUMN "public"."sys_timers"."cron" IS '定时任务表达式';
COMMENT ON COLUMN "public"."sys_timers"."status" IS '状态';
COMMENT ON COLUMN "public"."sys_timers"."remark" IS '备注信息';
COMMENT ON COLUMN "public"."sys_timers"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_timers"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_timers" IS '定时任务';

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."tag";
CREATE TABLE "public"."tag" (
  "tag_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "tag_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "show" bool NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."tag"."tag_id" IS '标签表主键id';
COMMENT ON COLUMN "public"."tag"."tag_name" IS '标签名称';
COMMENT ON COLUMN "public"."tag"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tag"."show" IS '是否删除 0=否 1=是';
COMMENT ON COLUMN "public"."tag"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."tag" IS '标签表';

-- ----------------------------
-- Primary Key structure for table admin_user
-- ----------------------------
ALTER TABLE "public"."admin_user" ADD CONSTRAINT "tb_admin_user_pkey" PRIMARY KEY ("id", "username");

-- ----------------------------
-- Primary Key structure for table blog_category
-- ----------------------------
ALTER TABLE "public"."blog_category" ADD CONSTRAINT "tb_blog_category_pkey" PRIMARY KEY ("relation_id");

-- ----------------------------
-- Primary Key structure for table blog_info
-- ----------------------------
ALTER TABLE "public"."blog_info" ADD CONSTRAINT "tb_blog_info_pkey" PRIMARY KEY ("blog_id");

-- ----------------------------
-- Primary Key structure for table blog_tag
-- ----------------------------
ALTER TABLE "public"."blog_tag" ADD CONSTRAINT "tb_blog_tag_pkey" PRIMARY KEY ("relation_id");

-- ----------------------------
-- Primary Key structure for table category
-- ----------------------------
ALTER TABLE "public"."category" ADD CONSTRAINT "tb_category_pkey" PRIMARY KEY ("category_id");

-- ----------------------------
-- Primary Key structure for table comment
-- ----------------------------
ALTER TABLE "public"."comment" ADD CONSTRAINT "tb_comment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table email_config
-- ----------------------------
ALTER TABLE "public"."email_config" ADD CONSTRAINT "email_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table img
-- ----------------------------
ALTER TABLE "public"."img" ADD CONSTRAINT "tb_img_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table link
-- ----------------------------
ALTER TABLE "public"."link" ADD CONSTRAINT "tb_link_pkey" PRIMARY KEY ("link_id");

-- ----------------------------
-- Primary Key structure for table sys_dict_data
-- ----------------------------
ALTER TABLE "public"."sys_dict_data" ADD CONSTRAINT "sys_dict_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "public"."sys_dict_type" ADD CONSTRAINT "sys_dict_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_op_log
-- ----------------------------
ALTER TABLE "public"."sys_op_log" ADD CONSTRAINT "sys_op_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_timers
-- ----------------------------
ALTER TABLE "public"."sys_timers" ADD CONSTRAINT "sys_timers_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tag
-- ----------------------------
ALTER TABLE "public"."tag" ADD CONSTRAINT "tb_tag_pkey" PRIMARY KEY ("tag_id");
