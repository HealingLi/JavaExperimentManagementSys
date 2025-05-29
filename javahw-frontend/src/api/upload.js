import axios from './axios'

export function uploadCode(file) {
  const formData = new FormData()
  formData.append('file', file)
  return axios.post('/upload/code', formData)
}
export function uploadResult(file) {
  const formData = new FormData()
  formData.append('file', file)
  return axios.post('/upload/result', formData)
}