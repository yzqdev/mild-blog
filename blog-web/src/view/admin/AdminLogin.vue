<template>
  <div class="admin-login">
    <div class="admin-container">
      <h3 class="text-center">{{ title }}</h3>
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="登录" name="first">
          <el-form ref="loginFormRef" :model="loginForm" label-width="80px" label-position="top" :rules="loginRules">
            <el-form-item prop="username" label="用户名">
              <el-input size="large" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
              <el-input @keydown.enter="login" placeholder="请输入密码" type="password" size="large" v-model="loginForm.password"></el-input>
            </el-form-item>
          </el-form>
          <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
        </el-tab-pane>
        <el-tab-pane label="注册" name="reg">
          <el-form ref="regFormRef" :rules="regRule" label-position="top" :model="regForm" label-width="80px">
            <el-form-item prop="username" label="用户名">
              <el-input size="large" placeholder="请输入用户名" v-model="regForm.username"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
              <el-input placeholder="请输入密码" type="password" size="large" v-model="regForm.password"></el-input>
            </el-form-item>
            <el-form-item prop="password2" label="确认密码">
              <el-input placeholder="请输入密码" size="large" type="password" v-model="regForm.password2"></el-input>
            </el-form-item>
          </el-form>
          <el-button type="primary" size="large" style="width: 100%" @click="reg">注册</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { loginApi, regApi } from '@/utils/apiConfig'
import { defineComponent, onBeforeMount, reactive, ref, toRefs, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

import { useUserStore } from '@/store/user'
let store = useUserStore()
let activeName = $ref('first')
let title = $ref('用户登录')
let regForm = $ref({ username: '', password: '', password2: '' })
let loginRules = $ref({
  username: [{ required: true, message: '请输入用户名' }],
  password: [{ required: true, message: '请输入密码' }],
})
let regRule = $ref({
  username: [{ required: true, message: '请输入用户名' }],
  password: [{ required: true, message: '请输入密码' }],
  password2: [{ required: true, message: '请输入确认密码' }],
})
let loginForm = $ref({ username: '', password: '' })
let loginFormRef = ref(null)
let regFormRef = ref(null)

async function login() {
  loginFormRef.value.validate(async (valid) => {
    if (valid) {
      let res = await loginApi(loginForm)
      console.log(res)
      console.log(`%c看到雷锋`, `color:red;font-size:16px;background:transparent`)
      if (res.success) {
        store.setUserToken(res.data)
        localStorage.token = res.data
        ElMessage({ message: res.message, type: 'success' })
        router.push({ name: 'adminWelcome' })
      } else {
        ElMessage({ message: '登录失败', type: 'error' })
      }
    }
  })
}

function reg() {
  regFormRef.value.validate((valid) => {
    if (valid) {
      regApi(regForm.username, regForm.password).then(({ data }) => {
        if (data) {
          ElMessage({
            message: '成功',
            type: 'success',
          })
        }
      })
    }
  })
}

watch(
  () => activeName,
  (val, preVal) => {
    if (val == 'first') {
      title = '用户登录'
    } else {
      title = '用户注册'
    }
  }
)
onBeforeMount(() => {
  if (localStorage.token) {
    router.push({ name: 'adminWelcome' })
  }
})
</script>

<style lang="scss" scoped>
.admin-login {
  transition: all 0.4s;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: #132ea0 url('@/assets/img/login-bg.png') no-repeat 50%;

  .admin-container {
    background: #fff;

    border-radius: 5px;

    margin: -160px 0 0 -160px;
    width: 320px;
    padding: 22px 28px 28px 28px;
    border: 1px solid #eaeaea;
    //box-shadow: 0 0 25px #cac6c6;
    :deep .el-tabs__nav-scroll {
      display: flex;
      justify-content: center;
    }

    .text-center {
      text-align: center;
    }
  }
}
</style>
