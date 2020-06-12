<template>
<div>
    <div>
        <title-bar title="등록된 이력" subtitle="등록한 이력에 관한정보를 보여줍니다."></title-bar>
    </div>
     
    <div style="text-align:center;">
       <div id="add-listing" style="margin-top:250px; width:870px;  display: inline-block;">
            <div class="add-listing-section mb-4" style="margin-top:40px">
               <div  style="margin-bottom:-20px; margin-top:20px;">
                  <h3 class="mb-3">등록된 이력</h3>
               </div>

               <div class="mb-4">
                    <div style="margin-top:60px;">
                        <div v-if="datadetail!=null&&datadetail.category==null" style="border:1px solid #EAEAEA">
                        <div style="margin-top:30px;">
                            <h4 class="mb-3">{{datadetail.contract_date}}</h4>
                            <div style="margin-top:-20px; margin-bottom:50px">
                                <h3><span style="background-color:#B7F0B1; color:#00c03f;" class="badge badge-pill badge-danger text-uppercase">계약이력</span> </h3>
                            </div>
                        </div>
                        <div style="">
                            <div style="float:left; margin-right:15px; margin-left:25px">
                                <img :src="url+datadetail.image"  style="width:375px;height:295px;margin-bottom:20px"/>
                            </div>
                            <div>
                                <b-input-group prepend="주소" class="mt-3" style="width:350px;">
   			                        <b-form-input  readonly v-model="datadetail.address" style="background-color:white; text-align:center"></b-form-input>
  		                        </b-input-group>
                            </div>

                            <div>
                                <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                        <b-form-input  :value="datadetail.floor+'층'+' '+datadetail.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                        </b-input-group>
                            </div>

                            <b-input-group prepend="전용 면적" append="평" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			                    <b-form-input readonly style="background-color:white;text-align:center" :value="(datadetail.exclusive/3.3058).toFixed(0)"></b-form-input>
  		                    </b-input-group>

                            <b-input-group prepend="단 위" append="㎡" class="mt-3" style="width:170px; ">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.exclusive"></b-form-input>
  		                    </b-input-group>

					        <b-input-group prepend="건물종류" class="mt-3" style="width:170px; margin-right:10px; float:left;">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.kind"></b-form-input>
  		                    </b-input-group>

                            <b-input-group prepend="계약내용" class="mt-3" style="width:170px; margin-right:10px;">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.detail"></b-form-input>
  		                    </b-input-group>
					
					        <b-input-group v-if="datadetail.detail!='월세'" prepend="금액" append="만원" class="mt-3" style="width:350px; margin-right:10px; float:left;">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.cost"></b-form-input>
  		                    </b-input-group>

					        <b-input-group v-if="datadetail.detail=='월세'" prepend="금액" append="만원" class="mt-3" style="width:170px; margin-right:10px; float:left;">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.cost"></b-form-input>
  		                    </b-input-group>

                            <b-input-group v-if="datadetail.detail=='월세'" prepend="월세" append="만원" class="mt-3" style="width:170px; margin-right:10px;">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.monthly"></b-form-input>
  		                    </b-input-group>

                            <div style="margin-top:-20px; margin-left:25px; margin-bottom:30px;">
                                <b-input-group prepend="공인중개사"  class="mt-3" style="width:740px; ">
   			                        <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.license"></b-form-input>
  		                        </b-input-group>
                            </div> 
                        </div>  
                        </div>
                        <div v-if="datadetail!=null&&datadetail.category!=null" style="border:1px solid #EAEAEA">
                            <div style="margin-top:30px;">
                            <h4 class="mb-3">{{datadetail.startDate}} ~ {{datadetail.endDate}}</h4>
                            <div style="margin-top:-20px;margin-bottom:50px;">
                               <h3><span style="background-color:#B2CCFF; color:#1428A0;" class="badge badge-pill badge-danger text-uppercase">상태이력</span> </h3>
                            </div>
                        </div>
                        <div style="">
                            <div style="float:left; margin-right:15px; margin-left:25px">
                                <img :src="url+datadetail.image"  style="width:375px;height:295px;margin-bottom:20px"/>
                            </div>
                            <div>
                                <b-input-group prepend="주소" class="mt-3" style="width:350px;">
   			                        <b-form-input  readonly v-model="datadetail.address" style="background-color:white; text-align:center"></b-form-input>
  		                        </b-input-group>
                            </div>

                            <div>
                                <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                        <b-form-input  :value="datadetail.floor+'층'+' '+datadetail.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                        </b-input-group>
                            </div>

					        <b-input-group prepend="상태분류" class="mt-3" style="width:170px; margin-right:10px; float:left;">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.category"></b-form-input>
  		                    </b-input-group>

                            <b-input-group prepend="비용" append="만원" class="mt-3" style="width:170px; margin-right:10px;">
   			                    <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.cost"></b-form-input>
  		                    </b-input-group>
                            
                            <div style="margin-top:20px;">
                            <b-form-textarea
                                style="width:350px; height: 105px;"
                                id="textarea-no-resize"
                                rows="3"
                                no-resize
                                v-model="datadetail.detail"
                                readonly=""
                            ></b-form-textarea>
                            </div>

                            <div style="margin-top:-20px; margin-left:25px; margin-bottom:30px;">
                                <b-input-group prepend="공인중개사"  class="mt-3" style="width:740px; ">
   			                        <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.license"></b-form-input>
  		                        </b-input-group>
                            </div> 
                        </div>  
                        
                        </div>
                        <a href="javascript:" @click="goHome()" style="background-color:#00c03f; border-color:#00c03f; margin-top:30px" class="btn btn-danger">홈으로 <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
            </div>  
       </div>
    </div>
</div>
</template>

<script>
import {getUserBlockDetails} from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import TitleBar from '../common/TitleBar';
import { addAround } from "../../api/item.js";
export default {
   
  components:{
      TitleBar
  },
   data() {
      return {
        
        num:'',
        type:'',
        datadetail:null,
        url:'',



      };
   },
   computed:{
             
   },
   created(){
      this.url = getUrl();
      this.num = this.$route.query.num;
      this.type = this.$route.query.type;
      if(this.type==0){
         this.type=null;
      }
      getUserBlockDetails(this.num, this.type, responses=>{

         this.datadetail = responses.data;  
          
                addAround(this.datadetail.address, responses=>{
               
                })
                
      })
    
   },
   mounted() {
     
   },
   watch:{
      
      
   },
   methods: {  
      goHome(){
         this.$router.push('/');
      }
              
   },    
};
</script>
