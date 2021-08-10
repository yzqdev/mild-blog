<template>
  <header class="home-header">
    <div>yza</div>
    <div class="nav">
      <el-button type="text" v-for="item in navs">{{ item.text }}</el-button>
    </div>
  </header>
</template>

<script>
export default {
  name: "HomeHeader",
  data() {
    return {
      navs: [{ text: "主页" }, { text: "友情链接" }, { text: "关于" }],
    };
  },
};
</script>

<style lang="less" scoped>
.home-header {
  background-color: gray;
  padding: 0 20px;
  display: flex;
  align-items: center;
  height: 60px;
  .nav {
    flex: 1;
    text-align: right;
    height: 100%;
    line-height: 60px;
    background: transparent;
    padding: 0;
    margin: 0;
    .nav-item {
      margin: 0;
      list-style: none;
      position: relative;
      cursor: pointer;
    }
  }
}
</style>
