import axios from 'axios';

export default {

  list() {
    return axios.post(`/review`)
  },
  get(id) {
    return axios.post(`/review/${id}`)
  },
  listProviderReviews(id) {
    return axios.get(`/provider/review/${id}`);
  },
  getReviewById(id) {
    return axios.get(`/provider/review/${id}`);
  },
  create(id) {
    return axios.put(`/review/${id}`);
  }
}
