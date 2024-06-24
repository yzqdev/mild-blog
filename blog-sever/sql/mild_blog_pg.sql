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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admin_user (
    id character varying(32) NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    nickname character varying(50),
    locked boolean,
    role smallint,
    avatar character varying(255),
    email character varying(255),
    uuid character varying(255)
);


ALTER TABLE public.admin_user OWNER TO postgres;

--
-- Name: TABLE admin_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.admin_user IS '后台管理员信息表';


--
-- Name: COLUMN admin_user.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.id IS '管理员id';


--
-- Name: COLUMN admin_user.username; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.username IS '管理员登陆名称';


--
-- Name: COLUMN admin_user.password; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.password IS '管理员登陆密码';


--
-- Name: COLUMN admin_user.nickname; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.nickname IS '管理员显示昵称';


--
-- Name: COLUMN admin_user.locked; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.locked IS '是否锁定 0未锁定 1已锁定无法登陆';


--
-- Name: COLUMN admin_user.role; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.role IS '0普通用户,1管理员';


--
-- Name: COLUMN admin_user.avatar; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.avatar IS '用户头像';


--
-- Name: COLUMN admin_user.email; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.email IS '邮箱';


--
-- Name: COLUMN admin_user.uuid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.admin_user.uuid IS 'uuid';


--
-- Name: authority; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authority (
    id integer NOT NULL,
    username character varying(255),
    authority character varying(255),
    create_time timestamp without time zone,
    create_user character varying(255)
);


ALTER TABLE public.authority OWNER TO postgres;

--
-- Name: authority_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.authority_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.authority_id_seq OWNER TO postgres;

--
-- Name: authority_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.authority_id_seq OWNED BY public.authority.id;


--
-- Name: blog_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.blog_category (
    relation_id character varying(64) NOT NULL,
    blog_id character varying(64),
    category_id character varying(64),
    create_time timestamp without time zone
);


ALTER TABLE public.blog_category OWNER TO postgres;

--
-- Name: blog_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.blog_config (
    id character varying(255),
    config_code character varying(255),
    config_name character varying(255),
    config_value character varying(255),
    create_time timestamp without time zone,
    update_time timestamp without time zone
);


ALTER TABLE public.blog_config OWNER TO postgres;

--
-- Name: blog_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.blog_info (
    blog_id character varying(64) NOT NULL,
    blog_title character varying(200) NOT NULL,
    sub_url character varying(200),
    preface character varying(255),
    blog_content text NOT NULL,
    blog_views bigint,
    enable_comment boolean,
    create_time timestamp(6) without time zone NOT NULL,
    update_time timestamp(6) without time zone,
    show boolean NOT NULL,
    deleted boolean
);


ALTER TABLE public.blog_info OWNER TO postgres;

--
-- Name: TABLE blog_info; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.blog_info IS '博客信息表';


--
-- Name: COLUMN blog_info.blog_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.blog_id IS '博客表主键id';


--
-- Name: COLUMN blog_info.blog_title; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.blog_title IS '博客标题';


--
-- Name: COLUMN blog_info.sub_url; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.sub_url IS '博客自定义路径url';


--
-- Name: COLUMN blog_info.preface; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.preface IS '博客前言';


--
-- Name: COLUMN blog_info.blog_content; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.blog_content IS '博客内容';


--
-- Name: COLUMN blog_info.blog_views; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.blog_views IS '阅读量';


--
-- Name: COLUMN blog_info.enable_comment; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.enable_comment IS '0-允许评论 1-不允许评论';


--
-- Name: COLUMN blog_info.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.create_time IS '添加时间';


--
-- Name: COLUMN blog_info.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.update_time IS '修改时间';


--
-- Name: COLUMN blog_info.show; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.show IS '是否发布';


--
-- Name: COLUMN blog_info.deleted; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_info.deleted IS '是否已经删除';


--
-- Name: blog_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.blog_tag (
    relation_id character varying(64) NOT NULL,
    blog_id character varying(64) NOT NULL,
    tag_id character varying(64) NOT NULL,
    create_time timestamp without time zone
);


ALTER TABLE public.blog_tag OWNER TO postgres;

