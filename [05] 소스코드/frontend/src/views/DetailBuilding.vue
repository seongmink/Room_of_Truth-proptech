<template>
  <div>
    <Menu></Menu>

    <div id="main-overview" class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Menu from "../components/common/Menu";

export default {
  data(){
		return{

		}
	},
  components: {
    Menu,
  }
};
</script>

<style>
#haribo-bear {
  height: 200px;
}
</style>
