import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import {createApp} from "vue";
import router from "./router";
import App from "./App.vue";
import "./index.less";
import VueMarkdownEditor from "@kangc/v-md-editor";
import VMdPreview from "@kangc/v-md-editor/lib/preview";
import "@kangc/v-md-editor/lib/style/preview.css";

import "@kangc/v-md-editor/lib/style/base-editor.css";
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index';
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css';
import createTipPlugin from '@kangc/v-md-editor/lib/plugins/tip/index';
import '@kangc/v-md-editor/lib/plugins/tip/tip.css';

import Prism from 'prismjs'
import store from "@/store";
import dayjs from "dayjs";

VMdPreview.use(vuepressTheme, {
    Prism
});
VueMarkdownEditor.use(vuepressTheme, {
    Prism
});
import '@kangc/v-md-editor/lib/plugins/mermaid/mermaid.css';
import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index';
import '@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css';

VueMarkdownEditor.use(createTodoListPlugin());
VueMarkdownEditor.use(createTipPlugin());
VueMarkdownEditor.use(createEmojiPlugin());
VMdPreview.use(createEmojiPlugin())
VMdPreview.use(createTipPlugin())
VMdPreview.use(createTodoListPlugin());
const app = createApp(App);
app.use(VMdPreview);
app.use(VueMarkdownEditor);

app.config.globalProperties.$dayjs = dayjs;
app.use(ElementPlus);
app.use(router);
app.use(store);
app.mount("#app");
