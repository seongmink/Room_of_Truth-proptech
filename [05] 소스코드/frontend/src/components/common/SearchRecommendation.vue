<template>
	<div class="block-space">
		<div class="container" style="margin-bottom:80px;">
			<div class="block-head text-center mb-5 context">
				<p  class="head-line display-3;" style="margin-top:-100px; font-size:25px;">
					<span style="font-weight:bold">{{this.$store.state.userInfo.nickname}}</span>님, 이런 방은 어떠신가요?
				</p>
				<p class="lead mt-2 context" v-html="title" style="margin-bottom:-50px;font-size:25px;"></p>
			</div>
		</div>
		<!-- Categories Carousel -->
		<div class="fullwidth-carousel-container" >
			<div class="fullwidth-slick-carousel category-carousel">
				<!-- Item -->
				
				<slick :options="slickOptions" class="fullwidth-slick-carousel category-carousel" v-if="data" style="height:190px;">
					<template v-for="(item, index) in data" >
						<div class="fw-carousel-item slide" :key="index" @click="showModal(item.latitude, item.longitude, item.name)">
							<div class="category-box-container text-center">
								<div  class="category-box">
									<div class="category-box-content">
										<div class="icon-title">
											<b-icon icon="house-door-fill" style="color: #ffffff; margin-top:-30px;" font-scale="3" ></b-icon>
										</div>
										<p style="color:white;font-weight:bold">{{item.name}}</p>
										
									</div>
									<div class="category-box-background" :style="{'background-image': 'url(' +  url+item.image+ ')'}">
									</div>
									<span v-if="item.isLike==true" class="like-banner d-block h4 mb-2" style="color: #dc3545; z-index:1000"><b-icon icon="heart-fill"></b-icon></span>
								</div>
							</div>
						</div>
					</template>
				</slick>
			</div>
		</div>
		<!-- 모달 -->
		<b-modal id="modal-4" title="상세보기" scrollable  size="lg" hide-footer>
            
            <div class="row" style="margin-top:20px" >
                <div class="col-lg-6 col-md-12 grid-layout-list mb-4" v-for="(list,index) in list" :key="index">
			        <div class="list-cap" style="height:310px" @click="detail(list.address,list.floor,list.ho)">
				        <div class="list-cap-list mb-4" >        
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
    import Slick from "vue-slick";
	import { getUrl } from "../../api/index.js";
	import { getdetailrecord} from "../../api/item.js";
    export default {
		data() {
        	return {
				list:null,
           		 send:[],
				like:'',
				title:'',
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
	
			toggle(){
				if(this.like==1){
					this.like = 0;
				}else{
					this.like = 1;
				}
			},
			showModal(latitude, longitude, address){
			this.list = null;
			var arr = address.split(" ");
            this.send[0] = {latitude:latitude,
							longitude:longitude,
							sd:arr[0],
                        	sgg:arr[1]
                            }
            
            getdetailrecord(this.send, response => {
                      
                           this.list = response;
                                          
            })
			this.$bvModal.show('modal-4');	
			
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