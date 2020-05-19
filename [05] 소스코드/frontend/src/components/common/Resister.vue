<!-- 사용 -->
<template>
	<div class="main-wrapper" style="margin-top:150px">
		<!-- Content -->
		<div id="content">
  			<div class="container">
	 			<div class="profile-page">
	 				<div class="card card-profile shadow" style="margin-bottom:30px;">
		  				<div class="px-4">
			 				<div class="row justify-content-center">
								<div class="col-lg-3 order-lg-2">
									<div class="card-profile-image">
										<a href="#">
                                 		<img v-if="$store.state.userInfo!=null&&$store.state.userInfo.picture!=null" v-bind:src="this.userInfo.picture" class="rounded-circle"  alt="" >
							            <img v-if="$store.state.userInfo!=null&&$store.state.userInfo.picture==null" src="../../../public/images/kakaoimage.jpg" class="rounded-circle"  alt="" >
										</a>
									</div>
								</div>
								<div class="col-lg-4 order-lg-3 text-lg-right align-self-lg-center">
									<div class="card-profile-actions py-4 mt-lg-0">
										<a href="#" @click="deleteAuth"  v-if="$store.state.userInfo!=null&&$store.state.userInfo.auth!=null"  style=" background-color:#CC3D3D; border-color:#CC3D3D;" class="btn btn-sm btn-info ">공인인증 해제</a>
										<a href="#" v-b-modal.modal-2 style="margin-right:-30px; background-color:#1428A0; border-color:#1428A0;" class="btn btn-sm btn-info ">정보 수정하기</a>
									</div>
								</div>
						
								<div class="col-lg-4 order-lg-1">
				  					<div class="card-profile-stats d-flex justify-content-center">
					 					<div>
											
					 					</div>
										<div>
										
										</div>
										<div>
											
							 			</div>
					  				</div>
								</div>
							</div>
							
							<div class="text-center mt-5">
								<h3 v-if="$store.state.userInfo!=null">{{userInfo.nickname}}
								</h3>
                        <h4><span v-if="$store.state.userInfo!=null&&$store.state.userInfo.auth!=null" style="background-color:#59DA50; color:#fff;" class="badge badge-pill badge-danger text-uppercase">공인중개사</span></h4>
                        <h4><span v-if="$store.state.userInfo!=null&&$store.state.userInfo.auth==null" style="background-color:#1428A0; color:#fff;" class="badge badge-pill badge-danger text-uppercase">일반사용자</span></h4>
				  					
                        
           
                  <br/> 
						<div class="flex-grow-1 px-5">
                   
                     <div class="mb-4">
                        <div class="detail-list mb-2">
                           <div class="detail-list-label mb-1">
                              <i class="fa fa-map-marker mr-2" aria-hidden="true"></i>  <small class="text-uppercase font-weight-bold">Address</small>
                           </div>
                           <p v-if="$store.state.userInfo!=null&&$store.state.userInfo.address==null" class="m-0 text-small text-muted">주소를 등록해주세요.</p>
						   <p v-if="$store.state.userInfo!=null&&$store.state.userInfo.address!=null" class="m-0 text-small text-muted">{{userInfo.address}}</p>
                        </div>
                        <br/>
                        <div class="detail-list mb-2">
                           <div class="detail-list-label mb-1">
                              <i class="fa fa-phone mr-2" aria-hidden="true"></i> <small class="text-uppercase font-weight-bold">Contact No.</small>
                           </div>
						   <p v-if="$store.state.userInfo!=null&&$store.state.userInfo.phoneNum==null" class="m-0 text-small text-muted">전화번호를 등록해주세요.</p>
						   <p v-if="$store.state.userInfo!=null&&$store.state.userInfo.phoneNum!=null" class="m-0 text-small text-muted">{{userInfo.phoneNum}}</p>
                        </div>
                     </div>
                  </div>


                        
							</div>
							<div class="mt-5 py-5 border-top text-center">
								<div class="row justify-content-center">
									<div class="col-lg-9">
										<!-- <p>단계 설명</p>
										 -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
		 		
			</div>
		</div>

	<b-modal id="modal-2"  title="정보수정" v-model="modalshow">
	  <div align="center">
	  <img src="../../../public/images/logo2.png" height="150px" width="290px" style="margin-top:-20px" >
	
		<b-input-group prepend="주소" class="mt-3">
   			 <b-form-input v-b-modal.modal-3  v-model="address" placeholder="주소를 입력해주세요."></b-form-input>
   
  		</b-input-group>
		<b-input-group prepend="전화번호" class="mt-3">
   			 <b-form-input  v-model="phone" style="text-align:center"></b-form-input>
			 <b-form-input  value="-" readonly style="background-color:#fff; text-align:center"></b-form-input>
			 <b-form-input  ref="phone2" v-model="phone2" style="text-align:center"></b-form-input>
			 <b-form-input  value="-" readonly style="background-color:#fff; text-align:center"></b-form-input>
			 <b-form-input  maxlength="4" ref="phone3" v-model="phone3" style="text-align:center"></b-form-input>
  		</b-input-group>
		<br>
    	

	  </div>

	  <template v-slot:modal-footer="{ ok, cancel}">
     
      <b-button size="lg" variant="success" @click="submit()">
        수정완료
      </b-button>
      <b-button size="lg" variant="danger" @click="cancel()">
        취소하기
      </b-button>

    </template>
    </b-modal>

	<b-modal id="modal-3" title="주소검색" hide-footer>
    	<DaumPostcode style="height: 400px;"
      		:on-complete=handleAddress
    	/>
  	</b-modal>

	</div>
