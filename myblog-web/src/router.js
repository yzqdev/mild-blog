import { createRouter, createWebHistory } from "vue-router";
import Home from "@/view/Home.vue";
import Main from "@/view/Main.vue";
import HomeMain from "@/view/home/HomeMain.vue";
import HomeLink from "@/view/home/HomeLink.vue";
import AdminPage from "@/view/AdminPage.vue";
import HomeTag from "@/view/home/HomeTag.vue";
import HomeAbout from "@/view/home/HomeAbout.vue";

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
        name: "HomeTag",
        component: HomeTag,
      },{
        path: "about",
        name: "HomeAbout",
        component: HomeAbout,
      },
    ],
  },
  { path: "/", redirect: "/home/main" },
  { path: "/admin", redirect: "/admin/main" },
  {
    path: "/admin",
    name: "admin",
    component: AdminPage,
    children: [
      {
        path: "main",
        name: "main",
        component: Main,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;
