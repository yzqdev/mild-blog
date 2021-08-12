<template>
  <el-dialog v-model="editFormShow" width="30%">
    <el-form :model="editForm">
      <el-form-item label="标签名"
        ><el-input v-model="editForm.tagName"></el-input>
      </el-form-item>
      <el-form-item label="当前状态"
        ><el-switch
          v-model="editForm.isDeleted"
          :active-value="1"
          :inactive-value="0"
          active-text="已删除"
          inactive-text="未删除"
        ></el-switch>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitEdit">确定</el-button>
    </template>
  </el-dialog>
  <el-table :data="data">
    <el-table-column prop="tagId" label="标签id"></el-table-column>
    <el-table-column prop="tagName" label="标签名"></el-table-column>
    <el-table-column prop="isDeleted" label="当前状态">
      <template v-slot="{ row }"
        >
        {{ row.isDeleted == 1 ? `已删除` : `未删除` }}
      </template>
    </el-table-column>

    <el-table-column label="操作" width="250">
      <template v-slot="{ row }">
        <el-button type="primary" size="mini" @click="editRow(row)"
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
            <el-button type="danger" size="mini">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { EditTagList, getCommentList, getTagList } from "@/utils/apiConfig";

export default {
  name: "TagList",
  data() {
    return {
      data: "",
      editForm: {},
      editFormShow: false,
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      getTagList().then(({ data }) => {
        console.log(data);
        this.data = data;
      });
    },
    deleteRow(row) {},
    submitEdit() {
      EditTagList(this.editForm).then((data) => {
        console.log(data);
        if (data) {
          this.$message.success("成功");
          this.getData();
          this.editFormShow = false;
        }
      });
    },
    editRow(row) {
      this.editForm = row;
      this.editFormShow = true;
    },
    clearRow(row) {},
  },
};
</script>

<style scoped></style>
