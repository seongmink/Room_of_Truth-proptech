<!--사용-->
<template>
   <div style="text-align:center;">
      <div style="margin-top:70px; display: inline-block;">
         <div  style="float:left">
            <img src="../../../public/images/paper.png" width="200px">
            <h3 style="margin-left:-20px">정보입력</h3>
         </div>
          <div style="float:left">
            <img src="../../../public/images/arrows.png" width="80px" style="margin-right:40px; margin-left:20px; margin-top:50px;">
         </div>
         <div  style="float:left">
            <img src="../../../public/images/search.png" width="200px">
            <h3>검토</h3>
         </div>
         <div style="float:left"> 
            <img src="../../../public/images/arrows.png" width="80px" style="margin-right:40px; margin-left:40px; margin-top:50px;">
         </div>
         <div  style="float:left">
            <img src="../../../public/images/survey.png" width="200px">
            <h3 style="margin-left:-40px">승인</h3>
         </div>   
      </div>
 
   <div class="row" style="width:600px; margin-top:60px; margin-bottom:50px; display: inline-block;">
      <div class="col-lg-12"> 
         <div id="add-listing">
            <!-- Section -->
            <div class="add-listing-section mb-4">
               <!-- Headline -->
               <div class="add-listing-headline">
                  <h3> 공인중개사 정보입력</h3>
               </div>
             
               <!-- Row -->
               <div class="row with-forms">
                  <!-- Status -->
                   <div class="col-md-6">
                     <div class="form-group">
                        <input type="text" v-model="fname" placeholder="상호명" class="form-control form-control-alternative">
                     </div>
                  </div>
                  <!-- Type -->
                  <div class="col-md-6">
                     <div class="form-group">
                        <input type="text" v-model="lname" placeholder="대표명" class="form-control form-control-alternative">
                     </div>
                  </div>
               </div>

               <b-input-group prepend="주소" class="mt-3">
   			      <b-form-input  v-b-modal.modal-3  v-model="address" placeholder="주소를 입력해주세요."></b-form-input>
  		         </b-input-group>
               <!-- Row / End -->
            </div>
            <!-- Section / End -->
            <!-- Section -->
            <div class="add-listing-section mb-4">
               <!-- Headline -->
               <div class="add-listing-headline">
                  <h3>자격증 번호</h3>
               </div>
               <div style=" margin-bottom:10px;"><span style="font-size:13px; margin-left:-150px;">* 재등록시 한번 등록된 번호로 등록해주시기 바랍니다.</span></div>
               <b-form-input v-model="number" style="float:left; width:350px;" class="form-control form-control-alternative" placeholder="(-)없이 자격증 번호를 입력해주세요."></b-form-input>
               <a v-if="!isgo" href="javascript:" style="background-color:#1428A0; border-color:#1428A0; margin-top:0px; margin-left:40px;" @click="check()" class="btn btn-danger">검사하기</a>
               <a v-else href="javascript:" style="background-color:#8C8C8C; border-color:#8C8C8C; margin-top:0px; margin-left:40px;" class="btn btn-danger">검사완료</a>
            </div>
            <a href="javascript:" style="background-color:#1428A0; border-color:#1428A0;" @click="submit()" class="btn btn-danger">인증하기</a>
         </div>
      </div>
     
   </div>

   <b-modal id="modal-3" title="주소검색" hide-footer>
    	<DaumPostcode style="height: 400px;"
      		:on-complete=handleAddress
    	/>
  	</b-modal>
</div>
</template>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
import DaumPostcode from 'vuejs-daum-postcode'
import Dropzone from "vue2-dropzone";
import {mapState} from 'vuex';
import { auth } from "../../api/user.js";
import { checkAgentNum } from "../../api/user.js";
 

export default {
   components: {
      Dropzone,
      DaumPostcode
   },
   data() {
      return {
         address: '',
         fname: '',
         lname: '',
         number: '',
         isgo:false,
      };
   },
   watch:{
      number:function(hook){
         //console.log(hook)
         this.isgo = false;
      }
   },
   methods:{
      handleAddress(data){
           let fullAddress = data.address
      
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
          
          this.address = fullAddress
          this.$bvModal.hide('modal-3')	
      },
      submit(){
         if(this.isgo==false){
            alert("공인중개사 자격증 번호를 검사해주세요.")
         }else{
            if(this.fname!='' && this.lname!='' && this.address!='' && this.number!=''){

				auth(this.$store.state.userInfo.num, this.fname, this.lname, this.address, this.number);
            alert("인증이 완료되었습니다.")
            this.$router.push('/')
			}else{
				alert("정보를 모두 입력해주세요!")
			}
         }

         
         
      },
      check(){
         //console.log("공인중개사번호 검사")
         //console.log(this.number+" "+this.$store.state.userInfo.num)
         checkAgentNum(this.$store.state.userInfo.num, this.number,responses => {
                //console.log(responses.data)
               if(responses.data=='success'){
                  alert("공인중개사 자격증 번호가 확인 되었습니다.")
                  this.isgo = true;
               }else{
                  alert("공인중개사 자격증 번호를 다시한번 확인해 주세요.")
                  this.isgo = false;
               }

            })
      }
   },
   computed: {
	  ...mapState(['userInfo']),
   },
};
</script>
