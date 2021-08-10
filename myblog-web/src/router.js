import { createRouter, createWebHistory } from "vue-router";
import Home from "@/view/Home.vue";
import Main from "@/view/Main.vue";
import AdminPage from "@/view/AdminPage.vue";

const routes = [
  { path: "/", redirect: "/home/main" },
  { path: "/admin", redirect: "/admin/main" },
  {
    path: "/home",
    name: "home",
    component: Home,
    children: [
      {
        path: "main",
        name: "main",
        component: Main,
      },
    ],
  },
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
