<template>
  <div class="home-tag"><PassageList :list="list"></PassageList></div>
</template>

<script>
import { getArticleByTag } from "@/utils/apiConfig";
import PassageList from "@/components/PassageList.vue";

export default {
  name: "HomeTag",
  components: { PassageList },
  data() {
    return {
      list: null,
    };
  },
  async created() {
    let id = this.$route.params.id;
    const { data } = await getArticleByTag(id);
    this.list = data.blogPageResult.list;
  },
};
</script>

<style lang="less" scoped>
.home-tag {
  flex: 1;
  padding: 20px 100px;
}
</style>
