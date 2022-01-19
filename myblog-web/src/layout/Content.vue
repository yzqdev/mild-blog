<template>
  <el-main class="content">

    <div class="wrap">
      <router-view/>
    </div>

  </el-main>
</template>
<style lang="scss" scoped>
.content {
  padding: 1.5rem;
  height: 100%;
  background-color: #f5f6f7;
  .wrap {
    min-height: calc(100% - 4rem);
    background-color: white;
    padding: 1.5rem;
  }
}
</style>
