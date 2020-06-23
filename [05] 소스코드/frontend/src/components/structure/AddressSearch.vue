<template>
    <div>
    <div class="main-wrapper">
        
        <div id="content"  style="margin-top:100px;">
            <div class="container">
                <div class="profile-page">
                    <div class="card card-profile shadow" style=" margin-bottom:30px;">
                        
                        <div class="px-4" style="margin-top:20px;">
                            <h3 style="text-align:center; margin-top:30px;">검색한 결과</h3>
                            <h4 style="text-align:center; margin-bottom:30px;" v-if="address!=''">( {{address}} )</h4>
                        </div>
                        <div class="row p-4" >
                                <div class="col-md-6 mb-4" v-for="(lists, index) in list" :key="index">
                                    <div class="listing-item-container " @click="showModal(lists.latitude, lists.longitude, lists.name)">
                                        <div class="listing-item">
                                        <div style="text-align:right;">
                                            <img :src="url+lists.image" alt="" style="width:100%; height:250px; ">
                                             
                                        </div>
                                        
                                        <div class="listing-item-content" style="height:100px;">
                                                
                                                <div class="listing-item-inner" style="margin-top:-27px;">
                                                <h6>{{lists.name}}</h6>
                                                  
                                        </div>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                        <h3 v-if="list!=null&&list.length==0" style="text-align:center; margin-top:40px;">검색한 결과가 없습니다.</h3>
                        <div v-if="check==false">
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
    <b-modal id="modal-7" title="상세보기" scrollable  size="lg" hide-footer>
            
            <div class="row" style="margin-top:20px" >
                <div class="col-lg-6 col-md-12 grid-layout-list mb-4" v-for="(list,index) in detail" :key="index">
			        <div class="list-cap" style="height:310px">
				        <div class="list-cap-list mb-4" @click="details(list.address,list.floor,list.ho)">        
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
import {mapState} from 'vuex';
import { getAddressSearch } from "../../api/item.js";
import { plusAddressSearch } from "../../api/item.js";
import { getUrl } from "../../api/index.js";
import TitleBar from '../common/TitleBar';
import { getdetailrecord} from "../../api/item.js";
export default {
  created(){
      if(this.$route.query.search!=null){
 
     
          this.address=this.$route.query.search;
          getAddressSearch(this.address, responses=>{
            
                this.page = responses.next;
                this.list = responses.results;
            })
      }
        

      
  },
  components:{
      TitleBar
  },
   data() {
      return {
          check:false,
          list:null,
          address:'',
          page : '',
          count : 0,
          send : [],
          detail:null,
          url : getUrl(),
      }
            
      
   },
   mounted() {


     
   },
   watch:{
      
                            
   },
   methods: {  

              add(){
                  if(this.page==null){
                     
                      this.check = true;
             
                  }else{
                    plusAddressSearch(this.page, this.address, responses=>{
                   
                    this.page = responses.next;
                  

                        for(var i=0; i<responses.results.length; i++){
                           
                            this.list.push(responses.results[i]);
                        }
                    })
                }
                
            },
            showModal(latitude, longitude, address){
;
			var arr = address.split(" ");
            this.send[0] = {latitude:latitude,
                            longitude:longitude,
                            sd:arr[0],
                        	sgg:arr[1]}
		
			
            getdetailrecord(this.send, response => {
                       
                           this.detail = response;
                                
            })
            this.$bvModal.show('modal-7');	
         },
         details(address,floor,ho){
              
                   let route = this.$router.resolve({name : 'Detail', query:{
                            address : address,
                            floor : floor,
                            ho : ho
                    }});
                    window.open(route.href, '_blank');
      
         },
              
              
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