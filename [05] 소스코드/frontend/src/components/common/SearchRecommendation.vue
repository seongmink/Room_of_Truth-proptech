<template>
	<div class="block-space">
		<div class="container" style="margin-bottom:80px;">
			<div class="block-head text-center mb-5 context">
				<p  class="head-line display-3;" style="margin-top:-100px; font-size:25px;">
					우동님, 이런 방은 어떠신가요?
				</p>
				<p class="lead mt-2 context" v-html="title" style="margin-bottom:-30px;font-size:25px;"></p>
			</div>
		</div>
		<!-- Categories Carousel -->
		<div class="fullwidth-carousel-container" >
			<div class="fullwidth-slick-carousel category-carousel">
				<!-- Item -->
				
				<slick :options="slickOptions" class="fullwidth-slick-carousel category-carousel" v-if="data" style="height:190px;">
					<template v-for="(item, index) in data">
						<div class="fw-carousel-item slide" :key="index" v-b-modal.modal-3>
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