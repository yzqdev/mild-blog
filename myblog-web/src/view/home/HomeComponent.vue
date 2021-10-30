<template>
  <div class="home-component" v-loading="loading">
    <div v-if="routeName == `tags`" class="d-flex">
      <el-tag
          style="margin: 0 10px;cursor: pointer"
          v-for="(item, index) in data"
          @click="gotoTag(item)"
          :key="`tags${index}`"
      >{{ item.tagName }}
      </el-tag
      >
    </div>
    <div v-if="routeName == `cates`" class="d-flex">
      <el-button
          type="plain"
          class="cate-span"
          v-for="(item, index) in data"
          :key="`cate${index}`"
          @click="gotoCate(item)"
      >{{ item.categoryName }}
      </el-button
      >
    </div>
    <div v-if="routeName == `timeline`" class="timeline">
      <el-timeline>
        <el-timeline-item
            v-for="(item, index) in timelines"
            :key="`timeline${index}`"
            :timestamp="formatTime(item.updateTime)"
            placement="top"
        >
          <el-card @click="gotoBlog(item)">
            <h4>{{ item.blogTitle }}</h4>
            <p>{{ item.blogPreface }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <div v-if="routeName=='search'">


      <passage-list :list="keywordList"  :loading="false"></passage-list>
    </div>
  </div>
</template>

<script>
import {defineComponent} from "vue";
import {getHomeCates, getHomeTags, getSearch, getTimeline} from "@/utils/apiConfig";
import PassageList from "@/components/PassageList.vue";

export default defineComponent({
  name: "HomeComponent",
  components: {PassageList},
  data() {
    return {
      loading: true,
      routeName: "tags",
      data: [],
      pageNum: 1,
      pageSize: 55,
      timelines: [],
      keywordList:[]
    };
  },
  methods: {
    gotoBlog(item) {
      this.$router.push("/home/blog/" + item.blogId);
    },
    formatTime(time) {
      return this.$dayjs(time).format("YYYY-MM-DD HH:mm:ss");
    },
    gotoTag(item) {
      this.$router.push("/home/tag/" + item.tagId);
    },gotoCate(item) {
      this.$router.push("/home/category/" + item.categoryId);
    },
  },
  watch: {
    $route: {
      handler: async function (val) {
        let route = val.name;
        switch (route) {
          case "homeTags":
            this.routeName = "tags";
            let {data} = await getHomeTags();
            this.data = data;
            this.loading = false;
            break;
          case "homeCategories":
            this.routeName = "cates";
            let res = await getHomeCates();
            this.data = res.data;
            this.loading = false;
            break;
          case "homeTimeline":
            this.routeName = "timeline";
            getTimeline({
              pageNum: this.pageNum,
              pageSize: this.pageSize,
            }).then((res) => {
              this.timelines = res.data;
              this.loading = false;
            });

            break;
          case 'homeSearch':
            this.routeName = "search";
            getSearch(this.$route.query.text).then(({data}) => {
              console.log(data)
              this.keywordList=data
            })

            this.loading = false
            break;
            default:
              break;
        }
      },
      immediate: true,
    },
  },
});
</script>

<style lang="less" scoped>
.home-component {
  height: 100%;
  margin: 20px;
  padding: 20px;
  border: 1px solid #f5f5f5;
  border-radius: 5px;

  .cate-span {
    border: 1px solid #53a8ff;
  }

  ::v-deep(.el-card) {
    &:hover {
      cursor: pointer;
    }
  }

  .timeline {
    width: 80%;
    padding: 10px 50px;
  }

  .d-flex {
    display: flex;
    justify-content: center;
  }
}
</style>
