<template>
  <div>
    <Menu></Menu>

    <div id="main-overview" class="container">
      <router-view></router-view>
   
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Menu from "../components/common/Menu";
import Footer from "../components/common/Footer";

export default {
  data(){
		return{

		}
	},
  components: {
    Menu,
    Footer,
  }
};
</script>

<style>
#haribo-bear {
  height: 200px;
}
</style>
