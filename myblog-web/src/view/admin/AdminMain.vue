<template>
  <div class="admin-main">
    <aside class="sidebar">
      <el-menu :default-active="actMenu" router class="el-menu-vertical-demo">
        <el-menu-item index="/admin/home/welcome">
          <template #title><i class="el-icon-menu"></i>系统信息</template>
        </el-menu-item>
        <el-submenu index="2">
          <template #title><i class="el-icon-menu"></i>文章管理</template>
          <el-menu-item index="/admin/home/article-edit">
            文章编辑
          </el-menu-item>  <el-menu-item index="/admin/home/article-list">
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
      <header class="admin-header">
        <el-link href="/home/main">七月飞雪</el-link>
        <article class="logout">
          <el-dropdown>
            <span class="el-dropdown-link">
              {{ `用户名` }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>账户信息</el-dropdown-item>
                <el-dropdown-item>系统信息</el-dropdown-item>
                <el-dropdown-item divided @click="logout"
                  >退出登录</el-dropdown-item
                >
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </article>
      </header>
      <div class="admin-content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue";
import { getUserInfo } from "@/utils/apiConfig";
import axios from "axios";

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
  created() {
    // this.getToken()
    this.getUser();
  },
  methods: {
    getToken() {
      console.log(
        `%cxinhaijioao`,
        `color:red;font-size:16px;background:transparent`
      );
      axios
        .get("http://localhost:8060/users", { headers: { token: "123456787" } })
        .then((res) => {
          console.log(res);
        });
    },
    logout() {
      localStorage.clear();
      this.$router.push("/admin");
    },
    getUser() {

      getUserInfo().then(({ data }) => {
        console.log(data);
      });
    },
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
    flex: 1;
    .admin-header {
      padding: 20px;
      background-color: #eee;
      display: flex;
      align-items: center;
      span {
        cursor: pointer;
      }
      .logout {
        flex: 1;
        text-align: right;
      }
    }
    .admin-content {
      padding: 20px;
    }
  }
}
</style>
