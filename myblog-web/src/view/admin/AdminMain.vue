<template>
  <div class="admin-main"></div>
</template>

<script setup>
import { defineComponent, onBeforeMount, reactive, toRefs, watch } from "vue";
import { getUserInfo } from "@/utils/apiConfig";

const router = useRouter();
import { useRouter } from "vue-router";

let state = reactive({
  user: {},
  actMenu: [],
});
let { user, actMenu } = toRefs(state);
</script>

<style lang="scss" scoped>
.admin-main {
  height: 100vh;
  display: flex;

  .sidebar {
    width: 200px;
  }

  .right-content {
    flex: 1;

    .admin-header {
      padding: 10px 20px;
      background-color: #eee;
      display: flex;
      align-items: center;

      span {
        cursor: pointer;
      }

      .logout {
        flex: 1;
        text-align: right;

        .dropdown-link {
          display: flex;
          align-items: center;
        }
      }
    }

    .admin-content {
      padding: 20px;
    }
  }
}
</style>
