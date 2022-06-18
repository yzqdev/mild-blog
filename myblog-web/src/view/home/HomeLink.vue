<template>
  <div class="home-link">
    <h2>友情链接</h2>
    <template v-if="favoriteLinks.length > 0">
      <article v-for="item in favoriteLinks">
        <el-link :href="item.linkUrl" target="_blank">{{ item.linkName }}</el-link>
      </article>
    </template>
    <span v-else>暂无</span>
    <h2>推荐网站</h2>
    <template v-if="recommendLinks.length > 0"
      ><article v-for="item in recommendLinks">
        <el-link :href="item.linkUrl"  target="_blank">{{ item.linkName }}</el-link>
      </article></template
    ><span v-else>暂无</span>
    <h2>个人网站</h2>
    <template v-if="personalLinks.length > 0">
      <article v-for="item in personalLinks">
        <el-link :href="item.linkUrl" target="_blank">{{ item.linkName }}</el-link>
      </article> </template
    ><span v-else>暂无</span>
  </div>
</template>

<script setup>
import { getIndex, getLinks } from "@/utils/homeApi";
import { onBeforeMount, reactive, toRefs } from "vue";

let links = $ref();
let favoriteLinks = $ref([]);
let recommendLinks = $ref([]);
let personalLinks = $ref([]);
onBeforeMount(async () => {
  let { data } = await getLinks();
  favoriteLinks = data.favoriteLinks;
  recommendLinks = data.recommendLinks;
  personalLinks = data.personalLinks;
});
</script>

<style lang="scss" scoped>
.home-link {
  flex: 1;
  padding: 20px 100px;
}
</style>
