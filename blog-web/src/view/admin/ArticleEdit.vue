<template>
  <div class="article-edit">
    <el-form ref="form" :rules="rules" :model="articleForm" label-width="6rem">
      <div class="d-flex">
        <el-form-item label="标题" prop="blogTitle">
          <el-input v-model="articleForm.blogTitle" placeholder="输入文章标题"></el-input>
        </el-form-item>
        <el-form-item label="自定义路径">
          <el-input v-model="articleForm.subUrl" placeholder="输入文章标题"></el-input>
        </el-form-item>
      </div>
      <div class="d-flex">
        <el-form-item label="标签" prop="blogTagIds">
          <el-select style="width: 100%" v-model="articleForm.blogTagIds" :multiple="true" placeholder="请选择">
            <el-option v-for="item in tagOptions" :key="item.tagId" :label="item.tagName" :value="item.tagId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分类" prop="blogCategoryId">
          <el-select style="width: 100%" v-model="articleForm.blogCategoryId" placeholder="请选择">
            <el-option v-for="item in cateOptions" :key="item.categoryId" :label="item.categoryName" :value="item.categoryId"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <div class="d-grid3">
        <el-form-item label="前言">
          <el-input v-model="articleForm.preface" placeholder="输入文章前言"></el-input>
        </el-form-item>
        <div class="inner-flex">
          <el-switch active-text="草稿" inactive-text="发布" v-model="articleForm.show" :active-value="false" :inactive-value="true"></el-switch>
          <el-switch active-text="开启评论" inactive-text="关闭评论" :active-value="true" :inactive-value="false" v-model="articleForm.enableComment"></el-switch>
        </div>
      </div>
      <el-form-item prop="blogContent" label="内容">
        <md-editor-v3 v-model="articleForm.blogContent" height="400px" code-theme="atomDark" :show-code-row-number="true" @copy-code-success="handleCopyCodeSuccess" @on-upload-img="handleUploadImage"></md-editor-v3>
      </el-form-item>
      <el-button type="primary" @click="submit">提交</el-button>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { addBlog, getAdminBlogById, getCateList, getTagList, uploadImg } from '@/utils/apiConfig'
import { onBeforeMount, reactive, toRefs } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Article } from '@/interface/result'
import type { FormInstance, FormRules } from 'element-plus'

const state = reactive({
  articleForm: {
    blogId: '',
    blogTitle: '',
    blogTagIds: [],
    blogCategoryId: undefined,
    blogContent: '',
    preface: '',
    subUrl: '',
    show: false,
    enableComment: false,
  },
  form: {},
  rules: {
    blogTitle: [{ required: true, message: '请输入文章标题', trigger: 'blur' }],
    blogTagIds: [{ required: true, message: '请选择标签', trigger: 'change' }],
    blogCategoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
    blogContent: [{ required: true, message: '请填写内容', trigger: 'blur' }],
  },
  text: '',
  tagOptions: [],
  cateOptions: [],
})
const { articleForm, form, rules, text, tagOptions, cateOptions } = toRefs(state)
const route = useRoute()
const router = useRouter()

function handleCopyCodeSuccess(code) {
  console.log(code)
}

function handleUploadImage(files, callback) {
  console.log(files)
  const formData = new FormData()
  formData.append('img', files[0])
  uploadImg(formData).then((res) => {
    console.log(res)
    callback([res.url])
  })
}

async function getData() {
  try {
    const res = await getAdminBlogById(route.query.id as string)
    if (res.success) {
      state.articleForm = res.data as unknown as Article
    } else {
      ElMessage({
        type: 'error',
        message: res.message,
      })
    }
  } catch (error) {
    console.log(error)
  }
}

async function tagList() {
  try {
    const res = await getTagList({ page: 1, limit: 100, show: true })
    if (res.success) {
      state.tagOptions = res.data.list
    } else {
      ElMessage.error(res.message)
    }
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

async function cateList() {
  try {
    const res = await getCateList()
    if (res.success) {
      state.cateOptions = res.data
    } else {
      ElMessage.error(res.message)
    }
  } catch (e) {
    console.log(e)
  }
}

async function submit() {
  if (route.query.id) {
    state.articleForm.blogId = route.query.id
  }
  console.log(`%ctag`, `color:red;font-size:16px;background:transparent`)
  console.log(state.articleForm.blogTagIds)
  form.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await addBlog(state.articleForm)
        if (res.success) {
          router.push({
            name: 'articleList',
          })
          ElMessage({
            type: 'success',
            message: '成功',
          })
        } else {
          ElMessage({
            type: 'error',
            message: res.message,
          })
        }
      } catch (e) {}
    }
  })
}

onBeforeMount(() => {
  if (route.query.id) {
    getData()
  }
  tagList()
  cateList()
})
</script>

<style lang="scss" scoped>
.article-edit {
  :deep(.el-form-item) {
    margin: 10px;
  }

  .d-flex {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    width: 100%;
  }

  .d-grid3 {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    align-items: center;

    .inner-flex {
      display: flex;
      justify-content: space-around;
    }
  }
}
</style>
