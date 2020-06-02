<template>
<div>
	<div>
		<title-bar title="건물 추천서비스" subtitle="선호도별로 건물을 추천해줍니다."></title-bar>	
	</div>
   <div>
      <div>
         <h4>list</h4>
         
         <b-card-group deck  style="margin-top:300px;margin-bottom:60px">
            <b-card img-src="/static/b1.jpg" img-alt="Card image" img-top img-height="250px">
               <b-card-text>
                  <h5> 대전광역시 유성구 봉명동 온천북로33번길 </h5>
                  <h5> 2층 102호 </h5>
               </b-card-text>
            </b-card>
            <b-card>
               <canvas id ="planet-chart" width="330" height="330" style="margin-left:auto; margin-right:auto"></canvas>
            </b-card>
         </b-card-group>
      </div> 
   </div>
</div>
</template>

<script>
import TitleBar from '../common/TitleBar';
import Chart from 'chart.js'
   export default {
      data() {
         return {
            planetChartData:{
                type:'radar',
                data:{
                 
                    labels:['교통','마트/편의점','교육시설','의료시설','음식점/카페','문화시설'],
                    datasets:[
                        {
                            label:'군산시 서수면 동군산로 1142',
                            data:[90,35,55,66,80,70],
                            borderColor: "#ef562d",
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
                     }
                  }
               }
            }
		   }
      },
      created() {

		},
      watch: {

			

		},
      computed: {
			
      },
      mounted(){
        this.createChart('planet-chart', this.planetChartData)
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
			TitleBar,
		}
   };
</script>