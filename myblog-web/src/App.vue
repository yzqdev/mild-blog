<template>
  <router-view />
</template>

<script  setup>
import {useRouter} from "vue-router";
let router=useRouter()
console.log(router.getRoutes())
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  height: 100%;

}
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}
</style>
