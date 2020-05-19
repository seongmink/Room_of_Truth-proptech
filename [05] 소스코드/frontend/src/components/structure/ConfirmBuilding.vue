<template>
<div style="text-align:center;">
            <div id="add-listing" style="margin-top:30px; width:870px;  display: inline-block;">
            <div class="add-listing-section mb-4" style="margin-top:40px">
            <h3>등록된 이력 결과</h3>
            <br>
            <br>
            <div v-if="datadetail!=null&&datadetail.category==null">
                <div style="">
                    <h4 v-if="datadetail.endDate!=''" class="mb-3">{{datadetail.startDate}} ~ {{datadetail.endDate}}</h4>
                    <h4 v-else class="mb-3">{{datadetail.startDate}}</h4>
                    <div v-if="datadetail.category!=null" style=" margin-top:-20px;">
                        <h3><span style="background-color:#B7F0B1; color:#2F9D27;" class="badge badge-pill badge-danger text-uppercase">상태이력</span> </h3>
                    </div>
                    <div v-else style=" margin-top:-20px;">
                        <h3><span style="background-color:#B2CCFF; color:#1428A0;" class="badge badge-pill badge-danger text-uppercase">계약이력</span> </h3>
                     </div>
               </div>
                <div style="margin-top:20px; ">
                    <div style="float:left; margin-right:15px; margin-left:25px">
                        <img :src="url+datadetail.image"  style="width:375px;height:295px;margin-bottom:20px"/>
                    </div>
                    <div>
                        <b-input-group prepend="주소" class="mt-3" style="width:350px;">
   			                <b-form-input  readonly v-model="datadetail.address" style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-if="datadetail.dong!=''">
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="datadetail.dong+'동'+'  '+datadetail.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-else>
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="datadetail.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>
                     
                  
                     <b-input-group prepend="공급 면적" append="평" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="(datadetail.supply/3.3058).toFixed(0)"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="단 위" append="㎡" class="mt-3" style="width:170px;">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.supply" ></b-form-input>
  		            </b-input-group>

                    <b-input-group prepend="전용 면적" append="평" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="(datadetail.exclusive/3.3058).toFixed(0)"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="단 위" append="㎡" class="mt-3" style="width:170px; ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.exclusive"></b-form-input>
  		            </b-input-group>

                    <b-input-group prepend="계약내용" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.details"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="금액" append="만원" class="mt-3" style="width:170px; ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.cost"></b-form-input>
  		            </b-input-group>
                 
                    <div style="display: inline-block; margin-top:-20px;">
                    <b-input-group prepend="임대인"  class="mt-3" style="width:255px; float:left; margin-right:15px">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.name"></b-form-input>
  		            </b-input-group>

                    <b-input-group prepend="공인중개사"  class="mt-3" style="width:470px; ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.license"></b-form-input>
  		            </b-input-group>
                    </div>
                </div>  
        
            </div>
            <div v-else-if="datadetail!=null&&datadetail.category!=null"> 
                <div style="">
                <h4 v-if="datadetail.endDate!=''" class="mb-3">{{datadetail.startDate}} ~ {{datadetail.endDate}}</h4>
                <h4 v-else class="mb-3">{{datadetail.startDate}}</h4>
                <div v-if="datadetail.category!=null" style=" margin-top:-20px;">
                   <h3><span style="background-color:#B7F0B1; color:#2F9D27;" class="badge badge-pill badge-danger text-uppercase">상태이력</span> </h3>
               </div>
               <div v-else style=" margin-top:-20px;">
                   <h3><span style="background-color:#B2CCFF; color:#1428A0;" class="badge badge-pill badge-danger text-uppercase">계약이력</span> </h3>
               </div>
               </div>
                <div style="margin-top:20px; ">
                    <div style="float:left; margin-right:15px; margin-left:25px; ">
                        <img :src="url+datadetail.image"  style="width:375px;height:270px;margin-bottom:20px"/>
                    </div>
                    <div>
                        <b-input-group prepend="주소" class="mt-3" style="width:350px;">
   			                <b-form-input  readonly v-model="datadetail.address" style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-if="datadetail.dong!=''">
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="datadetail.dong+'동'+'  '+datadetail.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-else>
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="datadetail.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>
                    

                    <b-input-group prepend="상태" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.category"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="금액" append="만원" class="mt-3" style="width:170px;  ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.cost"></b-form-input>
  		            </b-input-group>

                    <div style="margin-top:16px;">
            
                        <div>
                        <b-form-textarea
                        style="width:350px;"
                        id="textarea-no-resize"
                        rows="3"
                        no-resize
                        v-model="datadetail.details"
                        readonly=""
                        ></b-form-textarea>
                        </div>
                    </div>
                    
                    <div style="margin-top:-20px;">
                
                        <b-input-group prepend="공인중개사"  class="mt-3" style="width:745px; margin-left:22px; ">
   			                <b-form-input readonly style="background-color:white; text-align:center" v-model="datadetail.license"></b-form-input>
  		                </b-input-group>
                    </div>
                    
                </div>  
               
            </div>   
            <a href="javascript:" @click="goHome()" style="background-color:#1428A0; border-color:#1428A0; margin-top:60px" class="btn btn-danger">홈으로 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
            </div>  
            </div>
</template>

<script>
import {getUserBlockDetail} from "../../api/item.js";
import { getUrl } from "../../api/index.js";

export default {
   
  components:{
   
     
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
      getUserBlockDetail(this.num, this.type, responses=>{
             
         this.datadetail = responses.data;  
      
                
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
