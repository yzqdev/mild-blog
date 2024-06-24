<template>
  <el-table v-loading="loading" :data="data" fit>
    <el-table-column prop="blogId" label="博客id" width="90">
      <template v-slot="{ row }">
        <el-button @click="copyBlogId(row)">复制</el-button>
      </template>
    </el-table-column>
    <el-table-column prop="blogTitle" label="博客标题" width="250">
      <template v-slot="{ row }">
        <el-link :href="`/home/blog/${row.blogId}`" target="_blank">{{ row.blogTitle }}</el-link>
      </template>
    </el-table-column>
    <el-table-column prop="blogCategoryName" label="博客分类" width="200">
      <template v-slot="{ row }">
        {{ row.blogCategory.categoryName }}
      </template>
    </el-table-column>
    <el-table-column prop="blogTags" width="200" label="博客标签">
      <template v-slot="{ row }">
        <el-tag style="margin: 0 5px" v-for="(item, index) in row.blogTags">{{ item.tagName }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="blogViews" width="80" label="阅读量"></el-table-column>
    <el-table-column width="180" prop="updateTime" label="修改时间">
      <template v-slot="{ row }">
        {{ $dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss') }}
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
        <el-button type="primary" @click="editArticle(row)">编辑</el-button>

        <el-button type="warning" @click="hide(row)">{{ row.show ? `草稿` : `发布` }}</el-button>

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

<script setup>
import { defineComponent, onBeforeMount, reactive, toRefs } from 'vue'
import { clearBlog, deleteBlog, getBlogList, getCateList, getTagList, hideBlog } from '@/utils/apiConfig'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const state = reactive({
  data: [],
  loading: true,
  cateList: [],
  tagList: [],
  count: 0,
  currentPage: 1,
  pageSize: 10,
})

const router = useRouter()
const route = useRoute()
const { data, loading, cateList, tagList, count, pageSize, currentPage } = toRefs(state)
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
  state.pageSize = size

  getData()
}
function getTags() {
  getTagList({ page: 1, limit: 100 }).then(({ data }) => {
    state.tagList = data
  })
}

async function getData() {
  state.loading = true
  try {
    const res = await getBlogList({ page: state.currentPage, limit: state.pageSize, deleted: false })
    if (res.success) {
      console.log(`%c获取`, `color:red;font-size:16px;background:transparent`)

      state.data = res.data.list
      state.count = res.data.count
      state.loading = false
    }
  } catch (e) {
    console.log(e)
  }
}
function copyBlogId(row) {
  navigator.clipboard.writeText(row.blogId).then(() => {
    ElMessage({ type: 'success', message: '成功' })
  })
}
function editArticle(row) {
  router.push({
    name: 'articleEdit',
    query: {
      id: row.blogId,
    },
  })
}

async function deleteRow(row) {
  try {
    const res = await deleteBlog(row.blogId)
    if (res.success) {
      getData()
      ElMessage({
        message: '成功',
        type: 'success',
      })
    }
  } catch (e) {
    console.log(e)
  }
}

async function hide(row) {
  try {
    const res = await hideBlog(row.blogId, !row.show)
    if (res.success) {
      getData()
      ElMessage({
        message: '成功',
        type: 'success',
      })
    } else {
      ElMessage.error(res.message)
    }
  } catch (e) {}
}
</script>

<style scoped></style>
