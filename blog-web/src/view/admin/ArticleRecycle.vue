<template>
  <el-table v-loading="loading" :data="data" fit>
    <el-table-column prop="blogId" label="博客id"></el-table-column>
    <el-table-column prop="blogTitle" label="博客标题">
      <template v-slot="{ row }">
        <el-link :href="`/home/blog/${row.blogId}`" target="_blank">{{ row.blogTitle }}</el-link>
      </template>
    </el-table-column>
    <el-table-column prop="blogCategoryName" label="博客分类">
      <template v-slot="{ row }">
        {{ row.blogCategory.categoryName }}
      </template>
    </el-table-column>
    <el-table-column prop="blogTags" width="100" label="博客标签">
      <template v-slot="{ row }">
        <el-tag style="margin: 0 5px" v-for="(item, index) in row.blogTags">{{ item.tagName }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="blogViews" width="80" label="阅读量"></el-table-column>
    <el-table-column width="180" prop="updateTime" label="修改时间">
      <template #default="{ row }">
        {{ formatTime(row.updateTime) }}
      </template>
    </el-table-column>
    <el-table-column prop="show" label="文章状态">
      <template v-slot="{ row }">{{ row.show ? `发布` : `草稿` }}</template>
    </el-table-column>

    <el-table-column prop="enableComment" label="评论">
      <template v-slot="{ row }">{{ row.enableComment ? `允许` : `禁止` }}</template>
    </el-table-column>
    <el-table-column label="操作" width="250">
      <template v-slot="{ row }">
        <el-popconfirm title="确定永久删除吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info" placement="right" iconColor="red" @confirm="clearRow(row)">
          <template #reference>
            <el-button type="danger">永久删除</el-button>
          </template>
        </el-popconfirm>

        <el-button type="primary" @click="restoreRow(row)">恢复</el-button>
      </template>
    </el-table-column>
  </el-table>

  <br />
  <el-pagination background layout="total,sizes,prev, pager, next " :total="count" :page-size="pageSize" @current-change="getData" v-model:current-page="currentPage" :page-sizes="[10, 20, 30, 40, 50, 100]" @size-change="sizeChange" />
</template>

<script setup>
import { defineComponent, onBeforeMount, reactive, toRefs } from 'vue'
import { hideBlog, deleteBlog, getBlogList, getCateList, getTagList, clearBlog } from '@/utils/apiConfig'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {formatTime}from '@/utils/utils'
const state = reactive({
  data: [],
  loading: true,
  cateList: [],
  tagList: [],count:0,currentPage:1,pageSize:10
})

const router = useRouter()
const route = useRoute()
let { data, loading, cateList, tagList ,count,currentPage,pageSize} = toRefs(state)
onBeforeMount(() => {
  getCate()
  getTags()
  getData()
})

function getCate() {
  getCateList().then(({ data }) => {
    state.cateList = data
  })
}
function sizeChange(size) {
 state. pageSize = size
  getData()
}
function getTags() {
  getTagList({ page: 1, limit: 100 }).then(({ data }) => {
    state.tagList = data
  })
}

function getData() {
  getBlogList({ page:state.currentPage, limit: state.pageSize, deleted: true }).then((res) => {
    console.log(`%c获取`, `color:red;font-size:16px;background:transparent`)
    console.log(res)
    state.data = res.data.list
    state.count = res.data.count
    state.loading = false
  })
}

function restoreRow(row) {
  deleteBlog(row.blogId, true).then(({ data }) => {
    if (data) {
      getData()
      ElMessage({
        message: '成功',
        type: 'success',
      })
    }
  })
}

function clearRow(row) {
  clearBlog(row.blogId).then(({ data }) => {
    if (data) {
      getData()
      ElMessage({
        message: '成功',
        type: 'success',
      })
    }
  })
}
</script>

<style scoped></style>
