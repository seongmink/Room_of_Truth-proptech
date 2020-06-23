<!-- 사용 -->
<template>
  <div>
	<nav id="navigation" class="style-1">
		<ul id="responsive">
		
			<li>
				<router-link to="/search" class="current">조회하기</router-link>
			</li>
			<li>
				<router-link to="/recommendation" class="current">추천서비스</router-link>
			</li>
			<li v-if="$store.state.userInfo==null||$store.state.userInfo.auth==null">
				<router-link to="/auth" class="current">공인중개사 등록</router-link>
			</li>
			<li v-if="$store.state.userInfo!=null&&$store.state.userInfo.auth!=null">
				<router-link to="/addbuilding" class="current">건물이력 등록</router-link>
			</li>
			<li v-if="!$store.state.isSigned">
				<a class="current" v-b-modal.modal-1>로그인</a>
			</li>

		</ul>
	
	</nav>

  <b-modal id="modal-1" hide-footer title="로그인" @hidden="close()">
	  <div align="center">
	  <img src="../../../public/images/logo2.png" height="150px" width="290px" style="margin-top:-20px" >
	  <p style="font-size:25px" >
    	간편하게 로그인하고<br>
		다양한 서비스를 이용하세요.
	  </p>
	  <br>
	  <br>
	  <KakaoLogin
      api-key="4ad8ff4da9eb8b9507c5afaee2b238b4"
      image="kakao_account_login_btn_large_narrow"
      :on-success=formClose
      :on-failure=onFailure
	  
      />
	  </div>
  </b-modal>

  </div>
	
</template>

<script>

import { menus } from './menu-items.js';
import KakaoLogin from 'vue-kakao-login'
import { login } from "../../api/user.js";
import {mapState} from 'vuex';
let onFailure = (data) => {

}

export default {
	created(){
		
	},
	components: {
    	KakaoLogin
  	},
	data(){
		return{
			menuItems : menus,
			open : false,
			result : '',
		}
	},
	methods: {
		onFailure,	
		formClose(data){
			
			login(data.access_token, response => {
				var a = response.data+""

                if(a.length<20){
						
						this.$router.push({name : 'AddInfo', query:{
							num: response.data
						}});
				}else{
				

					sessionStorage.setItem("access_token", response.data);
            		this.$store.dispatch('getMemberInfo');
				}
                           
            });
			this.$bvModal.hide('modal-1');
		},
		close(){
			Kakao.cleanup();
		}
	},
	computed: {
	  ...mapState(['userInfo']),
   },
};
</script>