<template>
  <div>
    <el-button type="info" @click="$router.push('/teacher/experiments')">返回实验管理</el-button>
    <h1 class="mt-3">实验提交列表 (实验ID: {{ experimentId }})</h1>
    <ul>
      <li v-for="submission in submissions" :key="submission.id">
        学生ID: {{ submission.studentId }}, 提交时间: {{ submission.createTime }}
        <!-- 批改按钮 -->
        <el-button type="primary" size="small" @click="gradeSubmission(submission.id)">批改</el-button>
      </li>
    </ul>
  </div>
</template>

<script>
import { listSubmissionsByExperiment } from '../api/submission';

export default {
  name: 'SubmissionListView',
  data() {
    return {
      experimentId: null,
      submissions: []
    };
  },
  created() {
    this.experimentId = this.$route.params.experimentId;
    if (this.experimentId) {
      this.fetchSubmissions(this.experimentId);
    }
  },
  methods: {
    fetchSubmissions(experimentId) {
      listSubmissionsByExperiment(experimentId)
        .then(response => {
          if (response.data) {
            this.submissions = response.data;
          }
        })
        .catch(error => {
          console.error('Error fetching submissions:', error);
        });
    },
    gradeSubmission(submissionId) {
      console.log('Navigating to grading for submissionId:', submissionId);
      this.$router.push(`/teacher/submissions/${submissionId}/grade`);
    }
  }
}
</script>

<style scoped>
/* 组件的样式将在这里添加 */
</style> 