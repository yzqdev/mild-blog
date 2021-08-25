<template>
  <el-table :data="data">
    <el-table-column prop="loginUserName" label="用户名"></el-table-column>

    <el-table-column prop="nickName" label="昵称"></el-table-column>
    <el-table-column prop="locked" label="是否冻结"><template v-slot="{row}">
      {{row.locked==1?`已冻结`:`正常`}}
    </template></el-table-column>

    <el-table-column label="操作">
      <template v-slot="{ row }">
        <el-button type="primary" v-if="row.locked==1" @click="editUserClick(row)">解冻</el-button>
        <el-button type="warning"  v-if="row.locked==0" @click="editUserClick(row)">冻结</el-button>
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

<script>
import {delUsers, getUsers, unlockUser} from "@/utils/apiConfig";

export default {
  name: "AdminList",
  data() {
    return {
      data: [],
    };
  },
  created() {
    this.getData();
  },
  methods: {
    async editUserClick(row) {
      const {data }=await  unlockUser(row.adminUserId )
      if (data ) {
        await this.getData()
        this.$message.success("解冻成功")
      }
    },
    async getData() {
      const { data } = await getUsers();
      this.data = data;
    },
    deleteRow(row) {
      delUsers(row.adminUserId).then(({data }) => {
        if (data ) {
          this.getData()
          this.$message.success("成功")
        }
      })
    },
  },
};
</script>

<style scoped></style>
