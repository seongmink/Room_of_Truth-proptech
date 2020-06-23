<!-- 사용 -->
<template>
<div>
	<header style="background-color:#000;" class="header-global bg-primary admin-header" >
		<nav  id="navbar-main" class="navbar fixed-top navbar-main bg-primary navbar-expand-lg navbar-transparent navbar-light headroom">
			<div class="container-fluid">
				<div id="logo">
					<router-link to="/"><img src="static/1logo.png" width="150" alt=""></router-link>
				</div>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar_global" aria-controls="navbar_global"
					aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
				</button>
				<div class="navbar-collapse collapse" id="navbar_global">
					<div class="navbar-collapse-header">
						<div class="row">
							<div class="col-6 collapse-brand">
							</div>
							<div class="col-6 collapse-close">
								<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbar_global"
									aria-controls="navbar_global" aria-expanded="false" aria-label="Toggle navigation">
								<span></span>
								<span></span>
								</button>
							</div>
						</div>
					</div>
					<div>
					</div>
					<ul class="navbar-nav navbar-nav-hover align-items-lg-center ml-lg-auto">
						<app-menu></app-menu>
					</ul>
                   
					<ul class="navbar-nav" v-if="$store.state.isSigned" style="margin-left:-15px; margin-right:-30px">
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="#" id="navbar-primary_dropdown_1" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="margin-right:30px">
							<img v-if="$store.state.userInfo.picture!=null" v-bind:src="this.userInfo.picture" class="img-fluid rounded-circle shadow-lg mr-1" width="35" height="45" alt="" >
							<img v-if="$store.state.userInfo.picture==null" src="../../../public/images/kakaoimage.jpg" class="img-fluid rounded-circle shadow-lg mr-1" width="35" height="45" alt="" >
							{{this.userInfo.nickname}}
								<span v-if="$store.state.userInfo.auth==null" style="font-size:12px; font-weight:900;">( 일반사용자 )</span>
								<span v-if="$store.state.userInfo.auth!=null" style="font-size:12px; font-weight:900;">( 공인중개사 )</span></a>
								
							
							<div class="dropdown-menu dropdown-menu-0ight" aria-labelledby="navbar-primary_dropdown_1">
								
								
									<router-link class="dropdown-item" to="/resister">내 정보</router-link>
									<router-link v-if="$store.state.userInfo.auth!=null" class="dropdown-item" to="/Realestate">공인중개사</router-link>
									<!-- <a class="dropdown-item" href="#">상세페이지</a> -->
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#" @click="$store.dispatch('logout')">로그아웃</a>
							</div>
						</li>
					</ul>

				</div>
			</div>
		</nav>
	</header>

</div>
</template>
<script>

import Menu from "./Menu2";
import {mapState} from 'vuex';
export default {
	
    data: () => {
        return {
			num:'',
			nickname:'',
			auth:'',
			pictrue:'',
        }
	},
   	components: {
	  appMenu: Menu,
   },
   computed: {
	  ...mapState(['userInfo']),
   },
   methods: {
	
   }
};
</script>

<style scoped>

</style>