<template>
    <div style="text-align:center;">
       <div id="add-listing" style="margin-top:30px; width:870px;  display: inline-block;">
            <!-- Section -->
           <!--  <div class="add-listing-section mb-4" style=" display: inline-block;">
                <b-navbar type="white" variant="white" style="margin-right:-35px; width:830px; margin-top:-32px">
                        <b-nav-form>
                <input type="text" v-b-modal.modal-4 v-model="date1"  placeholder="날짜검색" class="form-control form-control-alternative" style="float:left; height:43px; width:150px; margin-left:-27px;">
                <p style="float:left;font-size:30px; margin-left:10px; margin-right:10px; margin-top:5px;">~</p>
                <input type="text" v-b-modal.modal-5  v-model="date2" placeholder="" class="form-control form-control-alternative" style="float:left; height:43px; width:150px;">
                <a href="#" style="background-color:#1428A0; border-color:#1428A0; margin-top:1px; margin-left:10px; margin-right:40px;  float:left;" class="btn btn-danger">검색하기</a>
                        <b-dropdown id="dropdown-form" text="계약내용" ref="dropdown" class="m-2" style="width:110px">
                            <b-dropdown-form style="text-align:center">
                                <h4>매물종류</h4>
                                <p style="font-size:13px">중복선택이 가능합니다.</p>
                                <b-form-checkbox-group
                                    style="margin-right:-15px; margin-top:-10px;"
                                    v-model="selecteds"
                                    :options="options"
                                    class="mb-3"
                                    value-field="item"
                                    text-field="name"
                                    disabled-field="notEnabled"
                                    ></b-form-checkbox-group>
                                </b-dropdown-form>
                            </b-dropdown>

                        <b-dropdown id="dropdown-form" text="금액" ref="dropdown" class="m-2" style="">
                            <b-dropdown-form style="text-align:center"> 
                                <div>
                                    <h4>금액</h4>
                                    <b-form-input id="range-1" v-model="value" type="range" min="100" max="100000" step="100" style="width:200px"></b-form-input>
                                    <div class="mt-2">{{ value2 }}{{ won }} {{ value3 }}{{ won2 }}</div>
                                </div>
                            </b-dropdown-form>
                        </b-dropdown>

                        <b-dropdown id="dropdown-form" text="크기" ref="dropdown" class="m-2" >
                            <b-dropdown-form style="text-align:center"> 
                                <div>
                                    <h4>방크기</h4>
                                    <b-form-input id="range-1" v-model="value4" type="range" min="1" max="35" step="1" style="width:200px"></b-form-input>
                                    <div class="mt-2">{{ value4 }}평 ({{value5}}㎡)</div>
                                </div>
                            </b-dropdown-form>
                        </b-dropdown>
                        <a href="#" style="background-color:#1428A0; border-color:#1428A0; margin-top:1px; margin-left:6px; margin-right:-10px;" class="btn btn-danger">초기화</a> 
                    </b-nav-form>
                </b-navbar> 
          
          
            </div>-->

            <div class="add-listing-section mb-4" style="margin-top:40px">
               <!-- Headline -->
          
				<!-- <a href="#" style=" background-color:#CC3D3D; border-color:#CC3D3D; margin-top:-80px; margin-bottom:-40px; margin-left:740px" class="btn btn-sm btn-info ">신고하기</a> -->
					
				
               <div  style="margin-bottom:-20px">
                  <h3 class="mb-3">건물정보</h3>
                  
               </div>
               <div>
                   <h3><span style="background-color:#B2CCFF; color:#1428A0;" class="badge badge-pill badge-danger text-uppercase">유효</span> </h3>
               </div>
               <div style="margin-top:20px; display: inline-block;">
                     <vue-daum-map
                     :appKey="appKey"
                     :center="center"
                     :level.sync="level"
                     :mapTypeId="mapTypeId"
                     :libraries="libraries"
                     @load="onLoad"
                     style="width:770px; height:400px;"/>
               
               </div>  
                <div v-for="(list,index) in list" :key="index">
                    <div v-if="list.category==null">
                            <div style=" margin-top:40px">
               <h4 v-if="list.endDate!=''" class="mb-3">{{list.startDate}} ~ {{list.endDate}}</h4>
               <h4 v-else class="mb-3">{{list.startDate}}</h4>
                <div v-if="list.category!=null" style=" margin-top:-20px;">
                   <h3><span style="background-color:#B7F0B1; color:#2F9D27;" class="badge badge-pill badge-danger text-uppercase">상태이력</span> </h3>
               </div>
               <div v-else style=" margin-top:-20px;">
                   <h3><span style="background-color:#B2CCFF; color:#1428A0;" class="badge badge-pill badge-danger text-uppercase">계약이력</span> </h3>
               </div>
               </div>
                <div style="margin-top:20px; ">
                    <div style="float:left; margin-right:15px; margin-left:25px">
                        <img :src="url+list.image"  style="width:375px;height:295px;margin-bottom:20px"/>
                    </div>
                    <div>
                        <b-input-group prepend="주소" class="mt-3" style="width:350px;">
   			                <b-form-input  readonly v-model="list.address" style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-if="list.dong!=''">
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="list.dong+'동'+'  '+list.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-else>
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="list.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>
                     
                  
                     <b-input-group prepend="공급 면적" append="평" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="(list.supply/3.3058).toFixed(0)"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="단 위" append="㎡" class="mt-3" style="width:170px;">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.supply" ></b-form-input>
  		            </b-input-group>

                    <b-input-group prepend="전용 면적" append="평" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="(list.exclusive/3.3058).toFixed(0)"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="단 위" append="㎡" class="mt-3" style="width:170px; ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.exclusive"></b-form-input>
  		            </b-input-group>

                    <b-input-group prepend="계약내용" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.details"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="금액" append="만원" class="mt-3" style="width:170px; ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.cost"></b-form-input>
  		            </b-input-group>
                 
                    <div style="display: inline-block; margin-top:-20px;">
                    <b-input-group prepend="임대인"  class="mt-3" style="width:255px; float:left; margin-right:15px">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.name"></b-form-input>
  		            </b-input-group>

                    <b-input-group prepend="공인중개사"  class="mt-3" style="width:470px; ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.license"></b-form-input>
  		            </b-input-group>
                    </div>
                </div>  
                <hr/>
                    </div>
                    <div v-else> 
                        <div style=" margin-top:40px">
               <h4 v-if="list.endDate!=''" class="mb-3">{{list.startDate}} ~ {{list.endDate}}</h4>
               <h4 v-else class="mb-3">{{list.startDate}}</h4>
                <div v-if="list.category!=null" style=" margin-top:-20px;">
                   <h3><span style="background-color:#B7F0B1; color:#2F9D27;" class="badge badge-pill badge-danger text-uppercase">상태이력</span> </h3>
               </div>
               <div v-else style=" margin-top:-20px;">
                   <h3><span style="background-color:#B2CCFF; color:#1428A0;" class="badge badge-pill badge-danger text-uppercase">계약이력</span> </h3>
               </div>
               </div>
                <div style="margin-top:20px; ">
                    <div style="float:left; margin-right:15px; margin-left:25px; ">
                        <img :src="url+list.image"  style="width:375px;height:270px;margin-bottom:20px"/>
                    </div>
                    <div>
                        <b-input-group prepend="주소" class="mt-3" style="width:350px;">
   			                <b-form-input  readonly v-model="list.address" style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-if="list.dong!=''">
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="list.dong+'동'+'  '+list.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>

                    <div v-else>
                        <b-input-group prepend="기타" class="mt-3" style="width:350px;">
   			                <b-form-input  v-model="list.ho+'호'" readonly style="background-color:white; text-align:center"></b-form-input>
  		                </b-input-group>
                    </div>
                    

                    <b-input-group prepend="상태" class="mt-3" style="width:170px; margin-right:10px; float:left;   ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.category"></b-form-input>
  		            </b-input-group>

                     <b-input-group prepend="금액" append="만원" class="mt-3" style="width:170px;  ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="list.cost"></b-form-input>
  		            </b-input-group>

                    <div style="margin-top:16px;">
                
                   
                    <div>
                    <b-form-textarea
                    style="width:350px"
                    id="textarea-no-resize"
                    rows="3"
                    no-resize
                    v-model="list.details"
                    readonly=""
                    ></b-form-textarea>
                    </div>
                    </div>
                    <div style="margin-top:-20px;">
                
                    <b-input-group prepend="공인중개사"  class="mt-3" style="width:745px; margin-left:22px; ">
   			            <b-form-input readonly style="background-color:white; text-align:center" v-model="list.license"></b-form-input>
  		            </b-input-group>
                    </div>
                </div>  
                <hr/>
                    </div>
               
                </div>
            
            </div>  
            <infinite-loading @infinite="infiniteHandler" spinner="circles"></infinite-loading>

       </div>

       <b-modal id="modal-4" title="날짜검색" hide-footer >
    	<b-calendar v-model="value" @selected="selected(value)" width="450px" style="margin-top:20px"></b-calendar>
  	</b-modal>

     <b-modal id="modal-5" title="날짜검색" hide-footer>
    	<b-calendar v-model="value" @selected="selected2(value)"  width="450px" style="margin-top:20px"></b-calendar>
  	</b-modal>
    </div>
