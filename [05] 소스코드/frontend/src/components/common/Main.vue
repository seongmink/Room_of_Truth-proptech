<template>
    <div>
        <div class="context" style="margin:auto; margin-top:0px; ">
       
            <p style="text-align:center; font-size:60px; margin-bottom:20px; margin-top:200px;"> <span style="color:#00c03f;">진실의 방</span>으로</p>
            <!-- <p style="text-align:center; font-size:60px">궁금하세요?</p> -->
             
            <br>
            <p style="text-align:center; font-size:25px" >하이퍼레저 패브릭을 이용한 부동산 이력 조회 및 추천서비스</p>
            <p style="text-align:center; font-size:20px; color:#00c03f;  font-weight: bold "> “진실의 방은 신뢰성 있는 내역을 제공합니다”</p>
     
        </div>
       
        <br>
        <div class="styled__Box-sc-10v4ocj-4 kwmwXI" style="margin-top:20px; width:75%;">
            <svg width="18" height="17" viewBox="0 0 18 17">
                <g fill="none" fill-rule="evenodd" stroke="#006CFF" transform="translate(1 1)">
                    <circle cx="6.5" cy="6.5" r="6.5"></circle>
                    <path d="M11 11l5 5"></path>
                </g>
            </svg>
       
            <input
                type="text"
                v-on:input="typing"
                v-model="search"
                @click="openlist()"
                @blur="closelist()"
                name="keyword"
                list="input-list"
                autocomplete="off"
                class="styled__SearchInput-sc-10v4ocj-5 kZIxja"
                style="width:75%;">

                <b-list-group v-if="listopen" style="margin-top:5px;">

                    <b-list-group-item v-if="this.$store.state.userInfo!=null" button="button" disabled="disabled">최근 검색 기록</b-list-group-item>
                    <b-list-group-item class="click" v-for="(item,index) in selected" :key="index" @click="select(index)" button="button">

                        <span style="float:left">{{item.keyword}}</span>
                        <p style="text-align:right; margin-bottom:-10px">
                            <a @click="deletes(item.searchId)">X</a>
                        </p>

                    </b-list-group-item>

                </b-list-group>

                <b-list-group v-if="keywordopen" style="margin-top:5px;">
                    <b-list-group-item v-if="keyword==null || keyword.length==0" button="button" disabled="disabled">시도명 + 시군구명을 입력해주세요!</b-list-group-item>
                    <b-list-group-item class="click" v-for="(item,index) in keyword" :key="index" v-text="item.roadAddress" @click="select2(index)" button="button"></b-list-group-item>
                </b-list-group>
                <button class="styled__SearchBtn-sc-10v4ocj-6 kvrxoz" @click="searches(search)">방 찾기</button>
            </div>
            <MainRecommendation :data="rdata" style="margin-bottom:-50px;"></MainRecommendation>
            <MostVisitedPlaces  :data="data"></MostVisitedPlaces>
            <hr/>
            <div class="context" style="margin:auto; ">
            <p style="text-align:center; font-size:40px; margin-bottom:20px;">About <span style="color:#00c03f;">진실의 방</span></p>
            </div>
            <img src="/static/chain.png" style="width:100%; margin-bottom:120px;">
        <!-- <div class="block-space" style="margin-top:-100px">
                <div class="container">
                    <div class="row justify-content-center text-center mb-5">
                        <div class="col-lg-8">
                            <h2 class="display-3">{{teamSectionTitle}}</h2>
                            <p class="lead text-muted">{{teamSectionDesc}}</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class=" mb-5 mb-lg-0" v-for="member in teamMembers" :key="member.name">
                            <div class="px-4">
                                <img :src="member.image" class="rounded-circle img-center img-fluid shadow shadow-lg--hover" style="width: 150px; height:190px;">
                                <div class="pt-4 text-center">
                                    <h5 class="title">
                                        <span class="d-block mb-1">{{member.name}}</span>
                                        <small class="h6 text-muted">{{member.position}}</small>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
         </div> -->
    </div>
