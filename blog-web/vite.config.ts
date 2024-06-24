
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'

import Unocss from 'unocss/vite'
import { fileURLToPath } from "node:url";
export default defineConfig({
  plugins: [
    Unocss(),
    vue({}),
    Icons({
      autoInstall: true,
    }),
    AutoImport({
      imports: ['vue', 'vue-router', 'pinia', '@vueuse/core'],
      resolvers: [
        ElementPlusResolver(),
        IconsResolver({
          prefix: 'Icon',
        }),
      ],
      dts:fileURLToPath(new URL('./src/types/auto-imports.d.ts',import.meta.url)) // path.resolve(pathSrc, 'types', 'auto-imports.d.ts'),
    }),
    Components({
      resolvers: [
        ElementPlusResolver(),
        IconsResolver({
          enabledCollections: ['ep'],
        }),
      ],
      dts: fileURLToPath(new URL('./src/types/components.d.ts',import.meta.url)) // path.resolve(pathSrc, 'types', 'components.d.ts'),
    }),
  ],
  // publicDir:'/myblog',
  // base:'/myblog/',
  resolve: {
    //导入时想要省略的扩展名列表。注意，不 建议忽略自定义导入类型的扩展名（例如：.vue），因为它会干扰 IDE 和类型支持。
    alias: [{ find: '@', replacement: fileURLToPath(new URL('./src',import.meta.url)) }],
  },
  build: {
    target: 'es2022',
    // sourcemap: true,
    // minify: false,
  },
  server: {
    port: 2800,
  },
})
