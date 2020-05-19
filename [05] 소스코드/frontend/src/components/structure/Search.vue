<template>
   <div style="text-align:center;">

      <div class="row" style="width:920px; display: inline-block;">
      <div class="col-lg-12">

         <div id="add-listing" >
             
            <!-- Section -->
            <div class="add-listing-section mb-4" style="margin-top:40px;">
                <div style="margin-bottom:40px; display: inline-block; ">
                    <b-form-input v-model="address" class="mr-sm-2" readonly style="float:left; width:500px;" ></b-form-input>
                    <a href="#" v-b-modal.modal-4 class="btn btn-danger" style="background-color:#1428A0; border-color:#1428A0; margin-top:2px">주소검색</a>
                    <a href="#" @click="reset()" class="btn btn-danger" style="background-color:#1428A0; border-color:#1428A0; margin-left:40px; margin-top:2px">초기화</a>
                </div>
                
               <!-- Headline -->
               <div class="add-listing-headline" style="margin-bottom:-15px">
              
                  <div style="margin-top:30px;">
                      <h3 @click="changeaddress()"><a href="#">나의 주변으로부터 건물 찾기</a></h3>
                      <div v-if="this.check==false">
                      <p v-if="this.$store.state.userInfo!=null && this.$store.state.userInfo.address!=null">( {{userInfo.address}})</p>
                      <p v-else-if="this.$store.state.userInfo!=null && this.$store.state.userInfo.address==null">( 내 정보에서 주소정보를 입력해주세요. )</p>
                      <p v-else-if="this.$store.state.userInfo==null">( 로그인을 해주세요! )</p>      
                      </div>
                      <div v-else>
                           <p v-if="this.check && !this.errer">( {{getaddress}} )</p>
                           <p v-if="this.errer">( 검색한 결과를 찾을 수가 없습니다. )</p>        
                      </div>
                           
                  </div>
                  <div style="text-align:left; margin-top:40px; margin-bottom:-10px;">
                  <span style="margin-left:50px; font-size:15px;">* 지도에는 건물에 등록된 이력을 표시해주고 있습니다.</span>
                  </div>
               </div>
               
                    <div style=" display: inline-block;">
                     <vue-daum-map
                     :appKey="appKey"
                     :center.sync="center"
                     :level.sync="level"
                     :mapTypeId="mapTypeId"
                     :libraries="libraries"
                     @load="onLoad"
                     style="width:740px;height:600px;"/>
                    </div>    

            </div>
         </div>
      </div>
    </div>
  
    <b-modal id="modal-3" title="상세보기" scrollable  size="lg" hide-footer>
     
        <div class="row" style="margin-top:20px" >
        <div class="col-lg-6 col-md-12 grid-layout-list mb-4" v-for="(list,index) in data" :key="index">
			<div class="list-cap" style="height:310px">
				<div class="list-cap-list mb-4" @click="detail(list.address,list.dong,list.ho)">        
                    <div class="img-list">
                  
                        
						<img :src="url+list.image" alt="" style="width:353px; height:200px">
                    </div>
						<div class="list-cap-content list-cap-content--style">
						    <h6 class="mt-2">{{list.address}}</h6>
							<div class="address-bar"> 
                                <h6 v-if="list.dong!=''" style="float:left; margin-right:10px">{{list.dong}}동</h6><h6>{{list.ho}}호</h6>
                            </div>
						</div>
			
				</div>
		    </div>
        </div>
        </div>
  	</b-modal>

      <b-modal id="modal-4" title="주소검색" hide-footer>
    	<DaumPostcode style="height: 400px;"
      		:on-complete=handleAddress
    	/>
  	</b-modal>

     
    </div>


</template>

<script>
import VueDaumMap from 'vue-daum-map'  
import {mapState} from 'vuex';
import MyList from '../../data/listing.json';
import { getrecord } from "../../api/item.js";
import { getdetailrecord } from "../../api/item.js";
import { getImage } from "../../api/item.js";
import { getblockDetail } from "../../api/item.js";
import { getUrl } from "../../api/index.js";

