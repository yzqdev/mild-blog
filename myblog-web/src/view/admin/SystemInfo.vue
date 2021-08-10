<template>
  <el-button type="primary" @click="addDialogShow">添加系统信息</el-button>
  <el-dialog width="30%" v-model="addDialogVisible" title="添加系统信息">
    <el-form :model="addForm">
      <el-form-item prop="configField" label="字段名"
        ><el-input v-model="addForm.configField"></el-input> </el-form-item
      ><el-form-item prop="configName" label="参数名"
        ><el-input v-model="addForm.configName"></el-input> </el-form-item
      ><el-form-item prop="configValue" label="参数值"
        ><el-input v-model="addForm.configValue"></el-input>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAdd">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  <el-table :data="data">
    <el-table-column prop="configName" label="参数名"></el-table-column>
    <el-table-column prop="configValue" label="参数值"></el-table-column>
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
</template>

<script>
import { defineComponent } from "vue";
import { addSystemInfo, delSystemInfo, getSystemInfo } from "@/utils/apiConfig";

export default defineComponent({
  name: "SystemInfo",
  data() {
    return {
      data: "",
      addDialogVisible: false,
      addForm: {
        configName: "name",
        configField: "field",
        configValue: "value",
      },
    };
  },
  methods: {
    deleteRow(row) {
      console.log(row);
      delSystemInfo(row.configField).then(({ data }) => {

          this.getData();
          this.$message.success("成功");

      });
    },
    addDialogShow() {
      this.addDialogVisible = true;
    },
    getData() {
      getSystemInfo().then(({ data }) => {
        console.log(data);
        this.data = data;
      });
    },
    confirmAdd() {
      addSystemInfo(this.addForm).then(({ data }) => {
this.getData()
          this.$message.success("成功");
          this.addDialogVisible = false;

      });
    },
  },
  created() {
    this.getData();
  },
});
</script>

<style scoped></style>
