<template>
   <div style="text-align:center;">
      <div class="row" style="width:920px; display: inline-block;">
        <div class="col-lg-12">
            <div id="add-listing" >
                <div class="add-listing-section mb-4" style="margin-top:40px;">
                    <div style="margin-bottom:40px; display: inline-block; width:90%; margin-top:20px;">
                        <div style="float:left">
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
                        </div>
                        <a href="#" @click="search1()" v-b-modal.modal-4 class="btn btn-danger" style="background-color:#00c03f; float:left; border-color:#00c03f; margin-top:2px">주소검색</a>
                        <a href="#" @click="reset()" class="btn btn-danger" style="background-color:#00c03f; border-color:#00c03f; margin-left:40px; margin-top:2px">초기화</a>
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
    </div>
</template>

<script>
import MyList from '../../data/listing.json';
import { getrecord } from "../../api/item.js";
import { getdetailrecord } from "../../api/item.js";
import { getImage } from "../../api/item.js";
import { getblockDetail } from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import { getLoginData } from "../../api/item.js";
import { getKeyword } from "../../api/item.js";
import SearchRecommendation from "../common/SearchRecommendation";
import { searchAround } from "../../api/item.js";

export default {
  created(){
      this.url = getUrl();
      if(this.$route.query.search!=null){
          this.search = this.$route.query.search;
        searchAround(this.search,response => {
				console.log(response)
			});
      }

      //로그인한 사용자만 광고데이터 받기
      //if(this.$store.state.userInfo==null){
			getLoginData(0,response => {
				console.log("로그인을 한 상태의 서치추천데이터")
				console.log(response)
				this.searchdata = response;
			});
        //}

      
  },
  components:{
     SearchRecommendation
  },
   data() {
      return {
        search:"",
        searchdata:'',
        state : 'null',
        data:null,
        dataImage:null,
        listopen: false,
            keywordopen: false,
        url:'',
        getaddress:'',
        viewaddress:'',
        address:'',
        recode:null,
        errer:false,
        send:[],
        check:false,
        list:[],
      }
            
      
   },
   mounted() {


     
   },
   watch:{
       userInfo:function(hook){
   
       },
                            
   },
   methods: {  
       search1(){
           alert(this.keyword);
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
        openlist() {
            if (this.keyword == '') {
                this.listopen = true;
                if (this.$store.state.userInfo != null) {
                    getAddress(this.$store.state.userInfo.num, response => {
                        console.log(response.data)
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

               detail(address,dong,ho){
                   (address+" "+dong+" "+ho)
                   let route = this.$router.resolve({name : 'detailbuilding', query:{
                            address : address,
                            dong : dong,
                            ho : ho
                    }});
                    window.open(route.href, '_blank');
      
               },
            search(){
                if(this.locationSelect=='시/도를 선택하세요.' || this.locationSelect2=='시/군/구를 선택하세요.'){
                    alert("주소를 모두 선택해주세요!")
                }else{
                }
            }
              
         },
         computed:{
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