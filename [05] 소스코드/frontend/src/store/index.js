import Vue from "vue";
import Vuex from "vuex";
import { getUserInfo } from "../api/user.js";
import router from '../router/index.js'
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userInfo : null,
    isSigned : false
  },
  mutations: {
    loginSucess(state, payload){
      state.isSigned = true;
      state.userInfo = payload;
    },
    loginError(state){
      state.isSigned = false;
      sessionStorage.removeItem('access_token');
      state.userInfo = null;
      
      router.push('/main').catch(err => {});
    }
  },
  actions: {

    //토큰으로 정보 불러오기
    getMemberInfo(){
      let token = sessionStorage.getItem("access_token");
      if(token!=null){
        //토큰으로 정보를 불러와 vuex에 저장하기
        getUserInfo(token);
      }
    },
    logout({commit}){
      commit("loginError")
    },
  },
  modules: {}
});
