<template>
  <HomeHeader></HomeHeader>
  <router-view />
  <HomeFooter></HomeFooter>
</template>

<script>
import HomeFooter from "@/view/home/HomeFooter.vue";
import HomeHeader from "@/view/home/HomeHeader.vue";
export default {
  name: "Home",
  components: { HomeHeader, HomeFooter },
};
</script>

<style scoped></style>
