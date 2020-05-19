import {createInstance} from "./index.js";

const instance = createInstance();

//건물이력등록
function addBuliding(info, success) {
    // console.log("건물이력등록전 정보") console.log(info)

    let formData = new FormData();
    formData.append('file', info.file);
    instance
        .post("/api/building", info)
        .then(Response => {
            success(Response);
            formData.append('num', Response.data);
            formData.append('flag', 0);
            //이미지전송
            instance
                .post("/api/uploadfile", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then(Response => {

                    // console.log(Response.data)

                })
                .catch(error => {
                    console.log(error);
                })
            })
        .catch(error => {
            console.log(error);
        })
    }

//공인중개사번호가져오기
function getLicense(num, success) {
    // console.log("공인중개사 번호 가져오기") console.log(num)
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
            num: num,
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
function deleteKeyword(num, keyword, success) {

    instance
        .delete("/api/search/" + num + "/" + keyword)
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//조회하기시 모든 건물이력 불러오기
function getrecord(success) {
    //console.log("맵에 건물이력 표시하기")
    instance
        .get("/api/building/")
        .then(function (response) {
            success(response)
        })
        .catch(error => {
            console.log(error);
        })
    }

//클러스터 클릭시 미리보기 정보 불러오기
function getdetailrecord(building, success) {
    //console.log("클러스터 클릭시 목록 보여주기")
    instance
        .post("/api/building/detail/", building)
        .then(function (response) {
            success(response)

        })
        .catch(error => {
            console.log(error);
        })
    }


function getblockDetail(address, dong, ho, success) {
    //console.log("블록체인에서 데이터 가져오기")
    instance
        .post("/api/building/detail/block/", {
            address: address,
            dong: dong,
            ho: ho
        })
        .then(function (response) {
            success(response)
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
    // console.log("상태 건물이력등록전 정보") console.log(info)

    let formData = new FormData();
    formData.append('file', info.file);
    instance
        .post("/api/maintenance", info)
        .then(Response => {
            success(Response);
            formData.append('num', Response.data);

            formData.append('flag', 1);

            //이미지전송
            instance
                .post("/api/uploadfile", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then(Response => {

                    //console.log(Response.data)

                })
                .catch(error => {
                    console.log(error);
                })
            })
        .catch(error => {
            console.log(error);
        })
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
    getUserBlockDetail
};
