<template>
  <div>
    <el-table :data="Imgs" fit>
      <el-table-column prop="imgName" label="图片名称"></el-table-column>
      <el-table-column prop="imgUrl" label="图片url"></el-table-column>
      <el-table-column prop="imgUrl" width="100" label="缩略图">
        <template v-slot="{row}"><img style=" height: 30px" :src="row.imgUrl"/></template>
      </el-table-column>
      <el-table-column prop="imgSize" width="100" label="图片大小">
        <template v-slot="{row}">
          {{
            convertSize(row.imgSize)
          }}
        </template>
      </el-table-column>
      <el-table-column prop="imgPath" label="图片目录"></el-table-column>

      <el-table-column prop="uploadTime" label="上传时间">
        <template v-slot="{row}">{{ $dayjs(row.uploadTime).format("YYYY-MM-DD HH:mm:ss") }}</template>
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

<script>
import {delImg, getImgs} from "@/utils/apiConfig";
import {convertSize} from "@/utils/utils";

export default {
  name: "ImgList", data() {
    return {
      Imgs: []
    }
  },
  mounted() {
    getImgs().then(({data}) => {
      this.Imgs = data
      console.log(this.Imgs)
    })
  }, methods: {
    convertSize,
    deleteRow(row) {
      delImg(row.id).then(({data}) => {
        console.log(data)
      })
    }
  }
}
</script>

<style scoped>

</style>
