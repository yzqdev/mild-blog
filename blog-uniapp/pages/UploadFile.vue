 
<template>
	<view class="upload-file">
		<lsj-upload
			ref="lsjUpload"
			childId="upload1"
			:width="width"
			:height="height"
			:option="option"
			:size="size"
			:formats="formats"
			:debug="debug"
			:instantly="instantly"
			@progress="progress"
			@change="onChange"
		>
			<view class="btn" :style="{ width: width, height: height }">选择附件</view>
		</lsj-upload>

		<view class="padding">
			<view>已选择文件列表：</view>

			<!-- #ifndef MP-WEIXIN -->
			<view v-for="(item, index) in files.values()" :key="index">
				<image style="width: 100rpx;height: 100rpx;" :src="item.path" mode="widthFix"></image>
				<text>{{ item.path }}</text>
				<text>{{ item.name }}</text>
				<text style="margin-left: 10rpx;">大小：{{ item.size }}</text>
				<text style="margin-left: 10rpx;">状态：{{ item.type }}</text>
				<text style="margin-left: 10rpx;">进度：{{ item.progress }}</text>
				<!-- <text style="margin-left: 10rpx;" v-if="item.responseText">服务端返回演示：{{item.responseText.code}}</text> -->
				<text @click="clear(item.name)" style="margin-left: 10rpx;padding: 0 10rpx;border: 1rpx solid #007AFF;">删除</text>
		 </view>
			<!-- #endif -->

			<!-- #ifdef MP-WEIXIN -->
			<view v-for="(item, index) in wxFiles" :key="index">
				<text>{{ item.name }}</text>
				<text style="margin-left: 10rpx;">大小：{{ item.size }}</text>
		 	<text style="margin-left: 10rpx;">状态：{{ item.type }}</text>
				<text style="margin-left: 10rpx;">进度：{{ item.progress }}</text>
				<view><button>删除</button></view>
			</view>
			<!-- #endif -->
		</view>
		<button type="primary" @click="upload">手动上传</button>
	</view>
</template>

<script>
export default {
    data() {
        return {
            // 上传接口参数
            option: {
                // 上传服务器地址，此地址需要替换为你的接口地址
                url: 'http://localhost:8080/upload',
                // 上传附件的key
                name: 'file',
                // 根据你接口需求自定义请求头
                header: {
                    // 'Authorization': 'bearer eyJhbGciOiJSUzI1NiIsI',
                    // 'uid': '27682',
                    // 'client': 'app',
                    // 'accountid': 'DP',
										     // 'content-type':"multipart/form-data"
                },
                // 根据你接口需求自定义body参数
                formData: {
                     'orderId': 1000,
										 file:''
                }
            },
            // 选择文件后是否立即自动上传，true=选择后立即上传
            instantly: true,
            // 必传宽高且宽高应与slot宽高保持一致
            width: '180rpx',
            height: '180rpx',
            // 限制允许选择的格式，空串=不限制，默认为空
            formats: 'png,jpg,mp4',
            // 文件上传大小限制
            size: 10,
            // 文件回显列表
            files: new Map(),
            // 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
            wxFiles: [],
            // 是否打印日志
            debug: true,

            // 演示用
            tabIndex: 0,
            list:[],
        }
    },
    onReady() {
        setTimeout(()=>{
            console.log('----演示动态更新参数-----');
            this.$refs.lsjUpload.setData('formData.orderId','动态设置的参数');

            console.log('以下注释内容为-动态更新参数更多演示，放开后可查看演示效果');
            // 修改option对象的name属性
            // this.$refs.lsjUpload.setData('name','myFile');

            // 修改option对象的formData内的属性
            // this.$refs.lsjUpload.setData('formData.appid','1111');

            // 替换option对象的formData
            // this.$refs.lsjUpload.setData('formData',{appid:'222'});

            // option对象的formData新增属性
            // this.$refs.lsjUpload.setData('formData.newkey','新插入到formData的属性');

            // ---------演示初始化值，用于已提交后再次编辑时需带入已上传文件-------
            // 方式1=传入数组
            let files1 = [ ];

            // 方式2=传入Map对象
            let files2 = new Map();
            files2.set('1.png',{name: '1.png'})

            // 设置初始files列表
            this.$refs.lsjUpload.setFiles(files1);

        },2000)
    },
    methods: {
        // 某文件上传结束回调(成功失败都回调)
        onuploadEnd(item) {
            console.log(`${item.name}已上传结束，上传状态=${item.type}`);

            // 更新当前状态变化的文件
            this.files.set(item.name,item);

            // 演示上传完成后取服务端数据
            if (item['responseText']) {
                console.log('演示服务器返回的字符串JSON转对象');
                this.files.get(item.name).responseText = JSON.parse(item.responseText);
            }

            // 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
            // #ifdef MP-WEIXIN
            this.wxFiles = [...this.files.values()];
            // #endif

            // 强制更新视图
            this.$forceUpdate();

            // ---可删除--演示判断是否所有文件均已上传成功
            let isAll = [...this.files.values()].find(item=>item.type!=='success');
            if (!isAll) {
                console.log('已全部上传完毕');
            }
            else {
                console.log(isAll.name+'待上传');
            }

        },
        // 上传进度回调
        progress(item) {
            // 更新当前状态变化的文件
            this.files.set(item.name,item);
 this.files.forEach(item=>{
					 	this.option.formData.file=item.path
					 	console.log(this.option.formData)
					 })
            console.log('打印对象',JSON.stringify(this.files.get(item.name)));
            // 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
            // #ifdef MP-WEIXIN
            this.wxFiles = [...this.files.values()];
            // #endif

            // 强制更新视图
            this.$forceUpdate();

        },
        // 文件选择回调
        onChange(files) {
            // 更新选择的文件
            this.files = files;
					
            // 强制更新视图
            this.$forceUpdate();

            // 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
            // #ifdef MP-WEIXIN
            this.wxFiles = [...this.files.values()];
            // #endif
        },
        // 手动上传
        upload() {
				 console.log("手动上传=====================================>")
				
            // name=指定文件名，不指定则上传所有type等于waiting和fail的文件
            this.$refs.lsjUpload.upload();
        },
        // 移除某个文件
        clear(name) {
            // name=指定文件名，不传name默认移除所有文件
            this.$refs.lsjUpload.clear(name);
        },

        /**
         * 以下为演示
         */
        // DOM重排演示，重排后组件内部updated默认会触发show方法,若特殊情况未能触发updated也可以手动调用一次show()
        // 什么是DOM重排？自行百度去~
        add() {
            this.list.push('DOM重排测试');
        },
        // 切换视图演示，APP端因为是webview，层级比view高，
        // 此时若不希望点击触发选择文件，需要手动调用hide()
        // 手动调用hide后，需要调用show()才能恢复触发面
        onTab(tabIndex) {
            this.tabIndex = tabIndex;

            if (tabIndex == 0 ) {
                this.$nextTick(()=>{
                    this.$refs.lsjUpload.show();
                })
            }
            else {
                this.$refs.lsjUpload.hide();
            }
        },
        // 打开nvue窗口
        open() {
            uni.navigateTo({
                url: '/pages/nvue-demo/nvue-demo'
            });
        }
    }
}
</script>

<style lang="scss" scoped>
	.upload-file{
		margin: 10upx;
		background-color: white
	}
</style>
