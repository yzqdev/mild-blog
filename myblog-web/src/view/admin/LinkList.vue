<template>
  <el-button type="primary" @click="addDialogShow">添加链接信息</el-button>
  <el-dialog
    width="30%"
    v-model="addDialogVisible"
    @close="closeAddDialog"
    title="添加系统信息"
  >
    <el-form :model="addForm" label-width="80px">
      <el-form-item prop="linkName" label="链接名">
        <el-input v-model="addForm.linkName"></el-input>
      </el-form-item>
      <el-form-item prop="linkUrl" label="链接url">
        <el-input v-model="addForm.linkUrl"></el-input>
      </el-form-item>
      <el-form-item prop="linkDescription" label="链接描述">
        <el-input v-model="addForm.linkDescription"></el-input>
      </el-form-item>
      <el-form-item prop="linkType" label="链接描述">
        <el-select  v-model="addForm.linkType"> <el-option
          v-for="item in linkTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        /></el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAdd">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  <el-table :data="linkData" fit>
    <el-table-column prop="linkName" label="链接名"></el-table-column>
    <el-table-column prop="linkUrl" label="链接url"
      ><template v-slot="{ row }"
        ><el-link :href="row.linkUrl">{{ row.linkUrl }}</el-link></template
      ></el-table-column
    >
    <el-table-column prop="linkDescription" label="链接描述"></el-table-column>
    <el-table-column prop="linkRank" label="链接rank"></el-table-column>
    <el-table-column prop="linkType" label="链接类型"
      ><template v-slot="{ row }">{{ getLinkType(row.linkType) }}</template></el-table-column
    >
    <el-table-column prop="updateTime" label="更新时间"></el-table-column>
    <el-table-column prop="show" label="当前状态">
      <template v-slot="{ row }">
        {{ row.show ? "显示" : "隐藏" }}
      </template>
    </el-table-column>
    <el-table-column label="操作" width="300">
      <template v-slot="{ row }">
        <el-button type="primary" @click="editLinkClick(row)">编辑</el-button>
        <el-button type="warning" @click="hideLink(row)">{{
          row.show ? `隐藏` : `显示`
        }}</el-button>
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
  <br />
  <el-pagination
    background
    layout="total,sizes,prev, pager, next "
    :total="count"
    :page-size="pageSize"
    @current-change="getData"
    v-model:current-page="currentPage"
    :page-sizes="[10, 20, 30, 40, 50, 100]"
    @size-change="sizeChange"
  />
</template>

<script setup lang="ts">
import { defineComponent, onBeforeMount } from "vue";
import {
  addSystemInfo,
  clearLink,
  delLink,
  delSystemInfo,
  editLink,
  getLinkList,
  getSystemInfo,
} from "@/utils/apiConfig";
import { ElMessage } from "element-plus";
import { Z_TEXT } from "zlib";

let count = $ref(0);
let pageSize = $ref(10);
let currentPage = $ref(1);
let linkData = $ref("");
let addDialogVisible = $ref(false);
let addForm = $ref({
  linkId: null,
  linkName: "",
  linkUrl: "",
  linkDescription: "",
  linkType:0
});
let defaultOption=[
  {label:'友链',value:0},
  {label:'推荐',value:1},
  {label:'个人网站',value:2},
]
let linkTypeOptions=$ref(defaultOption)
function getLinkType(type:number){
 let text=defaultOption.find((item) => {
  return  item.value==type
 })

  return text.label
}
function sizeChange(size: number) {
  pageSize = size;
  getData();
}
function hideLink(row: any) {
  row.show = !row.show;
  delLink(row).then(({ data }) => {

    if (data) {
      ElMessage({ type: "success", message: "成功" });
      getData();
    }
  });
}

function editLinkClick(row) {
  addForm = {
    linkId: row.linkId,
    linkName: row.linkName,
    linkUrl: row.linkUrl,
    linkDescription: row.linkDescription,
    linkType: row.linkType
  };
  addDialogVisible = true;
}

function deleteRow(row) {
  addForm = {
    linkId: null,
    linkName: "",
    linkUrl: "",
    linkDescription: "",
    linkType: null,

  };
  clearLink(row.linkId).then((res) => {
    if (res) {
      getData();
      ElMessage({ type: "success", message: "成功" });
    } else {
      ElMessage({ type: "error", message: "失败了" });
    }
  });
}

function closeAddDialog() {
  addForm = {
    linkId: null,
    linkName: "",
    linkUrl: "",
    linkDescription: "",
  };
}

function addDialogShow() {
  addDialogVisible = true;
}

function getData() {
  getLinkList({ page: currentPage, limit: pageSize }).then((res) => {
    linkData = res.data.list;
    count = res.data.count;
  });
}

function confirmAdd() {
  editLink(addForm).then(({ data }) => {
    if (data) {
      getData();
      ElMessage({ type: "success", message: "成功" });
      addDialogVisible = false;
    }
  });
}

onBeforeMount(() => {
  getData();
});
</script>

<style scoped></style>
