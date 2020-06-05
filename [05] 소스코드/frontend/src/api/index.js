import axios from "axios";

function createInstance() {
  const instance = axios.create({
    //baseURL: "http://211.46.225.84:8080/",
    //baseURL: "https://i02b201.p.ssafy.io/",
    //baseURL: "http://192.168.1.7:8000", //홍주
    baseURL: "http://192.168.1.28:8080/", //성민
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
