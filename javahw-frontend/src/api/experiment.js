import axios from './axios'

export function getExperiments() {
  return axios.get('/experiment/list')
}
export function addExperiment(data) {
  return axios.post('/experiment/add', data)
}
export function updateExperiment(data) {
  return axios.put('/experiment/update', data)
}
export function deleteExperiment(id) {
  return axios.delete(`/experiment/delete/${id}`)
}
export function getExperimentById(id) {
  return axios.get(`/experiment/${id}`)
}