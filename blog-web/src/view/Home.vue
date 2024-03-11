<template>
  <header class="home-header">
    <div style="display: flex; align-items: center">
      <el-link href="/home/main">{{ configs.websiteName }}</el-link>
      <img src="https://i0.hdslb.com/bfs/album/0a75a254e639e7ce606099e1d6c2b75582dc4e8a.jpg" style="height: 20px" />
    </div>
    <div class="nav">
      <router-link class="nav-link" v-for="item in navs" :to="item.link">{{ item.text }}</router-link>
    </div>
  </header>
  <router-view />
  <footer class="footer blog-footer">
    <div class="blog-text-center">
      <article>&copy;{{ configs.sysAuthor }}个人博客.</article>
      <span class="ft-warn">&heartsuit;</span>
      &nbsp;{{ configs.sysCopyRight }}
      <a>{{ configs.filing }}</a>
      <br />
      version: {{ configs.sysVersion }} Powered by
      <a href="#" target="_blank">2050 genshin</a>
      <span style="margin-left: 20px">更新时间:{{ configs.sysUpdateTime }}</span>
    </div>
  </footer>
</template>

<script setup>
import { getConfigs } from '@/utils/homeApi'
import { onBeforeMount, reactive, toRefs } from 'vue'

import { useConfigStore } from '@/store/sysConfig'
let configStore = useConfigStore()
let activeRoute = $ref('')
let navs = $ref([
  { text: '主页', id: 1, link: '/home/main' },
  { text: '标签', id: 2, link: '/home/tags' },
  { text: '分类', id: 3, link: '/home/categories' },
  { text: '时间线', id: 6, link: '/home/timeline' },
  { text: '友情链接', id: 4, link: '/home/link' },
  { text: '关于', id: 5, link: '/home/about' },
])
let configs = $ref({
  sysAuthor: '',
  sysAuthorImg: '',
  sysCopyRight: '',
  sysEmail: '',
  sysUpdateTime: '',
  sysUrl: '',
  sysVersion: '',
  websiteName: '',
  filing: '',
})
async function getData() {
  let { data } = await getConfigs()
  configStore.setSysConfig(data)
  configs = data
}

onBeforeMount(async () => {
  await getData()
})
</script>

<style lang="scss" scoped>
.blog-footer {
  padding: 2rem 0 2rem 0;
  margin-top: 5rem;
  background-color: #273547;
  color: #fff !important;

  a {
    color: #fff;

    span {
      color: #fff;
    }
  }

  .blog-text-center {
    text-align: center;
    font-size: 14px;
  }
}

.home-header {
  background-color: #f5f5f5;
  padding: 0 20px;
  display: flex;
  align-items: center;
  height: 60px;

  .nav {
    flex: 1;
    text-align: right;
    height: 100%;
    line-height: 60px;
    background: transparent;
    padding: 0;
    margin: 0;

    .nav-link {
      margin: 0 20px;
      cursor: pointer;
      color: #555;
      outline: 0;
      text-decoration: none;
      overflow-wrap: break-word;
      border-radius: 2px;
      padding: 5px 10px;
      transition: all 0.3s ease-in-out;

      &:hover {
        background-color: #ddd;
        color: #222;
      }
    }

    .nav-active,
    .router-link-active {
      background-color: #ddd;
      color: #222;
    }
  }
}
</style>
