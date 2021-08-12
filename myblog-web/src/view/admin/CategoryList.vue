<template>
  <el-table :data="data">
    <el-table-column prop="linkName" label="链接名"></el-table-column>
    <el-table-column prop="linkUrl" label="链接url"></el-table-column>
    <el-table-column prop="linkDescription" label="链接描述"></el-table-column>
    <el-table-column prop="linkRank" label="链接rank"></el-table-column>
    <el-table-column prop="linkType" label="当前状态">
      <template v-slot="{ row }">

      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template v-slot="{ row }">
        <el-button type="primary">编辑</el-button>
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
import {onMounted, ref} from "vue";
import {getCatePaging} from "@/utils/apiConfig";
function getData(){
  getCatePaging({page:1,limit:30}).then(({data }) => {
    this.data=data
  })
}
function deleteRow(row){
  console.log(row)
}
const data=ref([])
onMounted(() => {
  getData()
})
</script>

<style scoped>

</style>
