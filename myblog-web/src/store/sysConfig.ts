import { SysConfig} from "@/type/storeTypes";
import { defineStore } from "pinia";

export const useConfigStore = defineStore({
  id: "config",
  state: (): SysConfig => {
    return {
      sysAuthor: "",
      sysAuthorImg: "",
      sysCopyRight: "",
      sysEmail: "",
      sysUpdateTime: "",
      sysUrl: "",
      sysVersion: "1.1.0",
      websiteName: "",
    };
  },
  getters: {

  },
  actions: {

    setSysConfig(val:SysConfig) {
      this.$state = val;
    },

  },
});
