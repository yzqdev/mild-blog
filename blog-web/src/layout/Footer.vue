<template>
  <footer class="admin-footer" v-if="theme.showFooter">
    <div class="footer-content">
      <span class="copyright">&copy; {{ currentYear }} Blog Admin</span>
      <span class="separator">|</span>
      <span class="powered">Powered by Vue + Element Plus</span>
    </div>
  </footer>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useStorage } from '@vueuse/core'
import { defaultTheme } from '@/constants/defaultTheme'

const theme = useStorage('themeConfig', { ...defaultTheme })
const currentYear = computed(() => new Date().getFullYear())
</script>

<style lang="scss" scoped>
.admin-footer {
  height: 48px;
  background: white;
  border-top: 1px solid #eee;
  display: flex;
  align-items: center;
  justify-content: center;

  .footer-content {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 13px;
    color: #999;

    .separator {
      color: #ddd;
    }
  }
}
</style>
