<template>
  <div class="home-blog" v-loading="loading">
    <h1>{{ blog.blogTitle }}</h1>
    <div class="home-blog-head">
      <span>发表于{{ formatTime(blog.createTime) }}</span
      ><span>共有{{ blog.commentCount }}条评论</span
    ><span>{{ blog.blogViews }}浏览</span>
    </div>
    <div class="blog-tags">
      <el-tag
          style="margin-left: 10px; cursor: pointer"
          v-for="item in tags"
          @click="gotoTag(item)"
      >{{ item.tagName }}
      </el-tag
      >
    </div>
    <article class="blog-content">
      <v-md-preview :text="blog.blogContent"></v-md-preview>
    </article>

    <div class="blog-comment" v-if="blog.enableComment">
      <article class="blog-title">添加评论</article>
      <el-form ref="commentForm" :model="comment" label-position="top" :rules="commentRule">
        <el-form-item prop="commentator">
          <el-input :input-style="commentInput" v-model="comment.commentator">
            <template #prepend>昵称</template>
          </el-input
          >
        </el-form-item>
        <el-form-item prop="email">
          <el-input
              :input-style="commentInput"
              type="email"
              v-model="comment.email"
          >
            <template #prepend>邮箱</template>
          </el-input
          >
        </el-form-item>
        <el-form-item prop="website">
          <el-input :input-style="commentInput" v-model="comment.website">
            <template #prepend>网站地址</template>
          </el-input
          >
        </el-form-item>
        <article class="blog-title">评论内容</article>
        <el-form-item prop="commentBody">
          <div :class="active ? `active` : ``" style="width: 100%">
            <v-md-editor
                v-model="comment.commentBody"
                left-toolbar="undo redo | tip todo-list emoji h h1 h2 h3 h4 h5 h6 bold italic strikethrough quote ul ol table hr link image imageLink uploadImage code save "
                height="400px"
            ></v-md-editor>
          </div>
        </el-form-item>
        <el-button type="primary" @click="commentYou">提交</el-button>
      </el-form>
      <article class="blog-title">全部留言</article>
      <article v-if="commentList && commentList.length > 0">
        <el-card style="margin-top: 20px" v-for="item in commentList">
          <template #header>
            <div style="display: flex">
              <span>{{ item.commentator }}</span>
              <span style="flex: 1; text-align: right">{{
                  formatTime(item.commentCreateTime)
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
      <span v-else>暂无内容</span>
    </div>
  </div>
</template>

<script setup>
import {getBlogById, listComments, submitComment} from "@/utils/apiConfig";
import dayjs from "dayjs";
import {onMounted, reactive, ref, toRefs, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const router = useRouter()
const route = useRoute()

let state = reactive({
  commentRule: {
    commentator: [
      {required: true, message: "请输入网名", trigger: "blur"},
      {min: 1, max: 20, message: "长度在 3 到 5 个字符", trigger: "blur"},
    ],
    commentBody: [{required: true, message: "请输入评论内容"}],
  }, commentInput: {width: "50%"},
  blog: {},
  tags: {},
  comment: {commentBody: ''},
  commentList: [],

})
let active = ref(false)
let loading = ref(true)
let {commentRule, blog, tags, comment, commentList, commentInput} = toRefs(state)
let commentForm = ref(null)
onMounted(async () => {
  let id = route.params.id;
  const {data} = await getBlogById(id);

  blog.value = data.blogDetailVO;
  tags.value = data.tagList;

  getComments();
})

function commentYou() {
  if (!comment.commentBody) {
    active.value = true;
    ElMessage({
      message: '请输入评论内容!',
      grouping: true,
      type: 'error',
    })

    return;
  }
  comment.blogId = blog.blogId;
  commentForm.value.validate((valid) => {
    if (valid) {
      submitComment(comment).then((data) => {
        ElMessage({message: "成功", type: 'success'})
      });
    }
  })
}

function formatTime(time) {

  let res = dayjs(new Date(time)).format("YYYY-MM-DD HH:mm:ss");
  console.log(res);
  return res;
}

function gotoTag(item) {
  router.push("/home/tag/" + item.tagId);
}

function getComments() {
  listComments({page: 1, limit: 30, blogId: blog.value.blogId}).then(
      (res) => {
        loading.value = false;
        commentList.value = res.data;
      }
  );
}


watch(() => comment.value, (val, preVal) => {
  if (val.commentBody) {
    active.value = false;
  }
})


</script>

<style lang="less" scoped>
.home-blog {
  flex: 1;

  padding: 20px 100px;

  .home-blog-head {
    span {
      margin: 0 5px;
    }
  }

  .blog-tags {
    margin: 10px 0;
  }

  .active {
    border: 1px solid red;
  }

  .blog-comment {
    .blog-title {
      margin: 10px;
      font-weight: 600;
    }
  }
}
</style>
