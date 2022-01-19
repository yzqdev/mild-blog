<template>
  <q-page class="row items-center justify-evenly">
    <passage-list  :list="passages" :loading="loading"/>
    <example-component
      title="Example component"
      active
      :todos="todos"
      :meta="meta"
    ></example-component>
  </q-page>
</template>

<script setup lang="ts">
import { Todo, Meta } from 'components/models';
import ExampleComponent from 'components/CompositionComponent.vue';
import {onMounted, reactive, ref, toRefs} from 'vue';
import PassageList from "components/PassageList.vue";
import {getIndex} from "boot/apis";

let state=reactive({
  passages:[],loading:true,newBlog:[],hotTag:[]
})

let {passages,loading,newBlog,hotTag}=toRefs(state)
    const todos = ref<Todo[]>([
      {
        id: 1,
        content: 'ct1'
      },
      {
        id: 2,
        content: 'ct2'
      },
      {
        id: 3,
        content: 'ct3'
      },
      {
        id: 4,
        content: 'ct4'
      },
      {
        id: 5,
        content: 'ct5'
      }
    ]);
    const meta = ref<Meta>({
      totalCount: 1200
    });
function getData() {
  getIndex().then((res) => {
    console.log(res);
    const { blogPageResult, configurations, hotTags, newBlogs, pageName } =
        res.data;
    newBlog.value = newBlogs;
    passages.value = blogPageResult;

    hotTag.value = hotTags;
    loading.value = false;
  });
}
onMounted(() => {
getData()
})
</script>
