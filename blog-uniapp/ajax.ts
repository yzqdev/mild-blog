import ajax from '@/uni_modules/u-ajax/js_sdk';


let apiUrl='http://192.168.0.102:2801/v2'
// 创建请求实例
const instance = ajax.create({
	// 初始配置
	baseURL: apiUrl
});

// 添加请求拦截器
instance.interceptors.request.use(
	config => {
		// 在发送请求前做些什么
		return config;
	},
	error => {
		console.log('网络问题');
		console.log(error);
		// 对请求错误做些什么
		return Promise.reject(error);
	}
);

// 添加响应拦截器
instance.interceptors.response.use(
	response => {
		// 对响应数据做些什么
		let { data } = response;
		return data;
	},
	error => {
		console.log('返回数据');
		console.log(error);
		// 对响应错误做些什么
		return Promise.reject(error);
	}
);

// 导出 create 创建后的实例
export default instance;
