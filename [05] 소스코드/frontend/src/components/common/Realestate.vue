<template>
    <div class="main-wrapper">
        <div id="content">
            <div class="container">
                <div class="profile-page">
                    <div class="card card-profile shadow" style="margin-top:200px; margin-bottom:30px;">
                        <div class="px-4">
                            <div class="row justify-content-center">
                                <div class="col-lg-3 order-lg-2">
                                    <div class="card-profile-image">
                                        <a href="#">
                                        <img v-if="$store.state.userInfo!=null&&$store.state.userInfo.picture!=null" v-bind:src="this.userInfo.picture" class="rounded-circle">
                                        <img v-if="$store.state.userInfo!=null&&$store.state.userInfo.picture==null" src="../../../public/images/kakaoimage.jpg" class="rounded-circle"></a>
                                    </div>
                                </div>
                                <div class="col-lg-4 order-lg-3 text-lg-right align-self-lg-center">
                                </div>
                                <div class="col-lg-4 order-lg-1">
                                	<div class="card-profile-stats d-flex justify-content-center">
                                	<div>
                                    	<span v-if="this.info!=null" class="heading">{{info.rnk}}</span>
                                    	<span class="description">랭킹</span>
                               		</div>
                                	<div>
                                    	<span v-if="this.info!=null" class="heading">{{info.block}}</span>
                                    	<span class="description">이력수</span>
                                	</div>
                                	<div>
                                    	<span v-if="this.info!=null" class="heading">{{info.point}}</span>
                                    	<span class="description">포인트</span>
                                	</div>
                            		</div>
                                </div>
                            </div>
                            <div style="text-align:right;">
                            <a href="#" v-b-toggle.sidebar-backdrop style=" background-color:#1428A0; border-color:#1428A0; margin-top:-160px" class="btn btn-sm btn-info ">이미지 변경하기</a>
                            </div>
                            <div v-if="this.info!=null" class="text-center mt-5">
                                <h3>{{info.name}}</h3>
                                <h4><span style="background-color:#59DA50; color:#fff;" class="badge badge-pill badge-danger text-uppercase">공인중개사</span></h4>
                                <br/>
                            	<div class="flex-grow-1 px-5">
									<div class="mb-4">
                                        <div class="detail-list mb-2">
                                            <div class="detail-list-label mb-1">
                                                 <small style="font-size:18px" class=" font-weight-bold">대표자명</small>
                                            </div>
											<p v-if="this.info!=null" class="m-0 text-muted">{{info.representative}}</p>
                                        </div>
                                        <br>
                                     	<div class="detail-list mb-2">
                                            <div class="detail-list-label mb-1">
                                                <i class="fa fa-map-marker mr-2" aria-hidden="true"></i>
                                                <small class="text-uppercase font-weight-bold">Address</small>
                                            </div>
                                            <p v-if="this.info!=null" class="m-0 text-small text-muted">{{info.address}}</p>
                                        </div>
										<br>
                                        <div class="detail-list mb-2">
                                            <div class="detail-list-label mb-1">
                                                <i class="fa fa-phone mr-2" aria-hidden="true"></i>
                                                <small class="text-uppercase font-weight-bold">Contact No.</small>
                                            </div>
                                            <p v-if="this.info!=null" class="m-0 text-small text-muted">{{info.phone}}</p>
                                        </div>
                                        
                                        <br>
                                        <div class="detail-list mb-2">
                                            <div class="detail-list-label mb-1">
                                            	<i class="fa fa-info-circle mr-2" aria-hidden="true"></i>
                                                <small class="text-uppercase font-weight-bold">중개사무소 등록번호</small>
                                            </div>
                                            
                                            <p v-if="this.info!=null" class="m-0 text-small text-muted">{{info.license}}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
						</div>
                        <div class="px-4">
                            <h3>등록한 건물 이력</h3>
                        </div>
                        <div class="row p-4" >
                                <div class="col-md-6 mb-4" v-for="(list, index) in list" :key="index">
                                    <div class="listing-item-container " @click="detail(list.num, list.category)">
                                        <div class="listing-item">
                                        <div style="text-align:right;">
                                        <img :src="url+list.image" alt="" style="width:100%; height:220px">
                                          <a v-if="list.category==null" href="#" style=" background-color:rgb(20, 40, 160); border-color:rgb(20, 40, 160); margin-top:-420px; margin-right:17px " class="btn btn-sm btn-info ">계약이력</a>
                                                 <a v-else href="#" style=" background-color:#86E57F; border-color:#86E57F;margin-top:-420px; margin-right:17px" class="btn btn-sm btn-info ">상태이력</a>  
                                        </div>
                                        <div class="listing-item-content" style="height:100px;">
                                      
                                                <div class="listing-item-inner" style="margin-top:-27px;">
                                                <h6>{{list.address}}</h6>
                                                <span v-if="list.dong!=''" ><small style="font-weight: bold;">{{list.dong}}동</small></span>
                                                <span><small style="font-weight: bold;"> {{list.ho}}호</small></span>
                                                    
                                        </div>

                                        </div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                        <div v-if="!check">
                            <a href="javascript:" @click="add()" v-b-modal.modal-4 class="btn btn-danger" style="background-color:#1428A0; border-color:#1428A0; width:100%">더보기</a>
                        </div>
                        <div v-else>
                            <a href="javascript:" v-b-modal.modal-4 class="btn btn-danger" style="background-color:#1428A0; border-color:#1428A0; width:100%;">정보가 존재하지 않습니다.</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div >
 
    <b-sidebar
      id="sidebar-backdrop"
      title="이미지"
      backdrop
      shadow
      style="z-index:99999;"
    >
      <div class="px-3 py-2">
        <b-form-file
                     accept="image/*"
                     v-model="file"
                     :state="Boolean(file)"
                     placeholder=""
                     drop-placeholder="Drop file here..."
                     style=""
                  ></b-form-file>
                  <br/>
                  <br/>
             
        <b-img v-if="this.info!=null&&this.info.picture!=null" :src="url+this.info.picture" fluid thumbnail></b-img>
        <b-img v-else-if="this.info!=null&&this.info.picture==null" src="static/agent_default.png" fluid thumbnail></b-img>
         <a href="javascript:" @click="register()" style="background-color:#1428A0; border-color:#1428A0; width:100%;" class="btn btn-danger">등록하기 <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </b-sidebar>
    </div>
     <b-modal id="modal-3" title="상세보기" scrollable  size="lg" hide-footer >   
            <div style="text-align:center;">
            <div id="add-listing" style=" width:690px;  display: inline-block;">
            <div class="mb-4" style="margin-left:-50px; margin-right:-50px;">
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
                        style="width:350px"
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
            </div>
            </div>  
            </div>
  	    </b-modal>
    </div>
