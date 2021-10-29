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
              placeholder="输入文章标题"
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
        <v-md-editor
            v-model="articleForm.blogContent"
            height="400px"
            left-toolbar="undo redo | tip todo-list emoji h h1 h2 h3 h4 h5 h6 bold italic strikethrough quote ul ol table hr link image imageLink uploadImage code save "
            @copy-code-success="handleCopyCodeSuccess"
            :disabled-menus="[]"
            @upload-image="handleUploadImage"
        ></v-md-editor>
      </el-form-item>
      <el-button type="primary" @click="submit">提交</el-button>
    </el-form>
  </div>
</template>

<script>
import {
  addBlog,
  getAdminBlogById,
  getCateList,
  getTagList, uploadImg,
} from "@/utils/apiConfig";

export default {
  name: "ArticleEdit",
  data() {
    return {
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
    };
  },
  methods: {
    handleCopyCodeSuccess(code) {
      console.log(code);
    },
    handleUploadImage(event, insertImage, files) {
      // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
      console.log(files);
      let formData=new FormData();
      formData.append('img',files[0])
      uploadImg(formData).then((res) => {
        console.log(res)
        insertImage({
          url:
              res.url,
          desc: '七龙珠',
          // width: 'auto',
          // height: 'auto',
        });
      })
      // 此处只做示例

    },
    getData() {
      getAdminBlogById(this.$route.query.id).then(({data}) => {
        this.articleForm = data;
      });
    },
    tagList() {
      getTagList().then(({data}) => {
        this.tagOptions = data;
      });
    },
    cateList() {
      getCateList().then(({data}) => {
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
.article-edit {
  ::v-deep(.el-form-item) {
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
