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

 Date: 14/05/2022 08:09:49
*/


-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_admin_user";
CREATE TABLE "public"."tb_admin_user" (
  "admin_user_id" int4 NOT NULL,
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
INSERT INTO "public"."tb_admin_user" VALUES (1, 'admin', '670b14728ad9902aecba32e22fa4f6bd', '南街', 1, 1, NULL);
INSERT INTO "public"."tb_admin_user" VALUES (4, 'yzq', 'e10adc3949ba59abbe56e057f20f883e', 'yzqdev', 0, 1, 'https://i0.hdslb.com/bfs/album/0a75a254e639e7ce606099e1d6c2b75582dc4e8a.jpg');

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_blog_category";
CREATE TABLE "public"."tb_blog_category" (
  "relation_id" int4 NOT NULL,
  "blog_id" int4,
  "category_id" int4,
  "create_time" timestamp(6)
)
;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO "public"."tb_blog_category" VALUES (1, 48, 22, '2021-10-29 19:50:39');
INSERT INTO "public"."tb_blog_category" VALUES (28, 51, 22, '2021-10-29 20:59:53');
INSERT INTO "public"."tb_blog_category" VALUES (38, 53, 33, '2021-10-30 08:51:04');
INSERT INTO "public"."tb_blog_category" VALUES (39, 52, 33, '2021-10-30 08:58:34');
INSERT INTO "public"."tb_blog_category" VALUES (40, 54, 33, '2021-10-30 08:59:31');
INSERT INTO "public"."tb_blog_category" VALUES (45, 56, 33, '2021-10-30 09:21:33');
INSERT INTO "public"."tb_blog_category" VALUES (46, 55, 1, '2021-10-30 11:34:51');
INSERT INTO "public"."tb_blog_category" VALUES (47, 49, 22, '2021-10-30 11:35:13');
INSERT INTO "public"."tb_blog_category" VALUES (48, 57, 33, '2022-02-13 13:02:25');

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
  "blog_views" int8 NOT NULL,
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
INSERT INTO "public"."tb_blog_info" VALUES (48, 'manjaro安装后的配置', '', 'linux配置', '> 记得进入设置>工作空间行为>锁屏>关闭自动锁屏，不然会卡死在登陆
>
> ## 分区工具的使用 partitionmanager

```bash
sudo yay -S partitionmanager
```
> ## pacman 的日常使用

安装todesk远程控制    
```javascript
yay -Ss todesk
找到todesk  
yay -S todesk-bin
# 需要开启u服务
systemctl start todeskd.service 
```
```bash
pacman -S xx1 xx2   # 安装或升级软件包，或者一列软件包（包含依赖包）
pacman -Sy          # 更新软件源
pacman -Syy         # 强制更新软件源
pacman -Su          # 更新软件包
pacman -Syu         # 更新软件源并更新软件包（-Syyu）
pacman -Sc          # 清除软件包缓存
pacman -Ss  xxx     # 搜索名字含 xxx 的软件
pacman -Ss ^xxx     # 搜索名字以 xxx 开头的软件
pacman -R xxx       # 删除单个软件包，保留其安装的依赖关系
pacman -Rs xxx      # 删除指定软件包，及其没有被其他已安装软件包使用的依赖关系
pacman -Rn xxx      # 一并删除全局配置文件
pacman -Rns xxx     # 删除一个软件的推荐命令
pacman -Rns $(pacman -Qdtq) # 删除没有依赖的包
pacman -Q           # 查询本地软件包数据库
pacman -Qq          # 查询本地软件包数据库，但不显示版本信息
pacman -Qe          # 查询非系统自带软件包数据库
pacman -Q | wc -l   # 查看有多少本地软件包
pacman -Qs regex    # 按正则表达式查询软件包
pacman -Qdt         # 查看没有依赖的包
```
## 更换源
```bash
//1.配置镜像源:
sudo pacman-mirrors -i -c China -m rank
//2.设置 archlinuxcn 源,
#使用方法：在 /etc/pacman.conf 文件末尾添加以下两行：

[archlinuxcn]
Server = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/$arch

# 之后安装 archlinuxcn-keyring 包导入 GPG key。
//3.更新源列表
sudo pacman-mirrors -g
//4.更新pacman数据库并全面更新系统
sudo pacman -Syyu
//5.更新签名
sudo pacman -S archlinuxcn-keyring
//6.安装yay,e更换aur的源
sudo pacman -S yay git net-tools tree vim
//7.执行以下命令修改 aururl :
yay --aururl "https://aur.tuna.tsinghua.edu.cn" --save
//8.修改的配置文件位于 `~/.config/yay/config.json` ，还可通过以下命令查看修改过的配置：
yay -P -g
以后就可以直接执行yay -S 你要安装的软件名字，比如
yay -S netease-cloud-music
```
## 更新文件夹语言
```
export LANG=en_US
xdg-user-dirs-gtk-update  //弹出对话框，问是否改成英文，点是，并且选“不再提示”
export LANG=zh_CN
```
## 安装常用的软件：
```bash
yay -S net-tools base-devel
浏览器我推荐brave浏览器
yay -S brave
chrome浏览器： yay -S google-chrome 
Microsoft-edge浏览器：yay -S microsoft-edge-dev-bin
Git软件： yay -S git 
Uget配合aria2： yay -S aria2 
下载工具： yay -S uget 
解压工具： yay -S p7zip file-roller unrar 
图像编辑器： yay -S gimp （开源版PS）
WPS办公： yay -S wps-office 
WPS缺少的字体： yay -S ttf-wps-fonts 
WPS安装中文： sudo pacman -S wps-office-mui-zh-cn 
Vscode开发工具： yay -S visual-studio-code-bin 
Markdown编辑器： yay -S typora 
强大的Web内容（视频，音频，图片）下载工具：  yay -S you-get 
火焰截图： yay -S flameshot  (超好用,系统设置里面添加快捷键即可)
云笔记软件： yay -S joplin (开源免费，云服务需要飞天服务)
chm文件阅读器： yay -S kchmviewer 
有道词典： yay -S youdao-dict 
开源电子书阅读器： yay -S calibre 
MD文件编辑器： yay -S typora 
Gif录制软件： yay -S peek 
sudo pacman -S filezilla
# 关闭电子钱包
用了kde以后，每次打开浏览器都会跳出电子钱包什么的，十分烦人，也可能是我自己不习惯
于是就想办法把他关掉！
搜索Kwallet， 不是KwalletManager
然后把启用的勾勾去掉就好啦
```
## 安装docker：
```bash
sudo pacman -S docker
sudo pacman -S docker-compose
```
## 安装java：
（[https://wiki.archlinux.org/index.php/Java#Installation](https://wiki.archlinux.org/index.php/Java#Installation)）
```bash

sudo pacman -S java-runtime-common java-environment-common
yay jdk8  # Select extra/jdk8-openjdk
使用 archlinux-java 命令来管理 Java 环境。
列举 Java 环境：
archlinux-java status
选择 Java 环境：
sudo archlinux-java set java-8-openjdk

打开.bashrc
# 在后面加上， 地址根据你jdk修改
export JAVA_HOME=/home/hxy/java/jdk-13
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH

# 启用配置，不过我这边不知道怎么退出（我是强制关掉）
source  .bashrc

# 查看是否配置成功
java -version
```
## 安装node,nvm，yarn：


注意区别
```java
/etc/profile: 此文件为系统的每个用户设置环境信息,当用户第一次登录时,该文件被执行.并从/etc/profile.d目录的配置文件中搜集shell的设置.
/etc/bashrc:  为每一个运行bash shell的用户执行此文件.当bash shell被打开时,该文件被读取.
~/.bash_profile: 每个用户都可使用该文件输入专用于自己使用的shell信息,当用户登录时,该文件仅仅执行一次!默认情况下,他设置一些环境变量,执行用户的.bashrc文件.
~/.bashrc: 该文件包含专用于你的bash shell的bash信息,当登录时以及每次打开新的shell时,该该文件被读取.
~/.bash_logout: 当每次退出系统(退出bash shell)时,执行该文件.
  
另外,/etc/profile中设定的变量(全局)的可以作用于任何用户,而~/.bashrc等中设定的变量(局部)只能继承/etc/profile中的变量,他们是"父子"关系.

```
```bash
先配置下载github太慢
 kate /etc/hosts
在最后添加 
199.232.68.133  raw.githubusercontent.com
# 1.下载nvm脚本文件
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
# 2. 或者：
export NVM_NODEJS_ORG_MIRROR=https://npmmirror.com/mirrors/node
# 注：如果只是在终端输入了上面的代码只能在本次窗口没有关闭的时候生效，下次打开还是会还原成默认的源
# 如果需要长久的使用淘宝源则需要如下操作
# 确认你的sh是什么，一般bash或者zsh
# 如果是 bash 则键入 
echo "export NVM_NODEJS_ORG_MIRROR=https://npmmirror.com/mirrors/node" >> ~/.bash_profile

# 如果是 zsh 则输入 
echo "export NVM_NODEJS_ORG_MIRROR=https://npmmirror.com/mirrors/node" >> ~/.zshrc

# 最后再分别执行命令

# bash 为 ：source ~/.bash_profile

# zsh 为 ：source ~/.zshrc
等同于
在.bashrc中添加下面的内容
export NVM_NODEJS_ORG_MIRROR=http://npmmirror.com/mirrors/node
3.下载node,配置国内镜像
nvm install node
设置npm国内镜像
npm config set registry https://registry.npmmirror.com
安装镜像服务
# npm i -g mirror-config-china #不需要了
npm i -g yarn
设置yarn镜像（可以做成一个a.sh脚本执行）
yarn config set registry https://registry.npmmirror.com -g
yarn config set disturl https://npmmirror.com/dist -g
yarn config set electron_mirror https://npmmirror.com/mirrors/electron/ -g
yarn config set sass_binary_site https://npmmirror.com/mirrors/node-sass/ -g
yarn config set phantomjs_cdnurl https://npmmirror.com/mirrors/phantomjs/ -g
yarn config set chromedriver_cdnurl https://npmmirror.com/dist/chromedriver -g
yarn config set operadriver_cdnurl https://npmmirror.com/dist/operadriver -g
yarn config set fse_binary_host_mirror https://npmmirror.com/mirrors/fsevents -g

```
## 安装miniconda：
```bash
从清华镜像下载miniconda执行的sh
 https://mirrors.tuna.tsinghua.edu.cn/anaconda/miniconda/ 

 配置conda国内镜像
 i在.condarc中加入下面的：
 channels:
  - defaults
show_channel_urls: true
channel_alias: https://mirrors.tuna.tsinghua.edu.cn/anaconda
default_channels:
  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main
  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free
  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/r
  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/pro
  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/msys2
custom_channels:
  conda-forge: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
  msys2: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
  bioconda: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
  menpo: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
  pytorch: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
  simpleitk: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
```
 安装Anaconda后终端出现的(base)字样去除方法：
```bash
conda config --set auto_activate_base False
```
## 安装输入法：
设置小键盘开机自动启动方法
打开设置 找到输入设备->键盘 -> plasma启动时numlock状态->开启即可
关闭触控板也是输入设备->触摸板
```bash
下载fcitx5并安装
sudo yay -S fcitx5 fcitx5-chinese-addons  fcitx5-rime fcitx5-chewing  fcitx5-configtool
1、在用户文件夹下创建.xprofile配置文件

输入命令
sudo vim ~/.xprofile 

然后插入如下内容
export GTK_IM_MODULE=fcitx5
export QT_IM_MODULE=fcitx5
export XMODIFIERS=@im=fcitx
2、设置fcitx5为开机启动
①: 直接在~/.xprofile中插入下面这行
fcitx5 &
②: 如果是KDE用户，可在系统设置-启动和关闭-自启动中填加fcitx5为开机自启动项
编辑~`/.pam_environment`
INPUT_METHOD  DEFAULT=fcitx5
GTK_IM_MODULE DEFAULT=fcitx5
QT_IM_MODULE  DEFAULT=fcitx5
XMODIFIERS    DEFAULT=\@im=fcitx5
```
## 安装golang:
### 1.使用pacman/yay
```bash
sudo pacman -S go go-tools
```
> 可选用 `gcc-go` ，但若需要使用 JetBrains 的 IDE 则必须使用 `go` ，否则 IDE 无法在 `/usr/lib/go` 下找到 Go SDK。

### 2.从官网下载
```bash
在golang官网下载go的安装包，go1.16.linux-amd64.tar.gz 然后
# 1.解压到local目录
wget  https://golang.google.cn/dl/go1.16.4.linux-amd64.tar.gz
sudo tar -C /opt -xzf go1.16.4.linux-amd64.tar.gz
# 2.编辑环境变量，在/etc/profile和.bashrc添加
#vim ~/.zshrc  # OR ~/.bashrc OR ~/.profile
cp -r ~/.bashrc ~/bashrcbak
# 添加a环境变量
export GOMODPATH=/opt/go
export PATH="$GOMODPATH/bin:$GOMODPATH/golangmod/bin:$PATH"
# 修改/opt的权限
sudo chmod -R 777 /opt/go



echo ''export GOMODPATH=/opt/go
export PATH="$GOMODPATH/bin:$GOMODPATH/golangmod/bin:$PATH"'' >>~/.bashrc
source ~/.bashrc
# 3.输入go version检查是否有安装成功
# 4.配置go代理
go env -w GO111MODULE=on
go env -w GOPROXY=https://goproxy.cn,direct
go env -w GOPATH=/opt/go/golangmod
```
## 安装teamviewer
```bash
yay -S teamviewer
提示：TeamViewer Daemon is not running，解决方法：
sudo teamviewer --daemon enable
```
## 安装myql：（已经被替换为mariadb,请使用下面的方法）
如果要使用mysql8,需要设置aur！！！！！！！
```bash
//下载Mysql
pacman -S mysql
//初始化Mysql，记住生成的密码，方便修改
sudo mysqld --initialize --user=mysql --basedir=/usr --datadir=/var/lib/mysql
//设置开机启动
systemctl enable mysqld.service
//启动Mysql
sudo systemctl start mysqld.service
//修改密码
mysql -u root -p
mysql> ALTER USER ''root''@''localhost'' IDENTIFIED BY ''新密码'';
```
如果忘记密码，直接百度mysql8忘记密码怎么办
### manjaro 。忘记密码解决办法：
```bash
# 1.先编辑mysql配置文件
kate /etc/mysql/my.cnf
# 2.添加一个     skip-grant-tables
# 3.  重启mysql服务
sudo systemctl restart mysqld
# 4.进入mysql 直接在命令行进入lmysql
# 5 设置 root密码为空
mysql>use mysql;  
mysql>update user set authentication_string='''' where user=''root'';   #将密码置空 
mysql>exit
#6 去掉mysql配置文件里的     skip-grant-tables 重新登陆mysql使用
mysql -uroot -p  #两次确定就可进去
#7. ALTER USER ''root''@''localhost'' IDENTIFIED BY ''新密码'';# 然后退出mysql登陆就可以了
```
## 如果出现
```
ER_NOT_SUPPORTED_AUTH_MODE: Client does not support authentication protocol requested by server; consider upgrading MySQL client
```
命令行进入mysql输入
```bash
ALTER USER ''root''@''localhost'' IDENTIFIED WITH mysql_native_password BY ''123456''
```
就可以连上了
## 安装mariadb
##1. 安装 与Ubuntu不同，arch默认已经不再支持MySQL，但是可以安装MariaDB，其比MySQL的性能更好且操作基本相同。 输入下面命令安装：
```bash
systemctl stop mysqld    //停止mysql服务
sudo pacman -S mariadb libmariadbclient mariadb-clients    //安装mariadb
sudo mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
复制代码
```
##2. 启动
```bash
systemctl start mariadb
mysql_secure_installation    //设置密码等管理操作
systemctl restart mariadb
复制代码
```
登录：
```
mysql -u root -p
```
## 安装postgressql
安装：
```bash
sudo pacman -S  postgresql
```


初始化(必须)：
```swift
sudo su - postgres -c "initdb --locale zh_CN.UTF-8 -E UTF8 -D ''/var/lib/postgres/data''"
```


其中，我将原本的en_US改为了zh_CN，未见异常。想要撤销的话，只需要把`''/var/lib/postgres/data''`下面的内容清空。
启动/开机启动 PostgreSQL：
```css
systemctl start postgresql.service
systemctl enable postgresql.service
```
切换到postgres用户，然后登录（初始无密码）：
```undefined
sudo -i -u postgres
psql
```
要退出psql或返回原用户，都是用`exit`命令。
PostgreSQL的用户跟系统用户有些关联，前者必须也是后者。在初始化过程中会在系统中创建postgres用户，同时也是数据库的超级权限用户，postgres用户可以创建其他数据库用户。
> 提示： 如果创建一个与你的系统用户同名的数据库用户，并允许其访问 PostgreSQL 数据库，那么在登录PostgreSQL 数据库 shell 的时候无需切换用户（这样做会比较方便）。

### 通用基本操作[[1]](#fn1)
#### 数据库shell外
添加数据库（须在原用户操作）：
```bash
createdb myDatabaseName
```
连接数据库shell（须用postgres用户，所以先切一下用户）：
```bash
sudo -i -u postgres
psql -d myDatabaseName
```
也可以一步进入postgres用户的myDatabaseName数据库，与上面效果一样：


```bash
psql -U postgres -d myDatabaseName
```


若要创建用户，要在数据库程序外，用postgres用户执行：


```undefined
createuser --interactive myUserName
```


#### 数据库shell内


注意：数据库内的SQL语句，建议大写，必须分号结尾。
进入数据库后可修改密码：


```dart
alter user postgres with password '' *** 密码 *** '';
```


如果有其他用户，可以把postgres换成其他用户的名。
一些常用的命令：


```bash
\c myDatabaseName     # 连接到数据库myDatabaseName
\du    # 列出所有用户以及他们的权限
\dt    # 展示当前数据库中所有的表相关的汇总信息
\q    # 退出psql
```
## 开启ssh服务
```bash
systemctl enable sshd.service 开机启动
systemctl start sshd.service 立即启动
systemctl restart sshd.service 立即重启
```
## 安装nginx：
```bash
yay -S nginx-mainline
```
## 安装redis：
```bash
yay -S redis

1 启动服务端
sudo redis-server
启动redis
2 启动客户端
sudo redis-cli
systemctl redis start
# 关闭redis
systemctl redis stop
# 开机自启动
systemctl enable redis
```
配置redis密码：在`/etc/redis.conf`中找到requirepass,去掉h注释并更换为自己的密码；
出现 
### System limit for number of file watchers reached
```bash
echo fs.inotify.max_user_watches = 524288 | sudo tee -a /etc/sysctl.conf 
sudo sysctl -p
```
## 安装php和composer
安装php：
```bash
yay -S php
```
第一步，下载composer。（切换到项目的根目录，再执行）
```bash
php -r "readfile(''https://getcomposer.org/installer'');" | php
```
下载之后后自动安装，执行 php composer.phar。查看composer是否安装成功。
第二步，将composer.phar文件移动到bin目录以便全局使用composer命令
```bash
cp -r composer.phar /usr/local/bin/composer
```
（如果只是针对某个项目使用composer,可忽略此步）
第三步，切换国内源（如果第一步下载成功，可忽略此步）
```bash
composer config -g repo.packagist composer https://packagist.phpcomposer.com
```
第四步，安装phpcgi
```bash
yay -S php-apache php-cgi php-fpm php-gd  php-embed php-intl php-imap  php-redis php-snmp
```
第五步，安装pecl
```bash
wget http://pear.php.net/go-pear.phar
php go-pear.phar
```
第六步，安装xdebug
```bash
sudo pecl install xdebug

//为php.ini 添加 extension=xdebug.so
sudo vim /etc/php/php.ini
sudo systemctl reload php-fpm
```
', 1, 65, 1, 0, '2021-10-29 16:39:24', '2021-10-29 16:39:24');
INSERT INTO "public"."tb_blog_info" VALUES (49, 'Nginx配置', '', '啊啊啊', '```java
sudo apt-get install nginx
```
主分文件分片
[https://blog.csdn.net/qq_41783562/article/details/106530017](https://blog.csdn.net/qq_41783562/article/details/106530017)


```bash
sudo systemctl start nginx
```
注意要把    include conf.d/*.conf; 写在http{}内部
```java

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;
events {
    worker_connections  1024;
}




http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  ''$remote_addr - $remote_user [$time_local] "$request" ''
    #                  ''$status $body_bytes_sent "$http_referer" ''
    #                  ''"$http_user_agent" "$http_x_forwarded_for"'';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;
include conf.d/*.conf;
 
    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache''s document root
        # concurs with nginx''s one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}

```
config.d/nav.conf
```java

   server{
    listen    6800;
    server_name  localhost;
      location / {
            root   /opt/navurl/;
            index  index.html index.htm;
        }
    }
   server{
    listen    6900;
    server_name  localhost;
      location /home/main {
           root   /opt/myblog;
           try_files $uri $uri/ /index.html;
           index  index.html index.htm;
        }
         location / {
            root   /opt/myblog;
            try_files $uri $uri/ /index.html;
            index  index.html index.htm;
            
            
        }
    }

```
', 1, 70, 1, 0, '2021-10-30 11:35:13', '2021-10-30 11:35:13');
INSERT INTO "public"."tb_blog_info" VALUES (51, '策划', '', '啊啊啊啊', '- [ ] :smiley:- [ ] 任务列表
- ::: tip
  在此输入内容
:::', 1, 21, 1, 0, '2021-10-29 20:59:53', '2021-10-29 20:59:53');
INSERT INTO "public"."tb_blog_info" VALUES (54, '111', '', '111', '1111', 0, 0, 0, 0, '2021-10-30 08:59:31', '2021-10-30 08:59:31');
INSERT INTO "public"."tb_blog_info" VALUES (55, '222', '', '111', '313313', 1, 69, 1, 0, '2021-10-30 11:34:51', '2021-10-30 11:34:51');
INSERT INTO "public"."tb_blog_info" VALUES (56, 'ddf', '', 'sdfsdf', 'sfsfsf', 1, 3, 0, 1, '2021-10-30 09:21:32', '2022-02-13 13:02:53');
INSERT INTO "public"."tb_blog_info" VALUES (57, 'aaaa', 'dfdsfsdff', 'sfsfsff', 'asdfdsfffsaf

# 配置项

你可以配置在 `window.$docsify` 里。

```html
<script>
  window.$docsify = {
    repo: ''docsifyjs/docsify'',
    maxLevel: 3,
    coverpage: true
  }
</script>
```

## el

- 类型：`String`
- 默认值：`#app`

docsify 初始化的挂载元素，可以是一个 CSS 选择器，默认为 `#app` 如果不存在就直接绑定在 `body` 上。

```js
window.$docsify = {
  el: ''#app''
};
```

## repo

- 类型：`String`
- 默认值: `null`

配置仓库地址或者 `username/repo` 的字符串，会在页面右上角渲染一个 [GitHub Corner](http://tholman.com/github-corners/) 挂件。

```js
window.$docsify = {
  repo: ''docsifyjs/docsify'',
  // or
  repo: ''https://github.com/docsifyjs/docsify/''
};
```

## maxLevel

- 类型：`Number`
- 默认值: `6`

默认情况下会抓取文档中所有标题渲染成目录，可配置最大支持渲染的标题层级。

```js
window.$docsify = {
  maxLevel: 4
};
```

## loadNavbar

- 类型：`Boolean|String`
- 默认值: `false`

加载自定义导航栏，参考[定制导航栏](zh-cn/custom-navbar.md) 了解用法。设置为 `true` 后会加载 `_navbar.md` 文件，也可以自定义加载的文件名。

```js
window.$docsify = {
  // 加载 _navbar.md
  loadNavbar: true,

  // 加载 nav.md
  loadNavbar: ''nav.md''
};
```

## loadSidebar

- 类型：`Boolean|String`
- 默认值: `false`

加载自定义侧边栏，参考[多页文档](zh-cn/more-pages.md)。设置为 `true` 后会加载 `_sidebar.md` 文件，也可以自定义加载的文件名。

```js
window.$docsify = {
  // 加载 _sidebar.md
  loadSidebar: true,

  // 加载 summary.md
  loadSidebar: ''summary.md''
};
```

## subMaxLevel

- 类型：`Number`
- 默认值: `0`

自定义侧边栏后默认不会再生成目录，你也可以通过设置生成目录的最大层级开启这个功能。

```js
window.$docsify = {
  subMaxLevel: 2
};
```

## auto2top

- 类型：`Boolean`
- 默认值: `false`

切换页面后是否自动跳转到页面顶部。

```js
window.$docsify = {
  auto2top: true
};
```

## homepage

- 类型：`String`
- 默认值: `README.md`

设置首页文件加载路径。适合不想将 `README.md` 作为入口文件渲染，或者是文档存放在其他位置的情况使用。

```js
window.$docsify = {
  // 入口文件改为 /home.md
  homepage: ''home.md'',

  // 文档和仓库根目录下的 README.md 内容一致
  homepage:
    ''https://raw.githubusercontent.com/docsifyjs/docsify/master/README.md''
};
```

## basePath

- 类型：`String`

文档加载的根路径，可以是二级路径或者是其他域名的路径。

```js
window.$docsify = {
  basePath: ''/path/'',

  // 直接渲染其他域名的文档
  basePath: ''https://docsify.js.org/'',

  // 甚至直接渲染其他仓库 readme
  basePath:
    ''https://raw.githubusercontent.com/ryanmcdermott/clean-code-javascript/master/''
};
```

## coverpage

- 类型：`Boolean|String`
- 默认值: `false`

启用[封面页](zh-cn/cover.md)。开启后是加载 `_coverpage.md` 文件，也可以自定义文件名。

```js
window.$docsify = {
  coverpage: true,

  // 自定义文件名
  coverpage: ''cover.md'',

  // mutiple covers
  coverpage: [''/'', ''/zh-cn/''],

  // mutiple covers and custom file name
  coverpage: {
    ''/'': ''cover.md'',
    ''/zh-cn/'': ''cover.md''
  }
};
```

## logo

- 类型: `String`

在侧边栏中出现的网站图标，你可以使用`CSS`来更改大小

```js
window.$docsify = {
  logo: ''/_media/icon.svg''
};
```

## name

- 类型：`String`

文档标题，会显示在侧边栏顶部。

```js
window.$docsify = {
  name: ''docsify''
};
```

## nameLink

- 类型：`String`
- 默认值：`window.location.pathname`

点击文档标题后跳转的链接地址。

```js
window.$docsify = {
  nameLink: ''/'',

  // 按照路由切换
  nameLink: {
    ''/zh-cn/'': ''/zh-cn/'',
    ''/'': ''/''
  }
};
```

## markdown

- 类型: `Object|Function`

参考 [Markdown 配置](zh-cn/markdown.md)。

```js
window.$docsify = {
  // object
  markdown: {
    smartypants: true,
    renderer: {
      link: function() {
        // ...
      }
    }
  },

  // function
  markdown: function(marked, renderer) {
    // ...
    return marked;
  }
};
```

## themeColor

- 类型：`String`

替换主题色。利用 [CSS3 支持变量](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_variables)的特性，对于老的浏览器有 polyfill 处理。

```js
window.$docsify = {
  themeColor: ''#3F51B5''
};
```

## alias

- 类型：`Object`

定义路由别名，可以更自由的定义路由规则。 支持正则。

```js
window.$docsify = {
  alias: {
    ''/foo/(+*)'': ''/bar/$1'', // supports regexp
    ''/zh-cn/changelog'': ''/changelog'',
    ''/changelog'':
      ''https://raw.githubusercontent.com/docsifyjs/docsify/master/CHANGELOG'',
    ''/.*/_sidebar.md'': ''/_sidebar.md'' // See #301
  }
};
```

## autoHeader

- 类型：`Boolean`

同时设置 `loadSidebar` 和 `autoHeader` 后，可以根据 `_sidebar.md` 的内容自动为每个页面增加标题。[#78](https://github.com/docsifyjs/docsify/issues/78)

```js
window.$docsify = {
  loadSidebar: true,
  autoHeader: true
};
```

## executeScript

- 类型：`Boolean`

执行文档里的 script 标签里的脚本，只执行第一个 script ([demo](zh-cn/themes.md))。 如果 Vue 存在，则自动开启。

```js
window.$docsify = {
  executeScript: true
};
```

```markdown
## This is test

<script>
  console.log(2333)
</script>
```

注意如果执行的是一个外链脚本，比如 jsfiddle 的内嵌 demo，请确保引入 [external-script](plugins.md?id=外链脚本-external-script) 插件。

## noEmoji

- 类型: `Boolean`

禁用 emoji 解析。

```js
window.$docsify = {
  noEmoji: true
};
```

## mergeNavbar

- 类型: `Boolean`

小屏设备下合并导航栏到侧边栏。

```js
window.$docsify = {
  mergeNavbar: true
};
```

## formatUpdated

- 类型: `String|Function`

我们可以显示文档更新日期通过 **{docsify-updated<span>}</span>** 变量. 并且格式化日期通过 `formatUpdated`。参考 https://github.com/lukeed/tinydate#patterns

```js
window.$docsify = {
  formatUpdated: ''{MM}/{DD} {HH}:{mm}'',

  formatUpdated: function(time) {
    // ...

    return time;
  }
};
```

## externalLinkTarget

- 类型: `String`
- 默认: `_blank`

当前默认为 \_blank, 配置一下就可以：

```js
window.$docsify = {
  externalLinkTarget: ''_self'' // default: ''_blank''
};
```

## routerMode

- 类型: `String`
- 默认: `hash`

```js
window.$docsify = {
  routerMode: ''history'' // default: ''hash''
};
```

## noCompileLinks

- 类型: `Array`

有时我们不希望 docsify 处理我们的链接。 参考 [#203](https://github.com/docsifyjs/docsify/issues/203)

```js
window.$docsify = {
  noCompileLinks: [''/foo'', ''/bar/.*'']
};
```

## requestHeaders

- 类型: `Object`

设置请求资源的请求头。

```js
window.$docsify = {
  requestHeaders: {
    ''x-token'': ''xxx''
  }
};
```

## ext

- 类型: `String`

资源的文件扩展名。

```js
window.$docsify = {
  ext: ''.md''
};
```

## fallbackLanguages

- 类型: `Array<string>`

一个语言列表。在浏览这个列表中的语言的翻译文档时都会在请求到一个对应语言的翻译文档不存在时显示默认语言的同名文档

Example:

- 尝试访问`/de/overview`，如果存在则显示
- 如果不存在则尝试`/overview`（取决于默认语言），如果存在即显示 
- 如果也不存在，显示404页面

```js
window.$docsify = {
  fallbackLanguages: [''fr'', ''de'']
};
```

## notFoundPage

- 类型: `Boolean` | `String` | `Object`

在找不到指定页面时加载`_404.md`:

```js
window.$docsify = {
  notFoundPage: true
};
```

加载自定义404页面:

```js
window.$docsify = {
  notFoundPage: ''my404.md''
};
```

加载正确的本地化过的404页面:

```js
window.$docsify = {
  notFoundPage: {
    ''/'': ''_404.md'',
    ''/de'': ''de/_404.md''
  }
};
```

> 注意: 配置过`fallbackLanguages`这个选项的页面与这个选项`notFoundPage`冲突。
', 1, 12, 1, 0, '2022-02-13 13:02:25', '2022-02-13 13:02:25');

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
INSERT INTO "public"."tb_blog_tag" VALUES (451, 48, 1, '2021-10-29 16:39:24');
INSERT INTO "public"."tb_blog_tag" VALUES (454, 51, 1, '2021-10-29 20:59:53');
INSERT INTO "public"."tb_blog_tag" VALUES (468, 54, 1, '2021-10-30 08:59:31');
INSERT INTO "public"."tb_blog_tag" VALUES (478, 56, 1, '2021-10-30 09:21:33');
INSERT INTO "public"."tb_blog_tag" VALUES (479, 56, 152, '2021-10-30 09:21:33');
INSERT INTO "public"."tb_blog_tag" VALUES (480, 55, 1, '2021-10-30 11:34:51');
INSERT INTO "public"."tb_blog_tag" VALUES (481, 49, 1, '2021-10-30 11:35:13');
INSERT INTO "public"."tb_blog_tag" VALUES (482, 57, 1, '2022-02-13 13:02:25');

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_category";
CREATE TABLE "public"."tb_category" (
  "category_id" int4 NOT NULL,
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
  "comment_id" int4 NOT NULL,
  "blog_id" int4 NOT NULL,
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
INSERT INTO "public"."tb_comment" VALUES (1, 55, '安抚', '', '', '安抚', '2022-01-20 03:03:19', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Chrome97.0.4692.71', 'Windows 10 or Windows Server 2016');
INSERT INTO "public"."tb_comment" VALUES (2, 55, 'asdf ', '', '', 'sdf ', '2022-01-20 03:10:38', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Chrome97.0.4692.71', 'Windows 10 or Windows Server 2016');
INSERT INTO "public"."tb_comment" VALUES (3, 55, 'asdf ', '', '', 'asdf sf ', '2022-01-20 03:11:20', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Chrome97.0.4692.71', 'Windows 10 or Windows Server 2016');
INSERT INTO "public"."tb_comment" VALUES (4, 55, 'sdf ', '', '', 'sf ', '2022-01-20 03:12:42', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Chrome97.0.4692.71', 'Windows 10 or Windows Server 2016');
INSERT INTO "public"."tb_comment" VALUES (5, 49, '安抚', '', '', '声带肥大', '2022-01-20 14:26:00', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Chrome97.0.4692.99', 'Windows 10 or Windows Server 2016');
INSERT INTO "public"."tb_comment" VALUES (6, 57, 'aa', 'aaaa@qq.com', '', 'sfsffsffsfs', '2022-02-13 14:09:37', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Chrome98.0.4758.82', 'Windows 10 or Windows Server 2016');
INSERT INTO "public"."tb_comment" VALUES (7, 57, 'aa', 'aaaa@qq.com', '', '# 配置项

你可以配置在 `window.$docsify` 里。

```html
&lt;script&gt;
  window.$docsify = {
    repo: ''docsifyjs/docsify'',
    maxLevel: 3,
    coverpage: true
  }
&lt;/script&gt;
```

## el

- 类型：`String`
- 默认值：`#app`

docsify 初始化的挂载元素，可以是一个 CSS 选择器，默认为 `#app` 如果不存在就直接绑定在 `body` 上。

```js
window.$docsify = {
  el: ''#app''
};
```

## repo

- 类型：`String`
- 默认值: `null`

配置仓库地址或者 `username/repo` 的字符串，会在页面右上角渲染一个 [GitHub Corner](http://tholman.com/github-corners/) 挂件。

```js
window.$docsify = {
  repo: ''docsifyjs/docsify'',
  // or
  repo: ''https://github.com/docsifyjs/docsify/''
};
```

## maxLevel

- 类型：`Number`
- 默认值: `6`

默认情况下会抓取文档中所有标题渲染成目录，可配置最大支持渲染的标题层级。

```js
window.$docsify = {
  maxLevel: 4
};
```

## loadNavbar

- 类型：`Boolean|String`
- 默认值: `false`

加载自定义导航栏，参考[定制导航栏](zh-cn/custom-navbar.md) 了解用法。设置为 `true` 后会加载 `_navbar.md` 文件，也可以自定义加载的文件名。

```js
window.$docsify = {
  // 加载 _navbar.md
  loadNavbar: true,

  // 加载 nav.md
  loadNavbar: ''nav.md''
};
```

## loadSidebar

- 类型：`Boolean|String`
- 默认值: `false`

加载自定义侧边栏，参考[多页文档](zh-cn/more-pages.md)。设置为 `true` 后会加载 `_sidebar.md` 文件，也可以自定义加载的文件名。

```js
window.$docsify = {
  // 加载 _sidebar.md
  loadSidebar: true,

  // 加载 summary.md
  loadSidebar: ''summary.md''
};
```

## subMaxLevel

- 类型：`Number`
- 默认值: `0`

自定义侧边栏后默认不会再生成目录，你也可以通过设置生成目录的最大层级开启这个功能。

```js
window.$docsify = {
  subMaxLevel: 2
};
```

## auto2top

- 类型：`Boolean`
- 默认值: `false`

切换页面后是否自动跳转到页面顶部。

```js
window.$docsify = {
  auto2top: true
};
```

## homepage

- 类型：`String`
- 默认值: `README.md`

设置首页文件加载路径。适合不想将 `README.md` 作为入口文件渲染，或者是文档存放在其他位置的情况使用。

```js
window.$docsify = {
  // 入口文件改为 /home.md
  homepage: ''home.md'',

  // 文档和仓库根目录下的 README.md 内容一致
  homepage:
    ''https://raw.githubusercontent.com/docsifyjs/docsify/master/README.md''
};
```

## basePath

- 类型：`String`

文档加载的根路径，可以是二级路径或者是其他域名的路径。

```js
window.$docsify = {
  basePath: ''/path/'',

  // 直接渲染其他域名的文档
  basePath: ''https://docsify.js.org/'',

  // 甚至直接渲染其他仓库 readme
  basePath:
    ''https://raw.githubusercontent.com/ryanmcdermott/clean-code-javascript/master/''
};
```

## coverpage

- 类型：`Boolean|String`
- 默认值: `false`

启用[封面页](zh-cn/cover.md)。开启后是加载 `_coverpage.md` 文件，也可以自定义文件名。

```js
window.$docsify = {
  coverpage: true,

  // 自定义文件名
  coverpage: ''cover.md'',

  // mutiple covers
  coverpage: [''/'', ''/zh-cn/''],

  // mutiple covers and custom file name
  coverpage: {
    ''/'': ''cover.md'',
    ''/zh-cn/'': ''cover.md''
  }
};
```

## logo

- 类型: `String`

在侧边栏中出现的网站图标，你可以使用`CSS`来更改大小

```js
window.$docsify = {
  logo: ''/_media/icon.svg''
};
```

## name

- 类型：`String`

文档标题，会显示在侧边栏顶部。

```js
window.$docsify = {
  name: ''docsify''
};
```

## nameLink

- 类型：`String`
- 默认值：`window.location.pathname`

点击文档标题后跳转的链接地址。

```js
window.$docsify = {
  nameLink: ''/'',

  // 按照路由切换
  nameLink: {
    ''/zh-cn/'': ''/zh-cn/'',
    ''/'': ''/''
  }
};
```

## markdown

- 类型: `Object|Function`

参考 [Markdown 配置](zh-cn/markdown.md)。

```js
window.$docsify = {
  // object
  markdown: {
    smartypants: true,
    renderer: {
      link: function() {
        // ...
      }
    }
  },

  // function
  markdown: function(marked, renderer) {
    // ...
    return marked;
  }
};
```

## themeColor

- 类型：`String`

替换主题色。利用 [CSS3 支持变量](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_variables)的特性，对于老的浏览器有 polyfill 处理。

```js
window.$docsify = {
  themeColor: ''#3F51B5''
};
```

## alias

- 类型：`Object`

定义路由别名，可以更自由的定义路由规则。 支持正则。

```js
window.$docsify = {
  alias: {
    ''/foo/(+*)'': ''/bar/$1'', // supports regexp
    ''/zh-cn/changelog'': ''/changelog'',
    ''/changelog'':
      ''https://raw.githubusercontent.com/docsifyjs/docsify/master/CHANGELOG'',
    ''/.*/_sidebar.md'': ''/_sidebar.md'' // See #301
  }
};
```

## autoHeader

- 类型：`Boolean`

同时设置 `loadSidebar` 和 `autoHeader` 后，可以根据 `_sidebar.md` 的内容自动为每个页面增加标题。[#78](https://github.com/docsifyjs/docsify/issues/78)

```js
window.$docsify = {
  loadSidebar: true,
  autoHeader: true
};
```

## executeScript

- 类型：`Boolean`

执行文档里的 script 标签里的脚本，只执行第一个 script ([demo](zh-cn/themes.md))。 如果 Vue 存在，则自动开启。

```js
window.$docsify = {
  executeScript: true
};
```

```markdown
## This is test

&lt;script&gt;
  console.log(2333)
&lt;/script&gt;
```

注意如果执行的是一个外链脚本，比如 jsfiddle 的内嵌 demo，请确保引入 [external-script](plugins.md?id=外链脚本-external-script) 插件。

## noEmoji

- 类型: `Boolean`

禁用 emoji 解析。

```js
window.$docsify = {
  noEmoji: true
};
```

## mergeNavbar

- 类型: `Boolean`

小屏设备下合并导航栏到侧边栏。

```js
window.$docsify = {
  mergeNavbar: true
};
```

## formatUpdated

- 类型: `String|Function`

我们可以显示文档更新日期通过 **{docsify-updated&lt;span&gt;}&lt;/span&gt;** 变量. 并且格式化日期通过 `formatUpdated`。参考 https://github.com/lukeed/tinydate#patterns

```js
window.$docsify = {
  formatUpdated: ''{MM}/{DD} {HH}:{mm}'',

  formatUpdated: function(time) {
    // ...

    return time;
  }
};
```

## externalLinkTarget

- 类型: `String`
- 默认: `_blank`

当前默认为 \_blank, 配置一下就可以：

```js
window.$docsify = {
  externalLinkTarget: ''_self'' // default: ''_blank''
};
```

## routerMode

- 类型: `String`
- 默认: `hash`

```js
window.$docsify = {
  routerMode: ''history'' // default: ''hash''
};
```

## noCompileLinks

- 类型: `Array`

有时我们不希望 docsify 处理我们的链接。 参考 [#203](https://github.com/docsifyjs/docsify/issues/203)

```js
window.$docsify = {
  noCompileLinks: [''/foo'', ''/bar/.*'']
};
```

## requestHeaders

- 类型: `Object`

设置请求资源的请求头。

```js
window.$docsify = {
  requestHeaders: {
    ''x-token'': ''xxx''
  }
};
```

## ext

- 类型: `String`

资源的文件扩展名。

```js
window.$docsify = {
  ext: ''.md''
};
```

## fallbackLanguages

- 类型: `Array&lt;string&gt;`

一个语言列表。在浏览这个列表中的语言的翻译文档时都会在请求到一个对应语言的翻译文档不存在时显示默认语言的同名文档

Example:

- 尝试访问`/de/overview`，如果存在则显示
- 如果不存在则尝试`/overview`（取决于默认语言），如果存在即显示 
- 如果也不存在，显示404页面

```js
window.$docsify = {
  fallbackLanguages: [''fr'', ''de'']
};
```

## notFoundPage

- 类型: `Boolean` | `String` | `Object`

在找不到指定页面时加载`_404.md`:

```js
window.$docsify = {
  notFoundPage: true
};
```

加载自定义404页面:

```js
window.$docsify = {
  notFoundPage: ''my404.md''
};
```

加载正确的本地化过的404页面:

```js
window.$docsify = {
  notFoundPage: {
    ''/'': ''_404.md'',
    ''/de'': ''de/_404.md''
  }
};
```

&gt; 注意: 配置过`fallbackLanguages`这个选项的页面与这个选项`notFoundPage`冲突。
', '2022-02-13 14:10:05', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Chrome98.0.4758.82', 'Windows 10 or Windows Server 2016');

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
INSERT INTO "public"."tb_img" VALUES (53, '20220213_16181428.jpg', 2515536, 'C:\Users\yanni\.myblog\pic\20220213_16181428.jpg', 'http://localhost:2801/pic/20220213_16181428.jpg', 'jpg', '5e3da6fd2c44645fa428af9293eaf685', '2022-02-13 16:18:14.835');

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_link";
CREATE TABLE "public"."tb_link" (
  "link_id" int4 NOT NULL,
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
  "tag_id" int4 NOT NULL,
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
