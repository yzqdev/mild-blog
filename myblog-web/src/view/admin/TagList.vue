<template>
  <el-table :data="data">
    <el-table-column prop="tagId" label="标签id"></el-table-column>
    <el-table-column prop="tagName" label="标签名"></el-table-column>
    <el-table-column prop="isDeleted" label="当前状态"></el-table-column>

    <el-table-column label="操作" width="250">
      <template v-slot="{ row }"
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
            <el-button type="danger" size="mini">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getCommentList, getTagList} from "@/utils/apiConfig";

export default {
  name: "TagList",
  data() {
    return {
      data: "",
    };
  },
  created() {
    getTagList( ).then(({ data }) => {
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
