package com.site.blog.config.listener;

/**
 * @author yanni
 * @date time 2022/6/15 18:12
 * @modified By:
 */
public interface SqlConstant {
    String initAdminSql= """
            INSERT INTO "public"."admin_user" ("id", "username", "password", "nickname", "locked", "role", "avatar") VALUES ('myid', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '啊啊啊', 'f', 1, 'http://localhost:2801/upload/20220615_163021.jpg');
            """;
    String insertConfigDataSql = """
                INSERT INTO "public"."blog_config" VALUES ('sysAuthor', '开发者', '飞雪', '2019-08-24 20:33:17', '2019-08-30 03:27:35', 1);
                INSERT INTO "public"."blog_config" VALUES ('init', '初始化', 'init', '2019-08-24 20:33:17', '2019-08-30 03:27:35', 'init');
                INSERT INTO "public"."blog_config" VALUES ('sysAuthorImg', '开发者头像', 'http://localhost/2801/logo.img', '2019-08-24 20:33:14', '2019-08-24 21:56:23', 2);
                INSERT INTO "public"."blog_config" VALUES ('sysEmail', '开发者邮箱', 'xxxx@qq.com', '2019-08-24 14:06:48', '2019-08-24 14:06:51', 4);
                INSERT INTO "public"."blog_config" VALUES ('sysUrl', '服务器url', 'localhost:2801', '2019-08-24 14:03:23', '2019-08-24 14:03:26', 6);
                INSERT INTO "public"."blog_config" VALUES ('sysVersion', '当前版本号', '1.1.0', '2019-08-24 20:33:23', '2019-08-24 11:58:06', 7);
                INSERT INTO "public"."blog_config" VALUES ('websiteName', '博客名', '七月飞雪', '2018-11-11 20:33:01', '2021-08-10 09:54:45', 8);
                INSERT INTO "public"."blog_config" VALUES ('sysCopyRight', '版权所', 'yzqdev', '2019-08-24 20:33:31', '2022-05-14 20:42:11.383', 3);
                INSERT INTO "public"."blog_config" VALUES ('sysUpdateTime', '最后修改时间', '2022-03-24 20:33:23', '2019-08-24 20:33:20', '2022-05-14 20:43:17.239', 5);""";
    String initTagSql= """
            INSERT INTO "public"."tag" ("tag_id", "tag_name", "create_time", "show") VALUES ('1', '默认tag', '2021-08-26 13:05:01', 't');
            """;
    String initCateSql= """
            INSERT INTO "public"."category" ("category_id", "category_name", "category_icon", "category_rank", "create_time", "show") VALUES ('1', '默认分类', '', 1, '2019-08-30 15:07:02', 't');
            """;
    String initLinkSql= """
            INSERT INTO "public"."link" ("link_id", "link_type", "link_name", "link_url", "link_description", "link_rank", "is_deleted", "create_time", "update_time") VALUES ('1', 0, '百度', 'https://www.baidu.com', '这是百度', 0, 1, '2021-08-25 20:17:06', '2021-08-25 20:17:06');
            INSERT INTO "public"."link" ("link_id", "link_type", "link_name", "link_url", "link_description", "link_rank", "is_deleted", "create_time", "update_time") VALUES ('2', 0, 'github', 'https://github.com', '这是github', 0, 1, '2019-09-02 21:24:44', NULL);
            INSERT INTO "public"."link" ("link_id", "link_type", "link_name", "link_url", "link_description", "link_rank", "is_deleted", "create_time", "update_time") VALUES ('4', 1, '饿了吗', 'https://element-plus.gitee.io/#/zh-CN/component/link#tu-biao', '222222', 123, 0, '2019-09-03 14:47:21', NULL);
            """;
String oldSqlString="""
		oldstring
		""";

    String initSysDictTypeSql= """
            INSERT INTO "public"."sys_dict_type" ("id", "name", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537673136371220481', '邮件类型', 'email_type', 0, '啊啊', 't', NULL, NULL);
            INSERT INTO "public"."sys_dict_type" ("id", "name", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537672535637835778', '性别', 'sex', 0, '', 't', '2022-06-17 15:14:00', '2022-06-17 15:14:03');
                        
            """;
    String initSysDictDataSql= """
            INSERT INTO "public"."sys_dict_data" ("id", "type_id", "value", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537686611881369602', 'sex', '男', 'man', 0, '', 't', '2022-06-17 15:14:14', '2022-06-17 15:14:16');
            INSERT INTO "public"."sys_dict_data" ("id", "type_id", "value", "code", "sort", "remark", "status", "create_time", "update_time") VALUES ('1537687111485890562', 'sex', '女', 'girl', 0, '啊', 't', '2022-06-17 14:42:30.883645', '2022-06-17 14:42:30.883645');
                        
            """;
}
