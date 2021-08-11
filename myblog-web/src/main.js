import ElementPlus from "element-plus";
import "element-plus/lib/theme-chalk/index.css";
import { createApp } from "vue";
import router from "./router";
import App from "./App.vue";
import "./index.css";
import VMdEditor from '@kangc/v-md-editor';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// highlightjs
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
    Hljs: hljs,
});
VMdEditor.use(githubTheme, {
    Hljs: hljs,
});
const app = createApp(App);
app.use(VMdPreview);
app.use(VMdEditor);
import store from "@/store";

app.use(ElementPlus);
app.use(router);
app.use(store);
app.mount("#app");
