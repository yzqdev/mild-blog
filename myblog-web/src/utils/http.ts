import { useUserStore } from "@/store/user";
import qs from "qs";
import axios, { AxiosRequestConfig } from "axios";
import { Result } from "@/interface/result";

export const baseConfig = (await axios.get("/config.json")).data;
const instance = axios.create({
  baseURL: baseConfig.url + "/v2",
  timeout: 6000
});

instance.defaults.withCredentials = true;
instance.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    console.log("requestUrl==", config.url);

    // if (process.client) {
    config.headers["version"] = "1.0";
    config.headers["Content-Type"] = "application/json;charset=UTF-8";
    let userStore = useUserStore();
    if (userStore.token) {
      config.headers.token = userStore.token;
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

instance.interceptors.response.use(
  function(response) {
    // 对响应数据做点什么
    console.log("进入response");
    let data: Result = response.data;
    return data;
  },
  function(error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);
// @ts-ignore
instance.postForm = (url: string, data: any) => {
  return instance.post(url, qs.stringify(data), {
    headers: { "content-type": "application/x-www-form-urlencoded" }
  });
};
export default instance;