</template>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
import {mapState} from 'vuex';
import DaumPostcode from 'vuejs-daum-postcode'
import { mypage } from "../../api/user.js";
import { deleteAuth } from "../../api/user.js";
export default {
	data(){
		return{
			open : false,
			modalshow: false,
			address: '',
			phone:'',
			phone2:'',
			phone3:'',
			

		}
	},
	created(){
		if(this.$store.state.userInfo!=null){
			this.address = this.$store.state.userInfo.address;
			var n = this.$store.state.userInfo.phoneNum;
		
			var arr = n.split('-');
			
			this.phone = arr[0];
			this.phone2 = arr[1];
			this.phone3 = arr[2];
			
		}
		
		
		
	},
	watch:{
		modalshow: function(hook){
			if(hook==false){
				this.address = '';
				this.phone = '';
				this.phone2 = '';
				this.phone3 = '';
			}
		},
		// phone: function(hook){
		// 	if(hook.length==3){
		// 		this.$refs.phone2.focus();
		// 	}
		// },
		// phone2: function(hook){
		// 	if(hook.length==4){
		// 		this.$refs.phone3.focus();
		// 	}
		// },
		userInfo:function(hook){
			if(hook!=null){

				this.address = this.$store.state.userInfo.address;
				var n = this.$store.state.userInfo.phoneNum;
		
				var arr = n.split('-');
			
				this.phone = arr[0];
				this.phone2 = arr[1];
				this.phone3 = arr[2];
			}
		}

	},
   computed: {
	  ...mapState(['userInfo']),
   },
   methods:{
		handleAddress(data){
	
           let fullAddress = data.roadAddress
            let extraAddress = ''
        if (data.addressType === 'R') {
         if (data.bname !== '') {
           extraAddress += data.bname
         }
         if (data.buildingName !== '') {
         	extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName)
         }
         	fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '')
         }
         
          this.address = data.roadAddress
          this.$bvModal.hide('modal-3')	
		},
		submit(){
			if(this.address!='' && this.phone!='' && this.phone2!='' && this.phone3!=''){

				mypage(this.$store.state.userInfo, this.address, this.phone+"-"+this.phone2+"-"+this.phone3);
				this.$bvModal.hide('modal-2')	
			}else{
				alert("정보를 모두 입력해주세요!")
			}
		},
		deleteAuth(){
			
			deleteAuth(this.$store.state.userInfo.num);
		}

   },
   components: {
      DaumPostcode
	},
	
};



</script>
