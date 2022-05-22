<template>
  <el-aside width="15rem" :native-scrollbar="false" bordered class="sidebar">
    <router-link to="/" custom>
      <span class="logo">
        <img v-if="false" class="logo-icon" src="../assets/logo.png" />
        <span>七月飞雪</span>
      </span>
    </router-link>
    <el-menu
      :default-active="defaultActive"
      class="side-menu"
      router
      background-color="#001529"
      text-color="#fff"
      @open="handleOpen"
      :collapse-transition="false"
      @close="handleClose"
      ><el-menu-item index="/admin/welcome">
        <template #title>
          <el-icon><home-filled /></el-icon>首页</template
        >
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><Menu /></el-icon> 文章管理</template
        >
        <el-menu-item index="/admin/article-edit"> 文章编辑 </el-menu-item>
        <el-menu-item index="/admin/article-list"> 文章列表 </el-menu-item>
        <el-menu-item index="/admin/comment-list"> 评论列表 </el-menu-item>
        <el-menu-item index="/admin/tag-list"> 标签列表</el-menu-item>
        <el-menu-item index="/admin/category-list"> 分类列表 </el-menu-item>
        <el-menu-item index="/admin/img-list"> 图片列表 </el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="4">
        <template #title>
          <el-icon><setting /></el-icon> 系统管理</template
        >
        <el-menu-item index="/admin/system-info"> 系统信息 </el-menu-item>
        <el-menu-item index="/admin/users"> 用户列表 </el-menu-item>
        <el-menu-item index="/admin/link-list"> 链接列表</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-aside>
</template>

<script lang="ts" setup>
import { h, ref, computed, watchEffect, Component, watch } from "vue";
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
  HomeFilled,
  Menu,
} from "@element-plus/icons-vue";

import { useStore } from "vuex";
import { useRoute, RouterLink, useRouter } from "vue-router";

let defaultActive = $ref("home");
const router = useRouter();
const route = useRoute();
let currentKey = $ref("");
let expandedKeys = $ref([]);
function gotoRoute(params: string) {
  console.log("cur route", route.name);
  console.log(params);
  router.push({ name: params });
}
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
watch(
  route,
  (val, oldVal) => {
    defaultActive = val.name;
    console.log("move to ", val.name);
  },
  { immediate: true }
);
</script>

<style lang="scss" scoped>
.sidebar {
  width: 15rem;
  background-color: #001529;
  height: 100%;
  .logo {
    position: sticky;
    top: 0;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 12px 20px;
    text-align: center;
    font-size: 1.8em;
    font-weight: 600;
    line-height: 1;
    text-decoration: none;
    background: #001529;
    color: white;
    .logo-icon {
      width: 2rem;
      height: 2rem;
    }
  }
  :deep(.el-menu) {
    .el-menu {
      background-color: #000c17;
    }
    .el-menu-item.is-active {
      background: var(--el-color-primary);
      color: white;
    }
  }
}

.side-menu {
  height: 90vh;
}
</style>
