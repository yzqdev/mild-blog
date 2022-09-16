<template>
  <uni-combox label="所在城市" :candidates="candidates" placeholder="请选择所在城市" v-model="city"></uni-combox>
  <view><text>文件</text></view>
  <uni-file-picker v-model="imageValue" fileMediatype="image" mode="grid" @select="select" @progress="progress" @success="success" @fail="fail" />
</template>

<script setup lang="ts">
import { ref } from "vue";
let candidates = ref(["北京", "南京", "东京", "武汉", "天津", "上海", "海口"]);
let city = ref("上海");
let imageValue = ref("");
// 获取上传状态
function select(e) {
  console.log("选择文件：", e);
}
// 获取上传进度
function progress(e) {
  console.log("上传进度：", e);
}

// 上传成功
function success(e) {
  console.log("上传成功");
}

// 上传失败
function fail(e) {
  console.log("上传失败：", e);
}
</script>

<style></style>
