<template>
  <div class="home-tag">
    <PassageList v-if="list&&list.length>0" :list="list" :loading="loading"></PassageList>
    <el-empty v-else description="暂无内容"></el-empty>
  </div>
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
      loading: true,
    };
  },
  async created() {
    let id = this.$route.params.id;
    const { data } = await getArticleByTag(id, { pageNum: 1, pageSize: 30 });
    this.list = data;
    this.loading = false;
  },
};
</script>

<style lang="less" scoped>
.home-tag {
  flex: 1;
  padding: 20px 100px;
}
</style>
