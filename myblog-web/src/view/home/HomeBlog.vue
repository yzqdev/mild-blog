<template>
  <div class="home-blog" v-loading="loading">
    <h1>{{ blog.blogTitle }}</h1>
    <div class="home-blog-head">
      <span>发表于{{ formatTime(blog.createTime) }}</span>
      <span>共有{{ blog.commentCount }}条评论</span>
      <span>{{ blog.blogViews }}浏览</span>
    </div>
    <div class="blog-tags">
      <el-tag style="margin-left: 10px; cursor: pointer" v-for="item in tags" @click="gotoTag(item)">{{ item.tagName }}</el-tag>
    </div>
    <article class="blog-content">

      <md-preview editor-id="preview-only" :model-value="blog.blogContent" code-theme="atomDark" :show-code-row-number="true" ></md-preview>
      <md-catalog :editorId="id" :scrollElement="scrollElement" />
    </article>

    <div class="blog-comment" v-if="blog.enableComment">
      <article class="blog-title">添加评论</article>
      <el-form ref="commentForm" :model="comment" label-position="top" :rules="commentRule">
        <el-form-item prop="commentator" label="">
          <el-input :input-style="commentInput" v-model="comment.commentator">
            <template #prepend>昵称</template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input :input-style="commentInput" type="email" v-model="comment.email">
            <template #prepend>邮箱</template>
          </el-input>
        </el-form-item>
        <el-form-item prop="website">
          <el-input :input-style="commentInput" v-model="comment.website">
            <template #prepend>网站地址</template>
          </el-input>
        </el-form-item>
        <article class="blog-title">评论内容</article>
        <el-form-item prop="commentBody">
          <div :class="active ? `active` : ``" style="width: 100%">
            <md-editor-v3 v-model="comment.commentBody" @on-upload-img="handleUploadImage"></md-editor-v3>
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
              <span style="flex: 1; text-align: right">{{ formatTime(item.commentCreateTime) }}</span>
            </div>
          </template>
          <md-editor editor-id="preview-only" v-model="item.commentBody"></md-editor>

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

<script setup lang="ts">
import { uploadImg } from '@/utils/apiConfig'
import { getBlogById, listComments, submitComment } from '@/utils/homeApi'
import dayjs from 'dayjs'
import { onMounted, reactive, ref, toRefs, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { MdCatalog, MdEditor, MdPreview } from "md-editor-v3";
import 'md-editor-v3/lib/preview.css';
const router = useRouter()
const route = useRoute()
const scrollElement = document.documentElement;
const id = 'preview-only';
const text = ref('# Hello Editor');
let state = reactive({
  commentInput: { width: '50%' },
  blog: {},
  tags: {},
  comment: { commentator: null, email: null, commentBody: '' },
  commentList: [],
})
let commentRule = $ref<FormRules>({
  commentator: [
    { required: true, message: '请输入网名', trigger: 'blur' },
    { min: 1, max: 20, message: '长度在 3 到 5 个字符', trigger: 'blur' },
  ],
  email: [
    {
      pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
      required: true,
      message: '请输入邮箱',
      trigger: 'blur',
    },
    { min: 1, max: 20, message: '长度在 3 到 5 个字符', trigger: 'blur' },
  ],
  commentBody: [{ required: true, message: '请输入评论内容',trigger:'blur' }],
})
let active = ref(false)
let loading = ref(true)
let { blog, tags, comment, commentList, commentInput } = toRefs(state)
let commentForm = ref<FormInstance>( )
onMounted(async () => {
  let id = route.params.id
  const { data } = await getBlogById(id)

  blog.value = data.blogDetailVO
  tags.value = data.tagList

  getComments()
})

function handleUploadImage(files, callback) {
  console.log(files)
  let formData = new FormData()
  formData.append('img', files[0])
  uploadImg(formData).then((res) => {
    console.log(res)
    callback([res.url])
  })
}

function commentYou() {
  if (!state.comment.commentBody) {
    ElMessage({
      message: '请输入评论内容!',
      grouping: true,
      type: 'error',
    })

    return
  }
  state.comment.blogId = state.blog.blogId
  commentForm.value.validate(async (valid) => {
    console.log(state.comment)
    console.log(`%c看看是大幅度蓝山咖啡`, `color:red;font-size:16px;background:transparent`)
    if (valid) {
     try {
       let res=await submitComment(state.comment)
       if (res.success) {
         ElMessage({ message: '成功', type: 'success' })
         getComments()
       }else{
         ElMessage.error(res.message)
       }
     }
      catch(e){
        ElMessage.error(e.message)
    }}
  })
}

function formatTime(time) {
  return dayjs(new Date(time)).format('YYYY-MM-DD HH:mm:ss')
}

function gotoTag(item) {
  router.push('/home/tag/' + item.tagId)
}

function getComments() {
  listComments({ page: 1, limit: 30, blogId: blog.value.blogId }).then((res) => {
    loading.value = false
    commentList.value = res.data.list
  })
}

watch(
  () => comment.value,
  (val, preVal) => {
    active.value = !val.commentBody
  }
)
</script>

<style lang="scss" scoped>
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
