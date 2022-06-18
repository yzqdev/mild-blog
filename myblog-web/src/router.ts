import {
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from "vue-router";
import Home from "@/view/Home.vue";

const HomeMain = () => import("@/view/home/HomeMain.vue");
import HomeLink from "@/view/home/HomeLink.vue";
import HomeTag from "@/view/home/HomeTag.vue";
import HomeCategory from "@/view/home/HomeCategory.vue";
import HomeAbout from "@/view/home/HomeAbout.vue";
import HomeBlog from "@/view/home/HomeBlog.vue";
import HomeComponent from "@/view/home/HomeComponent.vue";
//admin
import AdminLogin from "@/view/admin/AdminLogin.vue";
import AdminWelcome from "@/view/admin/AdminWelcome.vue";
import AdminList from "@/view/admin/AdminList.vue";
import ImgList from "@/view/admin/ImgList.vue";
import ArticleList from "@/view/admin/ArticleList.vue";
import ArticleRecycle from "@/view/admin/ArticleRecycle.vue";
import CommentList from "@/view/admin/CommentList.vue";

const CategoryList = () => import(`./view/admin/CategoryList.vue`);
import TagList from "@/view/admin/TagList.vue";
import LinkList from "@/view/admin/LinkList.vue";

import ArticleEdit from "@/view/admin/ArticleEdit.vue";
import UserInfo from "@/view/admin/UserInfo.vue";
//system
import SystemDict from "@/view/system/SystemDict.vue";
import SystemLog from "@/view/system/SystemLog.vue";
import SystemInfo from "@/view/system/SystemInfo.vue";
//layout

import NotFound from "@/components/NotFound.vue";

import Layout from "./layout/Index.vue";
import { setDocumentTitle, domTitle } from "@/utils/meta";

const routes = [
  { path: "/:pathMatch(.*)*", name: "NotFound", component: NotFound },
  { path: "/home", redirect: "/home/main" },
  {
    path: "/home",
    name: "home",
    component: Home,
    children: [
      {
        path: "main",
        name: "homeMain",
        component: HomeMain,
        meta: { title: "主页" },
      },
      {
        path: "link",
        name: "homeLink",
        component: HomeLink,
        meta: {
          title: "链接",
          keepAlive: true /* permission: [ 'dashboard' ] */,
        },
      },
      {
        path: "timeline",
        name: "homeTimeline",
        component: HomeComponent,
        meta: { title: "时间线" },
      },
      {
        path: "search",
        name: "homeSearch",
        component: HomeComponent,
        meta: { title: "搜索" },
      },
      {
        path: "categories",
        name: "homeCategories",
        component: HomeComponent,
        meta: { title: "分类" },
      },
      {
        path: "tags",
        name: "homeTags",
        component: HomeComponent,
        meta: { title: "标签" },
      },
      {
        path: "tag/:id",
        name: "homeTag",
        component: HomeTag,
      },
      {
        path: "category/:id",
        name: "homeCategory",
        component: HomeCategory,
      },
      {
        path: "blog/:id",
        name: "homeBlog",
        component: HomeBlog,
      },
      {
        path: "about",
        name: "HomeAbout",
        component: HomeAbout,
      },
    ],
  },
  { path: "/", redirect: "/home/main" },
  { path: "/admin", redirect: "/admin/login" },
  {
    path: "/admin/login",
    name: "adminLogin",
    component: AdminLogin,
  },
  {
    path: "/admin",
    name: "admin",
    component: Layout,
    children: [
      {
        path: "welcome",
        name: "adminWelcome",
        component: AdminWelcome,
      },
      {
        path: "article-list",
        name: "articleList",
        component: ArticleList,
      },
      {
        path: "article-recycle",
        name: "articleRecycle",
        component: ArticleRecycle,
      },
      {
        path: "article-edit",
        name: "articleEdit",
        component: ArticleEdit,
      },
      {
        path: "comment-list",
        name: "commentList",
        component: CommentList,
      },
      {
        path: "category-list",
        name: "categoryList",
        component: CategoryList,
      },
      {
        path: "tag-list",
        name: "tagList",
        component: TagList,
      },
      {
        path: "link-list",
        name: "linkList",
        component: LinkList,
      },
      {
        path: "img-list",
        name: "imgList",
        component: ImgList,
      },
      {
        path: "system-info",
        name: "systemInfo",
        component: SystemInfo,
      },
      {
        path: "system-log",
        name: "systemLog",
        component: SystemLog,
      },  {
        path: "system-dict",
        name: "systemDict",
        component: SystemDict,
      },
      {
        path: "user-info",
        name: "userInfo",
        component: UserInfo,
      },
      {
        path: "users",
        name: "users",
        component: AdminList,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  to.meta &&
    typeof to.meta.title !== "undefined" &&
    setDocumentTitle(`${to.meta.title} `);
  next();
});
export default router;
