<template>
  <el-button type="primary" @click="addCate">添加</el-button>
  <el-dialog width="30%" v-model="addDialogVisible" title="添加系统信息">
    <el-form ref="addFormRef" :model="addForm" :rules="addCateRules">
      <el-form-item prop="categoryName" label="分类名"
        ><el-input
          placeholder="请输入分类名"
          v-model="addForm.categoryName"
        ></el-input> </el-form-item
      ><el-form-item prop="categoryRank" label="排序值"
        ><ElInputNumber v-model="addForm.categoryRank"></ElInputNumber>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAdd">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  <el-table :data="cateData">
    <el-table-column prop="categoryName" label="分类名"></el-table-column>
    <el-table-column prop="categoryRank" label="排序值"></el-table-column>
    <el-table-column width="180" prop="createTime" label="创建时间">
      <template v-slot="{ row }">{{
        $dayjs(row.createTime).format("YYYY-MM-DD HH:mm:ss")
      }}</template>
    </el-table-column>

    <el-table-column prop="isDeleted" label="当前状态">
      <template v-slot="{ row }"> </template>
    </el-table-column>
    <el-table-column label="操作">
      <template v-slot="{ row }">
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
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { addCateApi, clearCateApi, getCatePaging } from "@/utils/apiConfig";
let cateData = ref([]);
let addFormRef=ref(null)
let addCateRules = ref({
  categoryName: [{ required: true, message: "请输入名称" }],
  categoryRank: [{ required: true, message: "请输入排序" }],
});
let addDialogVisible = ref(false);
let addForm = reactive({ categoryName: "", categoryRank: 1 });
function getData() {
  getCatePaging({ page: 1, limit: 30 }).then(({ data }) => {
    console.log(data);
    cateData.value = data;
  });
}
function addCate() {
  addDialogVisible.value = true;
}
function confirmAdd() {
  console.log(addFormRef.value)
  addFormRef.value.validate((valid ) => {
    if (valid ) {
      addCateApi(addForm).then((res) => {
        if (res.success) {
          getData();
          addDialogVisible.value = false;
        }
      });
    }
  })
}
function deleteRow(row) {
  clearCateApi(row.categoryId).then(({ data }) => {
    if (data) {
      getData();
      this.$message.success("成功");
    }
  });
}

onMounted(() => {
  getData();
});
</script>

<style scoped></style>
