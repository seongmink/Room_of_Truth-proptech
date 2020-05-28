<script>
  //Importing Line class from the vue-chartjs wrapper
  import {Line} from 'vue-chartjs'
  //Exporting this so it can be used in other components
  export default { 
    extends: Line,
    props:{
        'data':Object
    },
    data () {
      return {
        datacollection: {
          //Data to be represented on x-axis
          labels: ['2020-04'], 
          datasets: [
            {
              label: '동',
              borderColor: '#009888',
              fill:false,
              pointBackgroundColor: 'white',
              borderWidth: 1,
              //Data to be represented on y-axis
              data: [100]
            },
            {
              label: '건물',
              borderColor: '#87c735',
              fill:false,
              pointBackgroundColor: 'white',
              borderWidth: 1,
              pointBorderColor: '#87c735',
              data: [50]
            }
          ]
        },
        //Chart.js options that controls the appearance of the chart
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              },
              gridLines: {
                display: true
              }
            }],
            xAxes: [ {
              gridLines: {
                display: false
              }
            }]
          },
          legend: {
            display: true
          },
          responsive: true,
          maintainAspectRatio: false
        }
      }
    },
    created(){
        if(this.data === undefined){
            console.log('no data')
        }else{
            console.log(this.datacollection)
            this.datacollection.labels = this.data.label
            this.datacollection.datasets[0].data = this.data.dong
            this.datacollection.datasets[1].data = this.data.addr
        }
    },
    mounted () {
      //renderChart function renders the chart with the datacollection and options object.
      this.renderChart(this.datacollection, this.options)
    }
  }
</script>
