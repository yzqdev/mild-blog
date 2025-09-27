<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated class="glass-header">
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
          class="menu-btn"
        />

        <q-toolbar-title class="title-section">
          <span class="blog-title">{{ blogTitle }}</span>
          <span class="blog-subtitle">记录生活，分享技术</span>
        </q-toolbar-title>

        <div class="header-actions">
          <q-input
            v-model="searchQuery"
            dense
            outlined
            placeholder="搜索文章..."
            class="search-input"
            @keyup.enter="handleSearch"
          >
            <template v-slot:prepend>
              <q-icon name="search" />
            </template>
          </q-input>

          <q-btn flat round icon="dark_mode" @click="toggleDark" />
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      class="custom-drawer"
    >
      <div class="drawer-header">
        <q-avatar size="80px" class="avatar-section">
          <img :src="authorAvatar" />
        </q-avatar>
        <div class="author-info">
          <div class="author-name">{{ authorName }}</div>
          <div class="author-desc">全栈开发者</div>
        </div>
      </div>

      <q-list class="nav-list">
        <q-item-label header class="nav-header">
          <q-icon name="menu" /> 导航菜单
        </q-item-label>

        <q-item
          v-for="item in navItems"
          :key="item.title"
          clickable
          v-ripple
          :to="item.link"
          class="nav-item"
          active-class="nav-item-active"
        >
          <q-item-section avatar>
            <q-icon :name="item.icon" />
          </q-item-section>
          <q-item-section>
            <q-item-label>{{ item.title }}</q-item-label>
            <q-item-label caption>{{ item.caption }}</q-item-label>
          </q-item-section>
        </q-item>

        <q-separator class="q-my-md" />

        <q-item-label header class="nav-header">
          <q-icon name="link" /> 友情链接
        </q-item-label>

        <q-item
          v-for="link in friendLinks"
          :key="link.title"
          clickable
          v-ripple
          :href="link.link"
          target="_blank"
          class="nav-item"
        >
          <q-item-section avatar>
            <q-icon :name="link.icon" />
          </q-item-section>
          <q-item-section>
            <q-item-label>{{ link.title }}</q-item-label>
            <q-item-label caption>{{ link.caption }}</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container class="page-container">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useQuasar } from 'quasar'
import { useRouter } from 'vue-router'

const $q = useQuasar()
const router = useRouter()

const leftDrawerOpen = ref(false)
const searchQuery = ref('')
const isDark = ref(false)

const blogTitle = ref('我的博客')
const authorAvatar = ref('https://cdn.quasar.dev/logo-v2/svg/logo.svg')
const authorName = ref('Yanni')

const navItems = [
  { title: '首页', caption: '最新文章', icon: 'home', link: '/' },
  { title: '标签', caption: '按标签浏览', icon: 'label', link: '/tags' },
  { title: '分类', caption: '按分类浏览', icon: 'folder', link: '/categories' },
  { title: '时间线', caption: '文章归档', icon: 'timeline', link: '/timeline' },
  { title: '友情链接', caption: '推荐网站', icon: 'link', link: '/links' },
  { title: '关于', caption: '关于我', icon: 'person', link: '/about' },
]

const friendLinks = [
  { title: 'GitHub', caption: '开源项目', icon: 'code', link: 'https://github.com' },
  { title: '掘金', caption: '技术社区', icon: 'article', link: 'https://juejin.cn' },
]

function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

function toggleDark() {
  isDark.value = !isDark.value
  $q.dark.set(isDark.value)
}

function handleSearch() {
  if (searchQuery.value.trim()) {
    router.push({ path: '/search', query: { q: searchQuery.value } })
  }
}
</script>

<style lang="scss">
.glass-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.title-section {
  display: flex;
  flex-direction: column;
  align-items: flex-start;

  .blog-title {
    font-size: 1.2rem;
    font-weight: 700;
    letter-spacing: 1px;
  }

  .blog-subtitle {
    font-size: 0.75rem;
    opacity: 0.8;
  }
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 8px;

  .search-input {
    width: 200px;
    background: rgba(255, 255, 255, 0.15);
    border-radius: 20px;

    .q-field__control {
      border-color: transparent;
      color: white;

      &::before {
        border-color: transparent;
      }
    }

    .q-field__native {
      color: white;

      &::placeholder {
        color: rgba(255, 255, 255, 0.7);
      }
    }
  }
}

.custom-drawer {
  background: linear-gradient(180deg, #f8f9fa 0%, #ffffff 100%);

  .drawer-header {
    padding: 24px 16px;
    text-align: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;

    .avatar-section {
      border: 4px solid white;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
      margin-bottom: 12px;
    }

    .author-info {
      .author-name {
        font-size: 1.2rem;
        font-weight: 600;
        margin-bottom: 4px;
      }

      .author-desc {
        font-size: 0.85rem;
        opacity: 0.9;
      }
    }
  }

  .nav-header {
    font-size: 0.75rem;
    text-transform: uppercase;
    letter-spacing: 1px;
    color: #666;
    padding: 8px 16px;
  }

  .nav-item {
    margin: 2px 8px;
    border-radius: 8px;
    transition: all 0.3s ease;

    &:hover {
      background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
    }

    &.nav-item-active {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;

      .q-item__label--caption {
        color: rgba(255, 255, 255, 0.8);
      }
    }
  }
}

.page-container {
  background: #f5f7fa;
}

// Global animations
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