</template>
<script>
import MostVisitedPlaces from "./MostVisitedPlaces";
import MainRecommendation from "./MainRecommendation";
import MyData from '../../data/dashboardone.json';
import MyData2 from '../../data/dashboardtwo.json';
import { getKeyword } from "../../api/item.js";
import { searchAddress } from "../../api/item.js";
import { getAddress } from "../../api/item.js";
import { deleteKeyword } from "../../api/item.js";
import { getRankInfo } from "../../api/user.js";
import VueDaumMap from 'vue-daum-map' 
import { getData } from "../../api/item.js";
import { getUserData } from "../../api/item.js";
import {mapState} from 'vuex';
export default {
    props: ['layout'],
    data() {
        return {
            rankdata : MyData2.data,
            rdata: null,
            data: null,
            listopen: false,
            keywordopen: false,
            search: '',
            selected: null,
            teamSectionTitle: 'The YangVengers Team',
            teamSectionDesc: '삼성 청년 SW 아카데미(SSAFY) 2기 대전',
            teamMembers: [
                {
                    name: '박창현',
                    position: 'Serve Leader & Block',
                    image: '/static/men_5.jpg'
                }, {
                    name: '장우영',
                    position: 'Front & UI/UX ',
                    image: '/static/men_2.jpg'
                }, {
                    name: '현진원',
                    position: 'Team Leader & Block',
                    image: '/static/man_1.jpg'
                }, {
                    name: '이정건',
                    position: 'Back End & Block',
                    image: '/static/men_4.jpg'
                }, {
                    name: '김성민',
                    position: 'Back End & Design',
                    image: '/static/men_3.jpg'
                }
            ],
            keyword: null,
            rdata:null,
        
        }
    },
    created() {
 
        //메인에서 랭킹데이터불러오기
        getRankInfo(response => {
         
            this.data = response.data;
        })

        //메인에 추천할 데이터 불러오기
		if(this.$store.state.userInfo==null){
			getData(response => {
			
				this.rdata = response
					
			});
        }else{

            //로그인할때 추천데이터 불러오기(num값 넘기기)
            getUserData(this.$store.state.userInfo.num, response => {
     
                    this.rdata=[]
                 

                    for(var i=0; i<3; i++){
                        
                        this.rdata.push(response.ages[i])
                    }
                 
                    for(var i=0; i<3; i++){
                    
                        this.rdata.push(response.genders[i])
                    }
                    for(var i=0; i<3; i++){
                        
                        this.rdata.push(response.categorys[i])
                    }
                   
               
                        
            });
        }

        
    },
    components: {
        MostVisitedPlaces,
        VueDaumMap,
        MainRecommendation,
    },
    watch: {
      userInfo:function(hook){
          if(hook==null){
              getData(response => {
			
              
				this.rdata = response
					
			});

          }else{
              
              //로그인할때 추천데이터 불러오기(num값 넘기기)
            getUserData(this.$store.state.userInfo.num, response => {
               
                    this.rdata=[]
                 

                    for(var i=0; i<3; i++){
                        
                        this.rdata.push(response.ages[i])
                    }
                   
                    for(var i=0; i<3; i++){
                    
                        this.rdata.push(response.genders[i])
                    }
                    for(var i=0; i<3; i++){
                        
                        this.rdata.push(response.categorys[i])
                    }
                   
                        
            });
          }
       },
    },
    mounted() {},
    methods: {
        openlist() {
            if (this.search == '') {
                this.listopen = true;
                if (this.$store.state.userInfo != null) {
                    getAddress(this.$store.state.userInfo.num, response => {
                    
                        this.selected = response.data;
                    })
                } else {
                    this.selected = null;
                }

            } else {
                this.keywordopen = true;
            }
          
           
        },
        closelist() {
            setTimeout(() => {
                this.listopen = false;
                this.keywordopen = false;
            }, 200);
        },
        select(index) {
         
            this.search = this
                .selected[index]
                .keyword;
            this.listopen = false;
             
        },
        select2(index) {
            
            this.search = this
                .keyword[index]
                .roadAddress;
            this.keywordopen = false;
        },
        typing(e) {

            this.search = e.target.value

            if (e.target.value != '') {

                getKeyword(e.target.value, response => {
              
                    this.keyword = response.data;
                    this.listopen = false;  
                    this.keywordopen = true;

                })
            } else {
                this.listopen = true;
                this.keywordopen = false;
            }
        },
        searches(keyword) {
             if(this.search==''){
                alert("주소를 입력해주세요!")
            }else{ 
            
            var n = null;
            if (this.$store.state.userInfo == null) {
              
                n = 0
            } else {
              
                n = this.$store.state.userInfo.num;

            }
            searchAddress(keyword, n, response => {
       
       
            })
            //최근검색 추가 끝

            this.$router.push({name : 'AddressSearch', query:{
				search: this.search
			}});
            }
          
        },
        deletes(id) {
           
            deleteKeyword(id, response => {
           
                if (response.data == "success") {
                    this.search = '';
                }
            })
        }
    },
     computed: {
	  ...mapState(['userInfo']),
   },
};
</script>

<style>
@import url(https://fonts.googleapis.com/earlyaccess/jejugothic.css);
.context{
	
font-family: 'Jeju Gothic', serif;

}
.kwmwXI {
	
    width: 800px;
    height: 60px;
    position: relative;
    box-shadow: rgba(42, 144, 255, 0.15) 0px 0px 6px 0px;
    background-color: rgb(255, 255, 255);
    margin: 0px auto;
    border-width: 1px;
    border-style: solid;
    border-color: #00c03f;
    border-image: initial;
}


.kvrxoz {
    width: 95px;
    height: 48px;
    color: rgb(255, 255, 255);
    font-size: 16px;
    line-height: 48px;
    letter-spacing: -0.3px;
    background-color:#00c03f;
    position: absolute;
    top: 50%;
    right: 6px;
    transform: translateY(-50%);
    border-width: 0px;
    border-style: initial;
    border-color: initial;
    border-image: initial;
    border-radius: 2px;
    outline: none;
}
.kwmwXI > svg {
    position: absolute;
    top: 50%;
    left: 20px;
    transform: translateY(-50%);
}
.kZIxja {
    display: inline-block;
    width: 636px;
    height: 30px;
    color: rgb(34, 34, 34);
    font-size: 20px;
    border-top-style: initial;
    border-right-style: initial;
    border-bottom-style: initial;
    border-top-color: initial;
    border-right-color: initial;
    border-bottom-color: initial;
    margin: 14px 110px 14px 54px;
    padding: 0px 20px;
    border-width: 0px 0px 0px 1px;
    border-image: initial;
    border-left: 1px solid rgb(221, 221, 221);
    outline: none;
}
.click{
    z-index:10;
}
</style>