--
-- Name: TABLE blog_tag; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.blog_tag IS '博客跟标签的关系表';


--
-- Name: COLUMN blog_tag.relation_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_tag.relation_id IS '关系表id';


--
-- Name: COLUMN blog_tag.blog_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_tag.blog_id IS '博客id';


--
-- Name: COLUMN blog_tag.tag_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_tag.tag_id IS '标签id';


--
-- Name: COLUMN blog_tag.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.blog_tag.create_time IS '添加时间';


--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    category_id character varying(64) NOT NULL,
    category_name character varying(50) NOT NULL,
    category_icon character varying(50),
    category_rank integer NOT NULL,
    create_time timestamp without time zone NOT NULL,
    show boolean NOT NULL,
    update_time timestamp without time zone
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: TABLE category; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.category IS '博客分类';


--
-- Name: COLUMN category.category_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.category.category_id IS '分类表主键';


--
-- Name: COLUMN category.category_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.category.category_name IS '分类的名称';


--
-- Name: COLUMN category.category_icon; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.category.category_icon IS '分类的图标';


--
-- Name: COLUMN category.category_rank; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.category.category_rank IS '分类的排序值 被使用的越多数值越大';


--
-- Name: COLUMN category.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.category.create_time IS '创建时间';


--
-- Name: COLUMN category.show; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.category.show IS '是否删除 0=否 1=是';


--
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment (
    id character varying(64) NOT NULL,
    blog_id character varying(64) NOT NULL,
    commentator character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    website_url character varying(50),
    comment_body text NOT NULL,
    comment_create_time timestamp without time zone NOT NULL,
    commentator_ip character varying(20),
    reply_body character varying(200),
    reply_create_time timestamp without time zone,
    comment_status boolean NOT NULL,
    user_agent character varying(255),
    os character varying(255),
    deleted boolean NOT NULL
);


ALTER TABLE public.comment OWNER TO postgres;

--
-- Name: TABLE comment; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.comment IS '评论信息表';


--
-- Name: COLUMN comment.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.id IS '主键id';


--
-- Name: COLUMN comment.blog_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.blog_id IS '关联的blog主键';


--
-- Name: COLUMN comment.commentator; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.commentator IS '评论者名称';


--
-- Name: COLUMN comment.email; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.email IS '评论人的邮箱';


--
-- Name: COLUMN comment.website_url; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.website_url IS '网址';


--
-- Name: COLUMN comment.comment_body; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.comment_body IS '评论内容';


--
-- Name: COLUMN comment.comment_create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.comment_create_time IS '评论提交时间';


--
-- Name: COLUMN comment.commentator_ip; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.commentator_ip IS '评论时的ip地址';


--
-- Name: COLUMN comment.reply_body; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.reply_body IS '回复内容';


--
-- Name: COLUMN comment.reply_create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.reply_create_time IS '回复时间';


--
-- Name: COLUMN comment.comment_status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.comment_status IS '是否审核通过 0-未审核 1-审核通过';


--
-- Name: COLUMN comment.user_agent; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.user_agent IS '用户使用的浏览器';


--
-- Name: COLUMN comment.os; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.os IS '用户的系统';


--
-- Name: COLUMN comment.deleted; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment.deleted IS '是否删除 0-未删除 1-已删除';


--
-- Name: email_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.email_config (
    id character varying(32) NOT NULL,
    email character varying(255),
    email_key character varying(255),
    email_url character varying(255),
    port character varying(255),
    email_name character varying(255),
    enable boolean
);


ALTER TABLE public.email_config OWNER TO postgres;

--
-- Name: COLUMN email_config.email; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.email_config.email IS '邮箱';


--
-- Name: COLUMN email_config.email_key; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.email_config.email_key IS '授权码';


--
-- Name: COLUMN email_config.email_url; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.email_config.email_url IS '服务器';


--
-- Name: COLUMN email_config.port; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.email_config.port IS '端口';


--
-- Name: COLUMN email_config.email_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.email_config.email_name IS '用户名';


--
-- Name: COLUMN email_config.enable; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.email_config.enable IS '1为可用，其他为不使用';


