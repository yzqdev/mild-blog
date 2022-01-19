<template>
  <el-button type="primary" @click="addDialogShow">添加链接信息</el-button>
  <el-dialog
    width="30%"
    v-model="addDialogVisible"
    @close="closeAddDialog"
    title="添加系统信息"
  >
    <el-form :model="addForm" label-width="80px">
      <el-form-item prop="linkName" label="链接名">
        <el-input v-model="addForm.linkName"></el-input>
      </el-form-item>
      <el-form-item prop="linkUrl" label="链接url">
        <el-input v-model="addForm.linkUrl"></el-input>
      </el-form-item>
      <el-form-item prop="linkDescription" label="链接描述">
        <el-input v-model="addForm.linkDescription"></el-input>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAdd">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  <el-table :data="linkData">
    <el-table-column prop="linkName" label="链接名"></el-table-column>
    <el-table-column prop="linkUrl" label="链接url"></el-table-column>
    <el-table-column prop="linkDescription" label="链接描述"></el-table-column>
    <el-table-column prop="linkRank" label="链接rank"></el-table-column>
    <el-table-column prop="linkType" label="当前状态">
      <template v-slot="{ row }">
        <el-switch
          v-model="row.isDeleted"
          @change="changeLinkType($event, row)"
          active-text="已删除"
          inactive-text="未删除"
          active-color="red"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template v-slot="{ row }">
        <el-button type="primary" @click="editLinkClick(row)">编辑</el-button>
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
</template>

<script>
import { defineComponent } from "vue";
import {
  addSystemInfo,
  clearLink,
  delLink,
  delSystemInfo,
  editLink,
  getLinkList,
  getSystemInfo,
} from "@/utils/apiConfig";

export default defineComponent({
  name: "LinkList",
  data() {
    return {
      linkData: "",
      addDialogVisible: false,
      addForm: {
        linkId: null,
        linkName: "",
        linkUrl: "",
        linkDescription: "",
      },
      linkType: "",
    };
  },
  methods: {
    changeLinkType(e, row) {
      console.log(e);
      delLink({ linkId: row.linkId, isDeleted: e }).then(({ data }) => {
        console.log(data);
        if (data) {
          this.$message.success("操作成功");
        }
      });
    },
    editLinkClick(row) {
      this.addForm = {
        linkId: row.linkId,
        linkName: row.linkName,
        linkUrl: row.linkUrl,
        linkDescription: row.linkDescription,
      };
      this.addDialogVisible = true;
    },
    deleteRow(row) {
      this.addForm = {
        linkName: "",
        linkUrl: "",
        linkDescription: "",
      };
      clearLink(row.linkId).then((res) => {
        if (res.success) {
          this.getData()
          this.$message.success("成功");
        }else {
          this.$message.error(res.msg)
        }
      });
    },
    closeAddDialog() {
      this.addForm = {
        linkId: null,
        linkName: "",
        linkUrl: "",
        linkDescription: "",
      };
    },
    addDialogShow() {
      this.addDialogVisible = true;
    },
    getData() {
      getLinkList({ page: 1, limit: 30 }).then(({ data }) => {
        console.log(data);
        this.linkData = data.data;
      });
    },
    confirmAdd() {
      editLink(this.addForm).then(({ data }) => {
        if (data) {
          this.getData();
          this.$message.success("成功");
          this.addDialogVisible = false;
        }
      });
    },
  },
  created() {
    this.getData();
  },
});
</script>

<style scoped></style>
