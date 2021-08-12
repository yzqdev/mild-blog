<template>
  <el-table :data="cateData">
    <el-table-column prop="categoryName" label="分类名"></el-table-column>
    <el-table-column prop="categoryRank" label="排序值"></el-table-column>

    <el-table-column prop="isDeleted" label="当前状态">
      <template v-slot="{ row }">

      </template>
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
import {onMounted, ref} from "vue";
import {getCatePaging} from "@/utils/apiConfig";
let cateData=ref([])
function getData(){
  getCatePaging({page:1,limit:30}).then(({data}) => {
    console.log(data)
    cateData.value=data
  })
}
function deleteRow(row){
  console.log(row)
}

onMounted(() => {
  getData()
})
</script>

<style scoped>

</style>
