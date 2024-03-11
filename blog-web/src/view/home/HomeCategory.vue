<template>
  <div class="home-tag">
    <PassageList v-if="list && list.length > 0" :list="list" :loading="loading"></PassageList>
    <el-empty v-else description="暂无内容"></el-empty>
  </div>
</template>

<script setup>
import { getArticleByCate } from '@/utils/homeApi'
import PassageList from '@/components/PassageList.vue'
import { onBeforeMount, reactive, toRefs } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
let state = reactive({
  list: null,
  loading: false,
})
let { list, loading } = toRefs(state)
onBeforeMount(async () => {
  let id = route.params.id
  const { data } = await getArticleByCate(id, { pageNum: 1, pageSize: 30 })
  state.list = data
  state.loading = false
})
</script>

<style lang="scss" scoped>
.home-tag {
  flex: 1;
  padding: 20px 100px;
}
</style>
