<template>
<div>
    <div>
        <title-bar title="건물정보" subtitle="건물에 관한정보를 보여줍니다."></title-bar>
    </div>
    <div style="text-align:center;">
       <div id="add-listing" style="margin-top:250px; width:870px;  display: inline-block;">
            <div class="add-listing-section mb-4" style="margin-top:40px">
               <div  style="margin-bottom:-20px; margin-top:20px;">
                  <h3 v-if="blist!=null" class="mb-3">{{blist[0].address}} ( {{blist[0].floor}}층 {{blist[0].ho}}호 )</h3>
               </div>
               <div>
                   <h3><span style="background-color:#B7F0B1; color:#00c03f;" class="badge badge-pill badge-danger text-uppercase">유효</span> </h3>
               </div>
                <div>
                    <!-- <b-form-rating id="rating-lg-no-border"  readonly style="width:200px; margin-left:615px;"  no-border size="lg"
                        v-model="like"
                        icon-empty="heart"
                        icon-half="heart-half"
                        icon-full="heart-fill"
                        icon-clear="slash-circle"
                        show-clear
                        variant="danger">
                    </b-form-rating> -->
                    <h5 v-if="like!=0" style="text-align:right">평균 관심지수 : {{like}}점대</h5>
                    <h5 v-if="like==0" style="text-align:right">평가되지 않은 건물입니다.</h5>
                </div>
               <a href="#" @click="modalon()" style=" background-color:#CC3D3D; border-color:#CC3D3D;  margin-left:725px; margin-top:-10px;" class="btn btn-sm btn-info ">평가하기</a>
                <p style="margin-left:690px; margin-top:30px;">( 단위 : 만원 )</p>
                <div style="">
                    <b-tabs content-class="mt-3" fill>
                        <b-tab title="월세" active @click="analysis('ws')">
                            <canvas id="planet-chart1"></canvas>
                        </b-tab>
                        <b-tab title="전세" @click="analysis('js')">
                            <canvas id="planet-chart2"></canvas>
                        </b-tab>
                        <b-tab title="매매" @click="analysis('mm')">
                            <canvas id="planet-chart3"></canvas>
                        </b-tab>
                    </b-tabs>
                </div>
                <hr/>
                <div style="margin-top:50px;">
                <b-button-group>
                    <b-button style="width:128px" @click="changeKeyword(3)">의료시설</b-button>
                    <b-button style="width:128px" @click="changeKeyword(1)">마트/편의점</b-button>
                    <b-button style="width:128px" @click="changeKeyword(0)">교통</b-button>
                    <b-button style="width:128px" @click="changeKeyword(2)">교육시설</b-button>
                    <b-button style="width:128px" @click="changeKeyword(4)">음식점/카페</b-button>
                    <b-button style="width:128px" @click="changeKeyword(5)">문화시설</b-button>
                </b-button-group>
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

               <div class="mb-4">
                    <div v-for="(datadetail, index) in list" :key="index" style="margin-top:60px;">
                        <div v-if="datadetail.category==null" style="border:1px solid #EAEAEA">
                        <div style="margin-top:30px;">
                            <h4 class="mb-3">{{datadetail.contractDate}}</h4>
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
                        <div v-else style="border:1px solid #EAEAEA">
                            <div style="margin-top:30px;">
                            <h4 class="mb-3">{{datadetail.contractDate}} ~ {{datadetail.endDate}}</h4>
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
                    </div>
                </div>
            </div>  
            <infinite-loading @infinite="infiniteHandler" spinner="circles" style="margin-bottom:20px">
                <div slot="no-more">데이터가 존재하지 않습니다:)</div>
            </infinite-loading>
       </div>
    </div>
    <b-modal id="modal-7"  title="평가하기" v-model="modalshow" style="height:100px;">
	  <div align="center" >
	    <b-form-rating id="rating-lg-no-border" style="width:400px;"  no-border size="lg"
                        v-model="mylike"
                        icon-empty="heart"
                        icon-half="heart-half"
                        icon-full="heart-fill"
                        icon-clear="slash-circle"
                        show-clear
                        variant="danger">
        </b-form-rating>
	  </div>
	  <template v-slot:modal-footer="{ ok, cancel}">
     
      <b-button size="lg" variant="success" @click="evaluation()">
        평가완료
      </b-button>
      <b-button size="lg" variant="danger" @click="cancel()">
        취소하기
      </b-button>

    </template>
    </b-modal>

</div>
</template>

<script>

import VueDaumMap from 'vue-daum-map'  
import MyList from '../../data/listing2.json';
import InfiniteLoading from 'vue-infinite-loading';
import { getblockDetail } from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import { analysis } from "../../api/item.js";
import { getEvaluation } from "../../api/item.js";

