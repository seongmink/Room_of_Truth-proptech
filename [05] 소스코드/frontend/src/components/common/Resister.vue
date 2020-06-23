<template>
	<div class="main-wrapper" style="margin-top:150px">
		<div id="content">
  			<div class="container">
	 			<div class="profile-page" style="width:970px">
	 				<div class="card card-profile shadow" style="margin-bottom:30px;">
		  				<div class="px-4">
			 				<div class="row justify-content-center" style="margin-bottom:70px;">
								<div class="col-lg-3 order-lg-2">
									<div class="card-profile-image">
									
                                 		<img v-if="$store.state.userInfo!=null&&$store.state.userInfo.picture!=null" v-bind:src="this.userInfo.picture" class="rounded-circle"  alt="" >
							            <img v-if="$store.state.userInfo!=null&&$store.state.userInfo.picture==null" src="../../../public/images/kakaoimage.jpg" class="rounded-circle"  alt="" >
										
									</div>
								</div>
								<div class="col-lg-4 order-lg-3 text-lg-right align-self-lg-center">
									<div class="card-profile-actions py-4 mt-lg-0">
										<!-- <a href="#" @click="deleteAuth"  v-if="$store.state.userInfo!=null&&$store.state.userInfo.auth!=null"  style=" background-color:#CC3D3D; border-color:#CC3D3D;" class="btn btn-sm btn-info ">공인인증 해제</a> -->
									</div>
								</div>
								<div class="col-lg-4 order-lg-1">
								</div>
							</div>
							
							<div class="text-center mt-5">
								<h3 v-if="$store.state.userInfo!=null" style="margin-bottom:-10px; margin-top:-30px;">{{userInfo.nickname}}</h3>
                        		<h4><span v-if="$store.state.userInfo!=null&&$store.state.userInfo.auth!=null" style="background-color:#59DA50; color:#fff;" class="badge badge-pill badge-danger text-uppercase">공인중개사</span></h4>
                        		<h4><span v-if="$store.state.userInfo!=null&&$store.state.userInfo.auth==null" style="background-color:#1428A0; color:#fff;" class="badge badge-pill badge-danger text-uppercase">일반사용자</span></h4>
				  				<br/> 	
								
                  				<div style=" margin-bottom:-40px; width:80%; display: inline-block; margin-top:10px;">
        							<div class="card-body">
            							<div class="tab-content" id="myTabContent">
               							<div
                    						class="tab-pane fade show active"
                    						id="tabs-icons-text-1"
                    						role="tabpanel"
                    						aria-labelledby="tabs-icons-text-1-tab">
                        					<div class="row">
                            					<div class="col-md-12">
                                					<div class="form-group" style="float:left; margin-right:245px;">
														<b-form-group>
      														<b-form-radio-group
       	 														id="btn-radios-2"
        														v-model="selected"
        														:options="options"
        														buttons
        														button-variant="outline-primary"
        														size="lg"
        														name="radio-btn-outline"
      															></b-form-radio-group>
    													</b-form-group>
													</div>
													<b-input-group prepend="출생년도" style="width:40%; height:50px;">
			 											<b-form-input type="number" max='4' placeholder="예:1980" v-model="date" style="text-align:center; height:50px;"></b-form-input>
  													</b-input-group>
                            					</div>
												<div class="add-listing-headline" style="margin-left:-260px; margin-top:20px;">
													<h3>관심지역</h3>
												</div>
                                				<div class="col-md-6">
                                    				<select class="custom-select mb-4 form-control-alternative" v-model="locationSelect">
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
                                				<div class="col-md-6">
                                    				<select class="custom-select mb-4 form-control-alternative" v-model="locationSelect2">
                                        				<option selected="선택없음">시/군/구를 선택하세요.</option>
														<option v-for="(item, index) in location" :value="item" :key="index">{{ item }}</option>
                                    				</select>
                                				</div>
												<div class="add-listing-headline" style="margin-left:-270px; margin-top:20px;">
													<h3>선호도</h3>
												</div>
                                				<div class="col-md-4">
                                    				<select class="custom-select mb-4 form-control-alternative" v-model="ranking_1">
														<option selected="선택없음">1순위</option>
														<option v-for="(item, index) in ranklist" :value="item" :key="index">{{ item }}</option>
                                    				</select>
                                				</div>
                                				<div class="col-md-4">
                                    				<select class="custom-select mb-4 form-control-alternative" v-model="ranking_2" >
                                        				<option selected="선택없음">2순위</option>
														<option v-for="(item, index) in sranklist" :value="item" :key="index">{{ item }}</option>
                                    				</select>
                                				</div>
												<div class="col-md-4">
                                    				<select class="custom-select mb-4 form-control-alternative" v-model="ranking_3" >
                                        				<option selected="선택없음">3순위</option>
														<option v-for="(item, index) in tranklist" :value="item" :key="index">{{ item }}</option>
                                    				</select>
                                				</div>
							
                                				<div class="col-md-12" style="margin-top:30px">
                                					<button class="btn btn-primary" type="button" @click="submit()" style="background-color:#00c03f; border-color:#00c03f;">수정완료</button>
                            					</div>
                        					</div>
                						</div>
            						</div>
       							 </div>
                  			</div>
						</div>
						<div class="mt-5 py-5 border-top">
							<div>
								<div>
									<div class="px-4">
                            			<h3>찜한 건물 이력</h3>
                        			</div>
                        			<div class="row p-4" >
                                		<div class="col-md-6 mb-4" v-for="(lists, index) in list" :key="index">
                                    		<div class="listing-item-container ">
                                        		<div class="listing-item" style="height:300px">
													<span class="like-banner d-block h4 mb-2" style="color: #dc3545; z-index:1000"><b-icon icon="heart-fill"></b-icon></span>
                                        			<div style="text-align:right;">
                                        				<img :src="url+lists.image" alt="" style="width:100%; height:220px; max-height: initial">
                                       				 </div>
                                        			<div class="listing-item-content" style="height:100px;">
                                                		<div class="listing-item-inner" style="margin-top:-10px;">
                                                			<h6>{{lists.address}}</h6>
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
                            			<a href="javascript:" v-b-modal.modal-4 class="btn btn-danger" style="background-color:#00c03f; border-color:#00c03f; width:100%;">정보가 존재하지 않습니다.</a>
                        			</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>		
		</div>
	</div>
