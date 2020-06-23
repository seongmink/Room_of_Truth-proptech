<template>
   <div style="text-align:center;">
      <div class="row" style="width:920px; display: inline-block;">
        <div class="col-lg-12">
            <div id="add-listing" >
                <div class="add-listing-section mb-4" style="margin-top:40px;">
                    <div style="margin-bottom:40px; display: inline-block; width:90%; margin-top:20px;">
                        <div style="float:left">
                            <select class="custom-select mb-4 form-control-alternative" v-model="locationSelect" style="width:200px; margin-right:20px;">
								<option selected="선택없음">시/도를 선택하세요.</option>
                                <option value="서울특별시">서울특별시</option>
								<option value="인천광역시">인천광역시</option>
                                <option value="부산광역시">부산광역시</option>
                                <option value="대구광역시">대구광역시</option>
								<option value="대전광역시">대전광역시</option>
								<option value="광주광역시">광주광역시</option>
                                <option value="울산광역시">울산광역시</option>      
                                <option value="강원도">강원도</option>
                                <option value="경기도">경기도</option>
                                <option value="경상남도">경상남도</option>
                                <option value="경상북도">경상북도</option>
								<option value="전라남도">전라남도</option>
                                <option value="전라북도">전라북도</option>
                                <option value="충청남도">충청남도</option>
                                <option value="충청북도">충청북도</option>
								<option value="세종특별자치시">세종특별자치시</option>
								<option value="제주특별자치도">제주특별자치도 </option>	    
                            </select>
                        </div>
                        <div style="float:left">
                            <select class="custom-select mb-4 form-control-alternative" v-model="locationSelect2" style="width:230px; margin-right:40px;">
                                <option selected="선택없음">시/군/구를 선택하세요.</option>
								<option v-for="(item, index) in location" :value="item" :key="index">{{ item }}</option>
                            </select>
                        </div>
                        <a href="#" @click="search()"  class="btn btn-danger" style="background-color:#00c03f; float:left; border-color:#00c03f; margin-top:2px">주소검색</a>
                        <a href="#" @click="reset()" class="btn btn-danger" style="background-color:#00c03f; border-color:#00c03f; margin-left:40px; margin-top:2px">초기화</a>
                    </div>
                    <b-overlay v-if="this.$store.state.userInfo!=null && this.searchdata==''" :show="show" rounded="sm" style="margin-bottom:70px;">
                    </b-overlay>
                    <search-recommendation v-if="ok" :data = searchdata></search-recommendation>
                    <div class="add-listing-headline" style="margin-bottom:-15px">
                        <div style="text-align:left; margin-top:-20px; margin-bottom:-10px;">
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
                     style="width:775px;height:600px;"/>
                    </div>  
                </div>
            </div>
        </div>
      </div>
        <b-modal id="modal-5" title="상세보기" scrollable  size="lg" hide-footer>
            
            <div class="row" style="margin-top:20px" >
                <div class="col-lg-6 col-md-12 grid-layout-list mb-4" v-for="(list,index) in data" :key="index">
			        <div class="list-cap" style="height:310px">
				        <div class="list-cap-list mb-4" @click="detail(list.address,list.floor,list.ho)">        
                            <div class="img-list" style="height:200px;">
					            <img :src="url+list.image" alt="" style="width:353px; height:200px; max-height:initial;">
                            </div>
					        <div class="list-cap-content " style="margin-top:7px; height:100px;">
						        <h6 style="font-size:14px;" class="mt-2">{{list.address}}</h6>
						        <div class="address-bar"> 
                                    <h6 style="float:left; margin-right:10px;font-size:14px;">{{list.floor}}층 {{list.ho}}호</h6>
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
import VueDaumMap from 'vue-daum-map'  
import {mapState} from 'vuex';
import MyList from '../../data/listing.json';
import { getrecord } from "../../api/item.js";
import { getdetailrecord } from "../../api/item.js";
import { getImage } from "../../api/item.js";
import { getblockDetail } from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import { getLoginData } from "../../api/item.js";
import SearchRecommendation from "../common/SearchRecommendation";

import DaumPostcode from 'vuejs-daum-postcode';

