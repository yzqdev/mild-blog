<template>
  <el-table :data="data">
    <el-table-column prop="commentator" label="评论者名称"></el-table-column>
    <el-table-column prop="email" label="评论者邮箱"></el-table-column>
    <el-table-column prop="blogId" label="文章">
      <template v-slot="{ row }">
        <el-link :href="`/home/blog/${row.blogId}`">{{ row.blogInfo.blogTitle }}</el-link>
      </template>
    </el-table-column>
    <el-table-column prop="commentBody" label="评论内容" width="300" :show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="commentCreateTime" label="评论时间" width="180">
      <template v-slot="{ row }">
        {{ $dayjs(row.commentCreateTime).format('YYYY-MM-DD HH:mm:ss') }}
      </template>
    </el-table-column>
    <el-table-column prop="replyBody" label="回复内容"></el-table-column>
    <el-table-column prop="replyCreateTime" label="回复时间"></el-table-column>
    <el-table-column prop="commentStatus" label="当前状态">
      <template v-slot="{ row }">{{ row.commentStatus ? `审核通过` : `不通过` }}</template>
    </el-table-column>
    <el-table-column label="操作" width="250">
      <template v-slot="{ row }">
        <el-popconfirm title="确定删除吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info" placement="right" iconColor="red" @confirm="deleteRow(row)">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
        <el-popconfirm title="确定审核通过吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info" placement="right" iconColor="red" @confirm="checkRow(row)">
          <template #reference>
            <el-button type="primary">审核</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <br />
  <el-pagination background layout="total,sizes,prev, pager, next " :total="count" :page-size="pageSize" @current-change="getData" v-model:current-page="currentPage" :page-sizes="[10, 20, 30, 40, 50, 100]" @size-change="sizeChange" />
</template>

<script setup lang="ts">
import { deleteCommentById, getBlogList, getCommentList, hideCommentById } from '@/utils/apiConfig'
import { onBeforeMount, ref } from 'vue'
import { ElMessage } from 'element-plus'

onBeforeMount(() => {
  getData()
})
const state = reactive({
  data: [],
  count: 0,
  currentPage: 1,
  pageSize: 10,
})
const { data, count, currentPage, pageSize } = toRefs(state)

function getData() {
  getCommentList({ page: state.currentPage, limit: state.pageSize }).then((res) => {
    state.data = res.data.list
    state.count = res.data.count
  })
}

function sizeChange(size: number) {
  state.pageSize = size
  getData()
}

async function deleteRow(row) {
  try {
    let res = await deleteCommentById(row.id)
    if (res.success) {
      getData()
      ElMessage.success(res.message)
    } else {
      ElMessage.error(res.message)
    }
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

function checkRow(row) {
  console.log('这是checkrow', row)
  hideCommentById(row.id, !row.commentStatus).then(({ data }) => {
    getData()
  })
}
</script>

<style scoped></style>
