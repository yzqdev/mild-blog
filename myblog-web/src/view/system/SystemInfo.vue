<template>
  <el-button type="primary" @click="addDialogShow">{{ dialogTxt }}</el-button>
  <el-dialog width="30%" v-model="addDialogVisible" title="添加系统信息">
    <el-form :model="addForm">
      <el-form-item prop="code" label="字段名">
        <el-input v-model="addForm.code"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="参数名">
        <el-input v-model="addForm.name"></el-input>
      </el-form-item>
      <el-form-item prop="value" label="参数值">
        <el-input v-model="addForm.value"></el-input>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAdd">确 定</el-button>
      </span>
    </template>
  </el-dialog>

  <el-table :data="tableData" fit>
    <el-table-column prop="code" label="字段名"></el-table-column>
    <el-table-column prop="name" label="参数名">
      <template v-slot="scope"
        ><span>
          {{ scope.row.name }}
        </span>
      </template>
    </el-table-column>
    <el-table-column prop="value" label="参数值">
      <template v-slot="scope"
        ><span>
          {{ scope.row.value }}
        </span>
      </template>
    </el-table-column>
    <el-table-column label="更新时间" prop="updateTime">
      <template v-slot="{ row }">
        {{ formatTime(row.updateTime) }}
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template v-slot="{ row }">
        <el-button type="primary" @click="editSystem(row)"> 编辑 </el-button>
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

<script setup lang="ts">
import { defineComponent, onMounted } from "vue";
import {
  addSystemInfo,
  delSystemInfo,
  editSystemInfo,
  getSystemInfo,
} from "@/utils/apiConfig";
import dayjs from "dayjs";
import { ElMessage } from "element-plus";

let dialogTxt = $ref("添加系统信息");
let tableData = $ref();
let addDialogVisible = $ref(false);

let addForm = $ref({
  id: null,
  name: "name",
  code: "field",
  value: "value",
});

function getData() {
  getSystemInfo().then((res) => {
    tableData = res.data.list;
  });
}

function editSystem(row) {
  addForm = row;
  addDialogVisible = true;
}
function changename(row) {
  editSystemInfo(row).then(({ data }) => {});
}

function formatTime(time) {
  let res = dayjs(new Date(time)).format("YYYY-MM-DD HH:mm:ss");
  return res;
}

function deleteRow(row) {
  delSystemInfo(row.code).then(({ data }) => {
    getData();
    ElMessage({
      type: "success",
      message: "成功",
    });
  });
}

function addDialogShow() {
  addDialogVisible = true;
}

async function confirmAdd() {
  if (addForm.id) {
    await changename(addForm);
  } else {
    await addSystemInfo(addForm);
  }
  getData();
  ElMessage({
    type: "success",
    message: "成功",
  });
  addDialogVisible = false;
}

onMounted(() => {
  getData();
});
</script>

<style scoped></style>
