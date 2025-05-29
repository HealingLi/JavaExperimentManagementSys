import axios from './axios'

export function studentLogin(data) {
  return axios.post('/student/login', data)
}
export function studentRegister(data) {
  return axios.post('/student/register', data)
}

export function getStudentById(id) {
  return axios.get(`/student/${id}`)
}