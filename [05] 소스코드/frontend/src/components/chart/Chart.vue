<template>
    <div>
      <canvas :id="id" width="330" height="330" style="margin-left:auto; margin-right:auto"></canvas>
    </div>
</template>
<script>
import Chart from 'chart.js'
   export default {
      data() {
         return {
            planetChartData:{
                type:'radar',
                data:{
                 
                    labels:[ "교통", "마트/편의점", "교육시설", "의료시설", "음식점/카페", "문화시설" ],
                    datasets:[
                        {
                            label:'대전광역시 유성구 봉명동 온천북로33번길',
                            data:[46, 35, 55, 66, 40, 30],
                            borderColor: "#00c03f",
                            pointBackgroundColor: "white",
                            borderWidth:3,
                            fill: false,
                           
                        },
                       
                    ]
                },
                options:{
                   responsive : false,
                    scale: {
                        angleLines: {
                        display: false
                     },
                     ticks: {
                        suggestedMin: 0,
                        suggestedMax: 100
                     },
                     animation: {
                        duration: 0
                    },
                  }
               }
            },
         }  
      },
      created() {
         var d = [this.trans,this.comforts,this.education,this.medical,this.eatery,this.culture];
         //100넘으면 100으로 초기화
         for(var i=0; i<d.length; i++){
            if(d[i]>=100){
               d[i] = 100;
            }
         }
         this.planetChartData.data.datasets[0].data = d;
         this.planetChartData.data.datasets[0].label = this.label;
 
          
		},
      watch: {
       

	   },
      computed: {
			
      },
      mounted(){
        this.createChart(this.id, this.planetChartData)
      },
      methods: {
			createChart(charId, chartData){
            const ctx = document.getElementById(charId);
            const myChart = new Chart(ctx, {
                type: chartData.type,
                data: chartData.data,
                options: chartData.options,
            })
        }
		},
      components: {
			
      },
      props: ['comforts','culture','eatery','education','medical','trans','data', 'label','id']
   };
</script>