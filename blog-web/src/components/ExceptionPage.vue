<template>
  <div class="exception">
    <el-result :status="type" :title="type" :subTitle="config[type].desc">
      <template v-slot:extra>
        <el-button type="primary" @click="handleToHome">返回仪表盘</el-button>
      </template>
    </el-result>
  </div>
</template>

<script>
import types from './type'

export default {
  name: 'Exception',
  props: {
    type: {
      type: String,
      default: '404',
    },
  },
  data() {
    return {
      config: types,
    }
  },
  methods: {
    handleToHome() {
      this.$router.push({ name: 'homeMain' })
    },
  },
}
</script>
