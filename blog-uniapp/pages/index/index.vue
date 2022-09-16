<template>
  <view class="container">
    <view class="intro">本项目已包含uni ui组件，无需import和注册，可直接使用。在代码区键入字母u，即可通过代码助手列出所有可用组件。光标置于组件名称处按F1，即可查看组件文档。</view>
    <text class="intro">详见：</text>
    <uni-link :href="href" :text="href"></uni-link>
    <button @click="getData">点我</button>
    <button type="primary" @click="gotoRoute">去登录</button>
    <button type="primary" @click="getLocation">位置</button>
    <view>
			<i style='font-size: 2rem;' class='iconfont icon-apk'></i>
		</view>
    <button type="primary" @click="openFolder">打开文件</button>
  </view>
</template>

<script setup lang="ts">
import { getMihoyo } from "@/apis/static";
import { ref } from "vue";
let href = ref("https://uniapp.dcloud.io/component/README?id=uniui");
function getLocation() {
  uni.getLocation({
    type: "wgs84",

    success: function (res) {
      console.log("当前位置的经度：" + res.longitude);
      console.log("当前位置的纬度：" + res.latitude);
      console.log(res.name);
    },
  });
}
function openFolder() {}
function gotoRoute() {
  uni.navigateTo({
    url: "/pages/Login",
  });
}
async function getData() {
  let res = await getMihoyo();
  console.log(res);
}
</script>

<style>
.container {
  padding: 20px;
  font-size: 14px;
  line-height: 24px;
}
</style>
