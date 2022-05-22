<template>
  <el-table v-loading="loading" :data="data" fit>
    <el-table-column prop="blogId" label="博客id"></el-table-column>
    <el-table-column prop="blogTitle" label="博客标题">
      <template v-slot="{row}">
        <el-link :href="`/home/blog/${row.blogId}`" target="_blank">{{row.blogTitle}}</el-link>
      </template>
    </el-table-column>
    <el-table-column prop="blogCategoryName" label="博客分类">
      <template v-slot="{ row }">
        {{ row.blogCategory.categoryName }}
      </template>
    </el-table-column>
    <el-table-column prop="blogTags" width="250" label="博客标签">
      <template v-slot="{ row }">
        <el-tag style="margin:0 5px;" type="primary" v-for="(item, index) in row.blogTags"
        >{{ item.tagName }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="blogViews" label="阅读量"></el-table-column>
    <el-table-column width="180" prop="updateTime" label="修改时间">
      <template v-slot="{ row }">
        {{ $dayjs(row.updateTime).format("YYYY-MM-DD HH:mm:ss") }}
      </template>
    </el-table-column>
    <el-table-column prop="blogStatus" label="文章状态"
    >
      <template v-slot="{ row }">{{
          row.blogStatus == 1 ? `发布` : `草稿`
        }}
      </template>
    </el-table-column
    >
    <el-table-column prop="isDeleted" label="隐藏状态">
      <template v-slot="{ row }">
        {{ row.isDeleted == 1 ? `隐藏` : `显示` }}
      </template>
    </el-table-column>
    <el-table-column prop="enableComment" label="评论">
      <template v-slot="{ row }">{{
          row.enableComment == 1 ? `允许` : `禁止`
        }}
      </template>
    </el-table-column>
    <el-table-column label="操作" width="250">
      <template v-slot="{ row }">
        <el-button type="primary" size="mini" @click="editArticle(row)"
        >编辑
        </el-button>
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
            <el-button type="warning" size="mini">隐藏</el-button>
          </template>
        </el-popconfirm>
        <el-popconfirm
            title="确定删除吗？"
            confirmButtonText="好的"
            cancelButtonText="不用了"
            icon="el-icon-info"
            placement="right"
            iconColor="red"
            @confirm="clearRow(row)"
        >
          <template #reference>
            <el-button type="danger" size="mini">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {defineComponent, onBeforeMount, reactive, toRefs} from "vue";
import {
  clearBlog,
  deleteBlog,
  getBlogList,
  getCateList,
  getTagList,
} from "@/utils/apiConfig";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";

let state = reactive({
  data: "",
  loading: true,
  cateList: [],
  tagList: [],
})
const router = useRouter()
const route = useRoute()
let {data, loading, cateList, tagList} = toRefs(state)
onBeforeMount(() => {
  getCate();
  getTags();
  getData();
})

function getCate() {
  getCateList().then(({data}) => {
    state.cateList = data;
  });
}

function getTags() {
  getTagList().then(({data}) => {
    state.tagList = data;
  });
}

function getData() {
  getBlogList({pageNum: 1, pageSize: 30}).then(({data}) => {
    console.log(data);
    state.data = data;
    state.loading = false;
  });
}

function editArticle(row) {
  router.push({
    name: "articleEdit",
    query: {
      id: row.blogId,
    },
  });
}

function deleteRow(row) {
  deleteBlog(row.blogId).then(({data}) => {
    if (data) {
      getData();
      ElMessage({
        message: "成功",
        type: "success"
      })
    }
  });
}

function clearRow(row) {
  clearBlog(row.blogId).then(({data}) => {
    if (data) {
      getData();
      ElMessage({
        message: "成功",
        type: "success"
      })
    }
  });
}
</script>

<style scoped></style>
