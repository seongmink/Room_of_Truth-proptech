import {createInstance} from "./index.js";
import {createInstancepython} from "./indexPython.js";

const instance = createInstance();
const instancepython = createInstancepython();

//건물이력등록
function addBuliding(info, success) {

    let formData = new FormData();
    formData.append('image', info.file);
    formData.append('flag', 0);
    formData.append('num', -1);
    if(info.file==null){
         //이미지전송
         instance
         .post("/api/uploadNull/", formData)
         .then(Response => {
           

             info.image = Response.data
        

             instance
             .post("/api/contract/save", info)
             .then(function (Response)  {
                     success(Response);
                     formData.append('num', Response.data);
                     formData.append('flag', 0);
     
             })
              .catch(error => {
                 console.log(error);
             })
                     
         })
         .catch(error => {
             console.log(error);
         })

    }else{
         //이미지전송
         instance
         .post("/api/upload/", formData)
         .then(Response => {
          

             info.image = Response.data
      

             instance
             .post("/api/contract/save", info)
             .then(function (Response)  {
                     success(Response);
                     formData.append('num', Response.data);
                     formData.append('flag', 0);
     
             })
              .catch(error => {
                 console.log(error);
             })
                     
         })
         .catch(error => {
             console.log(error);
         })

    }
   
}

    

//공인중개사번호가져오기
function getLicense(num, success) {

    instance
        .get("/api/agent/" + num)
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//주소검색 자동완성
function getKeyword(keyword, success) {
    instance
        .get("/api/address/" + keyword)
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//주소검색하기-미사용
function searchAddress(keyword, num, success) {

    instance
        .post("/api/search/", {
            userNum: num,
            keyword: keyword
        })
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//최근 검색주소 가져오기
function getAddress(num, success) {

    instance
        .get("/api/search/" + num)
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//최근 검색주소 삭제하기
function deleteKeyword(id, success) {

    instance
        .delete("/api/search/" + id)
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//조회하기시 모든 건물이력 불러오기
function getrecord(city,local,success) {

    
    instance
        .get("/api/contract/search/?city="+city+"&local="+local)
        .then(function (response) {
            console.log("조회하기시 모든이력")
            console.log(response.data)
            success(response.data)
        })
        .catch(error => {
            console.log(error);
        })
    }

//클러스터 클릭시 미리보기 정보 불러오기
function getdetailrecord(building, success) {

    instance
        .post("/api/contract/lists/", building)
        .then(function (response) {
            success(response.data)

        })
        .catch(error => {
            console.log(error);
        })
    }


function getblockDetail(address, floor, ho, success) {

    instance
        .post("/api/contract/detail", {
            address: address,
            floor: floor,
            ho: ho
        })
        .then(function (response) {
            success(response.data)
        })
        .catch(error => {
            console.log(error);
        })
    }

//자기가 등록한 블록체인 등록한 내역 가져오기
function getUserBlock(num, success) {
    //console.log("유저가 등록한 블록체인 가져오기"+num)
    instance
        .get("/api/agent/contribution/" + num)
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//상태건물 이력 등록
function addStateBuliding(info, success) {
  

    let formData = new FormData();
    formData.append('image', info.file);
    formData.append('flag', 1);
    formData.append('num', -1);
    if(info.file==null){
         //이미지전송
         instance
         .post("/api/uploadNull/", formData)
         .then(Response => {
           

             info.image = Response.data
         

             instance
             .post("/api/status/save", info)
             .then(function (Response)  {
                     success(Response);
            
     
             })
              .catch(error => {
                 console.log(error);
             })
                     
         })
         .catch(error => {
             console.log(error);
         })

    }else{
         //이미지전송
         instance
         .post("/api/upload/", formData)
         .then(Response => {
         
             info.image = Response.data
           
             instance
             .post("/api/status/save", info)
             .then(function (Response)  {
                     success(Response);
        
     
             })
              .catch(error => {
                 console.log(error);
             })
                     
         })
         .catch(error => {
             console.log(error);
         })

    }
    }

function getUserBlockDetail(num, type, success) {

    if (type == null) {
        type = 0;
    } else {
        type = 1;
    }

    instance
        .get("/api/agent/contribution/detail/" + type + "/" + num)
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

    function getUserBlockDetails(num, type, success) {
       
        if (type == null) {
            type = 0;
        } else {
            type = 1;
        }
        let formData = new FormData();
        formData.append('type', type);
        formData.append('num', num);
        instance
            .post("/api/contract/confirm/" , formData)
            .then(function (response) {
                success(response)
            })
            .catch(error => {
                console.log(error);
            })
        }
    //분석
    function analysis(type, address, success) {
   
        instancepython
            .get("/charts/?address=" + address + "&detail=" + type)
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
            })
        }

    //로그인안할때 메인 추천데이터
    function getData(success) {
 
        instancepython
            .get("/rank/")
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
        })
    }
    //로그인할때 메인 추천데이터
    function getUserData(num,success) {
 
        instancepython
            .get("/rank/"+num+'/')
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
        })
    }

    //추천서비스에서 추천데이터 6개 가져오기
    function getSearchData(sd,sgg,cate,success) {
        
        if(cate=='교통'){
            cate = 'gt'
        }else if(cate=='마트/편의점'){
            cate = 'mt'
        }else if(cate=='교육시설'){
            cate = 'ed'
        }else if(cate=='의료시설'){
            cate = 'md'
        }else if(cate=='음식점/카페'){
            cate = 'fc'
        }else if(cate=='문화시설'){
            cate = 'ct'
        }

        instancepython
            .get("/prefer/?sd="+sd+'&sgg='+sgg+'&cate='+cate)
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
        })
    }
    function getLoginData(num,success) {
    
        instancepython
            .get("/recommend/"+num+"/")
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
        })
    }
    function getEvaluation(num, address, score, success) {
     
            var info = {
                address : address,
                userId : num,
                score : score,
            }
        instance
            .post("/api/favorite/",info)
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
            })
        }

        function addAround(address,success) {
       
            instancepython
                .get("/around/?addr="+address)
                .then(function (response) {
                    success(response.data)
                })
                .catch(error => {
                    console.log(error);
            })
        }
        function getAddressSearch (address, success) {
       
            instancepython
            .get("/search/?keyword="+address)
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
            })
        }
        function plusAddressSearch (page, keyword, success) {
         
            instancepython
            .get("/search/?keyword="+keyword+"&page="+page)
            .then(function (response) {
                success(response.data)
            })
            .catch(error => {
                console.log(error);
            })
        }
     
export {
    addBuliding,
    getLicense,
    getKeyword,
    searchAddress,
    getAddress,
    deleteKeyword,
    getrecord,
    getdetailrecord,
    getblockDetail,
    getUserBlock,
    addStateBuliding,
    getUserBlockDetail,
    analysis,
    getData,
    getUserData,
    getSearchData,
    getLoginData,
    getEvaluation,
    addAround,
    getUserBlockDetails,
    getAddressSearch,
    plusAddressSearch,
    
};
