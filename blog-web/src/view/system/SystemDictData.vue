<template>
  <el-button type="primary" @click="addDictDataDialog = true">添加字典</el-button>
  <el-dialog v-model="addDictDataDialog" width="30%" title="添加字典">
    <el-form :model="dictDataForm" label-position="right" label-width="80px">
      <el-form-item label="字典值">
        <el-input v-model="dictDataForm.value"></el-input>
      </el-form-item>
      <el-form-item label="唯一编码">
        <el-input v-model="dictDataForm.code"></el-input>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="dictDataForm.sort"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" v-model="dictDataForm.remark"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitAddDictData">确定</el-button>
    </template>
  </el-dialog>

  <el-table :data="dictDataTable" fit v-loading="loading">
    <el-table-column prop="value" label="类型名称"></el-table-column>
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
        <el-button type="primary" size="small">编辑</el-button>
        <el-divider direction="vertical" />
        <el-popconfirm title="确定删除吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info" placement="right" iconColor="red" @confirm="deleteRow(row)">
          <template #reference>
            <el-button type="danger" size="small">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <br />
  <el-pagination background layout="total,sizes,prev, pager, next " :total="count" :page-size="pageSize" @current-change="getData" v-model:current-page="currentPage" :page-sizes="[10, 20, 30, 40, 50, 100]" @size-change="sizeChange" />
</template>

<script setup lang="ts">
import { onBeforeMount, watch } from 'vue'
import { addDictDataApi, getAllDictDataApi } from '@/utils/systemApi'
import { ElMessage } from 'element-plus'

let count = $ref(0)
let currentPage = $ref(1)
let pageSize = $ref(10)
let dictDataTable = $ref([])
let loading = $ref(false)
let addDictDataDialog = $ref(false)
let dictDataForm = $ref({ value: '', code: '', sort: 0, remark: '', typeId: '' })
let typeId = $ref('')
let props = defineProps({
  row: Object,
  dictType: { type: String, default: '' },
  dictDataShow: { type: Boolean, default: false },
})

watch(
  props,
  (val, oldValue) => {
    if (val) {
      typeId = props.dictType
      getData()
    }
  },
  { immediate: true }
)
async function getData() {
  loading = true
  let res = await getAllDictDataApi(props.dictType, { page: currentPage, limit: pageSize })
  if (res.success) {
    dictDataTable = res.data.list
    count = res.data.count
    loading = false
  }
}
function deleteRow(row) {}
async function submitAddDictData() {
  dictDataForm.typeId = props.dictType
  let res = await addDictDataApi(dictDataForm)
  if (res.success) {
    await getData()
    ElMessage({
      type: 'success',
      message: res.message,
    })
    addDictDataDialog = false
  }
}

function sizeChange(size: number) {
  pageSize = size
  getData()
}
</script>

<style scoped></style>
