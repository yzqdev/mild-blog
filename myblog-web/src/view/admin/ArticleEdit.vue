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
        ><el-input
          v-model="articleForm.blogSubUrl"
          placeholder="输入文章标题"
        ></el-input>
      </el-form-item>
    </div>
    <div class="d-flex">
      <el-form-item label="标签"
        ><el-select
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
    <div
      style="
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        align-items: center;
      "
    >
      <el-form-item label="前言"
        ><el-input
          v-model="articleForm.blogPreface"
          placeholder="输入文章标题"
        ></el-input> </el-form-item
      ><el-switch
        active-text="草稿"
        inactive-text="发布"
        v-model="articleForm.blogStatus"
        :active-value="1"
        :inactive-value="0"
      ></el-switch>
      <el-switch
        active-text="开启评论"
        inactive-text="关闭评论"
        :active-value="1"
        :inactive-value="0"
        v-model="articleForm.enableComment"
      ></el-switch>
    </div>
    <el-form-item>
      <v-md-editor
        v-model="articleForm.blogContent"
        height="400px"
      ></v-md-editor>
    </el-form-item>
    <el-button type="primary" @click="submit">提交</el-button>
  </el-form>
</template>

<script>
import {
  addBlog,
  getAdminBlogById,
  getCateList,
  getTagList,
} from "@/utils/apiConfig";

export default {
  name: "ArticleEdit",
  data() {
    return {
      articleForm: {
        blogId: "",
        blogTitle: "112",
        blogTagIds: [],
        blogCategoryId: 29,
        blogContent: "1contet",
        blogPreface: "22",
        blogSubUrl: "33",
        blogStatus: 0,
        enableComment: 0,
      },
      text: "",
      tagOptions: [],
      cateOptions: [],
    };
  },
  methods: {
    getData() {
      getAdminBlogById(this.$route.query.id).then(({ data }) => {

        this.articleForm = data;

      });
    },
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
      if (this.$route.query.id) {
        this.articleForm.blogId = +this.$route.query.id;
      }

      addBlog(this.articleForm).then((data) => {
        console.log(data);
        if (data) {
          this.$router.push({
            name: "articleList",
          });
          this.$message.success("成功");
        }
      });
    },
  },
  created() {
    if (this.$route.query.id) {
      this.getData();
    }
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
