<template>
  <div class="article-edit">
    <el-form ref="form" :model="articleForm" label-width="100px">
      <div class="d-flex">
        <el-form-item label="标题"
        >
          <el-input
              v-model="articleForm.blogTitle"
              placeholder="输入文章标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="自定义路径"
        >
          <el-input
              v-model="articleForm.blogSubUrl"
              placeholder="输入文章标题"
          ></el-input>
        </el-form-item>
      </div>
      <div class="d-flex">
        <el-form-item label="标签"
        >
          <el-select
              style="width: 100%"
              v-model="articleForm.blogTagIds"
              multiple
              placeholder="请选择"
          >
            <el-option
                v-for="item in tagOptions"
                :key="+item.tagId"
                :label="item.tagName"
                :value="item.tagId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分类"
        >
          <el-select
              style="width: 100%"
              v-model="articleForm.blogCategoryId"
              placeholder="请选择"
          >
            <el-option
                v-for="item in cateOptions"
                :key="item.categoryId"
                :label="item.categoryName"
                :value="item.categoryId"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <div class="d-grid3">
        <el-form-item label="前言"
        >
          <el-input
              v-model="articleForm.blogPreface"
              placeholder="输入文章前言"
          ></el-input>
        </el-form-item>
        <div class="inner-flex">
          <el-switch
              active-text="草稿"
              inactive-text="发布"
              v-model="articleForm.blogStatus"
              :active-value="0"
              :inactive-value="1"

          ></el-switch>
          <el-switch
              active-text="开启评论"
              inactive-text="关闭评论"
              :active-value="1"
              :inactive-value="0"
              v-model="articleForm.enableComment"
          ></el-switch>
        </div>
      </div>
      <el-form-item>
        <md-editor-v3
            v-model="articleForm.blogContent"
            height="400px"

            @copy-code-success="handleCopyCodeSuccess"
            @on-upload-img="handleUploadImage"
        ></md-editor-v3>
      </el-form-item>
      <el-button type="primary" @click="submit">提交</el-button>
    </el-form>
  </div>
</template>

<script setup>
import {
  addBlog,
  getAdminBlogById,
  getCateList,
  getTagList, uploadImg,
} from "@/utils/apiConfig";
import {onBeforeMount, reactive, toRefs} from "vue";
import {useRoute, useRouter} from "vue-router";

let state = reactive({
  articleForm: {
    blogId: "",
    blogTitle: "",
    blogTagIds: [],
    blogCategoryId: undefined,
    blogContent: "",
    blogPreface: "",
    blogSubUrl: "",
    blogStatus: 0,
    enableComment: 0,
  },
  text: "",
  tagOptions: [],
  cateOptions: [],
})
let {articleForm, text, tagOptions, cateOptions} = toRefs(state)
const route = useRoute()
const router = useRouter()

function handleCopyCodeSuccess(code) {
  console.log(code);
}

function handleUploadImage( files,callback) {
  console.log(files);
  let formData = new FormData();
  formData.append('img', files[0])
  uploadImg(formData).then((res) => {
    console.log(res)
    callback(  [res.url] );
  })

}

function getData() {
  getAdminBlogById(route.query.id).then(({data}) => {
    state.articleForm = data;
  });
}

function tagList() {
  getTagList().then(({data}) => {
    state.tagOptions = data;
  });
}

function cateList() {
  getCateList().then(({data}) => {
    state.cateOptions = data;
  });
}

function submit() {
  if (route.query.id) {
    state.articleForm.blogId = +route.query.id;
  }

  addBlog(state.articleForm).then((data) => {
    console.log(data);
    if (data) {
      router.push({
        name: "articleList",
      });
      this.$message.success("成功");
    }
  });
}

onBeforeMount(() => {
  if (route.query.id) {
    getData();
  }
  tagList();
  cateList();
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
