<template>
  <el-button type="danger" @click="clearAll">清空</el-button>
  <el-table v-loading="loading" :data="tableData" fit style="width: 100%">
    <el-table-column prop="name" label="名称" width="100"></el-table-column>

    <el-table-column prop="opType" width="80" label="操作类型"></el-table-column>
    <el-table-column width="180" prop="opTime" label="修改时间">
      <template v-slot="{ row }">
        {{ formatTime(row.updateTime)  }}
      </template>
    </el-table-column>
    <el-table-column prop="browser" label="浏览器" width="100" show-overflow-tooltip></el-table-column>

    <el-table-column prop="os" label="系统" width="100" show-overflow-tooltip></el-table-column>
    <el-table-column prop="url" label="url"></el-table-column>
    <el-table-column prop="reqMethod" label="请求方法" width="80" show-overflow-tooltip></el-table-column>
    <el-table-column prop="className" label="类名" show-overflow-tooltip></el-table-column>
    <el-table-column prop="methodName" label="方法名" width="150"></el-table-column>
    <el-table-column prop="param" label="参数" width="100">
      <template v-slot="{ row }">
        <el-button @click="showParams(row)">查看参数</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog width="30%" v-model="showParamVisible" title="显示参数">
    <el-input type="textarea" v-model="dialogParam" :autosize="{ minRows: 2 }"></el-input>
  </el-dialog>
  <br />
  <el-pagination background layout="total,sizes,prev, pager, next " :total="count" :page-size="pageSize" @current-change="getData" v-model:current-page="currentPage" :page-sizes="[10, 20, 30, 40, 50, 100]" @size-change="sizeChange" />
</template>

<script setup lang="ts">
import { getSysLogApi } from '@/utils/apiConfig'
import { onBeforeMount } from 'vue'
import { clearAllSysLogApi } from '@/utils/systemApi'
import { ElMessage } from 'element-plus'
import { formatTime } from "../../utils/utils";

const state=reactive({
   loading :false,
   count :0,
   pageSize :10,
   currentPage :1,
   tableData :[],
   showParamVisible :false,
   dialogParam :'',
})
const {loading,count,pageSize,currentPage,tableData,showParamVisible,dialogParam}=toRefs(state)

async function getData() {
state.  loading = true
  let res = await getSysLogApi({ page:state. currentPage, limit:state. pageSize })
  state.  tableData = res.data.list
  state. count = res.data.count
  state. loading = false
}

async function clearAll() {
  let res = await clearAllSysLogApi()
  if (res) {
    await getData()
    ElMessage({ type: 'success', message: '成功' })
  }
}

function sizeChange(size: number) {
  state. pageSize = size
  getData()
}

function showParams(row: any) {
  try {
    state. dialogParam = JSON.stringify(JSON.parse(row.param), null, 4)
    state. showParamVisible = true
  } catch (e) {
    state. dialogParam = row.param
    state. showParamVisible = true
  }
}

onBeforeMount(async () => {
  await getData()
})
</script>

<style scoped></style>
