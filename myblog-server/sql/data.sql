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

 Date: 23/05/2022 01:59:29
*/


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
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_blog_config"."config_field" IS '字段名';
COMMENT ON COLUMN "public"."tb_blog_config"."config_name" IS '配置名';
COMMENT ON COLUMN "public"."tb_blog_config"."config_value" IS '配置项的值';
COMMENT ON COLUMN "public"."tb_blog_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_blog_config"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."tb_blog_config"."id" IS 'id';

-- ----------------------------
-- Records of tb_blog_config
-- ----------------------------
INSERT INTO "public"."tb_blog_config" VALUES ('sysAuthor', '开发者', '飞雪', '2019-08-24 20:33:17', '2019-08-30 03:27:35', '1');
INSERT INTO "public"."tb_blog_config" VALUES ('sysAuthorImg', '开发者头像', 'http://localhost/authorImg/20190906_18162846.jpg', '2019-08-24 20:33:14', '2019-08-24 21:56:23', '2');
INSERT INTO "public"."tb_blog_config" VALUES ('sysEmail', '开发者邮箱', '1320291471@qq.com', '2019-08-24 14:06:48', '2019-08-24 14:06:51', '4');
INSERT INTO "public"."tb_blog_config" VALUES ('sysUrl', '服务器url', 'localhost:80', '2019-08-24 14:03:23', '2019-08-24 14:03:26', '6');
INSERT INTO "public"."tb_blog_config" VALUES ('sysVersion', '当前版本号', '1.1.0', '2019-08-24 20:33:23', '2019-08-24 11:58:06', '7');
INSERT INTO "public"."tb_blog_config" VALUES ('websiteName', '博客名', '七月飞雪', '2018-11-11 20:33:01', '2021-08-10 09:54:45', '8');
INSERT INTO "public"."tb_blog_config" VALUES ('sysCopyRight', '版权所', 'yzqdev', '2019-08-24 20:33:31', '2022-05-14 20:42:11.383', '3');
INSERT INTO "public"."tb_blog_config" VALUES ('sysUpdateTime', '最后修改时间', '2022-03-24 20:33:23', '2019-08-24 20:33:20', '2022-05-14 20:43:17.239', '5');

-- ----------------------------
-- Primary Key structure for table tb_blog_config
-- ----------------------------
ALTER TABLE "public"."tb_blog_config" ADD CONSTRAINT "tb_blog_config_pkey" PRIMARY KEY ("id");
