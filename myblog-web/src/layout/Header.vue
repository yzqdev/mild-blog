<template>
  <header class="header"><el-link href="/home" target="_blank" style="margin-left: 0.5rem;" v-html="notice"></el-link>
    <el-dropdown class="el-dropdown-link">
       <span class='el-dropdown-link '>
                <el-avatar :size='30'
                           :src='user.avatar'/>
                <el-button text class='ml-2'>{{ user.loginUserName}}<el-icon> <arrow-down/></el-icon></el-button>
                
            </span>


      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="gotoUserInfo"
          >账户信息
          </el-dropdown-item
          >
          <el-dropdown-item>系统信息</el-dropdown-item>
          <el-dropdown-item divided @click="logout"
          >退出登录
          </el-dropdown-item
          >
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </header>
</template>

<script setup>
import { ArrowDown } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { getUserInfo } from "@/utils/apiConfig";
import { onBeforeMount } from "vue";

let router = useRouter();
let userInfo = $ref({ name: "keqing" });
let notice = $ref("七月飞雪");
let user = $ref({});

function gotoRoute(name) {
  router.push({
    name: name
  });
}

function gotoUserInfo() {
  router.push({ name: "userInfo" });
}

function logout() {
  localStorage.clear();
  router.push({ name: "adminLogin" });
}

function getUser() {
  getUserInfo().then((res) => {
    console.log(`%c用户信息`, `color:red;font-size:16px;background:transparent`);
    console.log(res.data);
    if (res.success) {
      user = res.data.user;
    } else {
      router.push({ name: "adminLogin" });
    }
  });
}


onBeforeMount(() => {
  getUser();
});
</script>

<style lang="scss" scoped>
.header {
  height: 3rem;
  padding: 0.5rem 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .el-dropdown-link {
    display: flex;
    align-items: center;
    padding: 0 2rem;
  }
}
</style>
