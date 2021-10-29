<template>
  <div class="home-tag">
    <PassageList :list="list" :loading="loading"></PassageList>
  </div>
</template>

<script>
import {getArticleByCate } from "@/utils/apiConfig";
import PassageList from "@/components/PassageList.vue";

export default {
  name: "HomeCategory",
  components: { PassageList },
  data() {
    return {
      list: null,
      loading: true,
    };
  },
  async created() {
    let id = this.$route.params.id;
    const { data } = await getArticleByCate(id, { pageNum: 1, pageSize: 30 });
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
