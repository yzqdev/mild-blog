<template>
  <el-table :data="data">
    <el-table-column prop="commentator" label="评论者名称"></el-table-column>
    <el-table-column prop="email" label="评论者邮箱"></el-table-column>
    <el-table-column prop="commentBody" label="评论内容"></el-table-column>
    <el-table-column
      prop="commentCreateTime"
      label="评论时间"
    ></el-table-column>
    <el-table-column prop="replyBody" label="回复内容"></el-table-column>
    <el-table-column prop="replyCreateTime" label="回复时间"></el-table-column>
    <el-table-column prop="enableComment" label="审核"></el-table-column>
    <el-table-column prop="commentStatus" label="当前状态"></el-table-column>
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
import { getBlogList, getCommentList } from "@/utils/apiConfig";

export default {
  name: "ArticleList",
  data() {
    return {
      data: "",
    };
  },
  created() {
    getCommentList({ page: 1, limit: 30 }).then(({ data }) => {
      console.log(data);
      this.data = data;
    });
  },
  methods: {
    deleteRow(row) {},
    clearRow(row) {},
  },
};
</script>

<style scoped></style>
