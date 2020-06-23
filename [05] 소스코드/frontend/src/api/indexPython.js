import axios from "axios";

function createInstancepython() {
  const instance = axios.create({
    //baseURL: "http://211.46.225.84:8080/",
    baseURL: "http://k02b2031.p.ssafy.io:8000/",
   
    //baseURL: "http://192.168.43.241:8080/",
    //baseURL: "http://192.168.0.17:8000",
  // baseURL: "http://192.168.0.17:8000", //홍주

  });
  return instance;
}

function getUrl() {
  //return "http://192.168.43.241:8080/"
   return "http://k02b2031.p.ssafy.io/images/"
}

export { createInstancepython, getUrl };