export default {
  created(){

      this.url = getUrl();

    

      //로그인한 사용자만 광고데이터 받기
      if(this.$store.state.userInfo!=null){
			getLoginData(this.$store.state.userInfo.num,response => {
				
                this.searchdata = response;
                this.ok = true;
                this.show = false;
			});
        }

      
  },
  components:{
     VueDaumMap,
     DaumPostcode,
     SearchRecommendation
  },
   data() {
      return {
        show:true,
        searchdata:'',
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
        locationSelect:'시/도를 선택하세요.',
		locationSelect2:'시/군/구를 선택하세요.',
				location:null,
				locations:[
        		['강남구','강동구','강북구','강서구','관악구','광진구','구로구','금천구','노원구','도봉구','동대문구','동작구','마포구','서대문구','서초구','성동구','성북구','송파구','양천구','영등포구','용산구','은평구','종로구','중구','중랑구'],
        		['강서구','금정구','남구','동구','동래구','부산진구','북구','사상구','사하구','서구','수영구','연제구','영도구','중구','해운대구','기장군'],
        		['남구','달서구','동구','북구','서구','수성구','중구','달성군'],
        		['계양구','남구','남동구','동구','부평구','서구','연수구','중구','강화군','옹진군'],
        		['광산구','남구','동구','북구','서구'],
        		['대덕구','동구','서구','유성구','중구'],
        		['남구','동구','북구','중구','울주군'],
        		['강릉시','동해시','삼척시','속초시','원주시','춘천시','태백시','고성군','양구군','양양군','영월군','인제군','정선군','철원군','평창군','홍천군','화천군','횡성군'],
        		['고양시 덕양구','고양시 일산구','과천시','광명시','광주시','구리시','군포시','김포시','남양주시','동두천시','부천시 소사구','부천시 오정구','부천시 원미구','성남시 분당구','성남시 수정구','성남시 중원구','수원시 권선구','수원시 장안구','수원시 팔달구','시흥시','안산시 단원구','안산시 상록구','안성시','안양시 동안구','안양시 만안구','오산시','용인시','의왕시','의정부시','이천시','파주시','평택시','하남시','화성시','가평군','양주군','양평군','여주군','연천군','포천군'],
        		['거제시','김해시','마산시','밀양시','사천시','양산시','진주시','진해시','창원시','통영시','거창군','고성군','남해군','산청군','의령군','창녕군','하동군','함안군','함양군','합천군'],
        		['경산시','경주시','구미시','김천시','문경시','상주시','안동시','영주시','영천시','포항시 남구','포항시 북구','고령군','군위군','봉화군','성주군','영덕군','영양군','예천군','울릉군','울진군','의성군','청도군','청송군','칠곡군'],
        		['광양시','나주시','목포시','순천시','여수시','강진군','고흥군','곡성군','구례군','담양군','무안군','보성군','신안군','영광군','영암군','완도군','장성군','장흥군','진도군','함평군','해남군','화순군'],
        		['군산시','김제시','남원시','익산시','전주시 덕진구','전주시 완산구','정읍시','고창군','무주군','부안군','순창군','완주군','임실군','장수군','진안군'],
        		['서귀포시','제주시','남제주군','북제주군'],
        		['공주시','논산시','보령시','서산시','아산시','천안시','금산군','당진군','부여군','서천군','연기군','예산군','청양군','태안군','홍성군'],
        		['제천시','청주시 상당구','청주시 흥덕구','충주시','괴산군','단양군','보은군','영동군','옥천군','음성군','진천군','청원군'],
        		['반곡동','소담동','보람동','대평동','가람동','한솔동','나성동','새롬동','다정동','어진동','종촌동','고운동','아름동','도담동','조치원읍','연기면','연동면','부강면','금남면','장군면','연서면','전의면','전동명','소정면'],
                ],
                markers:[],
                ok:false,

      }
            
      
   },
   mounted() {


     
   },
   watch:{
       userInfo:function(hook){
           if(hook!=null){
               getLoginData(this.$store.state.userInfo.num,response => {
			
                this.searchdata = response;
                this.ok = true;
                this.show = false;
			});
           }else{
               this.searchdata = null;
               this.ok = false;
           }
       },
       locationSelect:function(hook){
               
			
				if(hook=='서울특별시'){
					this.location = this.locations[0];
					
				}else if(hook=='부산광역시'){
					this.location = this.locations[1];
			
				}else if(hook=='대구광역시'){
					this.location = this.locations[2];
				
				}else if(hook=='인천광역시'){
					this.location = this.locations[3];
				
				}else if(hook=='광주광역시'){
					this.location = this.locations[4];
				
				}else if(hook=='대전광역시'){
					this.location = this.locations[5];
				
				}else if(hook=='울산광역시'){
					this.location = this.locations[6];
				
				}else if(hook=='강원도'){
					this.location = this.locations[7];
				
				}else if(hook=='경기도'){
					this.location = this.locations[8];
				
				}else if(hook=='경상남도'){
					this.location = this.locations[9];
				
				}else if(hook=='경상북도'){
					this.location = this.locations[10];
				
				}else if(hook=='전라남도'){
					this.location = this.locations[11];
				
				}else if(hook=='전라북도'){
					this.location = this.locations[12];
			
				}else if(hook=='제주특별자치도'){
					this.location = this.locations[13];
			
				}else if(hook=='충청남도'){
					this.location = this.locations[14];
			
				}else if(hook=='충청북도'){
					this.location = this.locations[15];
			
				}else if(hook=='세종특별자치시'){
					this.location = ['통합'];
			
                }
			
				this.locationSelect2 = "시/군/구를 선택하세요."

			},
                            
   },
   methods: {  
              
              onLoad(map) {
                  this.map = map;
                  var imageSrc = '/static/marker.png'; // 마커이미지의 주소입니다    
                  var imageSize = new kakao.maps.Size(50, 50); // 마커이미지의 크기입니다
                  var imageOption = {offset: new kakao.maps.Point(27, 69)};
                  var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
                  this.marker = new kakao.maps.Marker({
                     position: map.getCenter(), 
                     map: map,
                     image : markerImage
                  });
                  this.markers.push(this.marker);
              
               },
               detail(address,floor,ho){
  
                   let route = this.$router.resolve({name : 'Detail', query:{
                            address : address,
                            floor : floor,
                            ho : ho
                    }});
                    window.open(route.href, '_blank');
      
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
                    this.locationSelect='시/도를 선택하세요.'
				    this.locationSelect2='시/군/구를 선택하세요.'
                    
            },
            search(){
                if(this.locationSelect=='시/도를 선택하세요.' || this.locationSelect2=='시/군/구를 선택하세요.'){
                    alert("주소를 모두 선택해주세요!")
                }else{

                    if(this.locationSelect2=="통합"){
                        this.locationSelect2 = '';
                    }
                     //마커 초기화
                     for ( var i = 0; i < this.markers.length; i++ ) {
                         this.markers[i].setMap(null);
                     }   
                     this.markers=[];
    
                    //센터 좌표 구하고 마커표시
                    var geocoder = new kakao.maps.services.Geocoder()
                    geocoder.addressSearch(this.locationSelect+" "+this.locationSelect2, (result, status)=> {
    
                      // 위도 경도 불러오기
                      if (status === kakao.maps.services.Status.OK) {
                      
                         var moveLatLon = new kakao.maps.LatLng(result[0].y, result[0].x);
            
                            this.map.setCenter(moveLatLon)
                            var imageSrc = '/static/marker.png'; // 마커이미지의 주소입니다    
                            var imageSize = new kakao.maps.Size(50, 50); // 마커이미지의 크기입니다
                            var imageOption = {offset: new kakao.maps.Point(27, 69)};
                            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
                            this.marker = new kakao.maps.Marker({
                                position: moveLatLon, 
                                map: this.map,
                                image : markerImage
                            });
                            this.markers.push(this.marker);
                         }
        
                    });

                    //표시할 지도데이터 얻어오기(클러스터화)
                    getrecord(this.locationSelect,this.locationSelect2,response => {
             
                        this.recode = response;

                        var clusterer = new kakao.maps.MarkerClusterer({
                        map: this.map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
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
                    
                            kakao.maps.event.addListener(marker, 'click', (markers)=> {
                                this.$bvModal.show('modal-5');	
                                this.send = [];
                                this.data =null;
                                
                                this.send[0] = {latitude:marker.getPosition().Ha.toFixed(10),
                                longitude:marker.getPosition().Ga.toFixed(10),
                                sd:this.locationSelect,
                                sgg:this.locationSelect2};
                  
                                //이력 미리보기
                                getdetailrecord(this.send, response => {
                                 
                                    this.data = response;
                           
                           
                                })
                             });

                        }
                         //클러스터 클릭이벤트
                        kakao.maps.event.addListener(clusterer, 'clusterclick', (cluster)=> {
                            this.$bvModal.show('modal-5');	
                      
                            var markers = cluster.getMarkers();
                            //("새로운마커 : "+cluster.getMarkers().length)
                            this.send = [];
                            this.data =null;
                            for(var idx=0; idx<markers.length; idx++){
                                this.send[idx] = {latitude:markers[idx].getPosition().Ha.toFixed(10),
                                longitude:markers[idx].getPosition().Ga.toFixed(10),
                                sd:this.locationSelect,
                                sgg:this.locationSelect2};
                            }
                  

                            this.dataImage = null;
                            getdetailrecord(this.send, response => {
                            
                                this.data = response;
                
                            })
        
                        });
        
                    });
                }
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