<template>
  <header class="header">
    <el-link style="margin-left: 0.5rem" v-html="notice"></el-link>
    <span class="header-right">
      <el-dropdown trigger="click">
        <el-badge v-if="comments.length > 0" :value="comments.length">
          <el-icon :size="20"> <bell /> </el-icon
          ></el-badge>
        <template #dropdown>
          <section class="notice-tab">
            <article class="notice-head">
              <div class="notice-text">通知</div>
              <div
                class="all-read"
                @click="readAll"
              >
                全部已读
              </div>
            </article>
            <article class="notice-item" v-for="item in comments">
              <div class="m-2">
                <el-avatar
                  size="large"
                  src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
                ></el-avatar>
              </div>
              <div class="flex flex-col justify-center">
                <div>
                  {{ item.content }}
                </div>
                <div>{{ formatTime(item.created_at) }}</div>
              </div>
            </article>
          </section>
          <section class="see-all-btn">
            <el-button style="width: 100%" type="primary" text>查看所有</el-button>
          </section>
        </template>
      </el-dropdown>
      <el-icon @click="toggle" :size="20">
        <full-screen />
      </el-icon>
      <el-icon @click="showSettings" :size="20">
        <setting />
      </el-icon>
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar
            :size="30"
            :src="userInfo.avatar"
          />
          <el-button text class="ml-2"
          >{{ userInfo.nickname ? userInfo.nickname : userInfo.username
            }}<el-icon> <arrow-down /></el-icon
            ></el-button>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <el-link @click="gotoRoute('userInfo')" :underline="false"
              >个人中心
              </el-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-link
                href="https://github.com/yzqdev/slim-admin"
                target="_blank"
                :underline="false"
              >项目地址
              </el-link>
            </el-dropdown-item>
            <el-dropdown-item divided @click="logout"
            >退出登录</el-dropdown-item
            >
          </el-dropdown-menu>
        </template>
      </el-dropdown></span
    >
    <el-drawer
      size="20%"
      v-model="settingDraw"
      title="主题配置"
      direction="rtl"
    >
      <el-row>
        <el-col :span="24">
          <el-switch
            v-model="theme.contentPadding"
            size="large"
            active-text="显示边距"
          />
        </el-col>
        <el-col :span="24">
          <el-switch
            v-model="theme.showFooter"
            size="large"
            active-text="显示footer"
          />
        </el-col>
      </el-row>
    </el-drawer>
  </header>
</template>

<script setup lang="ts">
import { ArrowDown, Bell, FullScreen, Setting } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { computed, onBeforeMount, watch } from "vue";
import { useThemeStore } from "@/store/themeConfig";
import { defaultTheme } from "@/constants/defaultTheme";
import { useUserStore } from "@/store/user";
import { UserState } from "@/interface/storeTypes";
import { useFullscreen, useStorage } from "@vueuse/core";
import { getUserInfo, getUsers } from "@/utils/apiConfig";
import { formatTime } from "@/utils/utils";

let router = useRouter();
let { setThemeConfig } = useThemeStore();
let userStore = useUserStore();
let userInfo: UserState = computed(() => {
  return userStore.$state;
});

function readAll() {
}

let notice = computed(() => {
  return `你好,${
    userStore.nickname ? userStore.nickname : userStore.username
  },今天要炸鱼吗?`;
});
let comments = $ref<any>([]);
let settingDraw = $ref<boolean>(false);
const theme = $(
  useStorage("themeConfig", {
    ...defaultTheme
  })
);
let noticeTab = $ref("notice");
const { isFullscreen, enter, exit, toggle } = useFullscreen();


function showSettings() {
  settingDraw = true;
}

function gotoRoute(name: string) {
  router.push({
    name: name
  });
}

function logout() {
  localStorage.clear();
  router.push({ name: "adminLogin" });
}

async function getUnread() {
  // let data = await getCommentsUnreadApi();
  comments = [
    {
      id: 5,
      created_at: "2022-05-12T13:21:47.077+08:00",
      updated_at: "2022-05-12T13:21:47.077+08:00",
      user_id: 0,
      content: "mnc",
      post_id: 8,
      read_state: false,
      nickname: "niu",
      avatar_url: "",
      github_url: ""
    },
    {
      id: 4,
      created_at: "2022-05-12T13:18:03.095+08:00",
      updated_at: "2022-05-12T13:18:03.095+08:00",
      user_id: 0,
      content: "zhendnnd",
      post_id: 8,
      read_state: false,
      nickname: "woc",
      avatar_url: "",
      github_url: ""
    },
    {
      id: 3,
      created_at: "2022-05-12T13:16:14.89+08:00",
      updated_at: "2022-05-12T13:16:14.89+08:00",
      user_id: 0,
      content: "asfsdfdsfsf",
      post_id: 8,
      read_state: false,
      nickname: "fff",
      avatar_url: "",
      github_url: ""
    },
    {
      id: 2,
      created_at: "2022-05-12T13:07:46.64+08:00",
      updated_at: "2022-05-12T13:07:46.64+08:00",
      user_id: 0,
      content: "sdffsdf",
      post_id: 8,
      read_state: false,
      nickname: "aaa",
      avatar_url: "",
      github_url: ""
    },
    {
      id: 1,
      created_at: "2022-05-12T12:54:31.62+08:00",
      updated_at: "2022-05-12T12:54:31.62+08:00",
      user_id: 1,
      content: "fsfsdf",
      post_id: 8,
      read_state: false,
      nickname: "辅导费",
      avatar_url: "",
      github_url: ""
    }
  ];
}

async function getUser() {
  try {
    let res = await getUserInfo();

    if (res.success) {
      userStore.setUser(res.data);
    }
  } catch (e) {
    console.log(e);
  }
}

onBeforeMount(() => {
  getUser();
  getUnread();
});
watch(
  theme,
  (val, oldVal) => {
    setThemeConfig(val);
  },
  { immediate: true }
);
</script>

<style lang="scss" scoped>
.see-all-btn {
  //border-2 border-gray-100
  border: solid 2px rgb(243, 244, 246);
}

.notice-tab {
  padding: 1rem;
  display: flex;
  flex-direction: column;

  .notice-head {
    display: flex;

    .notice-text {
      font-size: 1rem;
    }

    .all-read {
      font-size: 1rem;
      color: var(--el-color-primary);
      //text-base flex-1 cursor-pointer text-right text-primary
      flex: 1;
      cursor: pointer;
      text-align: right;

    }
  }

  .notice-item {
    width: 20rem;
    display: flex;

    &:hover {
      background-color: rgb(243 244 246);
    }
  }
}

.header {
  height: 3rem;
  padding: 0.5rem 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .header-right {
    display: flex;
    align-items: center;

    .el-icon {
      margin-left: 1rem;

      &:hover {
        cursor: pointer;
        color: var(--el-color-primary);
      }
    }

    .el-dropdown-link {
      display: flex;
      align-items: center;
      padding: 0 2rem;
      cursor: pointer;
    }
  }
}
</style>
