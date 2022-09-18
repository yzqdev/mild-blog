<template>
	<view class="login">
		<uni-forms ref="form" :modelValue="formData">
			<uni-forms-item label="用户名" required><uni-easyinput v-model="formData.username" placeholder="请输入姓名" /></uni-forms-item>
			<uni-forms-item label="密码" required><uni-easyinput v-model="formData.password" placeholder="请输入密码" /></uni-forms-item>
			<button class="button" @click="submit">登录</button>
		</uni-forms>
	</view>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { loginApi } from '../apis/static';
let formData = reactive({
	username: 'admin',
	password: '123456'
});
async function submit() {
	try{
		let res = await loginApi(formData.username, formData.password);
		console.log(res)
		if(res.success){
			uni.showToast({
				title: res.message,
				duration: 2000
			});
		}else{
			uni.showToast({
				title:res.message,
				
			})
		}
	}catch(e){
		console.log(e )
	}
}
</script>

<style lang="scss" scoped>
.login {
	padding: 20rpx;
}
</style>
