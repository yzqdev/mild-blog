<template>
  <div class="home-link">
    <h2>友情链接</h2>
    <article v-for="item in favoriteLinks">
      <el-link :href="item.linkUrl">{{ item.linkName }}</el-link>
    </article>
    <h2>推荐网站</h2>
    <article v-for="item in recommendLinks">
      <el-link :href="item.linkUrl">{{ item.linkName }}</el-link>
    </article>
  </div>
</template>

<script setup>
import { getIndex, getLinks } from "@/utils/apiConfig";
import { onBeforeMount, reactive, toRefs } from "vue";

let state = reactive({
  links: null,
  favoriteLinks: [],
  recommendLinks: [],
});
let { links, favoriteLinks, recommendLinks } = toRefs(state);
onBeforeMount(async () => {
  let { data } = await getLinks();
  state.favoriteLinks = data.favoriteLinks;
  state.recommendLinks = data.recommendLinks;
});
</script>

<style lang="scss" scoped>
.home-link {
  flex: 1;
  padding: 20px 100px;
}
</style>
