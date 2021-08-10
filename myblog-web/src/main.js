import ElementPlus from 'element-plus'
import 'element-plus/lib/theme-chalk/index.css'
import {createApp} from 'vue'
import router from './router'
import App from './App.vue'
import './index.css'
import store from "@/store";

const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.use(store)
app.mount('#app')
