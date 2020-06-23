import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import DetailBuilding from "../views/DetailBuilding.vue";
import store from '../store/index.js';
import PageNotFound from '../../src/components/Error/PageNotFound.vue';

Vue.use(VueRouter);

//로그아웃이 필요한 기능
const rejectAuthUser = (to, from, next) =>{
 
    if(sessionStorage.getItem("access_token")!=null ){

        alert("이미 로그인을 하였습니다.")
        next('/') //홈으로 이동
    } else {
        next()
    }
}

//로그인이 필요한 기능
const onlyAuthUser = (to, from, next) =>{
    
    if(sessionStorage.getItem("access_token")==null){
      alert("로그인을 해주세요!")
      next('/') //홈으로 이동
    }else{
      next()
    }
}

//부동산 인증이 필요한 기능
const onlyAuth = (to, from, next) =>{
    if(sessionStorage.getItem("access_token")==null){
        alert("로그인을 해주세요!")
        next('/') //홈으로 이동
    }else{
     
        if(store.state.userInfo!=null && store.state.userInfo.auth==null){

            alert("공인중개사 등록을 해주세요!")
            next('/') //홈으로 이동
          }else{
            next()
          }
    } 
}

//부동산 인증이 없어야 하는 기능
const rejectAuth = (to, from, next) =>{
    if(sessionStorage.getItem("access_token")==null){
        alert("로그인을 해주세요!")
        next('/') //홈으로 이동
    }else{

        if(store.state.userInfo!=null && store.state.userInfo.auth!=null){
            
            alert("이미 공인중개사에 등록되셨습니다.!")
            next('/') //홈으로 이동
          }else{
            next()
          }
    } 
}

const routes = [{
        path: "/",
        name: "home",
        component: Home,
        children: [{
            path: "main",
            component: () =>
                import ("../components/common/Main.vue")
            },
            {
            path: "resister",
            component: () =>
                import ("../components/common/Resister.vue"),
            //beforeEnter : onlyAuthUser
            
            },
            {
            path: "auth",
            component: () =>
                import ("../components/common/Auth.vue"),
            //beforeEnter : rejectAuth
            },
            {
            path: "addbuilding",
            component: () =>
                import ("../components/structure/Addbuilding.vue"),
            //beforeEnter : onlyAuth
            },
            {
            name: "Search",
            path: "search",
            component: () =>
                import ("../components/structure/Search.vue"),
                
            },
            {
            name: "Realestate",
            path: "realestate",
            component: () =>
                import ("../components/common/Realestate.vue"),
            ///beforeEnter : onlyAuth
            },
            {
                name: "UserRealestate",
                path: "userRealestate",
                component: () =>
                    import ("../components/common/UserRealestate.vue")
                
            },
            {
                name: "ConfirmBuilding",
                path: "confirmbuilding",
                component: () =>
                    import ("../components/structure/ConfirmBuilding.vue")
                
            },
            {
                name: "AddInfo",
                path: "addinfo",
                component: () =>
                    import ("../components/common/AddInfo.vue")
                
            },
            {
                name: "Detail",
                path: "detail",
                component: () =>
                    import ("../components/structure/Detail.vue")
                
            },
            {
                name: "Recommendation",
                path: "recommendation",
                component: () =>
                    import ("../components/common/Recommendation.vue")
                
            },
            {
                name: "AddressSearch",
                path: "addresssearch",
                component: () =>
                    import ("../components/structure/AddressSearch.vue")
                
            },
            

        ],
        redirect: () => {
            return "/main";
        }
    },
    // {
    //     path: "/detailbuilding",
    //     name: "detailbuilding",
    //     component: DetailBuilding,
    //     children: [{
            
    //         path: "detail",
    //         component: () =>
    //             import ("../components/structure/Detail.vue")
    //         },
    //     ],
    //     redirect: () => {
    //         return "/detailbuilding/Detail";
    //     }
    // },
    {
        path:'*',
        redirect:'/404',
    },
    {
        path:'/404',
        component : PageNotFound,
    },
];


const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
    scrollBehavior (to, from, savedPosition) {
        return { x: 0, y: 0 }
      }
});
export default router;