</div>
</template>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
import {mapState} from 'vuex';
import DaumPostcode from 'vuejs-daum-postcode'
import { mypage } from "../../api/user.js";
import { deleteAuth } from "../../api/user.js";
import { updateInfo } from "../../api/user.js";
import { likeBuilding } from "../../api/user.js";
import { getUrl } from "../../api/index.js";
export default {
	data(){
		return{
				url:getUrl(),
				userinfo:null,
				selected: 'radio1',
        		options: [
          			{ text: '남자', value: '남자' },
          			{ text: '여자', value: '여자' },
					],
				locationSelect:'시/도를 선택하세요.',
				locationSelect2:'시/군/구를 선택하세요.',
				location:null,
				locations:[
        		['강남구','강동구','강북구','강서구','관악구','광진구','구로구','금천구','노원구','도봉구','동대문구','동작구','마포구','서대문구','서초구','성동구','성북구','송파구','양천구','영등포구','용산구','은평구','종로구','중구','중랑구'],
        		['강서구','금정구','남구','동구','동래구','부산진구','북구','사상구','사하구','서구','수영구','연제구','영도구','중구','해운대구','기장군'],
        		['남구','달서구','동구','북구','서구','수성구','중구','달성군'],
        		['계양구','남구','남동구','동구','부평구','서구','연수구','중구','강화군','옹진군'],
        		['광산구','남구','동구','북구','서구'],
        		['대덕구','동구','서구','유구','중구'],
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
				ranking_1:'1순위',
				ranking_2:'2순위',
				ranking_3:'3순위',
				date:'',
				ranklist:['교통','마트/편의점','교육시설','의료시설','음식점/카페','문화시설'],
				sranklist:[],
				tranklist:[],
				list:[],
				data:null,
				check : false,
				count:0,

		}
	},
	created(){

		if(this.$store.state.userInfo!=null){
			
			this.selected = this.$store.state.userInfo.interest.gender;
			this.date = this.$store.state.userInfo.interest.birth+"";
			this.locationSelect = this.$store.state.userInfo.interest.sd;
			setTimeout(() => {
				if(this.$store.state.userInfo.interest.sgg==null){
			
				this.locationSelect2 = "통합";
			}else{
		
				this.locationSelect2 = this.$store.state.userInfo.interest.sgg;
				
				}
			}, 100)
			
					
			this.ranking_1 = this.$store.state.userInfo.interest.first;
			setTimeout(() => {
				this.ranking_2 = this.$store.state.userInfo.interest.second;
			}, 100);
			setTimeout(() => {
				this.ranking_3 = this.$store.state.userInfo.interest.third;
			}, 100)	

			//찜한 건물 불러오기
			likeBuilding(this.$store.state.userInfo.num, response => {
		
				this.data=response;
				if(this.data.length==0){
                    this.check = true;
                }else{
                    for(var i=this.count; i<this.count+2; i++){
                    if(this.data[i]!=null){
                        this.list.push(this.data[i]);
                        }
                    }
                }
			});
      	}

	},
	watch:{
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

			date:function(hook){
	
				if(hook.length>4){
					var s = hook.substr(0, 4);
					alert("4글자 이하로 입력해주세요!");
					this.date = '';
				}
			},
		
			ranking_1:function(hook){
			
				this.ranking_2='2순위'
				this.sranklist=[];
				this.ranking_3='3순위'
				this.tranklist=[];

				if(hook!='1순위'){
					for(var i=0; i<this.ranklist.length; i++){
						if(this.ranklist[i]!=this.ranking_1){
							this.sranklist.push(this.ranklist[i]);
						}
					}
				}
			
			},
			ranking_2:function(hook){
				this.ranking_3='3순위'
				this.tranklist=[];
				if(this.ranking_1=='1순위' && this.ranking_2!='2순위'){
					alert("1순위 선호도부터 설정해주세요!")
					this.ranking_2='2순위'
				}

				if(hook!='2순위'){
					for(var i=0; i<this.ranklist.length; i++){
						if(this.sranklist[i]!=this.ranking_2){
							this.tranklist.push(this.sranklist[i]);
						}
					}
				}
			},
			ranking_3:function(hook){
				
				if(this.ranking_1=='1순위' && this.ranking_3!='3순위'){
					alert("1순위 선호도부터 설정해주세요!")
					this.ranking_3='3순위'
				}else if(this.ranking_2=='2순위' && this.ranking_3!='3순위'){
					alert("2순위 선호도부터 설정해주세요!")
					this.ranking_3='3순위'
				}
			},
			userInfo:function(hook){
				if(hook!=null){
					this.selected = this.$store.state.userInfo.interest.gender;
					this.date = this.$store.state.userInfo.interest.birth+"";
					this.locationSelect = this.$store.state.userInfo.interest.sd;
					setTimeout(() => {
						if(this.$store.state.userInfo.interest.sgg==null){
							this.locationSelect2 = "통합";
						}else{
							this.locationSelect2 = this.$store.state.userInfo.interest.sgg;	
						}
					}, 100)
					
					this.ranking_1 = this.$store.state.userInfo.interest.first;
					setTimeout(() => {
						this.ranking_2 = this.$store.state.userInfo.interest.second;
					}, 100);
					setTimeout(() => {
						this.ranking_3 = this.$store.state.userInfo.interest.third;
					}, 100)	
					//찜한 건물 불러오기
					likeBuilding(this.$store.state.userInfo.num, response => {
					
					
						this.data=response;
						if(this.data.length==0){
                    		this.check = true;
                		}else{
                    		for(var i=this.count; i<this.count+2; i++){
                    		if(this.data[i]!=null){
                        		this.list.push(this.data[i]);
                        		}
                    		}
                		}
					});
				}
			}


	},
   computed: {
	  ...mapState(['userInfo']),
   },
   methods:{
		handleAddress(data){
	
           let fullAddress = data.roadAddress
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
         
          this.address = data.roadAddress
          this.$bvModal.hide('modal-3')	
		},
		submit(){

			if(this.date.length!=4){
					alert('출생년도를 모두 입력해주세요!')
				}else if(this.locationSelect=='시/도를 선택하세요.'||this.locationSelect==''){
					alert('시/도를 선택하세요!')
				}else if(this.locationSelect2=='시/군/구를 선택하세요.'||this.locationSelect2==''){
					alert('시/군/구를 선택하세요!')
				}else if(this.ranking_1=='1순위'){
					alert('선호도에서 1순위를 선택해주세요!')
				}else if(this.ranking_2=='2순위'){
					alert('선호도에서 2순위를 선택해주세요!')
				}else if(this.ranking_3=='3순위'){
					alert('선호도에서 3순위를 선택해주세요!')
				}else{
					var info = {
						userNum : this.$store.state.userInfo.num,
						gender : this.selected,
						birth : this.date,
						sd : this.locationSelect,
						sgg : this.locationSelect2,
						first : this.ranking_1,
						second : this.ranking_2,
						third : this.ranking_3
					}
				
					
					updateInfo(info,response => {
			
						alert("수정이 완료되었습니다.")
						this.$router.push('/')
					});
				
				}
		},
		deleteAuth(){
			
			deleteAuth(this.$store.state.userInfo.num);
		},
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

   },
   components: {
      DaumPostcode
	},
	
};



</script>
