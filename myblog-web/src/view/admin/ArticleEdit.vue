<template>
  <el-form ref="form" :model="articleForm" label-width="100px">
    <div class="d-flex">
      <el-form-item label="标题"
        ><el-input
          v-model="articleForm.blogTitle"
          placeholder="输入文章标题"
        ></el-input>
      </el-form-item>
      <el-form-item label="自定义路径"
        ><el-input placeholder="输入文章标题"></el-input>
      </el-form-item>
    </div>
    <div class="d-flex">
      <el-form-item label="标签"
        ><el-select
          style="width: 100%"
          v-model="articleForm.blogTags"
          multiple
          placeholder="请选择"
        >
          <el-option
            v-for="item in tagOptions"
            :key="item.tagId"
            :label="item.tagName"
            :value="item.tagId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分类"
        ><el-select
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
    <el-form-item label="前言"
      ><el-input
        v-model="articleForm.blogPreface"
        placeholder="输入文章标题"
      ></el-input>
    </el-form-item>
    <el-form-item label="前言">
      <v-md-editor
        v-model="articleForm.blogContent"
        height="400px"
      ></v-md-editor>
    </el-form-item>
    <el-button type="primary" @click="submit">提交</el-button>
  </el-form>
</template>

<script>
import { addBlog, getCateList, getTagList } from "@/utils/apiConfig";

export default {
  name: "ArticleEdit",
  data() {
    return {
      articleForm: {
        blogTitle: "111",
        blogTags: [141,140],
        blogCategoryId: 20,
        blogContent: "sdfdsfdsf",
        blogPreface: "asgsggs",
      },
      text: "",
      tagOptions: [],
      cateOptions: [],
    };
  },
  methods: {
    tagList() {
      getTagList().then(({ data }) => {
        this.tagOptions = data;
      });
    },
    cateList() {
      getCateList().then(({ data }) => {
        this.cateOptions = data;
      });
    },
    submit() {
      console.log(this.articleForm);
      this.articleForm.blogTags=this.articleForm.blogTags.join()
      addBlog(this.articleForm).then(({ data }) => {
        console.log(data);
      });
    },
  },
  created() {
    this.tagList();
    this.cateList();
  },
};
</script>

<style lang="less" scoped>
.d-flex {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  width: 100%;
}
</style>
