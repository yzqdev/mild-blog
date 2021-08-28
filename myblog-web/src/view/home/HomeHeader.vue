<template>
  <header class="home-header">
    <div>{{ config.websiteName }}</div>
    <div class="nav">
      <span
        class="nav-link"
        :class="[activeRoute == item.id ? `nav-active` : ``]"
        v-for="item in navs"
        @click="gotoRoute(item)"
        >{{ item.text }}</span
      >
    </div>
  </header>
</template>

<script>
import { getConfigs } from "@/utils/apiConfig";

export default {
  name: "HomeHeader",
  data() {
    return {
      navs: [
        { text: "主页", id: 1 },
        { text: "友情链接", id: 2 },
        { text: "关于", id: 3 },
      ],
      activeRoute: 1,
      config: {},
    };
  },
  methods: {
    gotoRoute(item) {
      this.activeRoute = item.id;
    },
  },
  async created() {
    let { data } = await getConfigs();
    this.config = data;
  },
};
</script>

<style lang="less" scoped>
.home-header {
  background-color: #f5f5f5;
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
    .nav-link {
      margin: 0 20px;
      cursor: pointer;
      color: #555;
      outline: 0;
      text-decoration: none;
      overflow-wrap: break-word;
      border-radius: 2px;
      padding: 5px 10px;
      transition: all 0.3s ease-in-out;
      &:hover {
        background-color: #ddd;
        color: #222;
      }
    }
    .nav-active {
      background-color: #ddd;
      color: #222;
    }
  }
}
</style>
