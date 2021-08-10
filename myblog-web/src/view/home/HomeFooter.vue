<template>
  <footer class="footer blog-footer"  >
    <div class="blog-text-center">
      &copy;
      <a  text="${configurations.get('sysAuthor')}">your singal blog.</a>
      <span class="ft-warn">&heartsuit;</span>&nbsp;site blog
      <a>浙ICP备 xxxxxx-x号</a><br/>
      Powered by <a href="#" target="_blank" th:text="${configurations.get('sysCopyRight')}">2019 南街</a>
    </div>
  </footer>
</template>

<script>
export default {
  name: "HomeFooter"
}
</script>

<style scoped>

</style>
