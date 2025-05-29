import axios from './axios'

export function addGrading(data) {
  return axios.post('/grading/add', data)
}
export function getGradingBySubmission(submissionId) {
  return axios.get(`/grading/by-submission/${submissionId}`)
}
export function getGradingByTeacher(teacherId) {
  return axios.get(`/grading/by-teacher/${teacherId}`)
}