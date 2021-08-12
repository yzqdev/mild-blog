<template>
  <el-table :data="data">
    <el-table-column prop="blogTitle" label="博客标题"></el-table-column>
    <el-table-column prop="blogCategoryName" label="博客分类"></el-table-column>
    <el-table-column prop="blogTags" label="博客标签">
      <template v-slot="{row}">
        <el-tag v-for="item in row.blogTags.split(`,`)">{{item}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="blogViews" label="阅读量"></el-table-column>
    <el-table-column prop="updateTime" label="修改时间"></el-table-column>
    <el-table-column prop="blogStatus" label="文章状态"></el-table-column>
    <el-table-column prop="enableComment" label="评论"></el-table-column>
    <el-table-column label="操作" width="250">
      <template v-slot="{ row }"
        ><el-button type="primary" size="mini">编辑</el-button
        ><el-button type="danger" size="mini">删除</el-button>
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
            <el-button type="danger" size="mini">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { defineComponent } from "vue";
import { getBlogList } from "@/utils/apiConfig";

export default defineComponent({
  name: "ArticleList",
  data() {
    return {
      data: "",
    };
  },
  created() {
    getBlogList({ page: 1, limit: 30 }).then(({ data }) => {
      console.log(data);
      this.data = data;
    });
  },
  methods: {
    deleteRow(row) {},
    clearRow(row) {},
  },
});
</script>

<style scoped></style>
