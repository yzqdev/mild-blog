<template>
  <header class="admin-header">
    <div class="header-left">
      <el-button
        :icon="isExpand ? 'Fold' : 'Expand'"
        text
        @click="toggleSidebar"
        class="collapse-btn"
      />
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="currentRoute">{{ currentRoute }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="header-right">
      <el-dropdown trigger="click">
        <el-badge :value="comments.length" :hidden="comments.length === 0" class="notification-badge">
          <el-icon :size="20" class="header-icon"><bell /></el-icon>
        </el-badge>
        <template #dropdown>
          <div class="notification-dropdown">
            <div class="notification-header">
              <span class="notification-title">通知</span>
              <el-button text size="small" @click="readAll">全部已读</el-button>
            </div>
            <div class="notification-list">
              <div
                v-for="item in comments"
                :key="item.id"
                class="notification-item"
              >
                <el-avatar :size="36" :src="item.avatar_url || defaultAvatar" />
                <div class="notification-content">
                  <div class="notification-text">{{ item.content }}</div>
                  <div class="notification-time">{{ formatTime(item.created_at) }}</div>
                </div>
              </div>
              <div v-if="comments.length === 0" class="empty-notification">
                暂无新通知
              </div>
            </div>
          </div>
        </template>
      </el-dropdown>

      <el-icon @click="toggleFullscreen" :size="20" class="header-icon">
        <full-screen />
      </el-icon>

      <el-icon @click="showSettings" :size="20" class="header-icon">
        <setting />
      </el-icon>

      <el-dropdown>
        <div class="user-info">
          <el-avatar :size="32" :src="userInfo.avatar || defaultAvatar" />
          <span class="username">{{ userInfo.nickname || userInfo.username }}</span>
          <el-icon><arrow-down /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="gotoRoute('userInfo')">
              <el-icon><user /></el-icon> 个人中心
            </el-dropdown-item>
            <el-dropdown-item divided @click="logout">
              <el-icon><switch-button /></el-icon> 退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <el-drawer
      v-model="settingDraw"
      title="主题配置"
      direction="rtl"
      size="320px"
    >
      <div class="settings-content">
        <div class="setting-item">
          <span class="setting-label">显示边距</span>
          <el-switch v-model="theme.contentPadding" />
        </div>
        <div class="setting-item">
          <span class="setting-label">显示 Footer</span>
          <el-switch v-model="theme.showFooter" />
        </div>
        <div class="setting-item">
          <span class="setting-label">侧边栏折叠</span>
          <el-switch v-model="theme.isCollapse" />
        </div>
      </div>
    </el-drawer>
  </header>
</template>

<script setup lang="ts">
import { ArrowDown, Bell, FullScreen, Setting, User, SwitchButton } from '@element-plus/icons-vue'
import { useRouter, useRoute } from 'vue-router'
import { computed, onBeforeMount, ref, watch } from 'vue'
import { useThemeStore } from '@/store/themeConfig'
import { defaultTheme } from '@/constants/defaultTheme'
import { useUserStore } from '@/store/user'
import { UserState } from '@/interface/storeTypes'
import { useFullscreen, useStorage } from '@vueuse/core'
import { getUserInfo } from '@/utils/apiConfig'
import { formatTime } from '@/utils/utils'

const router = useRouter()
const route = useRoute()
const { setThemeConfig } = useThemeStore()
const userStore = useUserStore()

const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'

const userInfo: UserState = computed(() => userStore.$state)
const isExpand = computed(() => !theme.value.isCollapse)

const currentRoute = computed(() => {
  const routeMap: Record<string, string> = {
    dashboard: '仪表盘',
    blogList: '文章管理',
    blogEdit: '文章编辑',
    category: '分类管理',
    tag: '标签管理',
    comment: '评论管理',
    link: '友链管理',
    config: '系统配置',
    img: '图片管理',
    log: '操作日志',
    userInfo: '个人中心',
  }
  return routeMap[route.name as string] || ''
})

const comments = ref<any[]>([])
const settingDraw = ref<boolean>(false)
const theme = useStorage('themeConfig', { ...defaultTheme })

const { isFullscreen, toggle: toggleFullscreen } = useFullscreen()

function readAll() {
  comments.value = []
}

function showSettings() {
  settingDraw.value = true
}

function toggleSidebar() {
  theme.value.isCollapse = !theme.value.isCollapse
}

function gotoRoute(name: string) {
  router.push({ name })
}

function logout() {
  localStorage.clear()
  router.push({ name: 'adminLogin' })
}

async function getUser() {
  try {
    const res = await getUserInfo()
    if (res.success) {
      userStore.setUser(res.data)
    }
  } catch (e) {
    console.error('Failed to get user info:', e)
  }
}

onBeforeMount(() => {
  getUser()
})

watch(theme, (val) => {
  setThemeConfig(val)
}, { immediate: true })
</script>

<style lang="scss" scoped>
.admin-header {
  height: 56px;
  background: white;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;

  .collapse-btn {
    font-size: 20px;
    color: #666;

    &:hover {
      color: #667eea;
    }
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;

  .header-icon {
    color: #666;
    cursor: pointer;
    padding: 8px;
    border-radius: 8px;
    transition: all 0.2s;

    &:hover {
      background: #f5f5f5;
      color: #667eea;
    }
  }

  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    padding: 4px 12px;
    border-radius: 8px;
    transition: background 0.2s;

    &:hover {
      background: #f5f5f5;
    }

    .username {
      font-size: 14px;
      color: #333;
    }
  }
}

.notification-badge {
  cursor: pointer;
}

.notification-dropdown {
  width: 320px;
  max-height: 400px;

  .notification-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 16px;
    border-bottom: 1px solid #eee;

    .notification-title {
      font-weight: 600;
      font-size: 14px;
    }
  }

  .notification-list {
    max-height: 300px;
    overflow-y: auto;
  }

  .notification-item {
    display: flex;
    gap: 12px;
    padding: 12px 16px;
    border-bottom: 1px solid #f5f5f5;
    transition: background 0.2s;

    &:hover {
      background: #f9f9f9;
    }

    .notification-content {
      flex: 1;

      .notification-text {
        font-size: 13px;
        color: #333;
        margin-bottom: 4px;
      }

      .notification-time {
        font-size: 12px;
        color: #999;
      }
    }
  }

  .empty-notification {
    text-align: center;
    padding: 40px 0;
    color: #999;
    font-size: 14px;
  }
}

.settings-content {
  padding: 20px;

  .setting-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #f5f5f5;

    .setting-label {
      font-size: 14px;
      color: #333;
    }
  }
}
</style>