--
-- Name: img; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.img (
    id character varying(32) NOT NULL,
    img_name character varying(255),
    img_size integer,
    img_path character varying(255),
    img_url character varying(255),
    img_type character varying(255),
    media_type character varying(255),
    upload_time timestamp without time zone,
    thumbnail_path character varying(255),
    md5 character varying(255)
);


ALTER TABLE public.img OWNER TO postgres;

--
-- Name: link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.link (
    link_id character varying(64) NOT NULL,
    link_type smallint NOT NULL,
    link_name character varying(50) NOT NULL,
    link_url character varying(100) NOT NULL,
    link_description character varying(100) NOT NULL,
    link_rank integer NOT NULL,
    show boolean NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone
);


ALTER TABLE public.link OWNER TO postgres;

--
-- Name: TABLE link; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.link IS '友情链接表';


--
-- Name: COLUMN link.link_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.link_id IS '友链表主键id';


--
-- Name: COLUMN link.link_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.link_type IS '友链类别 0-友链 1-推荐 2-个人网站';


--
-- Name: COLUMN link.link_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.link_name IS '网站名称';


--
-- Name: COLUMN link.link_url; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.link_url IS '网站链接';


--
-- Name: COLUMN link.link_description; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.link_description IS '网站描述';


--
-- Name: COLUMN link.link_rank; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.link_rank IS '用于列表排序';


--
-- Name: COLUMN link.show; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.show IS '是否删除 0-未删除 1-已删除';


--
-- Name: COLUMN link.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.link.create_time IS '添加时间';


