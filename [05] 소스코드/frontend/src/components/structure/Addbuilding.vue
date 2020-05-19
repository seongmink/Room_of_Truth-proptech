<template>

   <div style="text-align:center;">
       
      <img src="../../../public/images/bd.png" style="margin-top:-170px; margin-bottom:-280px; width:900px">
    
      <div class="row" style="width:920px; display: inline-block;">
         
      <div class="col-lg-12"> 
         <div id="add-listing" >

                  <div class="nav-wrapper" style="margin-top:50px;">
							 <ul class="nav nav-pills nav-fill flex-column flex-md-row" id="tabs-icons-text" role="tablist">
								 <li class="nav-item">
									 <a class="nav-link mb-sm-3 mb-md-0 active" @click="togle()" id="tabs-icons-text-1-tab" data-toggle="tab" href="#tabs-icons-text-1" role="tab" aria-controls="tabs-icons-text-1" aria-selected="true">계약 정보등록</a>
								 </li>
								 <li class="nav-item">
									 <a class="nav-link mb-sm-3 mb-md-0" @click="togle()" id="tabs-icons-text-2-tab" data-toggle="tab" href="#tabs-icons-text-2" role="tab" aria-controls="tabs-icons-text-2" aria-selected="false">상태 정보등록</a>
								 </li>
							 </ul>
						 </div>
                         <div>
                     <vue-daum-map
                     :appKey="appKey"
                     :center.sync="center"
                     :level.sync="level"
                     :mapTypeId="mapTypeId"
                     :libraries="libraries"
                     @load="onLoad"
                     style="width:100%;height:300px;margin-bottom:20px"/>
                  </div>
						 <div class="card shadow">
							 <div class="card-body">
								 <div class="tab-content" id="myTabContent">
									 <div class="tab-pane fade show active" id="tabs-icons-text-1" role="tabpanel" aria-labelledby="tabs-icons-text-1-tab">
										  <!-- Section -->
            <div class="add-listing-section mb-4" style="margin-top:10px; margin-bottom:90px">
               <!-- Headline -->
               <div class="add-listing-headline">
                  <h3>건물정보</h3>
               </div>
             
               
               <div style="margin-top:20px; ">
                  <div style=" margin-top:-15px">
                     <div>
                     <b-input-group prepend="주소" class="mt-3">
   			            <b-form-input ref="address"  v-b-modal.modal-3 v-model="address" placeholder="주소를 입력해주세요."></b-form-input>
  		               </b-input-group>
                       <span style="margin-left:-450px; font-size:13px">*등기부등본 상의 주소를 입력해 주세요.</span>
                     </div>
                     
                     <b-input-group  v-if="state=='null'" append="동" class="mt-3" style="width:330px; margin-right:30px; float:left;   ">
   			            <b-form-input ref="address2" placeholder="예) 101" v-model="address2"></b-form-input>
  		               </b-input-group>
   			         <b-input-group  append="호" class="mt-3" style="width:333px; ">
   			            <b-form-input ref="address3" placeholder="예) 201" v-model="address3"></b-form-input>
  		               </b-input-group>
                     <div style="padding-bottom:9px">
                     <b-form-checkbox
                     style="margin-top:10px; margin-left:-460px   "
                     id="checkbox-1"
                     v-model="state"
                     name="checkbox-1"
                     value=true
                     unchecked-value="null"
                     >
                        동 정보가 없으면 선택해주세요.
                     </b-form-checkbox>
                     </div>
                     <b-input-group prepend="공급 면적" append="평" class="mt-3" style="width:160px; margin-right:20px; float:left;   ">
   			            <b-form-input ref="size1" v-model="size1"></b-form-input>
  		               </b-input-group>

                     <b-input-group append="㎡" class="mt-3" style="width:150px; margin-right:24px; float:left;  ">
   			            <b-form-input v-model="size2"></b-form-input>
  		               </b-input-group>

                       <b-input-group prepend="전용 면적" append="평" class="mt-3" style="width:160px; margin-right:30px; float:left;   ">
   			            <b-form-input ref="size3" v-model="size3"></b-form-input>
  		               </b-input-group>

                     <b-input-group append="㎡" class="mt-3" style="width:150px; ">
   			            <b-form-input v-model="size4"></b-form-input>
  		               </b-input-group>

                  </div>
           

                  <b-form-file
                     accept="image/*"
                     v-model="file"
                     :state="Boolean(file)"
                     placeholder="Choose a file or drop it here..."
                     drop-placeholder="Drop file here..."
                     style="margin-top:20px; width:100%;"
                  ></b-form-file>
       
               </div>     
            </div>


             <div class="add-listing-section mb-4" style="margin-top:40px; text-align:center;" >
               <!-- Headline -->
               <div class="add-listing-headline">
                  <h3>계약정보</h3>
               </div>

               <div style="display: inline-block;">
                  <b-input-group prepend="계약내용"  class="mt-3" style="width:287px; float:left; margin-right:80px">
                     <b-form-select ref="selecteds" v-model="selecteds" :options="options" align="center"  style="text-align:right;"></b-form-select>
                  </b-input-group>

                  <b-input-group append="만원"  class="mt-3" style="width:270px;">
   			            <b-form-input ref="cost" style="text-align:right" v-model="cost"></b-form-input>
  		            </b-input-group>
               </div>
               <div style="display: inline-block; margin-top:15px">
                    
                  <b-input-group prepend="기간"  class="mt-3" style="width:287px; float:left; margin-bottom:9px; ">
                           <b-form-input ref="date1" style="text-align:center" v-b-modal.modal-4 v-model="date1"></b-form-input>
                  </b-input-group>
                  <p v-show="remove==true" style="float:left; visibility:hidden; font-size:30px; margin-top:10px; margin-left:30px; margin-right:30px">~</p>
                  <b-input-group  class="mt-3" style="width:270px; visibility:hidden" v-show="remove==true">
                           <b-form-input v-b-modal.modal-5 v-model="date2"></b-form-input>
                  </b-input-group>
                  <p v-show="remove==false" style="float:left; font-size:30px; margin-top:10px; margin-left:30px; margin-right:30px">~</p>
                  <b-input-group  class="mt-3" style="width:270px;" v-show="remove==false">
                           <b-form-input ref="date2" style="text-align:center" v-b-modal.modal-5 v-model="date2"></b-form-input>
                  </b-input-group>
               </div>

               <div style="display: inline-block;">
                      <b-input-group prepend="임대인"  class="mt-3" style="width:180px; float:left; margin-right:30px">
   			            <b-form-input ref="name" v-model="name"></b-form-input>
  		         </b-input-group>

                       <b-input-group prepend="공인중개사 번호"  class="mt-3" style="width:430px; ">
   			            <b-form-input readonly style="text-align: center" v-model="num" ></b-form-input>
  		               </b-input-group>
                  </div>
              
            </div>
             <a href="javascript:" @click="fcheck()" style="background-color:#1428A0; border-color:#1428A0;" class="btn btn-danger">등록하기 <i class="fa fa-arrow-circle-right"></i></a>
				</div>
									 
                            
             <div class="tab-pane fade" id="tabs-icons-text-2" role="tabpanel" aria-labelledby="tabs-icons-text-2-tab">
										 <div class="add-listing-section mb-4" style="margin-top:10px; margin-bottom:90px">
               <!-- Headline -->
               <div class="add-listing-headline">
                  <h3>건물정보</h3>
               </div>
             
               
               <div style="margin-top:20px; ">
                  <div style="margin-top:-15px">
                     <div>
                     <b-input-group prepend="주소" class="mt-3">
   			            <b-form-input ref="address"  v-b-modal.modal-3 v-model="address" placeholder="주소를 입력해주세요."></b-form-input>
  		               </b-input-group>
                       <span style="margin-left:-450px; font-size:13px">*등기부등본 상의 주소를 입력해 주세요.</span>
                     </div>
                     <div>
                     <b-input-group  v-if="state=='null'" append="동" class="mt-3" style="width:190px; margin-right:20px; float:left;   ">
   			            <b-form-input ref="address2" placeholder="예) 101" v-model="address2"></b-form-input>
  		               </b-input-group>
                       <b-input-group  v-if="state!='null'" append="동" class="mt-3" style="width:190px; margin-right:20px; float:left;   ">
   			            <b-form-input ref="address2" readonly="" placeholder="" v-model="address2"></b-form-input>
  		               </b-input-group>
   			         <b-input-group  append="호" class="mt-3" style="width:190px; float:left; ">
   			            <b-form-input ref="address3" placeholder="예) 201" v-model="address3"></b-form-input>
  		               </b-input-group>
                    
                   
                    

                     <b-form-file
                     accept="image/*"
                     v-model="file"
                     :state="Boolean(file)"
                     placeholder=""
                     drop-placeholder="Drop file here..."
                     style="width:40%; margin-top:15px; margin-left:10px; text-align:center"
                  ></b-form-file>
                  </div>
                    <b-form-checkbox
                     style="margin-top:10px; margin-left:-460px   "
                     id="checkbox-1"
                     v-model="state"
                     name="checkbox-1"
                     value=true
                     unchecked-value="null"
                     >
                        동 정보가 없으면 선택해주세요.
                     </b-form-checkbox>
                  </div>

           
               </div>     
            </div>
            
									<div class="add-listing-section mb-4" style="margin-top:40px; text-align:center;" >
               <!-- Headline -->
               <div class="add-listing-headline">
                  <h3>상태정보</h3>
               </div>

               <div style="display: inline-block;">
                  <b-input-group prepend="상태내용"  class="mt-3" style="width:257px; float:left; margin-right:80px">
                     <b-form-select ref="selecteds2" v-model="selecteds2" :options="options2" align="center"  style="text-align:right;"></b-form-select>
                  </b-input-group>

                  <b-input-group append="만원"  class="mt-3" style="width:270px;">
   			            <b-form-input ref="cost" style="text-align:right" v-model="cost"></b-form-input>
  		            </b-input-group>
               </div>
               <div style="display: inline-block; margin-top:15px">
                    
                  <b-input-group prepend="기간"  class="mt-3" style="width:257px; float:left; margin-bottom:9px; ">
                           <b-form-input ref="date1" v-b-modal.modal-4 v-model="date1" style="text-align:center"></b-form-input>
                  </b-input-group>
                  <p v-show="remove==true" style="float:left; visibility:hidden; font-size:30px; margin-top:10px; margin-left:30px; margin-right:30px">~</p>
                  <b-input-group  class="mt-3" style="width:250px; visibility:hidden" v-show="remove==true">
                           <b-form-input v-b-modal.modal-5 v-model="date2"></b-form-input>
                  </b-input-group>
                  <p v-show="remove==false" style="float:left; font-size:30px; margin-top:10px; margin-left:30px; margin-right:30px">~</p>
                  <b-input-group  class="mt-3" style="width:270px;" v-show="remove==false">
                           <b-form-input ref="date2" style="text-align:center" v-b-modal.modal-5 v-model="date2"></b-form-input>
                  </b-input-group>
               </div>

               <div style="display: inline-block;">
                      <b-input-group prepend="상세정보"  class="mt-3" style="width:280px; float:left; margin-right:30px">
   			            <b-form-input ref="name" v-model="name"></b-form-input>
  		         </b-input-group>

                       <b-input-group prepend="공인중개사 번호"  class="mt-3" style="width:300px; ">
   			            <b-form-input readonly style="text-align: center" v-model="num" ></b-form-input>
  		               </b-input-group>
                  </div>
              
            </div>	
             <a href="javascript:" @click="fcheck2()" style="background-color:#1428A0; border-color:#1428A0;" class="btn btn-danger">등록하기 <i class="fa fa-arrow-circle-right"></i></a>
				</div>
                            
           
				 </div>
				</div>
				</div>
         </div>
        
      </div> 
   </div>
    <b-modal id="modal-3" title="주소검색" hide-footer>
    	<DaumPostcode style="height: 400px;"
      		:on-complete=handleAddress
    	/>
  	</b-modal>
     <b-modal id="modal-4" title="날짜검색" hide-footer >
    	<b-calendar v-model="value" @selected="selected(value)" width="450px" style="margin-top:20px"></b-calendar>
  	</b-modal>

     <b-modal id="modal-5" title="날짜검색" hide-footer>
    	<b-calendar v-model="value" @selected="selected2(value)"  width="450px" style="margin-top:20px"></b-calendar>
  	</b-modal>
      
     <b-modal id="modal-2"  title="등록하기" v-model="modalshow">
       
	  <div align="center">
         <b-overlay :show="show" rounded="sm">
	  <img src="../../../public/images/logo2.png" height="150px" width="290px" style="margin-top:-20px; margin-bottom:20px;" >

      <h4>정말로 건물이력을 등록하시겠습니까?</h4>
      <h5 style="color:red;">( 등록이 완료되면 수정하실 수 없습니다. )</h5>
    	
   </b-overlay>
	  </div>

	  <template v-slot:modal-footer="{ ok, cancel}">
     
      <b-button size="lg" variant="success" @click="submit()">
        등록완료
      </b-button>
      <b-button size="lg" variant="danger" @click="cancel()">
        취소하기
      </b-button>

    </template>
     
    </b-modal>
    

     <b-modal id="modal-6"  title="등록하기" v-model="modalshow2">
      
	  <div align="center">
          <b-overlay :show="show" rounded="sm">
	  <img src="../../../public/images/logo2.png" height="150px" width="290px" style="margin-top:-20px; margin-bottom:20px;" >

      <h4>정말로 건물이력을 등록하시겠습니까?</h4>
      <h5 style="color:red;">( 등록이 완료되면 수정하실 수 없습니다. )</h5>
    	
