import {createInstance} from "./index.js";
import store from '../store/index';
const instance = createInstance();

//로그인 구현
function login(token, success) {
    //("로그인 해보기 " + token)
    let form = new FormData()
    form.append('access_Token', token)
    instance
        .post("/api/user/kakaologin", form)
        .then(function (response) {

            success(response)

        })
        .catch(error => {
            console.log("E" + error);
        })
    }

//토큰으로 멤버정보 불러오기
function getUserInfo(token) {

    let form = new FormData
    form.append('access_token', token)
    instance
        .post("/api/user/logintoken", form)
        .then(Response => {

            //vuex에 정보 저장하기
      
            store.commit('loginSucess', Response.data);
        })
        .catch(error => {
            sessionStorage.removeItem('access_token')

        })
    }
    //처음 로그인시 유저정보 등록
function addInfo(info, success){
 
    instance
        .post("/api/user/", info)
        .then(Response => {

            success(Response)

        })
        .catch(error => {
            console.log(error);
        })
}
//마이페이지 수정하기
function updateInfo(Info, success) {

    instance
        .patch("/api/interest", Info)
        .then(Response => {

            

            //다시 갱신
            sessionStorage.removeItem('access_token')
            sessionStorage.setItem("access_token", Response.data);
            store.dispatch('getMemberInfo');
            success(Response.data);

        })
        .catch(error => {
            console.log(error);
        })
    }

//부동산 인증
function auth(num, name, representative, address, license) {

    instance
        .post("/api/agent/", {
            userNum: num,
            name: name,
            representative: representative,
            address: address,
            license: license
        },)
        .then(Response => {


            // 다시 갱신
            sessionStorage.removeItem('access_token')
            sessionStorage.setItem("access_token", Response.data);
            store.dispatch('getMemberInfo');

        })
        .catch(error => {
            console.log(error);
        })

    }

//부동산 인증 해제
function deleteAuth(num) {
   

    instance
        .delete("/api/agent/" + num)
        .then(Response => {

            // 다시 갱신
            sessionStorage.removeItem('access_token')
            sessionStorage.setItem("access_token", Response.data);
            store.dispatch('getMemberInfo');

        })
        .catch(error => {
            console.log(error);
        })

    }

//부동산 정보불러오기
function getRealestateInfo(num, success) {
   

    instance
        .get("/api/agent/detail/" + num)
        .then(function (response) {

            success(response)

        })
        .catch(error => {

            console.log(error);
        })

    }

//메인페이지에서 랭킹 불러오기
function getRankInfo(success) {

    instance
        .get("/api/agent/ranking")
        .then(function (response) {

            success(response)

        })
        .catch(error => {

            console.log(error);
        })
    }

    //공인중개사 사진 등록하기
function addAimage(num,file,flag, success) {

    let formData = new FormData();
    formData.append('image', file);
    formData.append('num', num);
    formData.append('flag', flag);


    instance
        .post("/api/upload/",formData)
        .then(function (response) {

            success(response)

        })
        .catch(error => {

            console.log(error);
        })
    }



    //공인중개사 번호 검사하기
function checkAgentNum(num, number, success) {

    

    let formData = new FormData();
   
    formData.append('num', num);
    formData.append('license', number);

 
    

    instance
        .post("/api/agent/check",formData)
        .then(function (response) {

            success(response)

        })
        .catch(error => {

            console.log(error);
        })
    }

    
//내가 찜한 건물 불러오기
function likeBuilding(num,success) {

    instance
        .get("/api/favorite/"+num)
        .then(function (response) {

            success(response.data)

        })
        .catch(error => {

            console.log(error);
        })
    }
export {
    login,
    getUserInfo,
    updateInfo,
    auth,
    deleteAuth,
    getRealestateInfo,
    getRankInfo,
    addAimage,
    checkAgentNum,
    addInfo,
    likeBuilding
};
