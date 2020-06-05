<template>
	<div class="block-space ">
		<div class="container">
			<div class="block-head text-center mb-5">
				<h2 class="head-line display-3;" style="margin-top:-40px;color:#00c03f;">
					실시간 랭킹
				</h2>
				<p class="lead mt-2  context" style="margin-bottom:-30px">부동산 공인중개사</p>
			</div>
			<div class="row">
				<div class="col-md-12">
					<slick :options="slickOptions" v-if="data" class="simple-slick-carousel dots-nav">
						<template v-for="(place) in data" >
							<div class="carousel-item" :key="place.title" @click="detail(place.userNum)">
								<div class="category-box-container text-center">
									<div class="listing-item-container">
										<div class="listing-item text-center">
					
                                            <!-- <div class="mostviewed-bg" :style="{'background': 'url(' + url+place.apicture + ')','background-size': '100% 200px','background-repeat': 'no-repeat'}" >  -->
                                            <div class="mostviewed-bg" :style="{'background': 'url(' + url+place.agentPicture + ')','background-size': '100% 200px','background-repeat': 'no-repeat'}" > 
                                            
												<div class="listing-item-content">
													<div class="list-logo">
														<a> <img v-if="place.userPicture!=null" :src="place.userPicture" width="80" height="80" alt=""></a>
														<a> <img v-if="place.userPicture==null" src=../../../public/images/kakaoimage.jpg width="80" height="80" alt=""></a>
													</div>
                                                    <img v-if="place.rnk==1" src=static/1rnk.png style="width:20px; float:left; margin-top:-40px; margin-left:-20px">
                                                    <img v-if="place.rnk==2" src=static/2rnk.png style="width:20px; float:left; margin-top:-40px; margin-left:-20px">
                                                    <img v-if="place.rnk==3" src=static/3rnk.png style="width:20px; float:left; margin-top:-40px; margin-left:-20px">
													<span v-if="place.rnk>3" class="badge badge-pill badge-primary text-uppercase badge-cat category-banner">{{place.rnk}} 등</span>
													<a><h5 class="mb-0">{{place.agentName}} </h5></a>
													<p class="mb-0"> <small> {{place.nickname}}</small></p>
													<span class="badge badge-pill badge-success text-uppercase open-close-banner">{{place.point}} Point</span>
												</div>
                                            	
											</div>
										
										</div>
									</div>
								</div>
							</div>
						</template>
					</slick>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import Slick from "vue-slick";
import { getUrl } from "../../api/index.js";
export default {
    props: ['data'],
    components: {
        Slick
    },
    created(){
      this.url = getUrl();  
    },
    computed: {
        slickOptions() {
            return {
                url:'',
                dots: true,
                infinite: true,
                speed: 500,
                slidesToShow: 3,
                slidesToScroll: 1,
                autoplay: true,
                arrows: false,
                responsive: [
                    {
                        breakpoint: 1024,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 3,
                            infinite: true,
                            dots: true
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

				],
				
            };
        }
    },
    methods: {
        detail(num) {
			//(num)
			this.$router.push({name : 'UserRealestate', query:{
				num: num
			}});
        }
    }
};
</script>
