import axios from "axios";

const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000,
  headers: {
    'Content-Type' : 'application/json',
  },
});

api.interceptors.response.use (
  (response) => {
    return response;
  },

  (error) => {
    if(error.response && error.response.data) {
      return Promise.reject(error.response.data);
    }

    return Promise.reject({ msg: "서버와 통신할 수 없습니다.", code: "NETWORK_ERROR"})
  }
);

export default api;