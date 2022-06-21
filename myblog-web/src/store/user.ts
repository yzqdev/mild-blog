
import { defineStore } from "pinia";
import { UserState } from "@/interface/storeTypes";

export const useUserStore = defineStore({
  id: "user",
  state: (): UserState => {
    return {
      token: "",
      username: "",
      nickname: "",
      id: "",
      role: 1,
      avatar: "",
      email: "",
      telephone: "",
    };
  },
  getters: {
    getToken(): string {
      return this.token;
    },
  },
  actions: {
    setUserToken(val: string) {
      this.token = val;
    },
    setUser(val: UserState) {
      this.$state = val;
    },
    setNickname(val: string) {
      this.nickname = val;
    },
    setUsername(val: string) {
      this.username = val;
    },
  },
});
