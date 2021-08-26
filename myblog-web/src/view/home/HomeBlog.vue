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
      <el-form :model="comment"  label-position="top" :rules="commentRule">
        <el-form-item prop="commentator">
          <el-input v-model="comment.commentator">
            <template #prepend>昵称</template></el-input
          >
        </el-form-item>
        <el-form-item prop="email">
          <el-input type="email" v-model="comment.email">
            <template #prepend>邮箱 </template></el-input
          >
        </el-form-item>
        <el-form-item prop="website">
          <el-input v-model="comment.website">
            <template #prepend>网站地址 </template></el-input
          >
        </el-form-item>
        评论内容
        <el-form-item prop="commentBody">
          <div :class="active ? `active` : ``">
            <v-md-editor
              v-model="comment.commentBody"
              height="400px"
            ></v-md-editor>
          </div>
        </el-form-item>
        <el-button type="primary" @click="commentYou">提交</el-button>
      </el-form>
      <article>全部留言</article>
      <article>
        <el-card style="margin-top: 20px" v-for="item in commentList">
          <template #header>
            <div style="display: flex">
              <span>{{ item.commentator }}</span>
              <span style="flex: 1; text-align: right">{{
                $dayjs(item.commentCreateTime) .format("YYYY-MM-DD HH:mm:ss")
              }}</span>
            </div>
          </template>
          <v-md-preview :text="item.commentBody"></v-md-preview>
          <div v-if="item.replyBody" style="text-indent: 40px">
            回复:
            {{ item.replyBody }}
          </div>
        </el-card>
      </article>
    </div>
  </div>
</template>

<script>
import { getBlogById, listComments, submitComment } from "@/utils/apiConfig";
import dayjs from "dayjs";

export default {
  name: "HomeBlog",
  data() {
    return {
      blog: {},
      tags: [],
      comment: {},
      commentList: [],
      active: false,
      commentRule: {
        commentator: [
          { required: true, message: "请输入网名", trigger: "blur" },
          { min: 1, max: 20, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        commentBody: [{ required: true, message: "请输入评论内容" }],
      },
    };
  },
  async created() {
    let id = this.$route.params.id;
    const { data } = await getBlogById(id);

    this.blog = data.blogDetailVO;
    this.tags = data.tagList;

    this.getComments();
  },
  computed: {},
  methods: {
    commentYou() {
      if (!this.comment.commentBody) {
        this.active = true;
        this.$message.error("请输入评论内容!");
        return;
      }
      this.comment.blogId = this.blog.blogId;
      submitComment(this.comment).then((res) => {
        console.log(res);
        if (res) {
          this.getComments();
          this.$message.success("成功");
        }
      });
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
    getComments() {
      listComments({ page: 1, limit: 30, blogId: this.blog.blogId }).then(
        (res) => {
          this.commentList = res.data;
        }
      );
    },
  },
};
</script>

<style lang="less" scoped>
.home-blog {
  flex: 1;

  padding: 20px 100px;
  .active {
    border: 1px solid red;
  }
  .blog-comment {
  }
}
</style>
