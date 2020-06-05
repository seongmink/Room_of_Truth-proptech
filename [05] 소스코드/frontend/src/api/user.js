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
  //console.log("토큰으로 정보 불러오기")
    let form = new FormData
    form.append('access_token', token)
    instance
        .post("/api/user/logintoken", form)
        .then(Response => {

            //vuex에 정보 저장하기
            console.log(Response.data)
            store.commit('loginSucess', Response.data);
        })
        .catch(error => {
            sessionStorage.removeItem('access_token')

        })
    }
    //처음 로그인시 유저정보 등록
function addInfo(info, success){
    console.log(info)
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
  //console.log("마이페이지가서 수정하기")
    instance
        .patch("/api/interest", Info)
        .then(Response => {

            console.log(Response.data)

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
  //console.log("부동산 인증하기")
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
    //console.log("부동산 해제하기")

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
    //console.log("부동산 정보 불러오기")

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
    //console.log("메인페이지에서 랭킹 불러오기")
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
    //console.log("공인중개사 사진 등록하기")
    let formData = new FormData();
    formData.append('image', file);
    formData.append('num', num);
    formData.append('flag', flag);

    //console.log(formData)
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
    //console.log(number)

    let formData = new FormData();
   
    formData.append('num', num);
    formData.append('license', number);

    //console.log("공인중개사 번호 검사하기")

    instance
        .post("/api/agent/check",formData)
        .then(function (response) {

            success(response)

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
    addInfo
};
