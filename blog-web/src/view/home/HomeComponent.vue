<template>
  <div class="home-component" v-loading="loading">
    <div v-if="routeName == `tags`" class="d-flex">
      <el-tag style="margin: 0 10px; cursor: pointer" v-for="(item, index) in data" @click="gotoTag(item)" :key="`tags${index}`">{{ item.tagName }}</el-tag>
    </div>
    <div v-if="routeName == `cates`" class="d-flex">
      <el-button type="plain" class="cate-span" v-for="(item, index) in data" :key="`cate${index}`" @click="gotoCate(item)">{{ item.categoryName }}</el-button>
    </div>
    <div v-if="routeName == `timeline`" class="timeline">
      <el-timeline>
        <el-timeline-item v-for="(item, index) in timelines" :key="`timeline${index}`" :timestamp="formatTime(item.updateTime)" placement="top">
          <el-card @click="gotoBlog(item)">
            <h4>{{ item.blogTitle }}</h4>
            <p>{{ item.blogPreface }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <div v-if="routeName == 'search'">
      <passage-list :list="keywordList" :loading="false"></passage-list>
    </div>
  </div>
</template>

<script setup>
import { reactive, toRefs, watch } from 'vue'
import { getHomeCates, getHomeTags, getSearch, getTimeline } from '@/utils/homeApi'
import PassageList from '@/components/PassageList.vue'
import { useRoute, useRouter } from 'vue-router'
import dayjs from 'dayjs'

let state = reactive({
  loading: true,
  routeName: 'tags',
  data: [],
  pageNum: 1,
  pageSize: 55,
  timelines: [],
  keywordList: [],
})
const router = useRouter()
const route = useRoute()
let { loading, routeName, data, pageNum, pageSize, timelines, keywordList } = toRefs(state)

function gotoBlog(item) {
  router.push('/home/blog/' + item.blogId)
}

function formatTime(time) {
  return dayjs(time).format('YYYY-MM-DD HH:mm:ss')
}

function gotoTag(item) {
  router.push('/home/tag/' + item.tagId)
}

function gotoCate(item) {
  router.push('/home/category/' + item.categoryId)
}

watch(
  () => route,
  async (val, preVal) => {
    switch (val.name) {
      case 'homeTags':
        state.routeName = 'tags'
        let { data } = await getHomeTags()
        state.data = data
        state.loading = false
        break
      case 'homeCategories':
        state.routeName = 'cates'
        let res = await getHomeCates()
        state.data = res.data
        state.loading = false
        break
      case 'homeTimeline':
        state.routeName = 'timeline'
        getTimeline({
          pageNum: state.pageNum,
          pageSize: state.pageSize,
        }).then((res) => {
          state.timelines = res.data
          state.loading = false
        })

        break
      case 'homeSearch':
        state.routeName = 'search'
        getSearch(route.query.text).then(({ data }) => {
          console.log(data)
          state.keywordList = data
        })

        state.loading = false
        break
      default:
        break
    }
  },
  { immediate: true, deep: true },
)
</script>

<style lang="scss" scoped>
.home-component {
  flex: 1;
  margin: 20px;
  padding: 20px;
  border: 1px solid #f5f5f5;
  border-radius: 5px;

  .cate-span {
    border: 1px solid #53a8ff;
  }

  :deep(.el-card) {
    &:hover {
      cursor: pointer;
    }
  }

  .timeline {
    width: 80%;
    padding: 10px 50px;
  }

  .d-flex {
    display: flex;
    justify-content: center;
  }
}
</style>