--
-- Name: sys_dict_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_dict_data (
    id character varying(64) NOT NULL,
    type_id character varying(64) NOT NULL,
    value text NOT NULL,
    code character varying(50) NOT NULL,
    sort integer NOT NULL,
    remark character varying(255),
    status boolean NOT NULL,
    create_time timestamp(6) without time zone,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.sys_dict_data OWNER TO postgres;

--
-- Name: TABLE sys_dict_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.sys_dict_data IS '系统字典值表';


--
-- Name: COLUMN sys_dict_data.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.id IS '主键';


--
-- Name: COLUMN sys_dict_data.type_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.type_id IS '字典类型id';


--
-- Name: COLUMN sys_dict_data.value; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.value IS '值';


--
-- Name: COLUMN sys_dict_data.code; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.code IS '编码';


--
-- Name: COLUMN sys_dict_data.sort; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.sort IS '排序';


--
-- Name: COLUMN sys_dict_data.remark; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.remark IS '备注';


--
-- Name: COLUMN sys_dict_data.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.status IS '状态（字典 0正常 1停用 2删除）';


--
-- Name: COLUMN sys_dict_data.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.create_time IS '创建时间';


--
-- Name: COLUMN sys_dict_data.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_data.update_time IS '更新时间';


--
-- Name: sys_dict_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_dict_type (
    id character varying(64) NOT NULL,
    name character varying(100) NOT NULL,
    code character varying(50) NOT NULL,
    sort integer NOT NULL,
    remark character varying(255),
    status boolean NOT NULL,
    create_time timestamp(6) without time zone,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.sys_dict_type OWNER TO postgres;

--
-- Name: TABLE sys_dict_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.sys_dict_type IS '系统字典类型表';


--
-- Name: COLUMN sys_dict_type.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.id IS '主键';


--
-- Name: COLUMN sys_dict_type.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.name IS '名称';


--
-- Name: COLUMN sys_dict_type.code; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.code IS '编码';


--
-- Name: COLUMN sys_dict_type.sort; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.sort IS '排序';


--
-- Name: COLUMN sys_dict_type.remark; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.remark IS '备注';


--
-- Name: COLUMN sys_dict_type.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.status IS '状态（字典 0正常 1停用 2删除）';


--
-- Name: COLUMN sys_dict_type.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.create_time IS '创建时间';


--
-- Name: COLUMN sys_dict_type.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_dict_type.update_time IS '更新时间';


--
-- Name: sys_op_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_op_log (
    id character varying(64) NOT NULL,
    name character varying(50),
    op_type character varying(16),
    success character(1),
    message text,
    ip character varying(255),
    location character varying(255),
    browser character varying(255),
    os character varying(255),
    url character varying(500),
    class_name character varying(500),
    method_name character varying(500),
    req_method character varying(255),
    param text,
    result text,
    op_time timestamp(6) without time zone,
    account character varying(50)
);


ALTER TABLE public.sys_op_log OWNER TO postgres;

--
-- Name: sys_timers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_timers (
    id character varying(64) NOT NULL,
    timer_name character varying(255),
    action_class character varying(255),
    cron character varying(255),
    status boolean,
    remark character varying(1000),
    create_time timestamp(6) without time zone,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.sys_timers OWNER TO postgres;

--
-- Name: TABLE sys_timers; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.sys_timers IS '定时任务';


--
-- Name: COLUMN sys_timers.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.id IS '定时器id';


--
-- Name: COLUMN sys_timers.timer_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.timer_name IS '任务名称';


--
-- Name: COLUMN sys_timers.action_class; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.action_class IS '执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）';


--
-- Name: COLUMN sys_timers.cron; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.cron IS '定时任务表达式';


--
-- Name: COLUMN sys_timers.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.status IS '状态';


--
-- Name: COLUMN sys_timers.remark; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.remark IS '备注信息';


--
-- Name: COLUMN sys_timers.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.create_time IS '创建时间';


--
-- Name: COLUMN sys_timers.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sys_timers.update_time IS '更新时间';


--
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    tag_id character varying(64) NOT NULL,
    tag_name character varying(100) NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    show boolean NOT NULL,
    update_time timestamp without time zone
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- Name: TABLE tag; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tag IS '标签表';


--
-- Name: COLUMN tag.tag_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tag.tag_id IS '标签表主键id';


--
-- Name: COLUMN tag.tag_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tag.tag_name IS '标签名称';


--
-- Name: COLUMN tag.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tag.create_time IS '创建时间';


--
-- Name: COLUMN tag.show; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tag.show IS '是否删除 0=否 1=是';


--
-- Name: COLUMN tag.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tag.update_time IS '修改时间';


--
-- Name: authority id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authority ALTER COLUMN id SET DEFAULT nextval('public.authority_id_seq'::regclass);


--
-- Name: authority authority_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pkey PRIMARY KEY (id);


--
-- Name: email_config email_config_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.email_config
    ADD CONSTRAINT email_config_pkey PRIMARY KEY (id);


--
-- Name: sys_dict_data sys_dict_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_dict_data
    ADD CONSTRAINT sys_dict_data_pkey PRIMARY KEY (id);


--
-- Name: sys_dict_type sys_dict_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_dict_type
    ADD CONSTRAINT sys_dict_type_pkey PRIMARY KEY (id);


--
-- Name: sys_op_log sys_op_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_op_log
    ADD CONSTRAINT sys_op_log_pkey PRIMARY KEY (id);


--
-- Name: sys_timers sys_timers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_timers
    ADD CONSTRAINT sys_timers_pkey PRIMARY KEY (id);


--
-- Name: admin_user tb_admin_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_user
    ADD CONSTRAINT tb_admin_user_pkey PRIMARY KEY (id, username);


--
-- Name: blog_category tb_blog_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.blog_category
    ADD CONSTRAINT tb_blog_category_pkey PRIMARY KEY (relation_id);


--
-- Name: blog_info tb_blog_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.blog_info
    ADD CONSTRAINT tb_blog_info_pkey PRIMARY KEY (blog_id);


--
-- Name: blog_tag tb_blog_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.blog_tag
    ADD CONSTRAINT tb_blog_tag_pkey PRIMARY KEY (relation_id);


--
-- Name: category tb_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT tb_category_pkey PRIMARY KEY (category_id);


--
-- Name: comment tb_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT tb_comment_pkey PRIMARY KEY (id);


--
-- Name: img tb_img_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.img
    ADD CONSTRAINT tb_img_pkey PRIMARY KEY (id);


--
-- Name: link tb_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.link
    ADD CONSTRAINT tb_link_pkey PRIMARY KEY (link_id);


--
-- Name: tag tb_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tb_tag_pkey PRIMARY KEY (tag_id);


--
-- PostgreSQL database dump complete
--

