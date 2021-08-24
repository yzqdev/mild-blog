<template>
  <el-table :data="data">
    <el-table-column prop="blogTitle" label="博客标题"></el-table-column>
    <el-table-column prop="blogCategoryName" label="博客分类"><template v-slot="{row}">
      {{row.blogCategoryId}}
    </template></el-table-column>
    <el-table-column prop="blogTags" label="博客标签">
      <template v-slot="{ row }">
        <el-tag v-for="item in row.blogTags.split(`,`)">{{ item }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="blogViews" label="阅读量"></el-table-column>
    <el-table-column prop="updateTime" label="修改时间"></el-table-column>
    <el-table-column prop="blogStatus" label="文章状态"></el-table-column>
    <el-table-column prop="isDeleted" label="删除状态"
      ><template v-slot="{ row }">
        {{ row.isDeleted == 1 ? `已删除` : `未删除` }}
      </template></el-table-column
    >
    <el-table-column prop="enableComment" label="评论"></el-table-column>
    <el-table-column label="操作" width="250">
      <template v-slot="{ row }"
        ><el-button type="primary" size="mini" @click="editArticle(row)"
          >编辑</el-button
        >
        <el-popconfirm
          title="确定删除吗？"
          confirmButtonText="好的"
          cancelButtonText="不用了"
          icon="el-icon-info"
          placement="right"
          iconColor="red"
          @confirm="deleteRow(row)"
        >
          <template #reference>
            <el-button type="warning" size="mini">删除</el-button>
          </template>
        </el-popconfirm>
        <el-popconfirm
          title="确定删除吗？"
          confirmButtonText="好的"
          cancelButtonText="不用了"
          icon="el-icon-info"
          placement="right"
          iconColor="red"
          @confirm="clearRow(row)"
        >
          <template #reference>
            <el-button type="danger" size="mini">清除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { defineComponent } from "vue";
import {
  clearBlog,
  deleteBlog,
  getBlogList,
  getCateList,
  getTagList,
} from "@/utils/apiConfig";

export default defineComponent({
  name: "ArticleList",
  data() {
    return {
      data: "",
      cateList: [],
      tagList: [],
    };
  },
  created() {
    this.getCate();
    this.getTags();
    this.getData();
  },
  methods: {
    getCate() {
      getCateList().then(({ data }) => {
        this.cateList = data;
      });
    },
    getTags() {
      getTagList().then(({ data }) => {
        this.tagList = data;
      });
    },
    getData() {
      getBlogList({ page: 1, limit: 30 }).then(({ data }) => {
        console.log(data);
        this.data = data;
      });
    },
    editArticle(row) {
      this.$router.push({
        name: "articleEdit",
        query: {
          id: row.blogId,
        },
      });
    },
    deleteRow(row) {
      deleteBlog(row.blogId).then(({ data }) => {
        if (data) {
          this.getData();this.$message.success("成功");
        }
      });
    },
    clearRow(row) {
      clearBlog(row.blogId).then(({ data }) => {
        if (data) {
          this.getData();this.$message.success("成功");
        }
      });
    },
  },
});
</script>

<style scoped></style>
