const resolve = path.resolve;
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import * as path from "path";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import Icons from "unplugin-icons/vite";
import IconsResolver from "unplugin-icons/resolver";
const pathSrc = path.resolve(__dirname, 'src')
export default defineConfig({
  plugins: [
    vue({ reactivityTransform: true }),
    Icons({
      autoInstall: true,
    }),
    AutoImport({
      imports: ['vue'],
      resolvers: [
        ElementPlusResolver(), // 自动导入图标组件
        IconsResolver({
          prefix: "Icon",
        }),
      ],
      // dts: path.resolve(pathSrc, 'typings', 'auto-imports.d.ts'),
    }),
    Components({
      resolvers: [
        ElementPlusResolver(), // 自动注册图标组件
        IconsResolver({
          enabledCollections: ["ep"],
        }),
      ],
      // dts: path.resolve(pathSrc, 'typings', 'components.d.ts'),
    }),
  ],
  // publicDir:'/myblog',
  // base:'/myblog/',
  resolve: {
    //导入时想要省略的扩展名列表。注意，不 建议忽略自定义导入类型的扩展名（例如：.vue），因为它会干扰 IDE 和类型支持。
    alias: [{ find: "@", replacement: resolve(__dirname, "./src") }],
  },
  build: {
    outDir: "myblog",

    // sourcemap: true,
    // minify: false,
  },
  server: {
    port: 2800,
  },
});