import Chart from 'chart.js'
import TitleBar from '../common/TitleBar';
import axios from "axios";
export default {
  created(){
        this.url = getUrl();
        getblockDetail(this.$route.query.address, this.$route.query.floor, this.$route.query.ho, response => {    
         
           
            this.blist = response;
            this.center = {lat:this.blist[0].latitude, lng:this.blist[0].longitude}        
            
    
            var moveLatLon = new kakao.maps.LatLng(this.blist[0].latitude, this.blist[0].longitude);
  
            var imageSrc = '/static/marker.png'; // 마커이미지의 주소입니다    
            var imageSize = new kakao.maps.Size(50, 50); // 마커이미지의 크기입니다
            var imageOption = {offset: new kakao.maps.Point(27, 69)};
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
            this.marker = new kakao.maps.Marker({
                 position: moveLatLon, 
                 map: this.map,
                image : markerImage
            });
          
            this.like = response[0].isLike;
      
            this.analysis('ws');
               
        })

        //test
        //this.blist = this.data;

        
        
  },
  components:{
      VueDaumMap,
      InfiniteLoading,
      TitleBar

  },
   data() {
      return {
        mylike:0,
        modalshow: false,
        keyword : [ ["SW8"], ["MT1","CS2"],["PS3","SC4","AC5"],["HP8","PM9"],["FD6","CE7"],["CT1","PO3"] ],
        like : 0,
        state : 'null',
        appKey: '4ad8ff4da9eb8b9507c5afaee2b238b4', // 테스트용 appkey
        center: {lat:0, lng:0}, // 지도의 중심 좌표
        level: 3, // 지도의 레벨(확대, 축소 정도),
        mapTypeId: VueDaumMap.MapTypeId.NORMAL, // 맵 타입
        libraries:['services', 'clusterer', 'drawing'], // 추가로 불러올 라이브러리
        map: null, // 지도 객체. 지도가 로드되면 할당됨.
        markers:[],
        list:[],
        blist:null,
        url:'',
        data : [
            {
                address : '군산시 서수면 마룡리 993-22',
			    image : 'static/b1.jpg',
			    floor : '1',
			    ho : '201',
			    exclusive : '103',
			    detail : '전세',
			    cost : '3000',
			    license : 'SSAFY-대전-001',
			    kind : '아파트',
			    monthly : '30',
			    contractedAt : '2020-05-21'

            },
            {
                address : '전주시 완산구 서신동 993-22',
			    image : 'static/b1.jpg',
			    floor : '1',
                ho : '201',
                statekind : '주택',
                cost : '300',
                firstdate: '2020-03-22',
                enddate:'2020-03-25',
			    license : 'SSAFY-대전-001',
			    detail : '화장실 유지보수 및 교체 작업 실시',
			    contractedAt : '2020-07-21'

            },
            {
                address : '전주시 완산구 서신동 993-22',
			    image : 'static/b1.jpg',
			    floor : '1',
			    ho : '201',
			    exclusive : '106',
			    detail : '월세',
			    cost : '300',
			    license : 'SSAFY-대전-001',
			    kind : '주택',
			    monthly : '30',
			    contractedAt : '2020-07-21'

            },
            {
                address : '전주시 완산구 서신동 993-22',
			    image : 'static/b1.jpg',
			    floor : '1',
			    ho : '201',
			    exclusive : '106',
			    detail : '월세',
			    cost : '300',
			    license : 'SSAFY-대전-001',
			    kind : '주택',
			    monthly : '30',
			    contractedAt : '2020-07-21'

            },
            
        ],
        planetChartData:{
                type:'line',
                data:{
                 
                    labels:[],
                    datasets:[
                       
                    ]
                },
                options:{
                    responsive:true,
                    lineTension:1,
                    spanGaps:true,
                    scales:{
                        yAxes:[{
                            tick:{
                                beginAtZero:true,
                                padding:25,
                            }
                        }]
                    },
                    animation: {
                        duration: 0
                    },
                }
        },
        chart1:null,

      };
    },
    mounted() {
       this.createChart('planet-chart1', this.planetChartData)
        this.createChart('planet-chart2', this.planetChartData)
        this.createChart('planet-chart3', this.planetChartData)
    },
    watch:{
        
              
    },
    methods: {  
                changeKeyword(keyword){

                    //마커 초기화
                    for ( var i = 0; i < this.markers.length; i++ ) {
                            this.markers[i].setMap(null);
                    }   
                    this.markers=[];
                  
                    var ps = new kakao.maps.services.Places(this.map); 

                     for (let i=0; i<this.keyword[keyword].length; i++){
                  
                        ps.categorySearch(this.keyword[keyword][i], (data, status)=>{
                    
                            if (status === kakao.maps.services.Status.OK) {
                                
                                for (let i=0; i<data.length; i++) {
                               
                                    var imageSrc = null; // 마커이미지의 주소입니다
                                    if(keyword==3){
                                       
                                        imageSrc = "static/pin-의료.png"
                                    }else if(keyword==1){
                                        imageSrc = "static/pin-마트.png"
                                    }else if(keyword==2){
                                        imageSrc = "static/pin-교육.png"
                                    }else if(keyword==4){
                                        imageSrc = "static/pin-음식점.png"
                                    }else if(keyword==5){
                                        imageSrc = "static/pin-문화.png"
                                    }else{
                                        imageSrc = "static/pin-교통.png"
                                    }
                                    
                                    var imageSize = new kakao.maps.Size(50, 50); // 마커이미지의 크기입니다
                                    var imageOption = {offset: new kakao.maps.Point(27, 69)};
                                    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);


                                    this.marker = new kakao.maps.Marker({
                                        position: new kakao.maps.LatLng(data[i].y, data[i].x),
                                        image : markerImage
                                    });

                                    kakao.maps.event.addListener(this.marker, 'click', (markers)=> {

                                        
                                        var content = '<div class="placeinfo">' +
                                        '   <a class="title" href="' + data[i].place_url + '" target="_blank" title="' + data[i].place_name + '">' + data[i].place_name + '</a><br>';
                                            content += '    <p style="font-size:13px;margin-bottom:30px;" title="' + data[i].address_name + '">' + data[i].address_name + '</p>';  

                                        var infowindow = new kakao.maps.InfoWindow({
                                            content : content,
                                            removable : true
                                        });
                                        infowindow.open(this.map, new kakao.maps.Marker({
                                            position: new kakao.maps.LatLng(data[i].y, data[i].x)
                                        }));  
                                    });

                                    this.marker.setMap(this.map);
                                    this.markers.push(this.marker);
                                }       
                            }
                    
                        }, {useMapBounds:true}); 

                    }

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
      
                   this.markers.push(marker);
           
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
                
                },
                createChart(charId, chartData){
                    const ctx = document.getElementById(charId);
                    const myChart = new Chart(ctx, {
                        type: chartData.type,
                     data: chartData.data,
                    options: chartData.options,
                    })
                },
                analysis(type){
              
                  
                    analysis(type, this.blist[0].address ,response => {       
                
                        this.planetChartData.data.labels = [];
                        this.planetChartData.data.datasets = [];
                        if(response.datatype=='월세'){
                        
                            this.planetChartData.data.labels = response.label;
                 
                            this.planetChartData.data.datasets.push({
                                label:response.dong_address+'(보증금)',
                                data:response.dong_data,
                                borderColor: "#ef562d",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            this.planetChartData.data.datasets.push({
                                label:response.dong_address+'(월세)',
                                data:response.ws_dong_data,
                                borderColor: "#f6d258",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            this.planetChartData.data.datasets.push({
                                label:response.road_address+'(보증금)',
                                data:response.addr_data,
                                borderColor: "#88b14b",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            this.planetChartData.data.datasets.push({
                                label:response.road_address+'(월세)',
                                data:response.ws_addr_data,
                                borderColor: "#0c4c8a",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            
                           this.createChart('planet-chart1', this.planetChartData)
                           
                        }else if(response.datatype=='전세'){
                             this.planetChartData.data.labels = response.label;
                 
                            this.planetChartData.data.datasets.push({
                                label:response.dong_address,
                                data:response.dong_data,
                                borderColor: "#ef562d",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            this.planetChartData.data.datasets.push({
                                label:response.road_address,
                                data:response.addr_data,
                                borderColor: "#f6d258",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            this.createChart('planet-chart2', this.planetChartData)
                        }else{
                            this.planetChartData.data.labels = response.label;
                 
                            this.planetChartData.data.datasets.push({
                                label:response.dong_address,
                                data:response.dong_data,
                                borderColor: "#ef562d",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            this.planetChartData.data.datasets.push({
                                label:response.road_address,
                                data:response.addr_data,
                                borderColor: "#f6d258",
                                pointBackgroundColor: "white",
                                borderWidth:3,
                                fill: false,
                            })
                            this.createChart('planet-chart3', this.planetChartData)
                        }
                        
                    })
                },
                evaluation(){

                    if(this.mylike==null){
                        alert("평가를 다시해주세요!")
                    }else{
                         getEvaluation(this.$store.state.userInfo.num,this.blist[0].address,this.mylike,response => {       
        
                           
                           
                            this.like = response
                            alert("평가가 완료되었습니다.")
                            this.$bvModal.hide('modal-7')
                        })
                    }
                },
                modalon(){
                    if(this.$store.state.userInfo==null){
                        alert("로그인을 해주세요!")
                    }else{
                        this.$bvModal.show('modal-7')	
                    }
                }
              
    },
    computed:{
            
    }
};
</script>
<style>
.modal-content{
    height:auto;
}
</style>