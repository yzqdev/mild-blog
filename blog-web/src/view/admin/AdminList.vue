<template>
  <el-table :data="tableData">
    <el-table-column prop="username" label="用户名"></el-table-column>

    <el-table-column prop="nickname" label="昵称"></el-table-column>
    <el-table-column prop="role" label="权限">
      <template v-slot="{ row }">
        {{ row.role == '0' ? `普通用户` : `管理员` }}
      </template>
    </el-table-column>
    <el-table-column prop="locked" label="是否冻结">
      <template v-slot="{ row }">
        {{ row.locked ? `已冻结` : `正常` }}
      </template>
    </el-table-column>

    <el-table-column label="操作">
      <template v-slot="{ row }">
        <el-button :type="row.locked?'primary':'warning'"   @click="editUserClick(row)">{{row.locked?'解冻':'冻结'}}</el-button>

        <el-popconfirm title="确定删除吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info" placement="right" iconColor="red" @confirm="deleteRow(row)">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import { delUsers, getUsers, unlockUser } from '@/utils/apiConfig'
import { onBeforeMount, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'

onBeforeMount(() => {
  getData()
})
let tableData = ref([])

async function editUserClick(row) {
  const { data } = await unlockUser(row.id)
  console.log(data)
  if (data) {
    await getData()
    ElMessage({ message: '成功', type: 'success' })
  }
}

async function getData() {
  const { data } = await getUsers()
  console.log(data)
  console.log(`%c哈哈东风`, `color:red;font-size:16px;background:transparent`)
  tableData.value = data
}

function deleteRow(row) {
  delUsers(row.id).then(({ data }) => {
    if (data) {
      getData()
      ElMessage({ message: '成功', type: 'success' })
    }
  })
}
</script>

<style scoped></style>
