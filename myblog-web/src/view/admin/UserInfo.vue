<template>
  <el-form :model="user" label-width="100px">
    <el-form-item label="锁定状态" prop="locked">
      <label>{{ getLockInfo(user.locked) }}</label>
    </el-form-item>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="user.username"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickName">
      <el-input v-model="user.nickname" placeholder="请输入昵称"></el-input>
    </el-form-item>
    <el-form-item label="头像">
      <!--DOM模块-->
      <!--
          将auto-upload设置为false是为了关闭默认上传，
          过后将会使用单独的上传进行上传
          on-change事件获取当前upload组件的文件缓存
      -->
      <el-upload
        action=""
        :show-file-list="false"
        class="avatar-uploader"
        :auto-upload="false"
        :on-change="getUploadFile"
      >
        <img class="avatar" v-if="user.avatar" :src="user.avatar" alt="" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <!--我将裁剪的Dom放在了一个dialog弹框中-->
      <el-dialog title="图片剪裁" v-model="cropperVisible" append-to-body>
        <div class="cropper-content">
          <div class="cropper" style="text-align: center">
            <VueCropper
              ref="cropper"
              :img="option.img"
              :outputSize="option.size"
              :outputType="option.outputType"
              :info="true"
              :full="option.full"
              :canMove="option.canMove"
              :canMoveBox="option.canMoveBox"
              :original="option.original"
              :autoCrop="option.autoCrop"
              :fixed="option.fixed"
              :fixedNumber="option.fixedNumber"
              :centerBox="option.centerBox"
              :infoTrue="option.infoTrue"
              :fixedBox="option.fixedBox"
            ></VueCropper>
          </div>
        </div>
        <template #footer class="dialog-footer">
          <el-button @click="cropperVisible = false">取 消</el-button>
          <el-button type="primary" @click="finish">确认</el-button>
        </template>
      </el-dialog>
    </el-form-item>

    <el-button type="primary" @click="submitUserinfo">提交</el-button>
  </el-form>
</template>

<script setup>
import { onBeforeMount, onMounted, reactive, ref } from "vue";
import "vue-cropper/dist/index.css";
import { baseUrl, editUser, getUserInfo, uploadImg } from "@/utils/apiConfig";
import { VueCropper } from "vue-cropper";
import { ElMessage } from "element-plus";
let cropper = ref(null);
let user = $ref({});
const option = reactive({
  img: "", // 裁剪图片的地址
  info: true, // 裁剪框的大小信息
  outputSize: 0.8, // 裁剪生成图片的质量
  outputType: "jpeg", // 裁剪生成图片的格式
  canScale: false, // 图片是否允许滚轮缩放
  autoCrop: true, // 是否默认生成截图框
  // fixedBox: true, // 固定截图框大小 不允许改变
  fixed: true, // 是否开启截图框宽高固定比例
  fixedNumber: [], // 截图框的宽高比例
  full: true, // 是否输出原图比例的截图
  canMoveBox: false, // 截图框能否拖动
  original: false, // 上传图片按照原始比例渲染
  centerBox: false, // 截图框是否被限制在图片里面
  infoTrue: true, // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
});
let cropperVisible = $ref(false);

let fileinfo = reactive({ name: "", type: "" });
function finish() {
  console.log(cropper.value.getCropBlob);
  cropper.value.getCropBlob((data) => {
    // 获取当前裁剪好的数据
    // 注此时的data是一个Blob数据，部分接口接收的是File转化的FormData数据
    let formData = new FormData();
    formData.append(
      "img",
      new File(
        [data], // 将Blob类型转化成File类型
        fileinfo.name, // 设置File类型的文件名称
        { type: fileinfo.type } // 设置File类型的文件类型
      )
    );
    // 调用接口上传
    uploadImg(formData).then((result) => {
      console.log(
        `%c上传完成`,
        `color:red;font-size:16px;background:transparent`
      );
      console.log(result);

      user.avatar = baseUrl() + "/" + result.data.img.imgUrl;

      cropperVisible = false;
    });
  });
}

function getUploadFile(file, fileList) {
  console.log(
    `%c则是getupdatefile`,
    `color:red;font-size:16px;background:transparent`
  );
  console.log(file);
  let files = file.raw;
  fileinfo = file; // 保存下当前文件的一些基本信息
  let reader = new FileReader(); // 创建文件读取对象
  reader.onload = async (e) => {
    let data;
    if (typeof e.target.result === "object") {
      // 把Array Buffer转化为blob 如果是base64不需要
      data = window.URL.createObjectURL(new Blob([e.target.result]));
    } else {
      data = e.target.result;
    }
    option.img = data; // 设置option的初始image
    cropperVisible = true;
  };
  reader.readAsArrayBuffer(files);
  option.fixedNumber = [1, 1]; // 图片的裁剪宽高比在这里也可以进行设置
}

function getUserData() {
  getUserInfo().then(({ data }) => {
    user = data;
  });
}

function showCropper() {
  cropperVisible = true;
}

function getLockInfo(item) {
  if (item) {
    return "已锁定";
  } else {
    return "未锁定";
  }
}

function submitUserinfo() {
  editUser(user).then((res) => {
    if (res) {
      ElMessage({
        type: "success",
        message: "修改成功!",
      });
    }
  });
}

onBeforeMount(() => {
  getUserData();
});
</script>

<style lang="scss" scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
//设置裁剪框样式
.cropper-content {
  .cropper {
    width: auto;
    height: 800px;
  }
}
</style>