</b-overlay>
	  </div>

	  <template v-slot:modal-footer="{ ok, cancel}">
     
      <b-button size="lg" variant="success" @click="submit2()">
        등록완료
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
import DaumPostcode from 'vuejs-daum-postcode'
import { addBuliding } from "../../api/item.js";
import { getLicense } from "../../api/item.js";
import { addStateBuliding } from "../../api/item.js";
import {mapState} from 'vuex';
import VueGoogleAutocomplete from 'vue-google-autocomplete'


export default {
   
  components:{
     VueDaumMap,
     DaumPostcode,
     VueGoogleAutocomplete
     
  },
   data() {
      return {
         show: false,
         modalshow: false,
          modalshow2: false,
        state : 'null',
        appKey: '4ad8ff4da9eb8b9507c5afaee2b238b4', // 테스트용 appkey
        center: {lat:36.355079, lng:127.298320}, // 지도의 중심 좌표
        level: 3, // 지도의 레벨(확대, 축소 정도),
        mapTypeId: VueDaumMap.MapTypeId.NORMAL, // 맵 타입
        libraries:['services', 'clusterer', 'drawing'], // 추가로 불러올 라이브러리
        map: null, // 지도 객체. 지도가 로드되면 할당됨.,
        mapcheck:false,
         mapcheck2:false,
         show: false,
        address:"",
        value: '',
        selecteds: null,
        options: [
          { value: null, text: '선택' },
          { value: '전세', text: '전세' },
          { value: '월세', text: '월세' },
          { value: '임대', text: '임대' },
          { value: '매매', text: '매매' },
     
        ],
        selecteds2: null,
        options2: [
          { value: null, text: '선택' },
          { value: '시설', text: '시설' },
          { value: '환경', text: '환경' },
          { value: '유지', text: '유지' },

        ],
        date1: '',
        date2: '',
        size1: '',
        size2: '',
        size3: '',
        size4: '',
        remove: false,
        address2: '',
        address3: '',
        cost: '',
        name: '',
        num: '',
        file:null,
        la:'',
        lo:'',
        marker:null,


      };
   },
   computed:{
             ...mapState(['userInfo']),
         },
   created(){
      if(this.$store.state.userInfo!=null){

         getLicense(this.$store.state.userInfo.num, response => {
                (response.data)
                this.num = response.data;
                
         })
      }
    
   },
   mounted() {
     
   },
   watch:{
      size1:function(hook){
         var num = this.size1 * 3.3058;
         this.size2 = num.toFixed(2);
         
      },
      size3:function(hook){
         var num = this.size3 * 3.3058;
         this.size4 = num.toFixed(2);
      },
      selecteds:function(hook){
         if(hook=="매매"){
            this.remove = true;
            this.date2 = '';
         }else{
            this.remove = false;
         }
      },
      state:function(hook){
         (hook)
         if(hook=="true"){
            this.address2 = '';
         }
      },
      userInfo:function(hook){
         if(hook!=null){
             getLicense(this.$store.state.userInfo.num, response => {
 
            this.num = response.data;
            });
         }
      },
      
   },
   methods: {  
               selected(date){
                 
                  this.date1 = date;
                  this.$bvModal.hide('modal-4')	
               }, 
               selected2(date){
                 
                  this.date2 = date;
                  this.$bvModal.hide('modal-5')	
               }, 
               onLoad (map) {
                  this.map = map;
                  this.marker = new kakao.maps.Marker({
                     position: map.getCenter(), 
                     map: map
                  });
               },
               check(address){
                 
                  var geocoder = new kakao.maps.services.Geocoder()
                  geocoder.addressSearch(address, (result, status)=> {

                  // 정상적으로 검색이 완료됐으면
                  if (status === kakao.maps.services.Status.OK) {
                    
               
                     this.marker.setMap(null);

                     

                     var moveLatLon = new kakao.maps.LatLng(result[0].y, result[0].x);
                     this.map.setCenter(moveLatLon)
                     
                    this.marker =  new kakao.maps.Marker({
                        position: this.map.getCenter(), 
                        map:this.map
                        });     
                     }
                  
                     this.la = this.map.getCenter().Ha.toFixed(10);
                     this.lo = this.map.getCenter().Ga.toFixed(10);
                     
                  
 
                  });

                 
             },
            handleAddress(data){
               (data)
               let fullAddress = data.roadAddress;
               let extraAddress = '';
               if (data.addressType === 'R') {
               if (data.bname !== '') {
                  extraAddress += data.bname
               }
               if (data.buildingName !== '') {
         	      extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName)
               }
         	      fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '')
               }
         
               this.check(data.roadAddress);
               this.address = data.roadAddress;
               this.$bvModal.hide('modal-3')	
            },
            submit(){
                 
                  var info = {
                  "author": this.$store.state.userInfo.num,
                  "address" : this.address,
                  "dong" : this.address2,
                  "ho" : this.address3,
                  "supply" : this.size2,
                  "exclusive" : this.size4,
                  "details" : this.selecteds,
                  "cost" : this.cost,
                  "startDate" : this.date1,
                  "endDate" : this.date2,
                  "name" : this.name,
                  "agentNum" : this.num,
                  "file" :  this.file,
                  "image" :  this.file,
                  "latitude" : this.la,   
                  "longitude" : this.lo,
                  "license" : this.num
                  }
                  if(this.file!=null){
                     info.image = this.file.name;
                  }
                  var n = '';
                   addBuliding(info,responses => {
                      
                      n = responses.data
                   });
                   
                   
                
                 
                  this.show = true;
                   setTimeout(() => {
                      alert("등록이 완료되었습니다.")
                        this.$router.push({name : 'ConfirmBuilding', query:{
                           num: n,
                           type:0
			               }});
                   }, 2000);
                   
                   
                  
               },
                submit2(){
                   ("state")
                 
                  var info = {
                  "author": this.$store.state.userInfo.num,
                  "address" : this.address,
                  "dong" : this.address2,
                  "ho" : this.address3,
                  "category" : this.selecteds2,
                  "cost" : this.cost,
                  "startDate" : this.date1,
                  "endDate" : this.date2,
                  "details" : this.name,
                  "file" :  this.file,
                  "image" :  this.file,
                  "latitude" : this.la,   
                  "longitude" : this.lo,
                  "license" : this.num
                  }
                  if(this.file!=null){
                     info.image = this.file.name;
                  }
                   var n = '';
                  addStateBuliding(info,responses => {
                      
                      n = responses.data
                   });
                
            
                  this.show = true;
                   setTimeout(() => {
                      alert("등록이 완료되었습니다.")
                        this.$router.push({name : 'ConfirmBuilding', query:{
                           num: n,
                           type:1
			               }});
                   }, 2000);
               },
               togle(){
                  this.address = '';
                  this.address2 = '';
                  this.address3 = '';
                  this.file = null;
                  this.selecteds = '';
                  this.selecteds2 = '';
                  this.cost = '';
                  this.date1 = '';
                  this.date2 = '';
                  this.name = '';
                  this.size1 = '';
                  this.size2 = '';
                  this.size3 ='';
                  this.size4 = '';

                  if(this.state=="true"){

                     this.state = 'null';
                  }
         
            },
            fcheck(){
               ("제출검사시작")
               if(this.address==''){
                  alert("주소를 입력하여 주세요.")
                  this.$refs.address.focus();
               }else if(this.state!="true"&&this.address2==''){
                  alert("동을 입력하여 주세요.")
                  this.$refs.address2.focus();
               }else if(this.address3==''){
                  alert("호를 입력하여 주세요.")
                  this.$refs.address3.focus();
               }else if(this.size1==''){
                  alert("공급면적을 입력하여 주세요.")
                  this.$refs.size1.focus();
               }else if(this.size3==''){
                  alert("전용면적을 입력하여 주세요.")
                  this.$refs.size3.focus();
               }else if(this.selecteds==null || this.selecteds==''){
                  alert("계약내용을 입력하여 주세요.")
                  this.$refs.selecteds.focus();
               }else if(this.cost==''){
                  alert("비용을 입력하여 주세요.")
                  this.$refs.cost.focus();
               }else if(this.date1==''){
                  alert("시작날짜를 입력해주세요.")
                  this.$refs.date1.focus();
               }else if(this.remove==false && this.date2==''){
                  alert("마지막날짜를 입력해주세요.")
                  this.$refs.date2.focus();
               }else if(this.name==''){
                  alert("임대인을 입력해주세요.")
                  this.$refs.name.focus();
               }else{
                  this.$bvModal.show('modal-2')	
               }
            
         },
         fcheck2(){
            if(this.address==''){
                  alert("주소를 입력하여 주세요.")
                  this.$refs.address.focus();
               }else if(this.state!="true"&&this.address2==''){
                  alert("동을 입력하여 주세요.")
                  this.$refs.address2.focus();
               }else if(this.address3==''){
                  alert("호를 입력하여 주세요.")
                  this.$refs.address3.focus();
               }else if(this.selecteds2==null || this.selecteds2==''){
                  alert("상태내용을 입력하여 주세요.")
                  this.$refs.selecteds2.focus();
               }else if(this.cost==''){
                  alert("비용을 입력하여 주세요.")
                  this.$refs.cost.focus();
               }else if(this.date1==''){
                  alert("시작날짜를 입력해주세요.")
                  this.$refs.date1.focus();
               }else if(this.remove==false && this.date2==''){
                  alert("마지막날짜를 입력해주세요.")
                  this.$refs.date2.focus();
               }else if(this.name==''){
                  alert("상세정보를 입력해주세요.")
                  this.$refs.name.focus();
               }else{
                  this.$bvModal.show('modal-6')	
               }
         }

            },
             
      
       
            
};
</script>
<style>
.custom-file-input:lang(en) ~ .custom-file-label::after {
  content: '사진첨부';
}
</style>