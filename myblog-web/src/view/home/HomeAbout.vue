<template>
  <div style="flex: 1" class="home-about">
    <a href="https://vueuse.org/">vueuse</a>
    <img :src="avatar" class="header-avatar" />
    <br />
    我是刻晴，璃月七星中的玉衡
    <br />
    耽误的时间太多，事情可就做不完了
    <br />
    变革的时机即将到来，璃月维持千年的秩序即将被改写，你愿意和我一起见证吗？
  </div>
</template>

<script setup lang="ts">
import { useStorage } from '@vueuse/core/index'
import { useConfigStore } from '@/store/sysConfig'
let configStore = useConfigStore()
import { SysConfig } from '@/type/storeTypes'
import { defaultSysConfig } from '@/constants/defaultTheme'
import { onBeforeMount } from 'vue'

let avatar = $ref('')
onBeforeMount(() => {
  avatar = configStore.sysAuthorImg
})
</script>

<style lang="scss" scoped>
.home-about {
  padding: 40px;
  .header-avatar {
    width: 10rem;
  }
}
</style>
