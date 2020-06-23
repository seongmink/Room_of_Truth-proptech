import axios from "axios";

function createInstancepython() {
  const instance = axios.create({
  //baseURL: "http://192.168.0.17:8000",
   baseURL: "http://54.180.54.106:8000/", //홍주

  });
  return instance;
}

function getUrl() {
  //return "http://192.168.43.241:8080/"
   return "http://54.180.54.106/images/"
}

export { createInstancepython, getUrl };
