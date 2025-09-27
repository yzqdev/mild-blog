<template>
  <q-page class="index-page">
    <div class="hero-section">
      <div class="hero-content">
        <h1 class="hero-title">欢迎来到我的博客</h1>
        <p class="hero-subtitle">记录生活点滴，分享技术心得</p>
        <q-input
          v-model="searchQuery"
          outlined
          placeholder="搜索文章..."
          class="hero-search"
          @keyup.enter="handleSearch"
        >
          <template v-slot:prepend>
            <q-icon name="search" />
          </template>
        </q-input>
      </div>
    </div>

    <div class="content-section">
      <div class="main-content">
        <div class="section-header">
          <h2>最新文章</h2>
          <q-btn flat color="primary" label="查看全部" to="/articles" />
        </div>

        <div class="article-list" v-if="!loading">
          <div
            v-for="article in passages"
            :key="article.blogId"
            class="article-card"
            @click="goToArticle(article.blogId)"
          >
            <div class="article-cover" v-if="article.coverImage">
              <img :src="article.coverImage" :alt="article.blogTitle" />
            </div>
            <div class="article-info">
              <h3 class="article-title">{{ article.blogTitle }}</h3>
              <p class="article-preface">{{ article.preface }}</p>
              <div class="article-meta">
                <span class="meta-item">
                  <q-icon name="schedule" />
                  {{ formatDate(article.createTime) }}
                </span>
                <span class="meta-item">
                  <q-icon name="visibility" />
                  {{ article.blogViews }} 阅读
                </span>
              </div>
            </div>
          </div>
        </div>

        <div v-else class="loading-state">
          <q-spinner-dots size="40px" color="primary" />
          <p>加载中...</p>
        </div>
      </div>

      <div class="sidebar">
        <div class="sidebar-card">
          <h3 class="sidebar-title">
            <q-icon name="label" /> 热门标签
          </h3>
          <div class="tag-list">
            <q-chip
              v-for="tag in hotTags"
              :key="tag.tagId"
              :label="tag.tagName"
              color="primary"
              outline
              clickable
              @click="goToTag(tag.tagId)"
            />
          </div>
        </div>

        <div class="sidebar-card">
          <h3 class="sidebar-title">
            <q-icon name="trending_up" /> 热门文章
          </h3>
          <div class="hot-article-list">
            <div
              v-for="(article, index) in hotBlogs"
              :key="article.blogId"
              class="hot-article-item"
              @click="goToArticle(article.blogId)"
            >
              <span class="hot-rank">{{ index + 1 }}</span>
              <span class="hot-title">{{ article.blogTitle }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getIndex } from 'boot/apis'

const router = useRouter()

const searchQuery = ref('')
const loading = ref(true)
const passages = ref<any[]>([])
const hotTags = ref<any[]>([])
const hotBlogs = ref<any[]>([])

function formatDate(dateStr: string) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

function goToArticle(id: string) {
  router.push(`/article/${id}`)
}

function goToTag(id: string) {
  router.push(`/tag/${id}`)
}

function handleSearch() {
  if (searchQuery.value.trim()) {
    router.push({ path: '/search', query: { q: searchQuery.value } })
  }
}

async function getData() {
  try {
    loading.value = true
    const res = await getIndex()
    const { blogPageResult, hotTags: tags, newBlogs } = res.data
    passages.value = blogPageResult || []
    hotTags.value = tags || []
    hotBlogs.value = newBlogs || []
  } catch (error) {
    console.error('Failed to load data:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getData()
})
</script>

<style lang="scss" scoped>
.index-page {
  min-height: 100vh;
}

.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 60px 20px;
  text-align: center;
  color: white;

  .hero-content {
    max-width: 600px;
    margin: 0 auto;
  }

  .hero-title {
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: 16px;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .hero-subtitle {
    font-size: 1.2rem;
    opacity: 0.9;
    margin-bottom: 32px;
  }

  .hero-search {
    max-width: 400px;
    margin: 0 auto;
    background: rgba(255, 255, 255, 0.95);
    border-radius: 25px;

    .q-field__control {
      border-color: transparent;
      border-radius: 25px;
    }
  }
}

.content-section {
  display: flex;
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
  gap: 32px;
}

.main-content {
  flex: 1;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;

    h2 {
      font-size: 1.5rem;
      font-weight: 600;
      color: #333;
      margin: 0;
    }
  }
}

.article-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-card {
  display: flex;
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  cursor: pointer;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  }

  .article-cover {
    width: 200px;
    min-height: 150px;
    flex-shrink: 0;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .article-info {
    flex: 1;
    padding: 20px;

    .article-title {
      font-size: 1.25rem;
      font-weight: 600;
      color: #333;
      margin: 0 0 12px 0;
      line-height: 1.4;
    }

    .article-preface {
      color: #666;
      font-size: 0.95rem;
      line-height: 1.6;
      margin: 0 0 16px 0;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    .article-meta {
      display: flex;
      gap: 20px;

      .meta-item {
        display: flex;
        align-items: center;
        gap: 6px;
        color: #999;
        font-size: 0.85rem;
      }
    }
  }
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 0;
  color: #999;
}

.sidebar {
  width: 300px;
  flex-shrink: 0;
}

.sidebar-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);

  .sidebar-title {
    font-size: 1rem;
    font-weight: 600;
    color: #333;
    margin: 0 0 16px 0;
    display: flex;
    align-items: center;
    gap: 8px;
  }
}

.tag-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.hot-article-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.hot-article-item {
  display: flex;
  align-items: center;
  gap: 12px;
  cursor: pointer;
  padding: 8px;
  border-radius: 8px;
  transition: background 0.2s;

  &:hover {
    background: #f5f5f5;
  }

  .hot-rank {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 0.75rem;
    font-weight: 600;
  }

  .hot-title {
    flex: 1;
    font-size: 0.9rem;
    color: #333;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

@media (max-width: 768px) {
  .content-section {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
  }

  .article-card {
    flex-direction: column;

    .article-cover {
      width: 100%;
      height: 200px;
    }
  }
}
</style>
