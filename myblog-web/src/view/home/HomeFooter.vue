<template>
  <footer class="footer blog-footer">
    <div class="blog-text-center">
      <article>&copy;{{ configs.sysAuthor }}singal blog.</article>
      <span class="ft-warn">&heartsuit;</span>
      &nbsp;{{ configs.sysCopyRight }}
      <a>浙ICP备 xxxxxx-x号</a>
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

let state = reactive({
  configs: {
    sysAuthor: '',
    sysAuthorImg: '',
    sysCopyRight: '',
    sysEmail: '',
    sysUpdateTime: '',
    sysUrl: '',
    sysVersion: '1.1.0',
    websiteName: '',
  },
})
let { configs } = toRefs(state)
onBeforeMount(() => {
  getConfigs().then((res) => {
    state.configs = res.data
  })
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
</style>
