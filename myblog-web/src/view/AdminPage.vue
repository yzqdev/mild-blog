<template>
  <router-view />
</template>

<script  setup>

import {useStore} from "vuex";
import {onMounted} from "vue";
import {useRouter} from "vue-router";

const store=useStore()
const router=useRouter()
onMounted(() => {
  let token = localStorage.getItem("token");
  if (token && token !== "undefined") {
     store.commit("setUserToken", localStorage.getItem("token"));
  } else {
     router.push("/admin");
  }
})

</script>

<style scoped></style>
