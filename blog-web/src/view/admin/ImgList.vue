<template>
  <div>
    <el-button type="primary" @click="showUploadDialog">
      上传
      <el-icon><upload /></el-icon>
    </el-button>
    <el-dialog title="上传文件" v-model="uploadDialogVisible" @close="closeUpload">
      <file-pond name="img" ref="pond" label-idle="点击选择文件或者拖动文件到这里" :allow-multiple="true" accepted-file-types="image/jpeg, image/png"  v-bind:files="myFiles"  fileValidateTypeLabelExpectedTypes="请选择 {lastType} 格式的文件" labelFileProcessing="上传中" labelFileProcessingAborted="取消上传" labelFileProcessingComplete="上传完成" labelFileProcessingError="上传错误" labelFileTypeNotAllowed="不支持当前文件格式" labelTapToCancel="点击取消" labelTapToRetry="点击重试" v-on:init="handleFilePondInit" />
    </el-dialog>
    <el-dialog class="detail-dialog" title="图片详情" v-model="detailDialogVisible">
      <el-row>
        <el-col :span="9">
          <el-image preview-teleported :src="getImgUrl(detailImg.imgUrl)" />
        </el-col>
        <el-col :span="15">
          <div class="detail-wrap">
            <article class="detail-list">
              <h4 class="detail-h4">文件名:</h4>
              <div class="detail-content">{{ detailImg.imgName }}</div>
            </article>
            <article class="detail-list">
              <h4 class="detail-h4">文件类型:</h4>
              <div class="detail-content">{{ detailImg.mediaType }}</div>
            </article>
            <article class="detail-list">
              <h4 class="detail-h4">文件大小:</h4>
              <div class="detail-content">
                {{ convertSize(detailImg.imgSize) }}
              </div>
            </article>
            <article class="detail-list">
              <h4 class="detail-h4">文件md5:</h4>
              <div class="detail-content">{{ detailImg.md5 }}</div>
            </article>
            <article class="detail-list">
              <h4 class="detail-h4">文件上传时间:</h4>
              <div class="detail-content">
                {{ formatTime(detailImg.uploadTime) }}
              </div>
            </article>
            <article class="detail-list">
              <h4 class="detail-h4">
                文件路径:
                <el-icon :size="16" @click="copyImgUrl" color="cyan" style="cursor: pointer; margin-left: 1rem">
                  <copy-document />
                </el-icon>
              </h4>
              <div class="detail-content">
                {{ getImgUrl(detailImg.imgUrl) }}
              </div>
            </article>
            <article class="detail-list">
              <h4 class="detail-h4">
                markdown格式:
                <el-icon :size="16" @click="copyMarkdown" color="cyan" style="cursor: pointer; margin-left: 1rem">
                  <copy-document />
                </el-icon>
              </h4>
              <div class="detail-content">
                {{ `![img](${getImgUrl(detailImg.imgUrl)})` }}
              </div>
            </article>
          </div>
        </el-col>
      </el-row>
    </el-dialog>
    <el-table :data="Imgs" fit>
      <el-table-column prop="imgName" label="图片名称">
        <template v-slot="{ row }">
          <span @click="detailDialogShow(row)">{{ row.imgName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="imgUrl" label="图片url"></el-table-column>
      <el-table-column prop="imgUrl" width="100" label="缩略图">
        <template v-slot="{ row }">
          <el-image :preview-src-list="[getImgUrl(row.imgUrl)]" preview-teleported style="height: 30px" :src="getImgUrl(row.imgUrl)" />
        </template>
      </el-table-column>
      <el-table-column prop="imgSize" width="100" label="图片大小">
        <template v-slot="{ row }">
          {{ convertSize(row.imgSize) }}
        </template>
      </el-table-column>
      <el-table-column prop="imgPath" label="图片目录"></el-table-column>

      <el-table-column prop="uploadTime" label="上传时间">
        <template v-slot="{ row }">{{ formatTime(row.uploadTime)  }}</template>
      </el-table-column>

      <el-table-column label="操作">
        <template v-slot="{ row }">
          <el-popconfirm title="确定删除吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info" placement="right" iconColor="red" @confirm="deleteRow(row)">
            <template #reference>
              <el-button type="danger">删除</el-button>
            </template>
          </el-popconfirm>
          <el-button type="primary" @click="detailDialogShow(row)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br />
    <el-pagination background layout="total,sizes,prev, pager, next " :total="count" :page-size="pageSize" @current-change="getList" v-model:current-page="currentPage" :page-sizes="[10, 20, 30, 40, 50, 100]" @size-change="sizeChange" />
  </div>
</template>

<script setup lang="ts">
import { delImg, getImgs, uploadUrl } from '@/utils/apiConfig'
import { convertSize, formatTime } from '@/utils/utils'
import { useClipboard, usePermission } from '@vueuse/core'
const { text, isSupported, copied, copy } = useClipboard()
import vueFilePond,{setOptions} from 'vue-filepond'

// Import FilePond styles
import 'filepond/dist/filepond.min.css'

// Import FilePond plugins
// Please note that you need to install these plugins separately

// Import image preview plugin styles
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css'

// Import image preview and file type validation plugins
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type'
import FilePondPluginImagePreview from 'filepond-plugin-image-preview'
import 'element-plus/theme-chalk/el-message.css'
// Create component

import { computed, onBeforeMount, reactive, ref, toRefs } from 'vue'
import { CopyDocument, Upload } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { baseConfig } from '@/utils/http'
import { useUserStore } from "@/store/user";
const userStore=useUserStore()

const FilePond = vueFilePond(FilePondPluginFileValidateType, FilePondPluginImagePreview)

const state = reactive({
  myFiles: [],
  detailImg: {},
  server: uploadUrl,
  uploadDialogVisible: false,
  detailDialogVisible: false,
  Imgs: [],
  count: 0,
  pageNum: 0,
  pageSize: 10,
  currentPage: 1,
})
const { myFiles, detailImg, server, uploadDialogVisible, detailDialogVisible, Imgs, count, pageNum, pageSize, currentPage } = toRefs(state)
function sizeChange(size) {
  state.pageSize = size
  getList()
}

let pond = ref()

onBeforeMount(() => {
  getList()
})

function handleFilePondInit() {
  setOptions({server:{headers:{token:userStore.token},url:uploadUrl}})
}

function detailDialogShow(row) {
  state.detailImg = row
  state.detailDialogVisible = true
}
function copyMarkdown() {
  copy(`![img](${getImgUrl(state.detailImg.imgUrl)})`).then(() => {
    ElMessage({
      type: 'success',
      message: '复制markdown成功',
    })
  })
}
function copyImgUrl() {
  copy(getImgUrl(state.detailImg.imgUrl))
  ElMessage({
    type: 'success',
    message: '复制路径成功',
  })
}
function getImgUrl(url) {
  return baseConfig.url + '/' + url
}

async function getList() {
  try {
    let res = await getImgs({ page:state. pageNum, limit:state. pageSize })
    if (res.success) {
      state.Imgs = res.data.list
      state.count = res.data.count
    } else {
      ElMessage.error(res.message)
    }
    console.error(state.Imgs)
  } catch (e) {
    ElMessage.error((e as Error).message)
  }
}

function closeUpload() {
  pond.value.removeFiles()
  getList()
}

function showUploadDialog() {
  state.uploadDialogVisible = true
}

function deleteRow(row) {
  delImg(row.id).then(({ data }) => {
    console.log(data)
    getList()
    ElMessage({
      type: 'success',
      message: '删除成功',
    })
  })
}
</script>

<style lang="scss" scoped>
:deep(.el-dialog) {
  .detail-wrap {
    padding: 0 1rem;
    display: flex;
    flex-direction: column;

    justify-content: space-between;

    .detail-list {
      padding: 12px 0;
      border-bottom: 1px solid #e8e8e8;

      .detail-h4 {
        margin: 4px auto;
        color: rgba(0, 0, 0, 0.65);
        font-size: 14px;
        line-height: 22px;
      }

      .detail-content {
        color: rgba(0, 0, 0, 0.45);
        font-size: 14px;
        line-height: 22px;
      }
    }
  }
}
</style>
