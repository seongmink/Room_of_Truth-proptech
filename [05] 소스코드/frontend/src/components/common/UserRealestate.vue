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
                                        <img v-if="this.info!=null && this.info.picture!=null"  v-bind:src="this.info.picture" class="rounded-circle">
                                        <img v-if="this.info!=null && this.info.picture==null" src="../../../public/images/kakaoimage.jpg" class="rounded-circle"></a>
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
                                <div class="col-md-6 mb-4" v-for="(lists, index) in list" :key="index">
                                    <div class="listing-item-container " @click="detail(lists.address,lists.floor,lists.ho)">
                                        <div class="listing-item">
                                        <div style="text-align:right;">
                                            <img :src="url+lists.image" alt="" style="width:100%; height:250px; ">
                                            <a v-if="lists.type=='계약'" href="#" style=" background-color:#00c03f; border-color:#00c03f; margin-top:-460px; margin-right:17px " class="btn btn-sm btn-info ">계약이력</a>
                                            <a v-else href="#" style=" background-color:rgb(20, 40, 160); border-color:rgb(20, 40, 160);margin-top:-460px; margin-right:17px" class="btn btn-sm btn-info ">상태이력</a>  
                                        </div>
                                        
                                        <div class="listing-item-content" style="height:100px;">
                                                
                                                <div class="listing-item-inner" style="margin-top:-27px;">
                                                <h6>{{lists.address}}</h6>
                                                <small style="font-weight: bold;">{{lists.floor}}층</small>
                                                <span><small style="font-weight: bold;"> {{lists.ho}}호</small></span>
                                                  
                                        </div>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                        <div v-if="!check">
                            <a href="javascript:" @click="add()" v-b-modal.modal-4 class="btn btn-danger" style="background-color:#00c03f; border-color:#00c03f; width:100%">더보기</a>
                        </div>
                        <div v-else>
                            <a href="javascript:" v-b-modal.modal-4 class="btn btn-danger" style="background-color:#1428A0; border-color:#1428A0; width:100%;">정보가 존재하지 않습니다.</a>
                        </div>
                       
                    </div>
                </div>
            </div>
        </div>

        
    </div>
</template>

<script>
import {mapState} from 'vuex';
import {getRealestateInfo} from "../../api/user.js";
import {getUserBlock} from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import {getUserBlockDetail} from "../../api/item.js";




export default {
    data() {
        return {info: null,
        data:null,
        count:0,
        list:[],
        check:false,
        url:'',
        datadetail:null,

        }
    },
    created() {
            this.url = getUrl();
            getRealestateInfo(this.$route.query.num, responses => {
              
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
            }),

            getUserBlock(this.$route.query.num, responses=>{
  
                this.data = responses.data
               
                if(this.data.length==0){
                    this.check = true;
                }else{
                    for(var i=this.count; i<this.count+2; i++){
                    if(this.data[i]!=null){
                        this.list.push(this.data[i]);
                        }
                    }
                
                   
                }
                
                
            })
          
        
    },
    components: {},
    watch: {
      
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
            detail(address, floor, ho){
                let route = this.$router.resolve({name : 'Detail', query:{
                            address : address,
                            floor : floor,
                            ho : ho
                    }});
                    window.open(route.href, '_blank');
           
           
            }
    }
};
</script>
<style>
.modal-content{
    margin-top : 80px;
    height:auto;
    
  
}
</style>
