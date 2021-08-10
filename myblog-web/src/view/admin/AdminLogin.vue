<template>
  <div class="admin-login">
    <div class="admin-container">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="登录" name="first">
          <el-form ref="form" :model="loginForm" label-width="80px"
            ><el-form-item label="用户名">
              <el-input v-model="loginForm.username"></el-input> </el-form-item
            ><el-form-item label="密码">
              <el-input
                type="password"
                v-model="loginForm.password"
              ></el-input> </el-form-item
          ></el-form>
          <el-button type="primary" style="width: 100%" @click="login"
            >登录</el-button
          ></el-tab-pane
        >
        <el-tab-pane label="注册" name="reg"
          ><el-form ref="form" :model="regForm" label-width="80px"
            ><el-form-item label="用户名">
              <el-input v-model="regForm.username"></el-input> </el-form-item
            ><el-form-item label="密码">
              <el-input
                type="password"
                v-model="regForm.password"
              ></el-input> </el-form-item
            ><el-form-item label="确认密码">
              <el-input
                type="password"
                v-model="regForm.password2"
              ></el-input> </el-form-item
          ></el-form>
          <el-button type="primary" style="width: 100%" @click="reg"
            >注册</el-button
          ></el-tab-pane
        >
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {loginApi, regApi} from "@/utils/apiConfig";

export default {
  name: "AdminLogin",
  data() {
    return {
      activeName: "first",
      loginForm: { username: "yzq", password: "123456" },
      regForm: { username: "yzq", password: "123456", password2: "123456" },
    };
  },
  methods: {
    login() {
      loginApi(this.loginForm.username, this.loginForm.password).then(({ data }) => {
        console.log(data)
        if (data ) {
          this.$message.success("成功")
          this.$router.push({name:'adminWelcome'})
        }
      });
    },
    reg() {
      regApi(this.regForm.username,this.regForm.password).then(({data }) => {
        if (data ) {
          this.$message.success("成功")
        }
      })
    },
  },
};
</script>

<style lang="less" scoped>
.admin-login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: #132ea0 url("@/assets/img/login-bg.png") no-repeat 50%;
  .admin-container {
    border-radius: 8px;
    padding: 20px 40px;
    background-color: white;
    ::v-deep .el-tabs__nav-scroll {
      display: flex;
      justify-content: center;
    }
  }
}
</style>
