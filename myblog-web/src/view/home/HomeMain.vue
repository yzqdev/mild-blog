<template>
  <div class="home-img"><img src="" /></div>
  <div class="home-main">
    <div class="passage-list">
      <article v-for="(item, index) in passages.list">
        {{ item.blogTitle }}
      </article>
    </div>
    <div class="sidebar">
      <el-card
        ><template #header>搜索文章</template>
        <el-input
          type="text"
          placeholder="搜索"
          v-model="searchText"
          @click="search"
        >
          <template #append>
            <el-button
              icon="el-icon-search"
              @click="search"
            ></el-button> </template
        ></el-input>
      </el-card>
      <el-card
        ><template #header>标签</template><el-tag>标签一</el-tag>
        <el-tag type="success">标签二</el-tag>
        <el-tag type="info">标签三</el-tag>
        <el-tag type="warning">标签四</el-tag>
        <el-tag type="danger">标签五</el-tag> </el-card
      ><el-card
        ><template #header>最新发布</template>
        <section>
          <article v-for="item in newBlog">
            <el-link :href="`blog/${item.blogId}`">{{
              item.blogTitle
            }}</el-link>
          </article>
        </section>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { getIndex } from "@/utils/apiConfig";
let searchText = ref("");
function search() {}
const msg = "aaaaaasdda";
import { onBeforeMount, onMounted, reactive, ref } from "vue";
const color = ref("red");
let passages = ref([]);
let newBlog = ref([]);
function getData() {
  getIndex().then((res) => {
    console.log(res);
    const { blogPageResult, configurations, hotTags, newBlogs, pageName } =
      res.data;
    newBlog.value = newBlogs;
    passages.value = blogPageResult;
  });
}
onBeforeMount(() => {
  getData();
});
</script>

<style lang="less" scoped>
.home-main {
  flex:1;
  padding: 0 100px;
  display: grid;
  grid-template-columns: 5fr 2fr;
  .passage-list {
  }
  .sidebar {
  }
}
</style>
