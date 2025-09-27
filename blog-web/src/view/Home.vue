<template>
  <div class="home-layout">
    <header class="home-header">
      <div class="header-content">
        <div class="logo-section">
          <router-link to="/home/main" class="logo-link">
            <span class="logo-text">{{ configs.websiteName }}</span>
          </router-link>
        </div>

        <nav class="nav-section">
          <router-link
            v-for="item in navs"
            :key="item.id"
            :to="item.link"
            class="nav-link"
            active-class="nav-active"
          >
            {{ item.text }}
          </router-link>
        </nav>

        <div class="header-actions">
          <el-input
            v-model="searchQuery"
            placeholder="搜索..."
            prefix-icon="Search"
            size="default"
            class="search-input"
            @keyup.enter="handleSearch"
          />
        </div>
      </div>
    </header>

    <main class="main-content">
      <router-view />
    </main>

    <footer class="home-footer">
      <div class="footer-content">
        <div class="footer-info">
          <p class="copyright">&copy; {{ currentYear }} {{ configs.sysAuthor }} 个人博客</p>
          <p class="powered">
            {{ configs.sysCopyRight }}
            <span class="heart">&heartsuit;</span>
            Powered by <a href="#" target="_blank">Blog System</a>
          </p>
        </div>
        <div class="footer-meta">
          <span>版本: {{ configs.sysVersion }}</span>
          <span v-if="configs.sysUpdateTime">更新于: {{ configs.sysUpdateTime }}</span>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, reactive, toRefs, computed, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { getConfigs } from '@/utils/homeApi'
import { useConfigStore } from '@/store/sysConfig'

const router = useRouter()
const configStore = useConfigStore()

const searchQuery = ref('')

const state = reactive({
  navs: [
    { text: '主页', id: 1, link: '/home/main' },
    { text: '标签', id: 2, link: '/home/tags' },
    { text: '分类', id: 3, link: '/home/categories' },
    { text: '时间线', id: 6, link: '/home/timeline' },
    { text: '友情链接', id: 4, link: '/home/link' },
    { text: '关于', id: 5, link: '/home/about' },
  ],
  configs: {
    sysAuthor: '',
    sysAuthorImg: '',
    sysCopyRight: '',
    sysEmail: '',
    sysUpdateTime: '',
    sysUrl: '',
    sysVersion: '',
    websiteName: '',
    filing: '',
  },
})

const { navs, configs } = toRefs(state)

const currentYear = computed(() => new Date().getFullYear())

function handleSearch() {
  if (searchQuery.value.trim()) {
    router.push({ path: '/home/search', query: { q: searchQuery.value } })
  }
}

async function getData() {
  try {
    let { data } = await getConfigs()
    configStore.setSysConfig(data)
    state.configs = data
  } catch (error) {
    console.error('Failed to load configs:', error)
  }
}

onBeforeMount(async () => {
  await getData()
})
</script>

<style lang="scss" scoped>
.home-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.home-header {
  background: white;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 100;

  .header-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 24px;
    height: 64px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .logo-section {
    .logo-link {
      text-decoration: none;
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .logo-text {
      font-size: 1.5rem;
      font-weight: 700;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }
  }

  .nav-section {
    display: flex;
    gap: 8px;

    .nav-link {
      padding: 8px 16px;
      border-radius: 8px;
      text-decoration: none;
      color: #666;
      font-size: 0.95rem;
      transition: all 0.3s ease;

      &:hover {
        background: #f5f5f5;
        color: #333;
      }

      &.nav-active {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
      }
    }
  }

  .header-actions {
    .search-input {
      width: 200px;

      :deep(.el-input__wrapper) {
        border-radius: 20px;
        box-shadow: 0 0 0 1px #dcdfe6 inset;

        &:hover {
          box-shadow: 0 0 0 1px #667eea inset;
        }
      }
    }
  }
}

.main-content {
  flex: 1;
  background: #f5f7fa;
}

.home-footer {
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
  color: white;
  padding: 40px 24px;
  margin-top: auto;

  .footer-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;

    @media (max-width: 768px) {
      flex-direction: column;
      text-align: center;
      gap: 16px;
    }
  }

  .footer-info {
    .copyright {
      font-size: 1rem;
      margin: 0 0 8px 0;
      font-weight: 500;
    }

    .powered {
      font-size: 0.85rem;
      opacity: 0.8;
      margin: 0;

      .heart {
        color: #ff6b6b;
        margin: 0 4px;
      }

      a {
        color: #667eea;
        text-decoration: none;

        &:hover {
          text-decoration: underline;
        }
      }
    }
  }

  .footer-meta {
    display: flex;
    gap: 24px;
    font-size: 0.85rem;
    opacity: 0.7;

    @media (max-width: 768px) {
      flex-direction: column;
      gap: 8px;
    }
  }
}
</style>
