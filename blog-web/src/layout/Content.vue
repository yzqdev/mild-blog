<template>
  <main class="admin-content" :class="{ 'with-padding': theme.contentPadding }">
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component" :key="$route.fullPath" />
      </transition>
    </router-view>
  </main>
</template>

<script setup lang="ts">
import { useStorage } from '@vueuse/core'
import { defaultTheme } from '@/constants/defaultTheme'

const theme = useStorage('themeConfig', { ...defaultTheme })
</script>

<style lang="scss" scoped>
.admin-content {
  flex: 1;
  background: #f5f7fa;
  overflow-y: auto;

  &.with-padding {
    padding: 20px;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