</template>

<script>

import VueDaumMap from 'vue-daum-map'  
import MyList from '../../data/listing2.json';
import InfiniteLoading from 'vue-infinite-loading';
import { getblockDetail } from "../../api/item.js";
import { getUrl } from "../../api/index.js";
export default {
  created(){
        this.url = getUrl();
        getblockDetail(this.$route.query.address, this.$route.query.dong, this.$route.query.ho, response => {
                            
            this.blist = response.data;
           
            this.center = {lat:this.blist[0].latitude, lng:this.blist[0].longitude}
       
                                      
        })

  },
  components:{
      VueDaumMap,
      InfiniteLoading

  },
   data() {
      return {
         
        date1:"",
        date2:"",
        value:'',
        state : 'null',
        appKey: '4ad8ff4da9eb8b9507c5afaee2b238b4', // 테스트용 appkey
        center: {lat:36.355079, lng:127.298320}, // 지도의 중심 좌표
        level: 3, // 지도의 레벨(확대, 축소 정도),
        mapTypeId: VueDaumMap.MapTypeId.NORMAL, // 맵 타입
        libraries:['services', 'clusterer', 'drawing'], // 추가로 불러올 라이브러리
        map: null, // 지도 객체. 지도가 로드되면 할당됨.
        list:[],
        blist:[],
         options: [
          { item: 'A', name: '전세' },
          { item: 'B', name: '월세' },
          { item: 'C', name: '임대' },
          { item: 'D', name: '매매' },
    
        ],
        value: '100',
        won: '',
        value2: '',
        won2: '만원',
        value3:'100',  
        value4:'1',
        won3:'',
        value5:'3.3',
        selecteds: [],
        cost:'',
        url:'',

      };
   },
   mounted() {
       
       
     
   },
   watch:{
    value:function(hook){
           //(hook)
           if(hook<10000){
               this.value3 = hook;
               this.value2 ="";
               this.won = "";
               this.won2="만원"
           }else if(hook>=10000 && hook<100000){
               this.value2 = Math.floor(hook/10000);
               this.value3 = hook%10000;
               this.won = "억";
               this.won2="만원"
           }else if(hook==100000){
               this.value2="";
               this.won = "";
               this.value3='';
               this.won2="무제한"
           }
           
       },
       value4:function(hook){
           this.value5 = (hook*3.3058).toFixed(2)
       },
     
                            
   },
   methods: {  
       selected(date){
                 
                  this.date1 = date;
                  this.$bvModal.hide('modal-4')	
               }, 
               selected2(date){
                 
                  this.date2 = date;
                  this.$bvModal.hide('modal-5')	
               }, 
               onLoad (map) {
                    
                    new kakao.maps.Marker({
                       
                     position: map.getCenter(), 
                     map: map
                  });

                  var marker = new kakao.maps.Marker({
                                    position: map.getCenter(),
                                });
                                 marker.setMap(map);
                  this.map = map;
         
               },
               infiniteHandler($state) {
                setTimeout(() => {
                    
                }, 1500);
        
                setTimeout(() => { //스크롤 페이징을 띄우기 위한 시간 지연(1초)

                    const temp = [];
                    const size = this.list.length;
       
                    for (let i = size; i< size+3; i++) {
                        if(this.blist[i]!=null){
                
                            this.list.push(this.blist[i]);
                        }
                    }
                 
                    this.list = this.list.concat(temp);
                    $state.loaded();

                    if(this.list.length==this.blist.length){
                        $state.complete();
                  
                    }
   
                }, 1000)
                
    }
              
    },

    computed:{
            
    }
};
</script>
