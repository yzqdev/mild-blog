<template>
  <div class="admin-layout">
    <Sidebar />
    <div class="main-container">
      <Header />
      <Content />
      <Footer />
    </div>
  </div>
</template>

<script lang="ts" setup>
import Sidebar from './Sidebar.vue'
import Header from './Header.vue'
import Content from './Content.vue'
import Footer from './Footer.vue'

import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'

const store = useUserStore()
const router = useRouter()

onMounted(() => {
  const token = localStorage.getItem('token')
  if (token && token !== 'undefined') {
    store.setUserToken(token)
  } else {
    router.push('/admin')
  }
})
</script>

<style lang="scss" scoped>
.admin-layout {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
