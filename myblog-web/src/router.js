import { createRouter, createWebHistory } from "vue-router";
import Home from "@/view/Home.vue";
import Main from "@/view/Main.vue";
import HomeMain from "@/view/home/HomeMain.vue";
import AdminPage from "@/view/AdminPage.vue";

const routes = [

  {
    path: "/home",
    name: "home",
    component: Home,
    children: [
      {
        path: "main",
        name: "Homemain",
        component: HomeMain,
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
