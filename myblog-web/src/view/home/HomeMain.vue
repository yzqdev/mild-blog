<template>
  <div class="home-img"><img src="" /></div>
  <div class="home-main">
    <div class="passage-list">
      <article class="article" v-for="(item, index) in passages.list">
        <div class="article-top">
          <span>{{ item.blogCategoryName }}</span
          ><span class="right">{{ item.updateTime }}</span>
        </div>
        <h1 class="article-title">{{ item.blogTitle }}</h1>
        <div>{{ item.blogPreface }}</div>
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
      <el-card style="margin: 20px 0"
        ><template #header>标签</template
        ><el-tag
          class="tag-style"
          v-for="item in hotTag"
          @click="gotoRoute(item)"
          >{{ item.tagName }}</el-tag
        > </el-card
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
let hotTag = ref([]);
function gotoRoute(item) {
  console.log(item);
}
function getData() {
  getIndex().then((res) => {
    console.log(res);
    const { blogPageResult, configurations, hotTags, newBlogs, pageName } =
      res.data;
    newBlog.value = newBlogs;
    passages.value = blogPageResult;
    hotTag.value = hotTags;
  });
}
onBeforeMount(() => {
  getData();
});
</script>

<style lang="less" scoped>
.home-main {
  flex: 1;
  padding: 0 100px;
  display: grid;
  gap: 20px;
  grid-template-columns: 5fr 2fr;
  .passage-list {
    .article {
      padding: 20px;

      border-bottom: 1px solid #e5e5e5;
      .article-top {
        display: flex;
        .right {
          flex: 1;
          text-align: right;
        }
      }
      .article-title {

        cursor: pointer;
        &:hover{
          color:#777;
        }
      }
    }
  }
  .sidebar {
    .tag-style {
      margin-left: 10px;
      margin-bottom: 10px;
      cursor: pointer;
    }
  }
}
</style>
