/*
 Navicat Premium Data Transfer

 Source Server         : 新腾讯
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 49.234.131.170:3306
 Source Schema         : my_blog_db

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 29/10/2021 13:50:49
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
  `role` tinyint NULL DEFAULT NULL COMMENT '0普通用户,1管理员',
  PRIMARY KEY (`admin_user_id`, `login_user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES (1, 'admin', '670b14728ad9902aecba32e22fa4f6bd', '南街', 1, 1);
INSERT INTO `tb_admin_user` VALUES (4, 'yzq', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, 1);

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_category`;
CREATE TABLE `tb_blog_category`  (
  `relation_id` int NOT NULL AUTO_INCREMENT,
  `blog_id` int NULL DEFAULT NULL,
  `category_id` int NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO `tb_blog_category` VALUES (9, 1, 1, NULL);
INSERT INTO `tb_blog_category` VALUES (10, 43, 22, NULL);
INSERT INTO `tb_blog_category` VALUES (12, 44, 1, NULL);
INSERT INTO `tb_blog_category` VALUES (13, 46, 33, '2021-08-26 13:23:40');
INSERT INTO `tb_blog_category` VALUES (17, 45, 22, '2021-08-26 13:43:26');
INSERT INTO `tb_blog_category` VALUES (18, 47, 22, '2021-10-29 10:47:55');
INSERT INTO `tb_blog_category` VALUES (19, 48, 22, '2021-10-29 10:47:59');
INSERT INTO `tb_blog_category` VALUES (20, 49, 22, '2021-10-29 10:51:52');
INSERT INTO `tb_blog_category` VALUES (21, 50, 33, '2021-10-29 11:46:05');
INSERT INTO `tb_blog_category` VALUES (22, 50, 1, '2021-10-29 11:50:33');
INSERT INTO `tb_blog_category` VALUES (23, 51, 33, '2021-10-29 11:52:32');

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
INSERT INTO `tb_blog_config` VALUES ('sysCopyRight', '版权所', 'xuebingsi(xuebingsi) 访问官网', '2019-08-24 20:33:31', '2021-08-25 20:25:04');
INSERT INTO `tb_blog_config` VALUES ('sysEmail', '开发者邮箱', '1320291471@qq.com', '2019-08-24 14:06:48', '2019-08-24 14:06:51');
INSERT INTO `tb_blog_config` VALUES ('sysUpdateTime', '最后修改时间', '2021-10-24 20:33:23', '2019-08-24 20:33:20', '2021-08-10 09:16:49');
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
  `blog_status` tinyint NOT NULL DEFAULT 0 COMMENT '0-草稿 1-发布',
  `blog_views` bigint NOT NULL DEFAULT 0 COMMENT '阅读量',
  `enable_comment` tinyint NOT NULL DEFAULT 0 COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_info
-- ----------------------------
INSERT INTO `tb_blog_info` VALUES (48, 'manjaro安装后的配置', '', '', '> 记得进入设置>工作空间行为>锁屏>关闭自动锁屏，不然会卡死在登陆\n>\n> ## 分区工具的使用 partitionmanager\n\n```bash\nsudo yay -S partitionmanager\n```\n> ## pacman 的日常使用\n\n安装todesk远程控制    \n```javascript\nyay -Ss todesk\n找到todesk  \nyay -S todesk-bin\n# 需要开启u服务\nsystemctl start todeskd.service \n```\n```bash\npacman -S xx1 xx2   # 安装或升级软件包，或者一列软件包（包含依赖包）\npacman -Sy          # 更新软件源\npacman -Syy         # 强制更新软件源\npacman -Su          # 更新软件包\npacman -Syu         # 更新软件源并更新软件包（-Syyu）\npacman -Sc          # 清除软件包缓存\npacman -Ss  xxx     # 搜索名字含 xxx 的软件\npacman -Ss ^xxx     # 搜索名字以 xxx 开头的软件\npacman -R xxx       # 删除单个软件包，保留其安装的依赖关系\npacman -Rs xxx      # 删除指定软件包，及其没有被其他已安装软件包使用的依赖关系\npacman -Rn xxx      # 一并删除全局配置文件\npacman -Rns xxx     # 删除一个软件的推荐命令\npacman -Rns $(pacman -Qdtq) # 删除没有依赖的包\npacman -Q           # 查询本地软件包数据库\npacman -Qq          # 查询本地软件包数据库，但不显示版本信息\npacman -Qe          # 查询非系统自带软件包数据库\npacman -Q | wc -l   # 查看有多少本地软件包\npacman -Qs regex    # 按正则表达式查询软件包\npacman -Qdt         # 查看没有依赖的包\n```\n## 更换源\n```bash\n//1.配置镜像源:\nsudo pacman-mirrors -i -c China -m rank\n//2.设置 archlinuxcn 源,\n#使用方法：在 /etc/pacman.conf 文件末尾添加以下两行：\n\n[archlinuxcn]\nServer = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/$arch\n\n# 之后安装 archlinuxcn-keyring 包导入 GPG key。\n//3.更新源列表\nsudo pacman-mirrors -g\n//4.更新pacman数据库并全面更新系统\nsudo pacman -Syyu\n//5.更新签名\nsudo pacman -S archlinuxcn-keyring\n//6.安装yay,e更换aur的源\nsudo pacman -S yay git net-tools tree vim\n//7.执行以下命令修改 aururl :\nyay --aururl \"https://aur.tuna.tsinghua.edu.cn\" --save\n//8.修改的配置文件位于 `~/.config/yay/config.json` ，还可通过以下命令查看修改过的配置：\nyay -P -g\n以后就可以直接执行yay -S 你要安装的软件名字，比如\nyay -S netease-cloud-music\n```\n## 更新文件夹语言\n```\nexport LANG=en_US\nxdg-user-dirs-gtk-update  //弹出对话框，问是否改成英文，点是，并且选“不再提示”\nexport LANG=zh_CN\n```\n## 安装常用的软件：\n```bash\nyay -S net-tools base-devel\n浏览器我推荐brave浏览器\nyay -S brave\nchrome浏览器： yay -S google-chrome \nMicrosoft-edge浏览器：yay -S microsoft-edge-dev-bin\nGit软件： yay -S git \nUget配合aria2： yay -S aria2 \n下载工具： yay -S uget \n解压工具： yay -S p7zip file-roller unrar \n图像编辑器： yay -S gimp （开源版PS）\nWPS办公： yay -S wps-office \nWPS缺少的字体： yay -S ttf-wps-fonts \nWPS安装中文： sudo pacman -S wps-office-mui-zh-cn \nVscode开发工具： yay -S visual-studio-code-bin \nMarkdown编辑器： yay -S typora \n强大的Web内容（视频，音频，图片）下载工具：  yay -S you-get \n火焰截图： yay -S flameshot  (超好用,系统设置里面添加快捷键即可)\n云笔记软件： yay -S joplin (开源免费，云服务需要飞天服务)\nchm文件阅读器： yay -S kchmviewer \n有道词典： yay -S youdao-dict \n开源电子书阅读器： yay -S calibre \nMD文件编辑器： yay -S typora \nGif录制软件： yay -S peek \nsudo pacman -S filezilla\n# 关闭电子钱包\n用了kde以后，每次打开浏览器都会跳出电子钱包什么的，十分烦人，也可能是我自己不习惯\n于是就想办法把他关掉！\n搜索Kwallet， 不是KwalletManager\n然后把启用的勾勾去掉就好啦\n```\n## 安装docker：\n```bash\nsudo pacman -S docker\nsudo pacman -S docker-compose\n```\n## 安装java：\n（[https://wiki.archlinux.org/index.php/Java#Installation](https://wiki.archlinux.org/index.php/Java#Installation)）\n```bash\n\nsudo pacman -S java-runtime-common java-environment-common\nyay jdk8  # Select extra/jdk8-openjdk\n使用 archlinux-java 命令来管理 Java 环境。\n列举 Java 环境：\narchlinux-java status\n选择 Java 环境：\nsudo archlinux-java set java-8-openjdk\n\n打开.bashrc\n# 在后面加上， 地址根据你jdk修改\nexport JAVA_HOME=/home/hxy/java/jdk-13\nexport JRE_HOME=${JAVA_HOME}/jre\nexport CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib\nexport PATH=${JAVA_HOME}/bin:$PATH\n\n# 启用配置，不过我这边不知道怎么退出（我是强制关掉）\nsource  .bashrc\n\n# 查看是否配置成功\njava -version\n```\n## 安装node,nvm，yarn：\n\n\n注意区别\n```java\n/etc/profile: 此文件为系统的每个用户设置环境信息,当用户第一次登录时,该文件被执行.并从/etc/profile.d目录的配置文件中搜集shell的设置.\n/etc/bashrc:  为每一个运行bash shell的用户执行此文件.当bash shell被打开时,该文件被读取.\n~/.bash_profile: 每个用户都可使用该文件输入专用于自己使用的shell信息,当用户登录时,该文件仅仅执行一次!默认情况下,他设置一些环境变量,执行用户的.bashrc文件.\n~/.bashrc: 该文件包含专用于你的bash shell的bash信息,当登录时以及每次打开新的shell时,该该文件被读取.\n~/.bash_logout: 当每次退出系统(退出bash shell)时,执行该文件.\n  \n另外,/etc/profile中设定的变量(全局)的可以作用于任何用户,而~/.bashrc等中设定的变量(局部)只能继承/etc/profile中的变量,他们是\"父子\"关系.\n\n```\n```bash\n先配置下载github太慢\n kate /etc/hosts\n在最后添加 \n199.232.68.133  raw.githubusercontent.com\n# 1.下载nvm脚本文件\ncurl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash\n# 2. 或者：\nexport NVM_NODEJS_ORG_MIRROR=https://npmmirror.com/mirrors/node\n# 注：如果只是在终端输入了上面的代码只能在本次窗口没有关闭的时候生效，下次打开还是会还原成默认的源\n# 如果需要长久的使用淘宝源则需要如下操作\n# 确认你的sh是什么，一般bash或者zsh\n# 如果是 bash 则键入 \necho \"export NVM_NODEJS_ORG_MIRROR=https://npmmirror.com/mirrors/node\" >> ~/.bash_profile\n\n# 如果是 zsh 则输入 \necho \"export NVM_NODEJS_ORG_MIRROR=https://npmmirror.com/mirrors/node\" >> ~/.zshrc\n\n# 最后再分别执行命令\n\n# bash 为 ：source ~/.bash_profile\n\n# zsh 为 ：source ~/.zshrc\n等同于\n在.bashrc中添加下面的内容\nexport NVM_NODEJS_ORG_MIRROR=http://npmmirror.com/mirrors/node\n3.下载node,配置国内镜像\nnvm install node\n设置npm国内镜像\nnpm config set registry https://registry.npmmirror.com\n安装镜像服务\n# npm i -g mirror-config-china #不需要了\nnpm i -g yarn\n设置yarn镜像（可以做成一个a.sh脚本执行）\nyarn config set registry https://registry.npmmirror.com -g\nyarn config set disturl https://npmmirror.com/dist -g\nyarn config set electron_mirror https://npmmirror.com/mirrors/electron/ -g\nyarn config set sass_binary_site https://npmmirror.com/mirrors/node-sass/ -g\nyarn config set phantomjs_cdnurl https://npmmirror.com/mirrors/phantomjs/ -g\nyarn config set chromedriver_cdnurl https://npmmirror.com/dist/chromedriver -g\nyarn config set operadriver_cdnurl https://npmmirror.com/dist/operadriver -g\nyarn config set fse_binary_host_mirror https://npmmirror.com/mirrors/fsevents -g\n\n```\n## 安装miniconda：\n```bash\n从清华镜像下载miniconda执行的sh\n https://mirrors.tuna.tsinghua.edu.cn/anaconda/miniconda/ \n\n 配置conda国内镜像\n i在.condarc中加入下面的：\n channels:\n  - defaults\nshow_channel_urls: true\nchannel_alias: https://mirrors.tuna.tsinghua.edu.cn/anaconda\ndefault_channels:\n  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main\n  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free\n  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/r\n  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/pro\n  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/msys2\ncustom_channels:\n  conda-forge: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud\n  msys2: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud\n  bioconda: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud\n  menpo: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud\n  pytorch: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud\n  simpleitk: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud\n```\n 安装Anaconda后终端出现的(base)字样去除方法：\n```bash\nconda config --set auto_activate_base False\n```\n## 安装输入法：\n设置小键盘开机自动启动方法\n打开设置 找到输入设备->键盘 -> plasma启动时numlock状态->开启即可\n关闭触控板也是输入设备->触摸板\n```bash\n下载fcitx5并安装\nsudo yay -S fcitx5 fcitx5-chinese-addons  fcitx5-rime fcitx5-chewing  fcitx5-configtool\n1、在用户文件夹下创建.xprofile配置文件\n\n输入命令\nsudo vim ~/.xprofile \n\n然后插入如下内容\nexport GTK_IM_MODULE=fcitx5\nexport QT_IM_MODULE=fcitx5\nexport XMODIFIERS=@im=fcitx\n2、设置fcitx5为开机启动\n①: 直接在~/.xprofile中插入下面这行\nfcitx5 &\n②: 如果是KDE用户，可在系统设置-启动和关闭-自启动中填加fcitx5为开机自启动项\n编辑~`/.pam_environment`\nINPUT_METHOD  DEFAULT=fcitx5\nGTK_IM_MODULE DEFAULT=fcitx5\nQT_IM_MODULE  DEFAULT=fcitx5\nXMODIFIERS    DEFAULT=\\@im=fcitx5\n```\n## 安装golang:\n### 1.使用pacman/yay\n```bash\nsudo pacman -S go go-tools\n```\n> 可选用 `gcc-go` ，但若需要使用 JetBrains 的 IDE 则必须使用 `go` ，否则 IDE 无法在 `/usr/lib/go` 下找到 Go SDK。\n\n### 2.从官网下载\n```bash\n在golang官网下载go的安装包，go1.16.linux-amd64.tar.gz 然后\n# 1.解压到local目录\nwget  https://golang.google.cn/dl/go1.16.4.linux-amd64.tar.gz\nsudo tar -C /opt -xzf go1.16.4.linux-amd64.tar.gz\n# 2.编辑环境变量，在/etc/profile和.bashrc添加\n#vim ~/.zshrc  # OR ~/.bashrc OR ~/.profile\ncp -r ~/.bashrc ~/bashrcbak\n# 添加a环境变量\nexport GOMODPATH=/opt/go\nexport PATH=\"$GOMODPATH/bin:$GOMODPATH/golangmod/bin:$PATH\"\n# 修改/opt的权限\nsudo chmod -R 777 /opt/go\n\n\n\necho \'export GOMODPATH=/opt/go\nexport PATH=\"$GOMODPATH/bin:$GOMODPATH/golangmod/bin:$PATH\"\' >>~/.bashrc\nsource ~/.bashrc\n# 3.输入go version检查是否有安装成功\n# 4.配置go代理\ngo env -w GO111MODULE=on\ngo env -w GOPROXY=https://goproxy.cn,direct\ngo env -w GOPATH=/opt/go/golangmod\n```\n## 安装teamviewer\n```bash\nyay -S teamviewer\n提示：TeamViewer Daemon is not running，解决方法：\nsudo teamviewer --daemon enable\n```\n## 安装myql：（已经被替换为mariadb,请使用下面的方法）\n如果要使用mysql8,需要设置aur！！！！！！！\n```bash\n//下载Mysql\npacman -S mysql\n//初始化Mysql，记住生成的密码，方便修改\nsudo mysqld --initialize --user=mysql --basedir=/usr --datadir=/var/lib/mysql\n//设置开机启动\nsystemctl enable mysqld.service\n//启动Mysql\nsudo systemctl start mysqld.service\n//修改密码\nmysql -u root -p\nmysql> ALTER USER \'root\'@\'localhost\' IDENTIFIED BY \'新密码\';\n```\n如果忘记密码，直接百度mysql8忘记密码怎么办\n### manjaro 。忘记密码解决办法：\n```bash\n# 1.先编辑mysql配置文件\nkate /etc/mysql/my.cnf\n# 2.添加一个     skip-grant-tables\n# 3.  重启mysql服务\nsudo systemctl restart mysqld\n# 4.进入mysql 直接在命令行进入lmysql\n# 5 设置 root密码为空\nmysql>use mysql;  \nmysql>update user set authentication_string=\'\' where user=\'root\';   #将密码置空 \nmysql>exit\n#6 去掉mysql配置文件里的     skip-grant-tables 重新登陆mysql使用\nmysql -uroot -p  #两次确定就可进去\n#7. ALTER USER \'root\'@\'localhost\' IDENTIFIED BY \'新密码\';# 然后退出mysql登陆就可以了\n```\n## 如果出现\n```\nER_NOT_SUPPORTED_AUTH_MODE: Client does not support authentication protocol requested by server; consider upgrading MySQL client\n```\n命令行进入mysql输入\n```bash\nALTER USER \'root\'@\'localhost\' IDENTIFIED WITH mysql_native_password BY \'123456\'\n```\n就可以连上了\n## 安装mariadb\n##1. 安装 与Ubuntu不同，arch默认已经不再支持MySQL，但是可以安装MariaDB，其比MySQL的性能更好且操作基本相同。 输入下面命令安装：\n```bash\nsystemctl stop mysqld    //停止mysql服务\nsudo pacman -S mariadb libmariadbclient mariadb-clients    //安装mariadb\nsudo mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql\n复制代码\n```\n##2. 启动\n```bash\nsystemctl start mariadb\nmysql_secure_installation    //设置密码等管理操作\nsystemctl restart mariadb\n复制代码\n```\n登录：\n```\nmysql -u root -p\n```\n## 安装postgressql\n安装：\n```bash\nsudo pacman -S  postgresql\n```\n\n\n初始化(必须)：\n```swift\nsudo su - postgres -c \"initdb --locale zh_CN.UTF-8 -E UTF8 -D \'/var/lib/postgres/data\'\"\n```\n\n\n其中，我将原本的en_US改为了zh_CN，未见异常。想要撤销的话，只需要把`\'/var/lib/postgres/data\'`下面的内容清空。\n启动/开机启动 PostgreSQL：\n```css\nsystemctl start postgresql.service\nsystemctl enable postgresql.service\n```\n切换到postgres用户，然后登录（初始无密码）：\n```undefined\nsudo -i -u postgres\npsql\n```\n要退出psql或返回原用户，都是用`exit`命令。\nPostgreSQL的用户跟系统用户有些关联，前者必须也是后者。在初始化过程中会在系统中创建postgres用户，同时也是数据库的超级权限用户，postgres用户可以创建其他数据库用户。\n> 提示： 如果创建一个与你的系统用户同名的数据库用户，并允许其访问 PostgreSQL 数据库，那么在登录PostgreSQL 数据库 shell 的时候无需切换用户（这样做会比较方便）。\n\n### 通用基本操作[[1]](#fn1)\n#### 数据库shell外\n添加数据库（须在原用户操作）：\n```bash\ncreatedb myDatabaseName\n```\n连接数据库shell（须用postgres用户，所以先切一下用户）：\n```bash\nsudo -i -u postgres\npsql -d myDatabaseName\n```\n也可以一步进入postgres用户的myDatabaseName数据库，与上面效果一样：\n\n\n```bash\npsql -U postgres -d myDatabaseName\n```\n\n\n若要创建用户，要在数据库程序外，用postgres用户执行：\n\n\n```undefined\ncreateuser --interactive myUserName\n```\n\n\n#### 数据库shell内\n\n\n注意：数据库内的SQL语句，建议大写，必须分号结尾。\n进入数据库后可修改密码：\n\n\n```dart\nalter user postgres with password \' *** 密码 *** \';\n```\n\n\n如果有其他用户，可以把postgres换成其他用户的名。\n一些常用的命令：\n\n\n```bash\n\\c myDatabaseName     # 连接到数据库myDatabaseName\n\\du    # 列出所有用户以及他们的权限\n\\dt    # 展示当前数据库中所有的表相关的汇总信息\n\\q    # 退出psql\n```\n## 开启ssh服务\n```bash\nsystemctl enable sshd.service 开机启动\nsystemctl start sshd.service 立即启动\nsystemctl restart sshd.service 立即重启\n```\n## 安装nginx：\n```bash\nyay -S nginx-mainline\n```\n## 安装redis：\n```bash\nyay -S redis\n\n1 启动服务端\nsudo redis-server\n启动redis\n2 启动客户端\nsudo redis-cli\nsystemctl redis start\n# 关闭redis\nsystemctl redis stop\n# 开机自启动\nsystemctl enable redis\n```\n配置redis密码：在`/etc/redis.conf`中找到requirepass,去掉h注释并更换为自己的密码；\n出现 \n### System limit for number of file watchers reached\n```bash\necho fs.inotify.max_user_watches = 524288 | sudo tee -a /etc/sysctl.conf \nsudo sysctl -p\n```\n## 安装php和composer\n安装php：\n```bash\nyay -S php\n```\n第一步，下载composer。（切换到项目的根目录，再执行）\n```bash\nphp -r \"readfile(\'https://getcomposer.org/installer\');\" | php\n```\n下载之后后自动安装，执行 php composer.phar。查看composer是否安装成功。\n第二步，将composer.phar文件移动到bin目录以便全局使用composer命令\n```bash\ncp -r composer.phar /usr/local/bin/composer\n```\n（如果只是针对某个项目使用composer,可忽略此步）\n第三步，切换国内源（如果第一步下载成功，可忽略此步）\n```bash\ncomposer config -g repo.packagist composer https://packagist.phpcomposer.com\n```\n第四步，安装phpcgi\n```bash\nyay -S php-apache php-cgi php-fpm php-gd  php-embed php-intl php-imap  php-redis php-snmp\n```\n第五步，安装pecl\n```bash\nwget http://pear.php.net/go-pear.phar\nphp go-pear.phar\n```\n第六步，安装xdebug\n```bash\nsudo pecl install xdebug\n\n//为php.ini 添加 extension=xdebug.so\nsudo vim /etc/php/php.ini\nsudo systemctl reload php-fpm\n```\n', 1, 41, 1, 0, '2021-10-29 10:47:59', '2021-10-29 10:47:59');
INSERT INTO `tb_blog_info` VALUES (49, 'Nginx配置', '', '', '```java\nsudo apt-get install nginx\n```\n主分文件分片\n[https://blog.csdn.net/qq_41783562/article/details/106530017](https://blog.csdn.net/qq_41783562/article/details/106530017)\n\n\n```bash\nsudo systemctl start nginx\n```\n注意要把    include conf.d/*.conf; 写在http{}内部\n```java\n\n#user  nobody;\nworker_processes  1;\n\n#error_log  logs/error.log;\n#error_log  logs/error.log  notice;\n#error_log  logs/error.log  info;\n\n#pid        logs/nginx.pid;\nevents {\n    worker_connections  1024;\n}\n\n\n\n\nhttp {\n    include       mime.types;\n    default_type  application/octet-stream;\n\n    #log_format  main  \'$remote_addr - $remote_user [$time_local] \"$request\" \'\n    #                  \'$status $body_bytes_sent \"$http_referer\" \'\n    #                  \'\"$http_user_agent\" \"$http_x_forwarded_for\"\';\n\n    #access_log  logs/access.log  main;\n\n    sendfile        on;\n    #tcp_nopush     on;\n\n    #keepalive_timeout  0;\n    keepalive_timeout  65;\n\n    #gzip  on;\ninclude conf.d/*.conf;\n \n    server {\n        listen       80;\n        server_name  localhost;\n\n        #charset koi8-r;\n\n        #access_log  logs/host.access.log  main;\n\n        location / {\n            root   html;\n            index  index.html index.htm;\n        }\n\n        #error_page  404              /404.html;\n\n        # redirect server error pages to the static page /50x.html\n        #\n        error_page   500 502 503 504  /50x.html;\n        location = /50x.html {\n            root   html;\n        }\n\n        # proxy the PHP scripts to Apache listening on 127.0.0.1:80\n        #\n        #location ~ \\.php$ {\n        #    proxy_pass   http://127.0.0.1;\n        #}\n\n        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000\n        #\n        #location ~ \\.php$ {\n        #    root           html;\n        #    fastcgi_pass   127.0.0.1:9000;\n        #    fastcgi_index  index.php;\n        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;\n        #    include        fastcgi_params;\n        #}\n\n        # deny access to .htaccess files, if Apache\'s document root\n        # concurs with nginx\'s one\n        #\n        #location ~ /\\.ht {\n        #    deny  all;\n        #}\n    }\n\n\n    # another virtual host using mix of IP-, name-, and port-based configuration\n    #\n    #server {\n    #    listen       8000;\n    #    listen       somename:8080;\n    #    server_name  somename  alias  another.alias;\n\n    #    location / {\n    #        root   html;\n    #        index  index.html index.htm;\n    #    }\n    #}\n\n\n    # HTTPS server\n    #\n    #server {\n    #    listen       443 ssl;\n    #    server_name  localhost;\n\n    #    ssl_certificate      cert.pem;\n    #    ssl_certificate_key  cert.key;\n\n    #    ssl_session_cache    shared:SSL:1m;\n    #    ssl_session_timeout  5m;\n\n    #    ssl_ciphers  HIGH:!aNULL:!MD5;\n    #    ssl_prefer_server_ciphers  on;\n\n    #    location / {\n    #        root   html;\n    #        index  index.html index.htm;\n    #    }\n    #}\n\n}\n\n```\nconfig.d/nav.conf\n```java\n\n   server{\n    listen    6800;\n    server_name  localhost;\n      location / {\n            root   /opt/navurl/;\n            index  index.html index.htm;\n        }\n    }\n   server{\n    listen    6900;\n    server_name  localhost;\n      location /home/main {\n           root   /opt/myblog;\n           try_files $uri $uri/ /index.html;\n           index  index.html index.htm;\n        }\n         location / {\n            root   /opt/myblog;\n            try_files $uri $uri/ /index.html;\n            index  index.html index.htm;\n            \n            \n        }\n    }\n\n```\n', 0, 0, 0, 0, '2021-10-29 10:51:52', '2021-10-29 10:51:52');
INSERT INTO `tb_blog_info` VALUES (51, '策划', '', '', '- [ ] :smiley:- [ ] 任务列表\n- ::: tip\n  在此输入内容\n:::', 0, 5, 1, 0, '2021-10-29 11:52:32', '2021-10-29 11:52:32');

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag`;
CREATE TABLE `tb_blog_tag`  (
  `relation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint NOT NULL COMMENT '博客id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 445 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客跟标签的关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------
INSERT INTO `tb_blog_tag` VALUES (446, 48, 145, '2021-10-29 10:47:59');
INSERT INTO `tb_blog_tag` VALUES (447, 49, 145, '2021-10-29 10:51:52');
INSERT INTO `tb_blog_tag` VALUES (449, 51, 1, '2021-10-29 11:52:32');

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类的图标',
  `category_rank` int NOT NULL DEFAULT 1 COMMENT '分类的排序值 被使用的越多数值越大',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (1, '默认分类', '', 1, 0, '2019-08-30 15:07:02');
INSERT INTO `tb_category` VALUES (22, 'Java进阶', '', 22, 0, '2018-11-12 10:42:25');
INSERT INTO `tb_category` VALUES (33, '前端', NULL, 1, 0, '2021-08-26 13:23:23');

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
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
  `user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户使用的浏览器',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES (32, 48, 'dd', '1@qq.com', '', '写的不错啊', '2021-10-29 10:50:04', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36 Edg/95.0.1020.30');
INSERT INTO `tb_comment` VALUES (35, 51, ' 舒服', '3131681417@qq.com', '', '::: warning\n  :heart_eyes:\n:::', '2021-10-29 11:55:35', '0:0:0:0:0:0:0:1', '', NULL, 1, 0, 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36 Edg/95.0.1020.30');

-- ----------------------------
-- Table structure for tb_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_img`;
CREATE TABLE `tb_img`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `img_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img_size` int NULL DEFAULT NULL,
  `img_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `upload_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 864034819 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_img
-- ----------------------------
INSERT INTO `tb_img` VALUES (1, '20211029_13501964.png', 1896790, 'D:/tmp/myblog/upload/\\20211029_13501964.png', 'http://localhost:2801/20211029_13501964.png', '.png', '4f2b76e609f99016e68262573bc332b9', '2021-10-29 13:50:19.309000');

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_link`;
CREATE TABLE `tb_link`  (
  `link_id` int NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint NOT NULL DEFAULT 0 COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  `link_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站描述',
  `link_rank` int NOT NULL DEFAULT 0 COMMENT '用于列表排序',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '友情链接表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_link
-- ----------------------------
INSERT INTO `tb_link` VALUES (1, 0, '百度', 'https://www.baidu.com', '这是百度', 0, 1, '2021-08-25 20:17:06', '2021-08-25 20:17:06');
INSERT INTO `tb_link` VALUES (2, 0, 'github', 'https://github.com', '这是github', 0, 1, '2019-09-02 21:24:44', NULL);
INSERT INTO `tb_link` VALUES (4, 1, '饿了吗', 'https://element-plus.gitee.io/#/zh-CN/component/link#tu-biao', '222222', 123, 0, '2019-09-03 14:47:21', NULL);
INSERT INTO `tb_link` VALUES (5, 0, 'name', 'field', 'value', 0, 1, '2021-08-25 06:28:56', NULL);

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag`  (
  `tag_id` int NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
INSERT INTO `tb_tag` VALUES (1, '默认tag', 0, '2021-08-26 13:05:01');
INSERT INTO `tb_tag` VALUES (144, '第一个标签', 0, '2021-08-26 13:11:33');
INSERT INTO `tb_tag` VALUES (145, '技术', 0, '2021-08-26 13:23:10');

SET FOREIGN_KEY_CHECKS = 1;
