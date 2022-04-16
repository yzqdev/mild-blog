import { createStore } from "vuex";

const store = createStore({
  state() {
    return {
      user: {token:''},
      statVisible: false,
    };
  },
  getters:{
    token:(state)=>{
      return state.user.token
    }
  },
  mutations: {
    setUserToken(state,val ){
      state.user.token=val
    }
  },
});
export default store;
