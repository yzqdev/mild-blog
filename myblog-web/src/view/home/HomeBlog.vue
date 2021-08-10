<template>
  <div class="home-blog">
    <h1>{{ blog.blogTitle }}</h1>
    <div>
      <span>{{ formatTime(blog.createTime) }}</span
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
      <v-md-preview :text="blog.blogContent"></v-md-preview>
    </article>
  </div>
</template>

<script>
import { getBlogById } from "@/utils/apiConfig";
import dayjs from "dayjs";

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
    console.log(new Date(this.blog.createTime))
    console.log(dayjs(new Date(this.blog.createTime)).format("YYYY-MM-DD"));
  },
  computed: {},
  methods: {
    formatTime(time) {
      console.log();
      let res = dayjs(new Date(time)).format("YYYY-MM-DD");
      console.log(res);
      return res;
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
