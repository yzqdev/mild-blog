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
  <el-table
    :data="data"
    fit
    @cell-click="cellClick"
    :row-class-name="tableRowClassName"
    :cell-class-name="tableCellClassName"
  >
    <el-table-column prop="configName" label="参数名">
      <template v-slot="scope"
        ><span
          v-if="
            scope.row.index === rowIndex && scope.column.index === columnIndex
          "
        >
          <el-input
            @change="changeConfigName($event, scope.row)"
            v-model="scope.row.configName"
            @blur="inputBlur(scope.row)"
            placeholder="请输入顺序"
            size="mini"
          />
        </span>
        <span v-else>{{ scope.row.configName }}</span>
      </template>
    </el-table-column>
    <el-table-column prop="configValue" label="参数值">
      <template v-slot="scope"
        ><span
          v-if="
            scope.row.index === rowIndex && scope.column.index === columnIndex
          "
        >
          <el-input
            ref="editInput"
            v-model="scope.row.configValue"
            placeholder="请输入顺序"
            @blur="inputBlur(scope.row)"
            size="mini"
          />
        </span>
        <span v-else>{{ scope.row.configValue }}</span>
      </template></el-table-column
    ><el-table-column label="更新时间" prop="updateTime">
      <template v-slot="{ row }">
        {{ formatTime(row.updateTime) }}
      </template>
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
</template>

<script>
import { defineComponent } from "vue";
import {
  addSystemInfo,
  delSystemInfo,
  editSystemInfo,
  getSystemInfo,
} from "@/utils/apiConfig";
import dayjs from "dayjs";

export default defineComponent({
  name: "SystemInfo",
  data() {
    return {
      data: "",
      addDialogVisible: false,
      rowIndex: -1, //行索引
      columnIndex: -1, //列索引
      addForm: {
        configName: "name",
        configField: "field",
        configValue: "value",
      },
    };
  },
  methods: {
    //把每一行的索引加到行数据中
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex;
    },
    //把每一列的索引加到列数据中
    tableCellClassName({ column, columnIndex }) {
      column.index = columnIndex;
    },
    cellClick(row, column, cell, event) {
      this.rowIndex = row.index;
      this.columnIndex = column.index;
    },
    changeConfigName(val, row) {
      console.log(val);
      console.log(row);
      editSystemInfo(row).then(({ data }) => {
        console.log(data);
      });
    },
    formatTime(time) {
      console.log();
      let res = dayjs(new Date(time)).format("YYYY-MM-DD HH:mm:ss");
      console.log(res);
      return res;
    },
    inputBlur(row) {
      console.log(row);
      this.rowIndex = -1;
      this.columnIndex = -1;
    },
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
        this.getData();
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
