<template>
  <el-button type="primary" @click="addDictDialog = true">添加字典</el-button>
  <el-dialog v-model="addDictDialog" width="30%" title="添加字典" append-to-body>
    <el-form :model="dictTypeForm" label-position="right" label-width="80px">
      <el-form-item label="类型名称">
        <el-input v-model="dictTypeForm.name"></el-input>
      </el-form-item>
      <el-form-item label="唯一编码">
        <el-input v-model="dictTypeForm.code"></el-input>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="dictTypeForm.sort"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" v-model="dictTypeForm.remark"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitEdit">确定</el-button>
    </template>
  </el-dialog>
  <el-dialog v-model="addDictDataDialog" width="50%" title="字典值管理" @open="dictDataShow = !dictDataShow" append-to-body>
    <system-dict-data :dict-data-show="dictDataShow" :dict-type="dictType"></system-dict-data>
  </el-dialog>
  <el-table :data="dictTable" fit v-loading="loading">
    <el-table-column prop="name" label="类型名称"></el-table-column>
    <el-table-column prop="code" label="唯一编码"></el-table-column>
    <el-table-column prop="sort" label="排序"></el-table-column>
    <el-table-column prop="remark" label="备注"></el-table-column>

    <el-table-column prop="status" label="状态">
      <template v-slot="{ row }">
        {{ row.status ? '启用' : '禁用' }}
      </template>
    </el-table-column>

    <el-table-column label="操作" width="300">
      <template v-slot="{ row }">
        <el-button type="primary" @click="showDict(row)">字典</el-button>
        <el-button type="primary" @click="editDictClick(row)">编辑</el-button>

        <el-popconfirm title="确定删除吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info" placement="right" iconColor="red" @confirm="deleteRow(row)">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <br />
  <el-pagination background layout="total,sizes,prev, pager, next " :total="count" :page-size="pageSize" @current-change="getData" v-model:current-page="currentPage" :page-sizes="[10, 20, 30, 40, 50, 100]" @size-change="sizeChange" />
</template>

<script setup lang="ts">
import { addDictTypeApi, getAllDictTypeApi } from '@/utils/systemApi'
import { ElMessage } from 'element-plus'
import { onBeforeMount } from 'vue'
import SystemDictData from '@/view/system/SystemDictData.vue'

const state = reactive({
  addDictDialog: false,
  addDictDataDialog: false,
  dictTypeForm: { name: '', code: '', sort: 0, remark: '' },
  count: 0,
  pageSize: 10,
  currentPage: 1,
  loading: false,
  dictTable: [],
  dictType: '',
  dictDataShow: false,
})
const { addDictDialog, addDictDataDialog, dictTypeForm, count, pageSize, currentPage, loading, dictTable, dictType, dictDataShow } = toRefs(state)

function sizeChange(size: number) {
  state.pageSize = size
}

onBeforeMount(async () => {
  await getData()
})

async function getData() {
  state.loading = true
  let res = await getAllDictTypeApi({ page: state.currentPage, limit:state. pageSize })

  if (res.success) {
    state.dictTable = res.data.list
    state.count = res.data.count
    state.loading = false
  }
}

function showDict(row) {
  console.log(row)
  state.dictType = row.code
  state.addDictDataDialog = true
}

function editDictClick(row) {}

function deleteRow(row) {}

async function submitEdit() {
  let res = await addDictTypeApi(dictTypeForm)
  if (res.success) {
    await getData()
    state.addDictDialog = false
    ElMessage({
      type: 'success',
      message: res.message,
    })
  }
}
</script>

<style scoped></style>
