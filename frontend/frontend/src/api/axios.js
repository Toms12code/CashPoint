import axios from 'axios';

const api = axios.create({
    basuURL: 'http://localhost:8080/api',
});

export default api;