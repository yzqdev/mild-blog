import { boot } from 'quasar/wrappers';
import axios, { AxiosInstance } from 'axios';
import qs from 'qs'
declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $axios: AxiosInstance;
  }
}

// Be careful when using SSR for cross-request state pollution
// due to creating a Singleton instance here;
// If any client changes this (global) instance, it might be a
// good idea to move this instance creation inside of the
// "export default () => {}" function below (which runs individually
// for each client)
const api:AxiosInstance = axios.create({ baseURL: 'http://localhost:2801/v2' });

api.defaults.withCredentials = true;
api.interceptors.request.use(
    (config) => {
      console.log("requestUrl==", config.url);

      // if (process.client) {
      config.headers["version"] = "1.0";
      config.headers["Content-Type"] = "application/json;charset=UTF-8";
      // if (store.getters.token) {
      //   config.headers.token = store.getters.token;
      // }

      console.log(config);
      // }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);
// 添加响应拦截器

api.interceptors.response.use(
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
// @ts-ignore
api.postForm = (url:string, data:object) => {
  return api.post(url, qs.stringify(data), {
    headers: { "content-type": "application/x-www-form-urlencoded" },
  });
};
export default boot(({ app }) => {
  // for use inside Vue files (Options API) through this.$axios and this.$api

  app.config.globalProperties.$axios = axios;
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = api;
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API
});

export { api };
