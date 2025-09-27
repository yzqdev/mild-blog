<template>
  <aside class="admin-sidebar" :class="{ collapsed: isCollapse }">
    <div class="sidebar-header">
      <div class="logo-section" v-if="!isCollapse">
        <span class="logo-text">Blog Admin</span>
      </div>
      <div class="logo-collapsed" v-else>
        <span class="logo-icon">B</span>
      </div>
    </div>

    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapse"
      :collapse-transition="false"
      class="sidebar-menu"
      router
    >
      <el-menu-item index="/admin/welcome">
        <el-icon><data-analysis /></el-icon>
        <template #title>仪表盘</template>
      </el-menu-item>

      <el-sub-menu index="blog">
        <template #title>
          <el-icon><document /></el-icon>
          <span>文章管理</span>
        </template>
        <el-menu-item index="/admin/article-list">文章列表</el-menu-item>
        <el-menu-item index="/admin/article-edit">写文章</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="content">
        <template #title>
          <el-icon><folder /></el-icon>
          <span>内容管理</span>
        </template>
        <el-menu-item index="/admin/category-list">分类管理</el-menu-item>
        <el-menu-item index="/admin/tag-list">标签管理</el-menu-item>
        <el-menu-item index="/admin/comment-list">评论管理</el-menu-item>
        <el-menu-item index="/admin/link-list">友链管理</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="system">
        <template #title>
          <el-icon><setting /></el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/admin/system-info">系统配置</el-menu-item>
        <el-menu-item index="/admin/img-list">图片管理</el-menu-item>
        <el-menu-item index="/admin/system-log">操作日志</el-menu-item>
      </el-sub-menu>
    </el-menu>

    <div class="sidebar-footer">
      <div class="version-info" v-if="!isCollapse">
        <span>v1.0.0</span>
      </div>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { DataAnalysis, Document, Folder, Setting } from '@element-plus/icons-vue'
import { useThemeStore } from '@/store/themeConfig'
import { useStorage } from '@vueuse/core'
import { defaultTheme } from '@/constants/defaultTheme'

const route = useRoute()
const { themeConfig } = useThemeStore()
const theme = useStorage('themeConfig', { ...defaultTheme })

const isCollapse = computed(() => theme.value.isCollapse)
const activeMenu = computed(() => route.path)
</script>

<style lang="scss" scoped>
.admin-sidebar {
  width: 220px;
  height: 100vh;
  background: linear-gradient(180deg, #1a1a2e 0%, #16213e 100%);
  display: flex;
  flex-direction: column;
  transition: width 0.3s ease;
  overflow: hidden;

  &.collapsed {
    width: 64px;
  }
}

.sidebar-header {
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);

  .logo-section {
    .logo-text {
      font-size: 1.25rem;
      font-weight: 700;
      color: white;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }
  }

  .logo-collapsed {
    .logo-icon {
      width: 32px;
      height: 32px;
      border-radius: 8px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1rem;
      font-weight: 700;
    }
  }
}

.sidebar-menu {
  flex: 1;
  border-right: none;
  background: transparent;
  overflow-y: auto;

  :deep(.el-menu) {
    background: transparent;
  }

  :deep(.el-menu-item),
  :deep(.el-sub-menu__title) {
    color: rgba(255, 255, 255, 0.7);
    height: 48px;
    line-height: 48px;
    margin: 4px 8px;
    border-radius: 8px;

    &:hover {
      background: rgba(255, 255, 255, 0.1);
      color: white;
    }

    &.is-active {
        background: var(--el-color-primary);
      color: white;
    }
  }

  :deep(.el-sub-menu .el-menu-item) {
    min-width: auto;
    padding-left: 50px !important;
    height: 44px;
    line-height: 44px;
    margin: 2px 8px;
    border-radius: 6px;
    font-size: 13px;
  }

  :deep(.el-sub-menu__icon-arrow) {
    color: rgba(255, 255, 255, 0.5);
  }
}

.sidebar-footer {
  padding: 16px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);

  .version-info {
    text-align: center;
    color: rgba(255, 255, 255, 0.4);
    font-size: 12px;
  }
}
</style>
