import axios from './axios'

export function submitHomework(data) {

  const student = JSON.parse(localStorage.getItem('student') || '{}')
  return axios.post('/submission/add', {
    studentId: student.id,
    experimentId: data.experimentId,
    codeText: data.codeText,
    codeZip: data.codeZip,
    resultImg: data.resultImg
  })
}

// 添加根据实验ID获取提交列表的函数 (教师端使用)
export function listSubmissionsByExperiment(experimentId) {
  return axios.get(`/submission/by-experiment/${experimentId}`)
}

// 添加根据提交ID获取提交详情的函数 (查看详情或修改时使用)
export function getSubmissionById(submissionId) {
  return axios.get(`/submission/${submissionId}`)
}

// 添加更新提交的函数 (学生端修改作业使用)
export function updateSubmission(submissionData) {
  return axios.put('/submission/update', submissionData)
}

// 添加获取我的提交列表的函数 (学生端查看自己的提交记录使用)
export function listMySubmissions(studentId) {
   return axios.get(`/submission/my?studentId=${studentId}`)
}
