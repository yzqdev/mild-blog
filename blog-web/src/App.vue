<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<script setup>
import { useRouter } from 'vue-router'
import zhCn from 'element-plus/dist/locale/zh-cn'
let router = useRouter()
let locale = ref(zhCn)
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  height: 100%;
}
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}
</style>
