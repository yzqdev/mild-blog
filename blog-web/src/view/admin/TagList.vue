<template>
  <el-dialog v-model="editFormShow" width="30%">
    <el-form :model="editForm">
      <el-form-item label="标签名">
        <el-input v-model="editForm.tagName"></el-input>
      </el-form-item>
      <el-form-item label="当前状态">
        <el-switch v-model="editForm.show" :active-value="true" :inactive-value="false" active-text="显示"
                   inactive-text="隐藏"></el-switch>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitEdit">确定</el-button>
    </template>
  </el-dialog>
  <el-button type="primary" @click="showAddForm">添加标签</el-button>
  <el-table :data="data">
    <el-table-column prop="tagId" label="标签id"></el-table-column>
    <el-table-column prop="tagName" label="标签名"></el-table-column>
    <el-table-column prop="show" label="当前状态">
      <template v-slot="{ row }">
        {{ row.show ? `显示` : "不显示" }}
      </template>
    </el-table-column>

    <el-table-column label="操作" width="250">
      <template v-slot="{ row }">
        <el-button type="primary" @click="editRow(row)">编辑</el-button>
        <el-popconfirm title="确定删除吗？" confirmButtonText="好的" cancelButtonText="不用了" icon="el-icon-info"
                       placement="right" iconColor="red" @confirm="deleteRow(row)">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <br />
  <el-pagination background layout="total,sizes,prev, pager, next " :total="count" :page-size="pageSize"
                 @current-change="getData" v-model:current-page="currentPage" :page-sizes="[10, 20, 30, 40, 50, 100]"
                 @size-change="sizeChange" />
</template>

<script setup>
import { addTag, clearTagById, EditTagList, getCommentList, getTagList } from "@/utils/apiConfig";
import { onBeforeMount, reactive, toRefs } from "vue";
import { ElMessage } from "element-plus";

let count = $ref(0);
let pageSize = $ref(10);
let currentPage = $ref(1);

function sizeChange(size) {
  pageSize = size;
  getData();
}

let state = reactive({
  isEdit: true,
  data: [],
  editForm: { tagName: "", show: false },
  editFormShow: false
});
let { isEdit, data, editForm, editFormShow } = toRefs(state);
onBeforeMount(() => {
  getData();
});

function getData() {
  getTagList({ page: currentPage, limit: pageSize }).then(({ data }) => {
    console.log(data);
    state.data = data.list;
    count = data.count;
  });
}

async function deleteRow(row) {
clearTagById(row.tagId).then((res) => {
    if (res.success) {
      getData();
      ElMessage({
        message: res.message,
        type: "success"
      });
    } else {
      ElMessage.error(res.message);
    }
}).catch(e=>{
  ElMessage.error(e)
});
  // try {
  //   let res = await clearTagById(row.tagId);
  //   if (res.success) {
  //     getData();
  //     ElMessage({
  //       message: res.message,
  //       type: "success"
  //     });
  //   } else {
  //     ElMessage.error(res.message);
  //   }
  // } catch (e) {
  //   ElMessage.error(e.message);
  //
  // }
}

function showAddForm() {
  state.isEdit = false;
  state.editForm = { tagName: "", show: true };
  state.editFormShow = true;
}

function submitEdit() {
  if (state.isEdit) {
    EditTagList(state.editForm).then((data) => {
      console.log(data);
      if (data) {
        ElMessage({
          message: "成功",
          type: "success"
        });
        getData();
        state.editFormShow = false;
      }
    });
  } else {
    addTag(state.editForm).then(({ data }) => {
      if (data) {
        ElMessage({
          message: "成功",
          type: "success"
        });
        state.isEdit = true;
        getData();
        state.editFormShow = false;
      }
    });
  }
}

function editRow(row) {
  state.editForm = row;
  state.editFormShow = true;
}
</script>

<style scoped></style>
