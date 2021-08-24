/*
 Navicat Premium Data Transfer

 Source Server         : tencent
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 81.69.227.146:3306
 Source Schema         : my_blog_db

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 24/08/2021 22:36:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_user`;
CREATE TABLE `tb_admin_user`  (
  `admin_user_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员显示昵称',
  `locked` tinyint NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`, `login_user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES (1, 'admin', '670b14728ad9902aecba32e22fa4f6bd', '南街', 0);
INSERT INTO `tb_admin_user` VALUES (4, 'yzq', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0);

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_category`;
CREATE TABLE `tb_blog_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类的图标',
  `category_rank` int NOT NULL DEFAULT 1 COMMENT '分类的排序值 被使用的越多数值越大',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO `tb_blog_category` VALUES (1, '默认分类', '', 1, 0, '2019-08-30 15:07:02');
INSERT INTO `tb_blog_category` VALUES (20, 'About', '', 9, 0, '2018-11-12 00:28:49');
INSERT INTO `tb_blog_category` VALUES (22, 'Java进阶', '', 22, 0, '2018-11-12 10:42:25');
INSERT INTO `tb_blog_category` VALUES (24, '日常随笔', '', 23, 0, '2018-11-12 10:43:21');
INSERT INTO `tb_blog_category` VALUES (25, 'About2', NULL, 100, 0, '2019-09-02 01:58:53');

-- ----------------------------
-- Table structure for tb_blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_comment`;
CREATE TABLE `tb_blog_comment`  (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint NOT NULL DEFAULT 0 COMMENT '关联的blog主键',
  `commentator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论者名称',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
  `website_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '网址',
  `comment_body` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `comment_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论提交时间',
  `commentator_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '评论时的ip地址',
  `reply_body` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '回复内容',
  `reply_create_time` timestamp NULL DEFAULT NULL COMMENT '回复时间',
  `comment_status` tinyint NOT NULL DEFAULT 0 COMMENT '是否审核通过 0-未审核 1-审核通过',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_comment
-- ----------------------------
INSERT INTO `tb_blog_comment` VALUES (22, 7, '评论一下', '313134557@qq.com', '', '#  这是评论', '2021-08-12 15:40:36', '', '', NULL, 1, 0);

-- ----------------------------
-- Table structure for tb_blog_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_config`;
CREATE TABLE `tb_blog_config`  (
  `config_field` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '字段名',
  `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名',
  `config_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置项的值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`config_field`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_config
-- ----------------------------
INSERT INTO `tb_blog_config` VALUES ('sysAuthor', '开发者', '飞雪', '2019-08-24 20:33:17', '2019-08-30 03:27:35');
INSERT INTO `tb_blog_config` VALUES ('sysAuthorImg', '开发者头像', 'http://localhost/authorImg/20190906_18162846.jpg', '2019-08-24 20:33:14', '2019-08-24 21:56:23');
INSERT INTO `tb_blog_config` VALUES ('sysCopyRight', '版权所有', 'xuebingsi(xuebingsi) 访问官网', '2019-08-24 20:33:31', '2019-08-24 11:58:06');
INSERT INTO `tb_blog_config` VALUES ('sysEmail', '开发者邮箱', '1320291471@qq.com', '2019-08-24 14:06:48', '2019-08-24 14:06:51');
INSERT INTO `tb_blog_config` VALUES ('sysUpdateTime', '最后修改时间', '2021-08-24 20:33:23', '2019-08-24 20:33:20', '2021-08-10 09:16:49');
INSERT INTO `tb_blog_config` VALUES ('sysUrl', '服务器url', 'localhost:80', '2019-08-24 14:03:23', '2019-08-24 14:03:26');
INSERT INTO `tb_blog_config` VALUES ('sysVersion', '当前版本号', '1.1.0', '2019-08-24 20:33:23', '2019-08-24 11:58:06');
INSERT INTO `tb_blog_config` VALUES ('websiteName', '博客名', '七月飞雪', '2018-11-11 20:33:01', '2021-08-10 09:54:45');

-- ----------------------------
-- Table structure for tb_blog_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_info`;
CREATE TABLE `tb_blog_info`  (
  `blog_id` bigint NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blog_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标题',
  `blog_sub_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客自定义路径url',
  `blog_preface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客前言',
  `blog_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客内容',
  `blog_category_id` int NOT NULL COMMENT '博客分类id',
  `blog_category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客分类(冗余字段)',
  `blog_tags` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客标签(冗余字段)',
  `blog_status` tinyint NOT NULL DEFAULT 0 COMMENT '0-草稿 1-发布',
  `blog_views` bigint NOT NULL DEFAULT 0 COMMENT '阅读量',
  `enable_comment` tinyint NOT NULL DEFAULT 0 COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_info
-- ----------------------------
INSERT INTO `tb_blog_info` VALUES (1, '我是南街', 'about', '关于自己的简短介绍', '## About me\n\n我是南街:tw-1f471:，一名很普通的大三学生，写文章算是随心而欲的，基本上我想写什么就写什么，也不会为了谁谁谁而写。我弄这个博客写文章的初衷是为了记录自己的成长和锻炼自己。\n\n成长的路总是跌跌撞撞的，从大二到现在，差不多有两年了。在两年里，有过被人赞赏、被人肯定的喜悦；有过被人喷、被人怼的心酸；有过质疑自己的时候，甚至一度想就这样放弃……这个世界从不曾温情脉脉，也没有什么岁月静好，你我必须要非常努力才能争取到自己的一席之地。\n\n梦想永在，你不努力，谁也给不了你想要的生活。反问自己没有梦想，何必远方？\n\n相信浏览这段话的你也知道，学习是一件极其枯燥而无聊的过程，甚至有时候显得很无助，我也想告诉你，成长就是这样一件残酷的事情，任何成功都不是一蹴而就，需要坚持、需要付出、需要你的毅力，短期可能看不到收获，因为破茧成蝶所耗费的时间不是一天。\n\n最后，2020无畏年少青春，迎风潇洒前行！！\n## Contact\n\n我的邮箱：1320291471@qq.com\nQQ：1320291471\n欢迎加我交流', 24, '日常随笔', '世界上有一个很可爱的人,现在这个人就在看这句话', 0, 332, 1, 0, '2021-07-01 10:01:50', '2021-08-12 01:58:18');
INSERT INTO `tb_blog_info` VALUES (7, 'es6-es8用法', 'redis', '测试介绍', '## ES6 (ES2015)\r\n\r\n\r\n直接看掘金上的文章不香吗??\r\n[https://juejin.cn/post/6844903775329583112](https://juejin.cn/post/6844903775329583112)\r\n\r\n\r\n## ES7新特性（2016）\r\n\r\n\r\nES2016添加了两个小的特性来说明标准化过程：\r\n\r\n\r\n- 数组includes()方法，用来判断一个数组是否包含一个指定的值，根据情况，如果包含则返回true，否则返回false。\r\n- a ** b指数运算符，它与 Math.pow(a, b)相同。\r\n\r\n\r\n\r\n### 1.Array.prototype.includes()\r\n\r\n\r\n`includes()` 函数用来判断一个数组是否包含一个指定的值，如果包含则返回 `true`，否则返回`false`。\r\n\r\n\r\n`includes` 函数与 `indexOf` 函数很相似，下面两个表达式是等价的：\r\n\r\n\r\n```\r\narr.includes(x)\r\narr.indexOf(x) >= 0\r\n\r\n复制代码\r\n```\r\n\r\n\r\n接下来我们来判断数字中是否包含某个元素：\r\n\r\n\r\n> 在ES7之前的做法\r\n\r\n\r\n\r\n使用`indexOf()`验证数组中是否存在某个元素，这时需要根据返回值是否为-1来判断：\r\n\r\n\r\n```\r\nlet arr = [\'react\', \'angular\', \'vue\'];\r\n\r\nif (arr.indexOf(\'react\') !== -1)\r\n{\r\n    console.log(\'react存在\');\r\n}\r\n\r\n复制代码\r\n```\r\n\r\n\r\n> 使用ES7的includes()\r\n\r\n\r\n\r\n使用includes()验证数组中是否存在某个元素，这样更加直观简单：\r\n\r\n\r\n```\r\nlet arr = [\'react\', \'angular\', \'vue\'];\r\n\r\nif (arr.includes(\'react\'))\r\n{\r\n    console.log(\'react存在\');\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n### 2.指数操作符\r\n\r\n\r\n在ES7中引入了指数运算符`**`，`**`具有与`Math.pow(..)`等效的计算结果。\r\n\r\n\r\n> 不使用指数操作符\r\n\r\n\r\n\r\n使用自定义的递归函数calculateExponent或者Math.pow()进行指数运算：\r\n\r\n\r\n```\r\nfunction calculateExponent(base, exponent)\r\n{\r\n    if (exponent === 1)\r\n    {\r\n        return base;\r\n    }\r\n    else\r\n    {\r\n        return base * calculateExponent(base, exponent - 1);\r\n    }\r\n}\r\n\r\nconsole.log(calculateExponent(2, 10)); // 输出1024\r\nconsole.log(Math.pow(2, 10)); // 输出1024\r\n复制代码\r\n```\r\n\r\n\r\n> 使用指数操作符\r\n\r\n\r\n\r\n使用指数运算符**，就像+、-等操作符一样：\r\n\r\n\r\n```\r\nconsole.log(2**10);// 输出1024\r\n复制代码\r\n```\r\n\r\n\r\n## ES8新特性（2017）\r\n\r\n\r\n- async/await\r\n- `Object.values()`\r\n- `Object.entries()`\r\n- String padding: `padStart()`和`padEnd()`，填充字符串达到当前长度\r\n- 函数参数列表结尾允许逗号\r\n- `Object.getOwnPropertyDescriptors()`\r\n- `ShareArrayBuffer`和`Atomics`对象，用于从共享内存位置读取和写入\r\n\r\n\r\n\r\n### 1.async/await\r\n\r\n\r\nES2018引入异步迭代器（asynchronous iterators），这就像常规迭代器，除了`next()`方法返回一个Promise。因此`await`可以和`for...of`循环一起使用，以串行的方式运行异步操作。例如：\r\n\r\n\r\n```\r\nasync function process(array) {\r\n  for await (let i of array) {\r\n    doSomething(i);\r\n  }\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n### 2.Object.values()\r\n\r\n\r\n`Object.values()`是一个与`Object.keys()`类似的新函数，但返回的是Object自身属性的所有值，不包括继承的值。\r\n\r\n\r\n假设我们要遍历如下对象`obj`的所有值：\r\n\r\n\r\n```\r\nconst obj = {a: 1, b: 2, c: 3};\r\n复制代码\r\n```\r\n\r\n\r\n> 不使用Object.values() :ES7\r\n\r\n\r\n\r\n```\r\nconst vals=Object.keys(obj).map(key=>obj[key]);\r\nconsole.log(vals);//[1, 2, 3]\r\n复制代码\r\n```\r\n\r\n\r\n> 使用Object.values() :ES8\r\n\r\n\r\n\r\n```\r\nconst values=Object.values(obj1);\r\nconsole.log(values);//[1, 2, 3]\r\n复制代码\r\n```\r\n\r\n\r\n从上述代码中可以看出`Object.values()`为我们省去了遍历key，并根据这些key获取value的步骤。\r\n\r\n\r\n### 3.Object.entries()\r\n\r\n\r\n`Object.entries()`函数返回一个给定对象自身可枚举属性的键值对的数组。\r\n\r\n\r\n接下来我们来遍历上文中的`obj`对象的所有属性的key和value：\r\n\r\n\r\n> 不使用Object.entries() :ES7\r\n\r\n\r\n\r\n```\r\nObject.keys(obj).forEach(key=>{\r\n	console.log(\'key:\'+key+\' value:\'+obj[key]);\r\n})\r\n//key:a value:1\r\n//key:b value:2\r\n//key:c value:3\r\n复制代码\r\n```\r\n\r\n\r\n> 使用Object.entries() :ES8\r\n\r\n\r\n\r\n```\r\nfor(let [key,value] of Object.entries(obj1)){\r\n	console.log(`key: ${key} value:${value}`)\r\n}\r\n//key:a value:1\r\n//key:b value:2\r\n//key:c value:3\r\n\r\n复制代码\r\n```\r\n\r\n\r\n### 4.String padding\r\n\r\n\r\n在ES8中String新增了两个实例函数`String.prototype.padStart`和`String.prototype.padEnd`，允许将空字符串或其他字符串添加到原始字符串的开头或结尾。\r\n\r\n\r\n> String.padStart(targetLength,[padString])\r\n\r\n\r\n\r\n- targetLength:当前字符串需要填充到的目标长度。如果这个数值小于当前字符串的长度，则返回当前字符串本身。\r\n- padString:(可选)填充字符串。如果字符串太长，使填充后的字符串长度超过了目标长度，则只保留最左侧的部分，其他部分会被截断，此参数的缺省值为 \" \"。\r\n\r\n\r\n\r\n```\r\nconsole.log(\'0.0\'.padStart(4,\'10\')) //10.0\r\nconsole.log(\'0.0\'.padStart(20))// 0.00    \r\n复制代码\r\n```\r\n\r\n\r\n> String.padEnd(targetLength,padString])\r\n\r\n\r\n\r\n- targetLength:当前字符串需要填充到的目标长度。如果这个数值小于当前字符串的长度，则返回当前字符串本身。\r\n- padString:(可选) 填充字符串。如果字符串太长，使填充后的字符串长度超过了目标长度，则只保留最左侧的部分，其他部分会被截断，此参数的缺省值为 \" \"；\r\n\r\n\r\n\r\n```\r\nconsole.log(\'0.0\'.padEnd(4,\'0\')) //0.00    \r\nconsole.log(\'0.0\'.padEnd(10,\'0\'))//0.00000000\r\n复制代码\r\n```\r\n\r\n\r\n### 5.函数参数列表结尾允许逗号\r\n\r\n\r\n主要作用是方便使用git进行多人协作开发时修改同一个函数减少不必要的行变更。\r\n\r\n\r\n### 6.Object.getOwnPropertyDescriptors()\r\n\r\n\r\n`Object.getOwnPropertyDescriptors()`函数用来获取一个对象的所有自身属性的描述符,如果没有任何自身属性，则返回空对象。\r\n\r\n\r\n> 函数原型：\r\n\r\n\r\n\r\n```\r\nObject.getOwnPropertyDescriptors(obj)\r\n复制代码\r\n```\r\n\r\n\r\n返回`obj`对象的所有自身属性的描述符，如果没有任何自身属性，则返回空对象。\r\n\r\n\r\n```\r\nconst obj2 = {\r\n	name: \'Jine\',\r\n	get age() { return \'18\' }\r\n};\r\nObject.getOwnPropertyDescriptors(obj2)\r\n// {\r\n//   age: {\r\n//     configurable: true,\r\n//     enumerable: true,\r\n//     get: function age(){}, //the getter function\r\n//     set: undefined\r\n//   },\r\n//   name: {\r\n//     configurable: true,\r\n//     enumerable: true,\r\n//		value:\"Jine\",\r\n//		writable:true\r\n//   }\r\n// }\r\n复制代码\r\n```\r\n\r\n\r\n### 7.SharedArrayBuffer对象\r\n\r\n\r\nSharedArrayBuffer 对象用来表示一个通用的，固定长度的原始二进制数据缓冲区，类似于 ArrayBuffer 对象，它们都可以用来在共享内存（shared memory）上创建视图。与 ArrayBuffer 不同的是，SharedArrayBuffer 不能被分离。\r\n\r\n\r\n```\r\n/**\r\n * \r\n * @param {*} length 所创建的数组缓冲区的大小，以字节(byte)为单位。  \r\n * @returns {SharedArrayBuffer} 一个大小指定的新 SharedArrayBuffer 对象。其内容被初始化为 0。\r\n */\r\nnew SharedArrayBuffer(length)\r\n复制代码\r\n```\r\n\r\n\r\n### 8.Atomics对象\r\n\r\n\r\nAtomics 对象提供了一组静态方法用来对 SharedArrayBuffer 对象进行原子操作。\r\n\r\n\r\n这些原子操作属于 Atomics 模块。与一般的全局对象不同，Atomics 不是构造函数，因此不能使用 new 操作符调用，也不能将其当作函数直接调用。Atomics 的所有属性和方法都是静态的（与 Math  对象一样）。\r\n\r\n\r\n多个共享内存的线程能够同时读写同一位置上的数据。原子操作会确保正在读或写的数据的值是符合预期的，即下一个原子操作一定会在上一个原子操作结束后才会开始，其操作过程不会中断。\r\n\r\n\r\n- Atomics.add()\r\n\r\n\r\n\r\n> 将指定位置上的数组元素与给定的值相加，并返回相加前该元素的值。\r\n\r\n\r\n\r\n- Atomics.and()\r\n\r\n\r\n\r\n> 将指定位置上的数组元素与给定的值相与，并返回与操作前该元素的值。\r\n\r\n\r\n\r\n- Atomics.compareExchange()\r\n\r\n\r\n\r\n> 如果数组中指定的元素与给定的值相等，则将其更新为新的值，并返回该元素原先的值。\r\n\r\n\r\n\r\n- Atomics.exchange()\r\n\r\n\r\n\r\n> 将数组中指定的元素更新为给定的值，并返回该元素更新前的值。\r\n\r\n\r\n\r\n- Atomics.load()\r\n\r\n\r\n\r\n> 返回数组中指定元素的值。\r\n\r\n\r\n\r\n- Atomics.or()\r\n\r\n\r\n\r\n> 将指定位置上的数组元素与给定的值相或，并返回或操作前该元素的值。\r\n\r\n\r\n\r\n- Atomics.store()\r\n\r\n\r\n\r\n> 将数组中指定的元素设置为给定的值，并返回该值。\r\n\r\n\r\n\r\n- Atomics.sub()\r\n\r\n\r\n\r\n> 将指定位置上的数组元素与给定的值相减，并返回相减前该元素的值。\r\n\r\n\r\n\r\n- Atomics.xor()\r\n\r\n\r\n\r\n> 将指定位置上的数组元素与给定的值相异或，并返回异或操作前该元素的值。\r\n\r\n\r\n\r\nwait() 和 wake() 方法采用的是 Linux 上的 futexes 模型（fast user-space mutex，快速用户空间互斥量），可以让进程一直等待直到某个特定的条件为真，主要用于实现阻塞。\r\n\r\n\r\n- Atomics.wait()\r\n\r\n\r\n\r\n> 检测数组中某个指定位置上的值是否仍然是给定值，是则保持挂起直到被唤醒或超时。返回值为 \"ok\"、\"not-equal\" 或 \"time-out\"。调用时，如果当前线程不允许阻塞，则会抛出异常（大多数浏览器都不允许在主线程中调用 wait()）。\r\n\r\n\r\n\r\n- Atomics.wake()\r\n\r\n\r\n\r\n> 唤醒等待队列中正在数组指定位置的元素上等待的线程。返回值为成功唤醒的线程数量。\r\n\r\n\r\n\r\n- Atomics.isLockFree(size)\r\n\r\n\r\n\r\n> 可以用来检测当前系统是否支持硬件级的原子操作。对于指定大小的数组，如果当前系统支持硬件级的原子操作，则返回 true；否则就意味着对于该数组，Atomics 对象中的各原子操作都只能用锁来实现。此函数面向的是技术专家。-->\r\n\r\n\r\n\r\n## ES9新特性（2018）\r\n\r\n\r\n- 异步迭代\r\n- Promise.finally()\r\n- Rest/Spread 属性\r\n- [正则表达式命名捕获组](http://esnext.justjavac.com/proposal/regexp-named-groups.html)（Regular Expression Named Capture Groups）\r\n- [正则表达式反向断言](https://segmentfault.com/a/1190000006824133)（lookbehind）\r\n- 正则表达式dotAll模式\r\n- [正则表达式 Unicode 转义](https://juejin.im/post/6844903622870827022#heading-1)\r\n- [非转义序列的模板字符串](https://juejin.im/post/6844903622870827022#heading-1)\r\n\r\n\r\n\r\n### 1.异步迭代\r\n\r\n\r\n在`async/await`的某些时刻，你可能尝试在同步循环中调用异步函数。例如：\r\n\r\n\r\n```\r\nasync function process(array) {\r\n  for (let i of array) {\r\n    await doSomething(i);\r\n  }\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n这段代码不会正常运行，下面这段同样也不会：\r\n\r\n\r\n```\r\nasync function process(array) {\r\n  array.forEach(async i => {\r\n    await doSomething(i);\r\n  });\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n这段代码中，循环本身依旧保持同步，并在在内部异步函数之前全部调用完成。\r\n\r\n\r\nES2018引入异步迭代器（asynchronous iterators），这就像常规迭代器，除了`next()`方法返回一个Promise。因此`await`可以和`for...of`循环一起使用，以串行的方式运行异步操作。例如：\r\n\r\n\r\n```\r\nasync function process(array) {\r\n  for await (let i of array) {\r\n    doSomething(i);\r\n  }\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n### 2.Promise.finally()\r\n\r\n\r\n一个Promise调用链要么成功到达最后一个`.then()`，要么失败触发`.catch()`。在某些情况下，你想要在无论Promise运行成功还是失败，运行相同的代码，例如清除，删除对话，关闭数据库连接等。\r\n\r\n\r\n`.finally()`允许你指定最终的逻辑：\r\n\r\n\r\n```\r\nfunction doSomething() {\r\n  doSomething1()\r\n  .then(doSomething2)\r\n  .then(doSomething3)\r\n  .catch(err => {\r\n    console.log(err);\r\n  })\r\n  .finally(() => {\r\n    // finish here!\r\n  });\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n### 3.Rest/Spread 属性\r\n\r\n\r\nES2015引入了[Rest参数](https://link.juejin.im?target=https%3A%2F%2Fdeveloper.mozilla.org%2Fzh-CN%2Fdocs%2FWeb%2FJavaScript%2FReference%2FFunctions%2FRest_parameters)和[扩展运算符](https://link.juejin.im?target=https%3A%2F%2Fdeveloper.mozilla.org%2Fzh-CN%2Fdocs%2FWeb%2FJavaScript%2FReference%2FOperators%2FSpread_syntax)。三个点（...）仅用于数组。Rest参数语法允许我们将一个不定数量的参数表示为一个数组。\r\n\r\n\r\n```\r\nrestParam(1, 2, 3, 4, 5);\r\n\r\nfunction restParam(p1, p2, ...p3) {\r\n  // p1 = 1\r\n  // p2 = 2\r\n  // p3 = [3, 4, 5]\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n展开操作符以相反的方式工作，将数组转换成可传递给函数的单独参数。例如`Math.max()`返回给定数字中的最大值：\r\n\r\n\r\n```\r\nconst values = [99, 100, -1, 48, 16];\r\nconsole.log( Math.max(...values) ); // 100\r\n复制代码\r\n```\r\n\r\n\r\nES2018为对象解构提供了和数组一样的Rest参数（）和展开操作符，一个简单的例子：\r\n\r\n\r\n```\r\nconst myObject = {\r\n  a: 1,\r\n  b: 2,\r\n  c: 3\r\n};\r\n\r\nconst { a, ...x } = myObject;\r\n// a = 1\r\n// x = { b: 2, c: 3 }\r\n复制代码\r\n```\r\n\r\n\r\n或者你可以使用它给函数传递参数：\r\n\r\n\r\n```\r\nrestParam({\r\n  a: 1,\r\n  b: 2,\r\n  c: 3\r\n});\r\n\r\nfunction restParam({ a, ...x }) {\r\n  // a = 1\r\n  // x = { b: 2, c: 3 }\r\n}\r\n复制代码\r\n```\r\n\r\n\r\n跟数组一样，Rest参数只能在声明的结尾处使用。此外，它只适用于每个对象的顶层，如果对象中嵌套对象则无法适用。\r\n\r\n\r\n扩展运算符可以在其他对象内使用，例如：\r\n\r\n\r\n```\r\nconst obj1 = { a: 1, b: 2, c: 3 };\r\nconst obj2 = { ...obj1, z: 26 };\r\n// obj2 is { a: 1, b: 2, c: 3, z: 26 }\r\n复制代码\r\n```\r\n\r\n\r\n可以使用扩展运算符拷贝一个对象，像是这样`obj2 = {...obj1}`，但是 **这只是一个对象的浅拷贝**。另外，如果一个对象A的属性是对象B，那么在克隆后的对象cloneB中，该属性指向对象B。\r\n\r\n\r\n### 4.正则表达式命名捕获组\r\n\r\n\r\nJavaScript正则表达式可以返回一个匹配的对象——一个包含匹配字符串的类数组，例如：以`YYYY-MM-DD`的格式解析日期：\r\n\r\n\r\n```\r\nconst\r\n  reDate = /([0-9]{4})-([0-9]{2})-([0-9]{2})/,\r\n  match  = reDate.exec(\'2018-04-30\'),\r\n  year   = match[1], // 2018\r\n  month  = match[2], // 04\r\n  day    = match[3]; // 30\r\n复制代码\r\n```\r\n\r\n\r\n这样的代码很难读懂，并且改变正则表达式的结构有可能改变匹配对象的索引。\r\n\r\n\r\nES2018允许命名捕获组使用符号`?<name>`，在打开捕获括号`(`后立即命名，示例如下：\r\n\r\n\r\n```javascript\r\nconst\r\n  reDate = /(?<year>[0-9]{4})-(?<month>[0-9]{2})-(?<day>[0-9]{2})/,\r\n  match  = reDate.exec(\'2018-04-30\'),\r\n  year   = match.groups.year,  // 2018\r\n  month  = match.groups.month, // 04\r\n  day    = match.groups.day;   // 30\r\n复制代码\r\n```\r\n\r\n\r\n任何匹配失败的命名组都将返回`undefined`。\r\n\r\n\r\n命名捕获也可以使用在`replace()`方法中。例如将日期转换为美国的 MM-DD-YYYY 格式：\r\n\r\n\r\n```\r\nconst\r\n  reDate = /(?<year>[0-9]{4})-(?<month>[0-9]{2})-(?<day>[0-9]{2})/,\r\n  d      = \'2018-04-30\',\r\n  usDate = d.replace(reDate, \'$<month>-$<day>-$<year>\');\r\n复制代码\r\n```\r\n\r\n\r\n### 5.正则表达式反向断言\r\n\r\n\r\n目前JavaScript在正则表达式中支持先行断言（lookahead）。这意味着匹配会发生，但不会有任何捕获，并且断言没有包含在整个匹配字段中。例如从价格中捕获货币符号：\r\n\r\n\r\n```\r\nconst\r\n  reLookahead = /\\D(?=\\d+)/,\r\n  match       = reLookahead.exec(\'$123.89\');\r\n\r\nconsole.log( match[0] ); // $\r\n复制代码\r\n```\r\n\r\n\r\nES2018引入以相同方式工作但是匹配前面的反向断言（lookbehind），这样我就可以忽略货币符号，单纯的捕获价格的数字：\r\n\r\n\r\n```\r\nconst\r\n  reLookbehind = /(?<=\\D)\\d+/,\r\n  match        = reLookbehind.exec(\'$123.89\');\r\n\r\nconsole.log( match[0] ); // 123.89\r\n复制代码\r\n```\r\n\r\n\r\n以上是 **肯定反向断言**，非数字`\\D`必须存在。同样的，还存在 **否定反向断言**，表示一个值必须不存在，例如：\r\n\r\n\r\n```\r\nconst\r\n  reLookbehindNeg = /(?<!\\D)\\d+/,\r\n  match           = reLookbehind.exec(\'$123.89\');\r\n\r\nconsole.log( match[0] ); // null\r\n复制代码\r\n```\r\n\r\n\r\n### 6.正则表达式dotAll模式\r\n\r\n\r\n正则表达式中点`.`匹配除回车外的任何单字符，标记`s`改变这种行为，允许行终止符的出现，例如：\r\n\r\n\r\n```\r\n/hello.world/.test(\'hello\\nworld\');  // false\r\n/hello.world/s.test(\'hello\\nworld\'); // true\r\n复制代码\r\n```\r\n\r\n\r\n### 7.正则表达式 Unicode 转义\r\n\r\n\r\n到目前为止，在正则表达式中本地访问 Unicode 字符属性是不被允许的。ES2018添加了 Unicode 属性转义——形式为`\\p{...}`和`\\P{...}`，在正则表达式中使用标记 `u` (unicode) 设置，在`\\p`块儿内，可以以键值对的方式设置需要匹配的属性而非具体内容。例如：\r\n\r\n\r\n```\r\nconst reGreekSymbol = /\\p{Script=Greek}/u;\r\nreGreekSymbol.test(\'π\'); // true\r\n复制代码\r\n```\r\n\r\n\r\n此特性可以避免使用特定 Unicode 区间来进行内容类型判断，提升可读性和可维护性。\r\n\r\n\r\n### 8.非转义序列的模板字符串\r\n\r\n\r\n之前，`\\u`开始一个 unicode 转义，`\\x`开始一个十六进制转义，`\\`后跟一个数字开始一个八进制转义。这使得创建特定的字符串变得不可能，例如Windows文件路径 `C:\\uuu\\xxx\\111`。更多细节参考[模板字符串](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/template_strings)。\r\n', 22, 'Java进阶', 'javascript', 1, 229, 1, 0, '2021-07-07 03:25:28', '2021-08-12 01:45:40');
INSERT INTO `tb_blog_info` VALUES (11, 'vue3用法', 'asgsggs', 'asgsggs', '# 添加数组\r\n- 使用ref函数\r\n```vue\r\n setup(props,context) {\r\n      let arr = ref([])\r\n\r\n      function change(){\r\n        console.log(\"change...\");\r\n        let newArr = [1,2,3]\r\n        arr.value = newArr\r\n      }\r\n      \r\n\r\n      return{\r\n        arr,\r\n        change\r\n      }\r\n\r\n    },\r\n\r\n```\r\n\r\n- 使用数组的push方法\r\n```vue\r\n setup(props,context) {\r\n      let arr = reactive([])\r\n\r\n      function change(){\r\n        console.log(\"change...\");\r\n        let newArr = [1,2,3]\r\n        arr.push(...newArr)\r\n      }\r\n      \r\n\r\n      return{\r\n        arr,\r\n        change\r\n      }\r\n\r\n    },\r\n\r\n```\r\n\r\n- 创建一个响应式对象，对象的属性是数组\r\n```vue\r\n<template>\r\n  <div>\r\n\r\n      <div v-for=\"item in arr.list\" :key=\"item\"> \r\n          {{item}}\r\n      </div>\r\n\r\n        <button @click=\"change\">change</button>\r\n  </div>\r\n</template>\r\n\r\n<script>\r\n\r\n  import { defineComponent, reactive,ref } from \'vue\';\r\n\r\n  export default defineComponent({\r\n    setup(props,context) {\r\n      let arr = reactive({\r\n        list:[]\r\n      })\r\n\r\n      function change(){\r\n        console.log(\"change...\");\r\n        let newArr = [1,2,3]\r\n        arr.list = newArr\r\n      }\r\n      \r\n\r\n      return{\r\n        arr,\r\n        change\r\n      }\r\n\r\n    },\r\n  });\r\n</script>\r\n\r\n\r\n```\r\n', 24, NULL, '58,139', 1, 82, 1, 0, '2021-08-12 06:23:53', '2021-08-12 06:23:53');
INSERT INTO `tb_blog_info` VALUES (12, '111', 'asgsggs', 'asgsggs', 'sdfdsfdsf', 20, NULL, '141,140', 1, 4, 0, 0, '2021-08-24 05:14:31', '2021-08-24 05:14:31');

-- ----------------------------
-- Table structure for tb_blog_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_link`;
CREATE TABLE `tb_blog_link`  (
  `link_id` int NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint NOT NULL DEFAULT 0 COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  `link_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站描述',
  `link_rank` int NOT NULL DEFAULT 0 COMMENT '用于列表排序',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '友情链接表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_link
-- ----------------------------
INSERT INTO `tb_blog_link` VALUES (1, 0, '百度', 'https://www.baidu.com', '这是百度', 0, 1, '2019-09-07 02:21:03');
INSERT INTO `tb_blog_link` VALUES (2, 0, 'github', 'https://github.com', '这是github', 0, 1, '2019-09-02 21:24:44');
INSERT INTO `tb_blog_link` VALUES (4, 1, '饿了吗', 'https://element-plus.gitee.io/#/zh-CN/component/link#tu-biao', '222222', 123, 0, '2019-09-03 14:47:21');

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag`;
CREATE TABLE `tb_blog_tag`  (
  `tag_id` int NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 140 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------
INSERT INTO `tb_blog_tag` VALUES (1, '默认标题1', 0, '2019-09-01 11:19:47');
INSERT INTO `tb_blog_tag` VALUES (57, 'go语言1', 1, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (58, '标签22', 1, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (139, 'NoSQL', 0, '2019-08-06 21:23:38');

-- ----------------------------
-- Table structure for tb_blog_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag_relation`;
CREATE TABLE `tb_blog_tag_relation`  (
  `relation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint NOT NULL COMMENT '博客id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 339 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客跟标签的关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_tag_relation
-- ----------------------------
INSERT INTO `tb_blog_tag_relation` VALUES (325, 1, 57, '2019-09-06 18:01:50');
INSERT INTO `tb_blog_tag_relation` VALUES (326, 1, 58, '2019-09-06 18:01:50');
INSERT INTO `tb_blog_tag_relation` VALUES (330, 7, 139, '2019-09-07 11:25:28');
INSERT INTO `tb_blog_tag_relation` VALUES (331, 8, 141, '2021-08-12 12:45:22');
INSERT INTO `tb_blog_tag_relation` VALUES (332, 8, 140, '2021-08-12 12:45:22');
INSERT INTO `tb_blog_tag_relation` VALUES (333, 9, 141, '2021-08-12 12:46:12');
INSERT INTO `tb_blog_tag_relation` VALUES (334, 9, 140, '2021-08-12 12:46:12');
INSERT INTO `tb_blog_tag_relation` VALUES (335, 10, 58, '2021-08-12 14:19:51');
INSERT INTO `tb_blog_tag_relation` VALUES (336, 10, 139, '2021-08-12 14:19:51');
INSERT INTO `tb_blog_tag_relation` VALUES (337, 11, 58, '2021-08-12 14:23:52');
INSERT INTO `tb_blog_tag_relation` VALUES (338, 11, 139, '2021-08-12 14:23:52');
INSERT INTO `tb_blog_tag_relation` VALUES (339, 12, 141, '2021-08-24 13:14:31');
INSERT INTO `tb_blog_tag_relation` VALUES (340, 12, 140, '2021-08-24 13:14:31');

SET FOREIGN_KEY_CHECKS = 1;
