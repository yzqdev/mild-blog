<template>
  <div class="passage-list">
    <article class="article" v-for="(item, index) in  list">
      <div class="article-top">
        <span>{{ item.blogCategoryName }}</span
        ><span class="right">{{ item.updateTime }}</span>
      </div>
      <h1 class="article-title" @click="gotoBlog(item)">
        {{ item.blogTitle }}
      </h1>
      <div>{{ item.blogPreface }}</div>
    </article>
  </div>
</template>

<script>
export default {
  name: "PassageList",
  props: {
    list: { type: Array },
  },methods:{
    gotoBlog(item ) {
      this.$router.push("/home/blog/"+item.blogId)
    }
  }
};
</script>

<style lang="less" scoped>
.passage-list {
  .article {
    padding: 20px;

    border-bottom: 1px solid #e5e5e5;
    .article-top {
      display: flex;
      .right {
        flex: 1;
        text-align: right;
      }
    }
    .article-title {
      cursor: pointer;
      &:hover {
        color: #777;
      }
    }
  }
}
</style>
