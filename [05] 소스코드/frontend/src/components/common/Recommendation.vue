<template>
<div>
	<div>
		<title-bar title="건물 추천서비스" subtitle="선호도별로 건물을 추천해줍니다."></title-bar>	
	</div>
   <div>
      <div style="margin-bottom:30px;">
         <h4>list</h4>
         <b-navbar type="light" variant="light" style="margin-top:260px; margin-bottom:40px;">
            <b-nav-form style="width:65%">
               <span style="margin-right:10px;margin-left:15px">관심지역</span>
               <select class="custom-select form-control-alternative" v-model="locationSelect" style="margin-right:10px; width:210px">
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
              
               <select class="custom-select form-control-alternative" v-model="locationSelect2" style="margin-right:10px; width:210px">
                  <option selected="선택없음">시/군/구를 선택하세요.</option>
						<option v-for="(item, index) in location" :value="item" :key="index">{{ item }}</option>
               </select>
            </b-nav-form>
            
            <b-nav-form style="width:55% ">
               <span style="margin-right:10px; margin-left:50px;">선호도</span>
               <select class="custom-select form-control-alternative" v-model="ranking_1" style="margin-right:60px; width:150px">
						<option selected="선택없음">선택없음</option>
						<option v-for="(item, index) in ranklist" :value="item" :key="index">{{ item }}</option>
               </select>
                             
               <button class="btn btn-primary" type="button" @click="submit()" style="background-color:#00c03f; border-color:#00c03f;margin">검색하기</button>
            </b-nav-form>
         </b-navbar>
         <div v-for="(data,index) in list" :key="index" >
         <b-card-group deck style="margin-top:10px;margin-bottom:10px">
            <b-card class="listing-item-container" :img-src="url+data.image" img-alt="Card image" img-top img-height="250px;" @click="showModal(data.latitude, data.longitude, data.name)">

               <b-card-text style="height:80px;">
                  <h5> {{data.name}} </h5>
               </b-card-text>
            </b-card>
            
            <b-card class="listing-item-container"  @click="showModal(data.latitude, data.longitude, data.name)">
               <chart-bar :label="data.name" :comforts="data.comforts" :culture="data.culture" 
               :eatery="data.eatery" :education="data.education" :medical="data.medical" :trans="data.trans"  :id="index"></chart-bar>	
            </b-card>
         </b-card-group>
         </div>
         <h4 align="center" v-if="list!=null && list.length==0">검색한 결과가 존재하지 않습니다:)</h4>
         <img v-if="list==null" src="static/logotexts.png" style="width:100%; margin-bottom:40px;">
      </div> 
   </div>
   <b-modal id="modal-3" title="상세보기" scrollable  size="lg" hide-footer>
            
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
import TitleBar from '../common/TitleBar';
import ChartBar from '../chart/Chart';
import Chart from 'chart.js'
import { getSearchData } from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import { getdetailrecord} from "../../api/item.js";

   export default {
      data() {
         return {
            data:null,
            send:[],
            url : getUrl(),
            label:['교통','마트/편의점','교육시설','의료시설','음식점/카페','문화시설'],
            ranking_1:'선택없음',
				ranklist:['교통','마트/편의점','교육시설','의료시설','음식점/카페','문화시설'],
				sranklist:[],
				tranklist:[],
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
            planetChartData:{
                
                type:'radar',
                data:{
                 
                    labels:['교통','마트/편의점','교육시설','의료시설','음식점/카페','문화시설'],
                    datasets:[
                        {
                            label:'',
                            data:null,
                            borderColor: "#00c03f",
                            pointBackgroundColor: "white",
                            borderWidth:3,
                            fill: false,
                           
                        },
                       
                    ]
                },
                options:{
                   responsive : false,
                    scale: {
                        angleLines: {
                        display: false
                     },
                     ticks: {
                        suggestedMin: 0,
                        suggestedMax: 100
                     }
                  }
               }
            },
            list:null,
   
		   }
      },
      created() {
         if(this.$store.state.userInfo!=null){
            this.locationSelect = this.$store.state.userInfo.interest.sd;
            this.ranking_1 = this.$store.state.userInfo.interest.first
            setTimeout(() => {
               this.locationSelect2 = this.$store.state.userInfo.interest.sgg;
               getSearchData(this.locationSelect,this.locationSelect2,this.ranking_1, response => {
			
                  this.list = [];
                  setTimeout(() => {
                     this.list = response;
                  }, 100);
			      });
            }, 10);
            
            
         }
		},
      watch: {
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
					this.location = ["통합"];
			
				}
            this.locationSelect2 = '시/군/구를 선택하세요.';
            
         },
         
			

		},
      computed: {
			
      },
      mounted(){
        //this.createChart('planet-chart', this.planetChartData)
      },
      methods: {
		// 	createChart(charId, chartData){
      //       const ctx = document.getElementById(charId);
      //       const myChart = new Chart(ctx, {
      //           type: chartData.type,
      //           data: chartData.data,
      //           options: chartData.options
      //       })
      //   }
         submit(){
            
            if(this.locationSelect=='시/도를 선택하세요.'){
               alert("시/도를 선택하세요.")
            }else if(this.locationSelect2=='시/군/구를 선택하세요.'){
               alert("시/군/구를 선택하세요.")
            }else if(this.ranking_1=='선택없음'){
               alert("선호도를 선택하세요.")
            }else{
               getSearchData(this.locationSelect,this.locationSelect2,this.ranking_1,response => {
				   
                  this.list = [];
                  setTimeout(() => {
                     this.list = response;
                  }, 100);
                  
          
					
			      });
            }
         },
         showModal(latitude, longitude, address){
            this.data = null;

			   var arr = address.split(" ");
            this.send[0] = {latitude:latitude,
							longitude:longitude,
							sd:arr[0],
                     sgg:arr[1]
                     }
   
            getdetailrecord(this.send, response => {
               
         
                           this.data = response;
                    
                           
            })
            this.$bvModal.show('modal-3');	
         },
         detail(address,floor,ho){
              
                   let route = this.$router.resolve({name : 'Detail', query:{
                            address : address,
                            floor : floor,
                            ho : ho
                    }});
                    window.open(route.href, '_blank');
      
         },
		},
      components: {
         TitleBar,
         ChartBar,
      },
    
   };
</script>