<template>
    <div style="padding: 30px 10px">
        dd
    <graph v-bind:data = "data"></graph>
    </div>
</template>
<script>
  //Importing Line class from the vue-chartjs wrapper
  // https://www.chartjs.org/docs/latest/charts/line.html
  // https://vue-chartjs.org/guide/#creating-your-first-chart

  import graph from './Chart.vue'
export default {
  data(){
		return{
            data:{
                label:['2020-01','2020-02','2020-03','2020-04'],
                dong :[10,20,30,30,50,100],
                addr :[40,50,60,80,90,20]
                }
		}
	},
  components: { 
    graph,
  }
};
</script>
