<template>
  <div class="home-link">
    <h2>友情链接</h2>
    <article v-for="item in favoriteLinks">
      <el-link :href="item.linkId">{{ item.linkName }}</el-link>
    </article>
    <h2>推荐网站</h2>
    <article v-for="item in recommendLinks">
      <el-link :href="item.linkId">{{ item.linkName }}</el-link>
    </article>
  </div>
</template>

<script>
import { getIndex, getLinks } from "@/utils/apiConfig";

export default {
  name: "HomeLink",
  data() {
    return {
      links: null,
      favoriteLinks: [],recommendLinks:[]
    };
  },
  async created() {
    let { data } = await getLinks();
    this.favoriteLinks = data.favoriteLinks;
    this.recommendLinks=data.recommendLinks
  },
};
</script>

<style lang="less" scoped>
.home-link {
  flex: 1;
  padding: 20px 100px;
}
</style>
