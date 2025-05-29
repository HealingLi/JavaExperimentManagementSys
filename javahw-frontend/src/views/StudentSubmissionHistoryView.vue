<template>
    <div class="container">
      <h2 class="mb-4">我的历史提交</h2>
      <div v-if="submissions.length > 0">
        <ul>
          <li v-for="submission in submissions" :key="submission.id">
            实验ID: {{ submission.experimentId }}, 提交时间: {{ submission.createTime }}
            <el-button type="primary" size="small" @click="viewSubmissionDetail(submission.id)">查看详情/评语</el-button>
          </li>
        </ul>
      </div>
      <div v-else>
        <p>您还没有提交过作业。</p>
      </div>

       <el-dialog v-model="dialogVisible" title="作业详情" width="60%">
        <div v-if="currentSubmission">
          <h3>学生提交内容:</h3>
          <p><strong>实验ID:</strong> {{ currentSubmission.experimentId }}</p>
          <p><strong>提交时间:</strong> {{ currentSubmission.createTime }}</p>
          <p><strong>代码文本:</strong></p>
          <pre>{{ currentSubmission.codeText }}</pre>
          <div v-if="currentSubmission.resultImg">
            <p><strong>结果图片:</strong></p>
            <img :src="backendBaseUrl + currentSubmission.resultImg" alt="结果图片" style="max-width: 500px;"/>
            <br>
            <a :href="backendBaseUrl + currentSubmission.resultImg" download>下载结果图片</a>
          </div>
          <div v-if="currentSubmission.codeZip">
             <p><strong>代码压缩包:</strong> <a :href="backendBaseUrl + currentSubmission.codeZip" download>下载代码压缩包</a></p>
          </div>
        </div>
        <div v-else>
          <p>正在加载提交详情...</p>
        </div>

        <h3 class="mt-4">教师批改:</h3>
        <div v-if="currentGrading">
          <p><strong>分数:</strong> {{ currentGrading.grade }}</p>
          <p><strong>评语:</strong> {{ currentGrading.comment }}</p>
          <p class="text-muted">批改时间: {{ currentGrading.createTime }}</p>
        </div>
        <div v-else>
          <p>教师暂未批改。</p>
        </div>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
          </span>
        </template>
      </el-dialog>

    </div>
  </template>
  <script setup>
  import { ref, onMounted } from 'vue'
  import { listMySubmissions, getSubmissionById } from '../api/submission'
  import { getGradingBySubmission } from '../api/grading'
  import { ElDialog, ElButton } from 'element-plus'

  const submissions = ref([])
  const dialogVisible = ref(false)
  const currentSubmission = ref(null)
  const currentGrading = ref(null)
  const backendBaseUrl = 'http://localhost:8080'

  onMounted(async () => {
    const student = JSON.parse(localStorage.getItem('student') || '{}');
    if (student && student.id) {
      try {
        const { data: submissionsData } = await listMySubmissions(student.id)
        submissions.value = submissionsData
      } catch (error) {
        console.error('Error fetching submissions:', error)
      }
    }
  })

  const viewSubmissionDetail = async (submissionId) => {
    currentSubmission.value = null;
    currentGrading.value = null;
    dialogVisible.value = true;

    try {
      const { data: submissionData } = await getSubmissionById(submissionId)
      currentSubmission.value = submissionData
    } catch (error) {
      console.error('Error fetching submission details:', error);
    }

    try {
      const { data: gradingData } = await getGradingBySubmission(submissionId)
      if (gradingData && gradingData.length > 0) {
        currentGrading.value = gradingData[0];
      } else {
        currentGrading.value = null;
      }
    } catch (error) {
      console.error('Error fetching grading details:', error);
    }
  }
  </script>
  <style scoped>
  pre {
    white-space: pre-wrap;
    word-wrap: break-word;
  }
  </style> 