</template>

<script>
import {mapState} from 'vuex';
import {getRealestateInfo} from "../../api/user.js";
import {getUserBlock} from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import {addAimage} from "../../api/user.js";
import {getUserBlockDetail} from "../../api/item.js";
export default {
    data() {
        return {info: null,
         data:null,
        count:0,
        list:[],
        check:false,
        url:'',
        file:null,
        onfile:'',
        datadetail:null,
        }
    },
    created() {
        this.url = getUrl();
        if (this.$store.state.userInfo != null) {
            getRealestateInfo(this.$store.state.userInfo.num, responses => {
                //console.log(responses.data)
                this.info = {
                    name: responses.data.name,
                    representative: responses.data.representative,
                    address: responses.data.address,
                    phone: responses.data.phoneNum,
                    license: responses.data.license,
                    rnk: responses.data.rnk,
                    point: responses.data.point,
                    block: responses.data.count,
                    picture: responses.data.picture
                }
            })
            getUserBlock(this.$store.state.userInfo.num, responses=>{
                //console.log("내가등록한 블록 결과")
              
                this.data = responses.data
                if(this.data.length==0){
                    this.check = true;
                }else{
                    for(var i=this.count; i<this.count+2; i++){
                    if(this.data[i]!=null){
                     
                        this.list.push(this.data[i]);
                     
                    }
                   
                    }

                //console.log(this.list)
                }
                
            })
        }
    },
    components: {},
    watch: {
        userInfo: function (hook) {
            if (hook != null) {
                getRealestateInfo(this.$store.state.userInfo.num, responses => {
                //console.log(responses.data)
                this.info = {
                    name: responses.data.name,
                    representative: responses.data.representative,
                    address: responses.data.address,
                    phone: responses.data.phoneNum,
                    license: responses.data.license,
                    rnk: responses.data.rnk,
                    point: responses.data.point,
                    block: responses.data.count,
                    picture: responses.data.picture
                }
            })
            getUserBlock(this.$store.state.userInfo.num, responses=>{
                //console.log("내가등록한 블록 결과")
                //console.log(responses.data)
                this.data = responses.data
                if(this.data.length==0){
                    this.check = true;
                }
                for(var i=this.count; i<this.count+2; i++){
                    if(this.data[i]!=null){
                        this.list.push(this.data[i]);
                    }
                   
                }

                //console.log(this.list)
            })
            }
        },
        file: function (hook){
            //console.log(hook)
        }
    },
    computed: {
        ...mapState(['userInfo'])
    },
    methods:{
        add(){
             
                this.count = this.count+2;
          
                if(this.count>=this.data.length){
               
                    this.check = true;
                }else{
                
                 for(var i=this.count; i<this.count+2; i++){
                   
                    if(this.data[i]!=null){
                      
                        this.list.push(this.data[i]);
                    }
                   
                }
                }
              
            },
            register(){
                if(this.file==null){
                    alert("이미지를 등록해주세요.")
                }else{
                    addAimage(this.$store.state.userInfo.num,this.file,2, responses => {
                   // console.log(responses.data)
                  
                    getRealestateInfo(this.$store.state.userInfo.num, responses => {
                //console.log(responses.data)
                this.info = {
                    name: responses.data.name,
                    representative: responses.data.representative,
                    address: responses.data.address,
                    phone: responses.data.phoneNum,
                    license: responses.data.license,
                    rnk: responses.data.rnk,
                    point: responses.data.point,
                    block: responses.data.count,
                    picture: responses.data.picture
                }
            })

                })
                alert("등록이 완료되었습니다.")
                }
                
            },
             detail(num,type){
                    getUserBlockDetail(num, type, responses=>{
                    this.datadetail = responses.data;
                    this.$bvModal.show('modal-3');	    
                })
            },
            
    }
};
</script>
<style>
.modal-content{
    margin-top : 80px;
    height:auto;
    
  
}
</style>