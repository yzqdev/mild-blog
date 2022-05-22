<template>
  <div>
    <el-button type="primary" @click="showUploadDialog">上传</el-button>
    <el-dialog v-model="uploadDialogVisible" @close="closeUpload">
      <file-pond
        name="img"
        ref="pond"
        label-idle="点击选择文件或者拖动文件到这里"
         :allow-multiple="true"
        accepted-file-types="image/jpeg, image/png"
        :server="server"
        v-bind:files="myFiles"
        fileValidateTypeLabelExpectedTypes="请选择 {lastType} 格式的文件"
        labelFileProcessing="上传中"
        labelFileProcessingAborted="取消上传"
        labelFileProcessingComplete="上传完成"
        labelFileProcessingError="上传错误"
        labelFileTypeNotAllowed="不支持当前文件格式"
        labelTapToCancel="点击取消"
        labelTapToRetry="点击重试"
        v-on:init="handleFilePondInit"
      />
    </el-dialog>
    <el-table :data="Imgs" fit>
      <el-table-column prop="imgName" label="图片名称"></el-table-column>
      <el-table-column prop="imgUrl" label="图片url"></el-table-column>
      <el-table-column prop="imgUrl" width="100" label="缩略图">
        <template v-slot="{ row }"
          ><el-image
            :preview-src-list="[getImgUrl(row.imgUrl)]"
            preview-teleported
            style="height: 30px"
            :src="getImgUrl(row.imgUrl)"
        /></template>
      </el-table-column>
      <el-table-column prop="imgSize" width="100" label="图片大小">
        <template v-slot="{ row }">
          {{ convertSize(row.imgSize) }}
        </template>
      </el-table-column>
      <el-table-column prop="imgPath" label="图片目录"></el-table-column>

      <el-table-column prop="uploadTime" label="上传时间">
        <template v-slot="{ row }">{{
          $dayjs(row.uploadTime).format("YYYY-MM-DD HH:mm:ss")
        }}</template>
      </el-table-column>

      <el-table-column label="操作">
        <template v-slot="{ row }">
          <el-popconfirm
            title="确定删除吗？"
            confirmButtonText="好的"
            cancelButtonText="不用了"
            icon="el-icon-info"
            placement="right"
            iconColor="red"
            @confirm="deleteRow(row)"
          >
            <template #reference>
              <el-button type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { baseUrl, delImg, getImgs, uploadUrl } from "@/utils/apiConfig";
import { convertSize } from "@/utils/utils";

import vueFilePond from "vue-filepond";

// Import FilePond styles
import "filepond/dist/filepond.min.css";

// Import FilePond plugins
// Please note that you need to install these plugins separately

// Import image preview plugin styles
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css";

// Import image preview and file type validation plugins
import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type";
import FilePondPluginImagePreview from "filepond-plugin-image-preview";

// Create component
const FilePond = vueFilePond(
  FilePondPluginFileValidateType,
  FilePondPluginImagePreview
);
import { computed, onBeforeMount, reactive, ref, toRefs } from "vue";
let state = reactive({
  Imgs: [],
});
let pond =ref()
let myFiles=$ref([])
let server=$ref(uploadUrl)
let uploadDialogVisible=$ref(false)
let { Imgs } = toRefs(state);
onBeforeMount(() => {
  getList();
});
function handleFilePondInit(){

}
function getImgUrl(url){
  return baseUrl+"/"+ url
}
function getList() {
  getImgs().then(({ data }) => {
    state.Imgs = data;
  });
}
function closeUpload() {
  pond.value.removeFiles()
  getList()
}
function showUploadDialog(){
uploadDialogVisible=true
}
function deleteRow(row) {
  delImg(row.id).then(({ data }) => {
    console.log(data);
    getList();
  });
}
</script>

<style scoped></style>
