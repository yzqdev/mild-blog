import { getApiUrl } from "@/utils/getApiUrl";
import store from "@/store";
import qs from "qs";
import axios from "axios";
axios.defaults.withCredentials = true;
axios.interceptors.request.use(
  (config) => {
    console.log("requestUrl==", config.url);

    // if (process.client) {
    config.headers["version"] = "1.0";
    config.headers["Content-Type"] = "application/json;charset=UTF-8";
    if (store.getters.token) {
      config.headers.token = store.getters.token;
    }

    console.log(config);
    // }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
// 添加响应拦截器

axios.interceptors.response.use(
  function (response) {
    // 对响应数据做点什么
    console.log("进入response");
    let { data } = response;
    return data;
  },
  function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);
axios.postForm = (url, data) => {
  return axios.post(url, qs.stringify(data), {
    headers: { "content-type": "application/x-www-form-urlencoded" },
  });
};
axios.defaults.baseURL = getApiUrl();
export default axios;
