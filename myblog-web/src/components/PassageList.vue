<template>
  <div class="passage-list" v-loading="loading">
    <template v-for="(item, index) in list">
      <article class="article" v-if="item.blogStatus&&item.blogStatus>0&&!item.isDeleted ">
        <div class="article-top">
        <span class="category-link" @click="gotoCate(item)">{{
            item.blogCategory.categoryName
          }}</span>
          <span class="category-link">
          <el-tag type="primary" @click="gotoTagRoute(tag)" v-for="tag in item.blogTags">{{
              tag.tagName
            }}</el-tag> </span
          ><span class="right">{{
            $dayjs(item.updateTime).format("YYYY-MM-DD HH:mm:ss")
          }}</span>
        </div>
        <h1 class="article-title" @click="gotoBlog(item)">
          {{ item.blogTitle }}
        </h1>
        <div>{{ item.blogPreface }}</div>
      </article>
    </template>
  </div>
</template>

<script>
import {defineComponent} from "vue";

export default defineComponent({
  name: "PassageList",
  data() {
    return {};
  },
  props: {
    list: {type: Array},
    loading: {
      type: Boolean,
      default: true,
    },
  },
  mounted() {
  },
  methods: {
    gotoCate(item) {
    },
    gotoTagRoute(item) {
      this.$router.push("/home/tag/" + item.tagId);
    },
    gotoBlog(item) {
      this.$router.push("/home/blog/" + item.blogId);
    },
  },
});
</script>

<style lang="less" scoped>
.passage-list {
  .article {
    padding: 20px;

    border-bottom: 1px solid #e5e5e5;

    .article-top {
      display: flex;

      .category-link {
        flex: 1;
        cursor: pointer;
        transition: all 0.3s;

        &:hover {
          color: #0d84ff;
        }
      }

      .right {
        flex: 1;
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
