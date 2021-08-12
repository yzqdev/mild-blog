<template>
  <el-form :model="user" label-width="100px"
    ><el-form-item label="锁定状态"><span>{{getLockInfo(user.locked)}}</span> </el-form-item>
    <el-form-item label="用户名">
      <el-input v-model="user.loginUserName"></el-input>
    </el-form-item>
    <el-form-item label="昵称">
      <el-input v-model="user.nickName" placeholder="请输入昵称"></el-input
    ></el-form-item>


    <el-button type="primary" @click="submitUserinfo">提交</el-button>
  </el-form>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { getUserInfo } from "@/utils/apiConfig";

const user = ref("");

function getUserData() {
  getUserInfo().then(({ data }) => {
    user.value = data.user;
  });
}
function getLockInfo(item ){
  if (item==0) {
    return '已锁定'
  }else{
    return '未锁定'
  }
}
function submitUserinfo(){

}
onMounted(() => {getUserData()});
</script>

<style lang="less" scoped></style>
