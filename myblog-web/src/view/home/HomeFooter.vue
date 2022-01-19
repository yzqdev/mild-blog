<template>
  <footer class="footer blog-footer">
    <div class="blog-text-center">

      <article> &copy;{{ configs.sysAuthor }}singal blog.</article>
      <span class="ft-warn">&heartsuit;</span>&nbsp;{{ configs.sysCopyRight }}
      <a>浙ICP备 xxxxxx-x号</a><br/>
      version: {{ configs.sysVersion }} Powered by
      <a
          href="#"
          target="_blank"

      >2050 genshin</a
      ><span style="margin-left: 20px;">更新时间:{{ configs.sysUpdateTime }}</span>
    </div>
  </footer>
</template>

<script setup>
import {getConfigs} from "@/utils/apiConfig";
import {onBeforeMount, reactive, toRefs} from "vue";


let state = reactive({
  configs: {
    sysAuthor: "南街",
    sysAuthorImg: "http://localhost/authorImg/20190906_18162846.jpg",
    sysCopyRight: "xuebingsi(xuebingsi) 访问官网",
    sysEmail: "1320291471@qq.com",
    sysUpdateTime: "2019-08-24 20:33:23",
    sysUrl: "localhost:80",
    sysVersion: "1.1.0",
    websiteName: "v1 blog",
  }
})
let {configs} = toRefs(state)
onBeforeMount(() => {
  getConfigs().then((res) => {
    state.configs = res.data;
  });
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
