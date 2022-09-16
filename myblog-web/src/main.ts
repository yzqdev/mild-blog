import { createApp } from 'vue'
import router from './router'
import App from './App.vue'
import './index.scss'
import { pinia } from '@/store'
import dayjs from 'dayjs'
import { useMarkdown } from '@/utils/lazyUse'
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'

const app = createApp(App)

app.config.globalProperties.$dayjs = dayjs
useMarkdown(app)
app.use(VueViewer)
app.use(router)
app.use(pinia)

app.mount('#app')
