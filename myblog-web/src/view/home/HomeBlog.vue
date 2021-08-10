<template>
  <div class="home-blog">
    <h1>{{ blog.blogTitle }}</h1>
    <div>
      <span>{{ blog.createTime }}</span
      ><span>共有{{ blog.commentCount }}条评论</span
      ><span>{{ blog.blogViews }}浏览</span>
    </div>
    <div class="blog-tags">
      <el-tag
        style="margin-left: 10px; cursor: pointer"
        v-for="item in tags"
        @click="gotoTag(item)"
        >{{ item.tagName }}</el-tag
      >
    </div>
    <article class="blog-content">
      <div v-html="toHtml(blog.blogContent)"></div>
    </article>
  </div>
</template>

<script>
import { getBlogById } from "@/utils/apiConfig";
import marked from "marked";
export default {
  name: "HomeBlog",
  data() {
    return {
      blog: {},
      tags: [],
    };
  },
  async created() {
    let id = this.$route.params.id;
    const { data } = await getBlogById(id);

    this.blog = data.blogDetailVO;
    this.tags = data.tagList;
  },
  methods: {
    toHtml(item) {
      return marked(item);
    },
    gotoTag(item) {
      this.$router.push("/home/tag/" + item.tagId);
    },
  },
};
</script>

<style lang="less" scoped>
.home-blog {
  flex: 1;
  padding: 20px 100px;
}
</style>
