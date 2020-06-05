<template>
	<div class="block-space">
		<div class="container" style="margin-bottom:80px;">
			<div class="block-head text-center mb-5 context">
				<p v-if="this.$store.state.userInfo!=null" class="head-line display-3;" style="margin-top:30px; font-size:35px;">
					우동님, 이런 방은 어떠신가요?
				</p>
				<p v-if="this.$store.state.userInfo==null" class="head-line display-3;" style="margin-top:30px; font-size:35px;">
					혹시 이런 방은 어떠신가요?
				</p>
				<p class="lead mt-2 context" v-html="title" style="margin-bottom:-30px;font-size:25px;"></p>
			</div>
		</div>
		<!-- Categories Carousel -->
		<div class="fullwidth-carousel-container" >
			<div class="fullwidth-slick-carousel category-carousel">
				<!-- Item -->
				<div v-if="this.$store.state.userInfo!=null&& data">
				<slick :options="slickOptions" @afterChange="handleAfterChange"  class="fullwidth-slick-carousel category-carousel">
					<template v-for="(item, index) in data">
						<div class="fw-carousel-item slide" :key="index" v-b-modal.modal-3>
							<div class="category-box-container text-center">
								<div  class="category-box">
									<div class="category-box-content">
										<div class="icon-title">
											<b-icon icon="house-door-fill" style="color: #ffffff;" font-scale="3"></b-icon>
										</div>
										<a><h3>{{item.name}}</h3></a>
										
									</div>
									<div class="category-box-background" :style="{'background-image': 'url(' +  url+item.image+ ')'}">
									</div>
									<span class="like-banner d-block h4 mb-2" style="color: #dc3545; z-index:1000"><b-icon icon="heart-fill"></b-icon></span>
								</div>
							</div>
						</div>
					</template>
				</slick>
				</div>
				<div v-if="this.$store.state.userInfo==null&& data">
				<slick :options="slickOptions" class="fullwidth-slick-carousel category-carousel">
					<template v-for="(item, index) in data">
						<div class="fw-carousel-item slide" :key="index" v-b-modal.modal-3>
							<div class="category-box-container text-center">
								<div  class="category-box">
									<div class="category-box-content">
										<div class="icon-title">
											<b-icon icon="house-door-fill" style="color: #ffffff;" font-scale="3"></b-icon>
										</div>
										<a><h3>{{item.name}}</h3></a>
								
									</div>
								
									<div class="category-box-background" :style="{'background-image': 'url(' +  url+item.image+ ')'}">
									</div>
									
								</div>
							</div>
						</div>
					</template>
				</slick>
				</div>
			</div>
		</div>
		<!-- 모달 -->
		<b-modal id="modal-3" title="상세보기" scrollable  size="lg" hide-footer >   
			<div>
                <b-form-rating id="rating-lg-no-border" style="width:200px; margin-left:555px;"  no-border size="lg"
                    v-model="like"
                    icon-empty="heart"
                    icon-half="heart-half"
                    icon-full="heart-fill"
                    icon-clear="slash-circle"
                    show-clear
                    variant="danger">
                </b-form-rating>
            </div>
            <div style="text-align:center; margin-top:-10px">
            <div id="add-listing" style=" width:690px; display: inline-block;">
            <div class="mb-4" style="margin-left:-50px; margin-right:-50px;">
            <div>
                <div>
                    <h4 class="mb-3">{{datadetail.contractedAt}}</h4>
        
                    <div style=" margin-top:-20px;">
                        <h3><span style="background-color:#B2CCFF; color:#1428A0;" class="badge badge-pill badge-danger text-uppercase">상세보기</span> </h3>
                     </div>
               </div>
                <div style="margin-top:20px; ">
                    <div style="float:left; margin-right:15px; margin-left:25px">
                        <img :src="datadetail.image"  style="width:375px;height:295px;margin-bottom:20px"/>
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

                    <div style="margin-top:-20px; margin-left:25px">
                    <b-input-group prepend="공인중개사"  class="mt-3" style="width:380px; ">
   			            <b-form-input readonly style="background-color:white;text-align:center" v-model="datadetail.license"></b-form-input>
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
    import Slick from "vue-slick";
	import { getUrl } from "../../api/index.js";
    export default {
		data() {
        	return {
				like:'',
				title:'',
				datadetail:{
					address : '군산시 서수면 마룡리 99-22',
					image : 'static/b1.jpg',
					floor : '1',
					ho : '201',
					exclusive : '103',
					detail : '전세',
					cost : '300',
					license : 'SSAFY-대전-001',
					kind : '아파트',
					monthly : '30',
					contractedAt : '2020-05-21'
				},
				like:0,
				url : getUrl(),
			
        	}
		},
		created(){

			
      		
    	},
        props: ['data'],
		watch:{
		
		},
        components: {
            Slick
		},
		methods:{
			handleAfterChange(event, slick, currentSlide) {
			
				if(currentSlide==0){
					this.title = "<span style='color:#00c03f; font-size:30px'>20대</span>에 맞춤 추천 서비스!"
				}else if(currentSlide==3){
					this.title = "<span style='color:#00c03f; font-size:30px'>남성</span>에게 맞춤 추천 서비스!"
				}else if(currentSlide==6){
					this.title = "<span style='color:#00c03f; font-size:30px'>교통</span>이 편리한 맞춤 추천 서비스!"
				}
			},
			toggle(){
				if(this.like==1){
					this.like = 0;
				}else{
					this.like = 1;
				}
			}
		},
        computed: {
            slickOptions() {
				
                return {
                    dots: true,
                    infinite: true,
                    speed: 500,
                    slidesToShow: 3,
                    slidesToScroll: 3,
                    autoplay: true,
                    arrows: false,
                    responsive: [
                        {
                            breakpoint: 1024,
                            settings: {
                                slidesToShow: 3,
                                slidesToScroll: 3,
                                infinite: true,
								dots: true,
                            }
                        }, {
                            breakpoint: 768,
                            settings: {
                                slidesToShow: 1,
                                slidesToScroll: 2
                            }
                        }, {
                            breakpoint: 480,
                            settings: {
                                slidesToShow: 1,
                                slidesToScroll: 1
                            }
                        }

                    ]
                };
				
            }
        }
    };
</script>
<style>
.modal-content{
	margin-top:90px;
}
</style>