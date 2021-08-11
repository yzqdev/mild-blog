<template>
  <router-view />
</template>

<script>
export default {
  name: "App",
  components: {},
  created() {
    let token = localStorage.getItem("token");
    if (token && token !== "undefined") {
      this.$store.commit("setUserToken", localStorage.getItem("token"));
    } else {
      this.$router.push("/admin");
    }
  },
};
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  height: 100vh;
}
html,
body {
  margin: 0;
  padding: 0;
}
</style>
