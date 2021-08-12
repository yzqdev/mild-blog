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

    <div class="blog-comment" v-if="blog.enableComment">
      <article>添加评论</article>
      <el-form :model="comment" label-width="100px">
        <el-form-item>
          <el-input v-model="comment.commentator">
            <template #prepend>昵称</template></el-input
          >
        </el-form-item>
        <el-form-item>
          <el-input type="email" v-model="comment.email">
            <template #prepend>邮箱 </template></el-input
          >
        </el-form-item>
        <el-form-item>
          <el-input v-model="comment.website">
            <template #prepend>网站地址 </template></el-input
          >
        </el-form-item>
        评论内容
        <el-form-item>
          <el-input
            v-model="comment.commentBody"
            :autosize="{ minRows: 2, maxRows: 6 }"
            placeholder="既然来了,不如说两句?"
            type="textarea"
          ></el-input>
        </el-form-item>
        <el-button type="primary" @click="submitComment">提交</el-button>
      </el-form>
    </div>
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
      comment: {},
    };
  },
  async created() {
    let id = this.$route.params.id;
    const { data } = await getBlogById(id);

    this.blog = data.blogDetailVO;
    this.tags = data.tagList;
    console.log(new Date(this.blog.createTime));
    console.log(dayjs(new Date(this.blog.createTime)).format("YYYY-MM-DD"));
  },
  computed: {},
  methods: {
    submitComment(){

    },
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
  .blog-comment {
  }
}
</style>