import DaumPostcode from 'vuejs-daum-postcode';
export default {
  created(){

      this.url = getUrl();
      if(this.$route.query.search!=null){
          this.getaddress = this.$route.query.search;
      }
      
  },
  components:{
     VueDaumMap,
     DaumPostcode,
     

  },
   data() {
      return {
         
        state : 'null',
        appKey: '4ad8ff4da9eb8b9507c5afaee2b238b4', // 테스트용 appkey
        center: {lat:36.355079, lng:127.298320}, // 지도의 중심 좌표
        level: 3, // 지도의 레벨(확대, 축소 정도),
        mapTypeId: VueDaumMap.MapTypeId.NORMAL, // 맵 타입
        libraries:['services', 'clusterer', 'drawing'], // 추가로 불러올 라이브러리
        map: null, // 지도 객체. 지도가 로드되면 할당됨.
        data:null,
        dataImage:null,


        url:'',
        getaddress:'',
        viewaddress:'',
        address:'',
        recode:null,
        errer:false,
        send:[],
        check:false,
        list:[],
        marker:null,

      }
            
      
   },
   mounted() {


     
   },
   watch:{
       userInfo:function(hook){
           if(hook!=null){
               this.getaddress='';
               this.errer=false;
               this.check =false;
               this.onLoad(this.map);
           }
       },
      
                            
   },
   methods: {  
              
               onLoad (map) {
                
                //모든 건물이력 불러오기
                getrecord(response=>{
                    
                    (response.data)
                    this.recode = response.data;

                  

                //마커이미지(내위치만)
                var imageSrc = '/static/marker.png'; // 마커이미지의 주소입니다    
                var imageSize = new kakao.maps.Size(50, 50); // 마커이미지의 크기입니다
                var imageOption = {offset: new kakao.maps.Point(27, 69)};
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
            
                var clusterer = new kakao.maps.MarkerClusterer({
                    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
                    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
                    minLevel: 1, // 클러스터 할 최소 지도 레벨
                    disableClickZoom: true, // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
                    
                }); 

                //호이스팅 문제해결
                for (var i = 0; i < this.recode.length; i ++) {
                        var coords = new kakao.maps.LatLng(this.recode[i].latitude, this.recode[i].longitude);
                        let marker = new kakao.maps.Marker({//호이스팅해결
                        position : coords
                    });
                    clusterer.addMarker(marker);

                    //마커 클릭 이벤트
                    //(marker.getPosition())
                     kakao.maps.event.addListener(marker, 'click', (markers)=> {
                      
                        this.$bvModal.show('modal-3');	

                        this.send = [];
                        this.data =null;
                       
                        this.send[0] = {latitude:marker.getPosition().Ha.toFixed(10),
                        longitude:marker.getPosition().Ga.toFixed(10)};
                    
                        
                        
                        getdetailrecord(this.send, response => {
                    
                            this.data = response.data;
                           
                        })
                    });
                }  
                    
                    //클릭이벤트
                    kakao.maps.event.addListener(clusterer, 'clusterclick', (cluster)=> {
                        this.$bvModal.show('modal-3');	
                      
                        var markers = cluster.getMarkers();
                        //("새로운마커 : "+cluster.getMarkers().length)
                        this.send = [];
                        this.data =null;
                        for(var idx=0; idx<markers.length; idx++){
                        //(markers);
                        this.send[idx] = {latitude:markers[idx].getPosition().Ha.toFixed(10),
                        longitude:markers[idx].getPosition().Ga.toFixed(10)};
                        }
                  

                        this.dataImage = null;
                        getdetailrecord(this.send, response => {
                      
                            this.data = response.data;
                           
                            })
        
                    });

                //내주소 마커표시
                   
                   if(this.$store.state.userInfo!=null){
                      
                        var geocoder = new kakao.maps.services.Geocoder()

                        if(this.getaddress==''){
                       
    
                            geocoder.addressSearch(this.$store.state.userInfo.address, (result, status)=> {

                            if (status === kakao.maps.services.Status.OK) {
                                var moveLatLon = new kakao.maps.LatLng(result[0].y, result[0].x);
                                if(this.marker!=null){
                                    this.marker.setMap(null);
                                }
                                
                                map.setCenter(moveLatLon)

                                this.marker = new kakao.maps.Marker({
                                    position: map.getCenter(),
                                    image: markerImage 
                                });
                                 this.marker.setMap(map);
                    
                                
                            }
                        });
                        }else{
                         
                            this.check = true;
                            geocoder.addressSearch(this.getaddress, (result, status)=> {
                         
                            if (status === kakao.maps.services.Status.OK) {
                                var moveLatLon = new kakao.maps.LatLng(result[0].y, result[0].x);
                             
                                if(this.marker!=null){
                                 this.marker.setMap(null);
                            }
                                this.errer = false;
                                this.viewaddress = this.$route.query.search;
                                map.setCenter(moveLatLon)

                                this.marker = new kakao.maps.Marker({
                                    position: map.getCenter(),
                                    image: markerImage 
                                });
                                 this.marker.setMap(map);
                    
                                
                            }else{
                                this.errer = true;;
                           
                            }
                        });
                         
                        }

                        
                    }else{
                        //로그인을 않했을 때
                        var geocoder = new kakao.maps.services.Geocoder()
                        
                        if(this.getaddress!=''){
                   
                            geocoder.addressSearch(this.getaddress, (result, status)=> {
                         
                            if (status === kakao.maps.services.Status.OK) {
                                var moveLatLon = new kakao.maps.LatLng(result[0].y, result[0].x);
                               if(this.marker!=null){
                                 this.marker.setMap(null);
                            }
                                this.check = true;
                                this.errer = false;
                                map.setCenter(moveLatLon)

                                this.marker = new kakao.maps.Marker({
                                    position: map.getCenter(),
                                    image: markerImage 
                                });
                                 this.marker.setMap(map);
                    
                                
                            }else{
                                this.check = true;
                                this.errer = true;
                               
                            }
                        });
                        }else{
                            if(this.marker!=null){
                                 this.marker.setMap(null);
                            }
                           
                            this.marker = new kakao.maps.Marker({
                            position: map.getCenter(),
                            image: markerImage 
                            });
                            this.marker.setMap(map);
                        }

                        
                        
                    }
                    
                    });
                    this.map = map;    
               },
               detail(address,dong,ho){
                   (address+" "+dong+" "+ho)
                   let route = this.$router.resolve({name : 'detailbuilding', query:{
                            address : address,
                            dong : dong,
                            ho : ho
                    }});
                    window.open(route.href, '_blank');
      
               },
               handleAddress(data){
                   
               let fullAddress = data.address;
               let extraAddress = '';
               if (data.addressType === 'R') {
               if (data.bname !== '') {
                  extraAddress += data.bname
               }
               if (data.buildingName !== '') {
         	      extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName)
               }
         	      fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '')
               }
         
               this.address = data.address;

               this.getaddress = this.address;

               this.onLoad(this.map);
               this.$bvModal.hide('modal-4')	
            },
            changeaddress(){
                if(this.$store.state.userInfo==null){
                    alert("로그인이 필요합니다!")
                }else{
                     this.getaddress = this.$store.state.userInfo.address;
                this.check = false;
                this.address ='';
                this.onLoad(this.map);
            }
                },
                reset(){
                    this.getaddress='';
                    this.address='';
                    this.onLoad(this.map);
                    this.check =false;
                }
              
         },
         computed:{
             ...mapState(['userInfo']),
         }
};
</script>
<style>
.modal-content{
    margin-top : 80px;
    height:auto;
}

.tclass{
    font-size:50px;
}
</style>