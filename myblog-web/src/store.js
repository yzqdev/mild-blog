import { createStore } from "vuex";

const store = createStore({
  state() {
    return {
      user: {token:''},
      statVisible: false,
    };
  },
  mutations: {
    setUserToken(state,val ){
      state.user.token=val
    }
  },
});
export default store;
