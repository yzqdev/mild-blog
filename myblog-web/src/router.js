import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
import Home from "@/view/Home.vue";
import HomeMain from "@/view/home/HomeMain.vue";
import HomeLink from "@/view/home/HomeLink.vue";
import AdminPage from "@/view/AdminPage.vue";
import HomeTag from "@/view/home/HomeTag.vue";
import HomeAbout from "@/view/home/HomeAbout.vue";
import HomeBlog from "@/view/home/HomeBlog.vue";
import AdminLogin from "@/view/admin/AdminLogin.vue";
import AdminWelcome from "@/view/admin/AdminWelcome.vue";
import AdminMain from "@/view/admin/AdminMain.vue";
import ArticleList from "@/view/admin/ArticleList.vue";
import CommentList from "@/view/admin/CommentList.vue";
import CategoryList from "@/view/admin/CategoryList.vue";
import TagList from "@/view/admin/TagList.vue";
import LinkList from "@/view/admin/LinkList.vue";
import SystemInfo from "@/view/admin/SystemInfo.vue";
import ArticleEdit from "@/view/admin/ArticleEdit.vue";
import UserInfo from "@/view/admin/UserInfo.vue";

const routes = [
  {
    path: "/home",
    name: "home",
    component: Home,
    children: [
      {
        path: "main",
        name: "HomeMain",
        component: HomeMain,
      },
      {
        path: "link",
        name: "HomeLink",
        component: HomeLink,
      },
      {
        path: "tag/:id",
        name: "homeTag",
        component: HomeTag,
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
    path: "/admin",
    name: "admin",
    component: AdminPage,
    children: [
      {
        path: "login",
        name: "adminLogin",
        component: AdminLogin,
      },
      {
        path: "home",

        component: AdminMain,
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
            path: "system-info",
            name: "systemInfo",
            component: SystemInfo,
          },  {
            path: "user-info",
            name: "userInfo",
            component: UserInfo,
          },
        ],
      },
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});
export default router;
