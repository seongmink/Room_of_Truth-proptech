import axios from "axios";

function createInstance() {
  const instance = axios.create({
    //baseURL: "http://211.46.225.84:8080/",
  baseURL: "http://k02b2031.p.ssafy.io",
    //baseURL: "http://192.168.43.241:8080/",
   // baseURL: "http://172.20.10.11:8080",
   //baseURL: "http://192.168.0.18:8080", //홍주
    headers: {
      "Content-Type": "application/json"
    }
  });
  return instance;
}

function getUrl() {
  //return "http://192.168.43.241:8080/"
   return "http://k02b2031.p.ssafy.io/images/"
}

export { createInstance, getUrl };
