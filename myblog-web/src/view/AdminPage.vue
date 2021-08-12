<template>
  <router-view />
</template>

<script>
export default {
  name: "AdminPage",
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

<style scoped></style>
