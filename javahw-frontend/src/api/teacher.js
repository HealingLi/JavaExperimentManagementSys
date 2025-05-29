import axios from './axios'

export function teacherLogin(data) {
  return axios.post('/teacher/login', data)
}
export function teacherRegister(data) {
  return axios.post('/teacher/register', data)
}