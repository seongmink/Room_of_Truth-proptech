<template>
  <!-- <p>hello 홍주</p> -->
  <div id="app">
    <h1>홍주의 차트</h1>
    <h3>{{desc}}</h3>
    <input type="number" v-model="num" placeholder="건물번호" />
    <input type="text" v-model="type" placeholder="타입" />
    <input type="button" @click="getData()" value="짠" />

    <canvas id="planet-chart"></canvas>
  </div>
</template>
<script>
import Chart from "chart.js";
import axios from "axios";
export default {
  data() {
    return {
      planetChartData: {
        type: "line",
        data: {
          labels: ["no-data"],
          datasets: [
          ]
        },
        options: {
          responsive: true,
          lineTension: 1,
          spanGaps: true,
          scales: {
            yAxes: [
              {
                tick: {
                  beginAtZero: true,
                  padding: 25
                }
              }
            ]
          }
        }
      },
      num: 162843,
      type: "ws",
      desc: ""
    };
  },
  methods: {
    createChart(charId, chartData) {
      const ctx = document.getElementById(charId);
      const myChart = new Chart(ctx, {
        type: chartData.type,
        data: chartData.data,
        options: chartData.options
      });
    },
    getData() {
      console.log(this.num + " " + this.type);
      axios
        .get(`http://localhost:8000/buildings/${this.num}?detail=${this.type}`)
        .then(res => {
          console.log(res);

          this.planetChartData.data.datasets = [];
          this.planetChartData.data.labels = res.data.label;
          if (this.type == "ws") {
            let data_ws_dong = {
              label: "월세("+res.data.dong_address+")",
              borderColor: "#ef562d",
              fill: false,
              pointBackgroundColor: "white",
              borderWidth: 1,
              data: res.data.ws_dong_data
            };
            console.log(data_ws_dong)
            let data_ws_addr = {
              label: "월세("+res.data.road_address+")",
              borderColor: "#f6d258",
              fill: false,
              pointBackgroundColor: "white",
              borderWidth: 1,
              data: res.data.ws_addr_data
            };
            this.planetChartData.data.datasets.push(data_ws_dong);
            this.planetChartData.data.datasets.push(data_ws_addr);
          }
            let data_dong = {
              label: "거래가격("+res.data.dong_address+")",
              borderColor: "#88b14b",
              fill: false,
              pointBackgroundColor: "white",
              borderWidth: 1,
              data: res.data.dong_data
            };
            let data_addr = {
              label: "거래가격("+res.data.road_address+")",
              borderColor: "#0c4c8a",
              fill: false,
              pointBackgroundColor: "white",
              borderWidth: 1,
              data: res.data.addr_data
            };
            this.planetChartData.data.datasets.push(data_dong);
            this.planetChartData.data.datasets.push(data_addr);
          console.log(this.planetChartData.data);
          this.createChart("planet-chart", this.planetChartData);
        })
        .catch(() => {
            console.log('error')
        });
    }
  },
  created() {},
  mounted() {
    this.createChart('planet-chart', this.planetChartData)
  }
};
</script>