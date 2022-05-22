
import {createApp} from "vue";
import router from "./router";
import App from "./App.vue";
import "./index.scss";

import store from "@/store";
import dayjs from "dayjs";
import {useMarkdown} from "@/utils/lazyUse";

const app = createApp(App);


app.config.globalProperties.$dayjs = dayjs;
useMarkdown(app)
app.use(router);
app.use(store);
app.mount("#app");
