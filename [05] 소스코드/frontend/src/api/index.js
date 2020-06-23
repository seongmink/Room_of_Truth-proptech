import axios from "axios";

function createInstance() {
  const instance = axios.create({
  //baseURL: "http://211.46.225.84:8080/",
   baseURL: "http://54.180.54.106:8080",
    headers: {
      "Content-Type": "application/json"
    }
  });
  return instance;
}

function getUrl() {
  //return "http://192.168.43.241:8080/"
   return "http://54.180.54.106/images/"
}

export { createInstance, getUrl };
