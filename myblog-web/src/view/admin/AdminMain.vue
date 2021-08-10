<template>
  <div class="admin-main">
    <aside class="sidebar">
      <el-menu :default-active="actMenu" router class="el-menu-vertical-demo">
        <el-submenu index="2">
          <template #title><i class="el-icon-menu"></i>文章管理</template>
          <el-menu-item index="/admin/home/article-list">
            文章列表
          </el-menu-item>
          <el-menu-item index="/admin/home/comment-list">
            评论列表
          </el-menu-item>
          <el-menu-item index="/admin/home/tag-list"> 标签列表 </el-menu-item>
          <el-menu-item index="/admin/home/category-list">
            分类列表
          </el-menu-item>
        </el-submenu>

        <el-submenu index="4">
          <template #title> <i class="el-icon-setting"></i>系统管理</template>
          <el-menu-item index="/admin/home/system-info">
            系统信息
          </el-menu-item>
          <el-menu-item index="/admin/home/link-list"> 链接列表 </el-menu-item>
        </el-submenu>
      </el-menu>
    </aside>
    <div class="right-content">
      <header >
        adsdf
      </header>
      <router-view />
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "AdminMain",

  watch: {
    $route: {
      handler: function (newVal) {
        this.actMenu = newVal.path;
      },
      immediate: true,
    },
  },
  data() {
    return { actMenu: "" };
  },
});
</script>

<style lang="less" scoped>
.admin-main {
  height: 100vh;
  display: flex;
  .sidebar {
    width: 300px;
  }
  .right-content {
    padding: 20px;
    flex: 1;
  }
}
</style>
