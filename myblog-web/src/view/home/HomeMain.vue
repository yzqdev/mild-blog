<template>
  <div class="home-img"><img src="" /></div>
  <div class="home-main">
    <PassageList :list="passages" :loading="loading"></PassageList>
    <div class="sidebar">
      <el-card
        ><template #header>搜索文章<strong>(根据标题和内容搜索)</strong></template>
        <el-input
          type="text"
          placeholder="搜索"
          v-model="searchText"
          @keyup.enter="search"
        >
          <template #append>
            <el-button
              :icon="Search"
              @click="search"
            ></el-button> </template
        ></el-input>
      </el-card>
      <el-card style="margin: 20px 0"
        ><template #header
          ><span class="link-title" @click="gotoTags">标签</span> </template
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
            <el-link :href="`/home/blog/${item.blogId}`">{{
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
import {
  Search,
  Edit,
  Check,
  Message,
  Star,
  Delete,
} from '@element-plus/icons-vue'
const msg = "aaaaaasdda";
import {onBeforeMount, onMounted, reactive, ref} from "vue";
import { useRoute, useRouter } from "vue-router";
import PassageList from "@/components/PassageList.vue";
let loading = ref(true);
const color = ref("red");
let passages = ref([]);
let newBlog = ref([]);
let hotTag = ref([]);
let state=reactive({name:''})
const router = useRouter();
const route = useRoute();
function gotoRoute(item) {
  router.push("/home/tag/" + item.tagId);
}
function search() {
  router.push("/home/search?text=" + searchText.value);
}
function gotoTags() {
  router.push("/home/tag");
}
function gotoBlog(item) {
  router.push("/home/blog/" + item.blogId);
}
function getData() {
  getIndex().then((res) => {
    console.log(res);
    const { blogPageResult, configurations, hotTags, newBlogs, pageName } =
      res.data;
    newBlog.value = newBlogs;
    passages.value = blogPageResult;

    hotTag.value = hotTags;
    loading.value = false;
  });
}
onBeforeMount(() => {
  getData();
});
</script>

<style lang="scss" scoped>
.link-title {
  cursor: pointer;
}
.home-main {
  flex: 1;
  padding: 0 100px;
  display: grid;
  gap: 20px;
  grid-template-columns: 5fr 2fr;

  .sidebar {
    .tag-style {
      margin-left: 10px;
      margin-bottom: 10px;
      cursor: pointer;
    }
  }
}
</style>
