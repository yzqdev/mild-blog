import { defaultTheme } from "@/constants/defaultTheme";
import { defineStore } from "pinia";
import { ThemeState } from "@/interface/storeTypes";

export const useThemeStore = defineStore({
  id: "themeConfig",
  state: (): ThemeState => ({ ...defaultTheme }),
  getters: {
    getDark(): boolean {
      return this.dark;
    },
    getContentPadding(): boolean {
      return this.contentPadding;
    },
    getShowFooter(): boolean {
      return this.showFooter;
    },
  },
  actions: {
    setContentPadding(val: boolean) {
      this.contentPadding = val;
    },
    setThemeConfig(val: ThemeState) {
      this.$state = val;
    },
    setShowFooter(val: boolean) {
      this.showFooter = val;
    },
  },
});
