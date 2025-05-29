<template>
  <div>
    <el-button type="info" @click="$router.push(`/teacher/experiments/${submission.experimentId}/submissions`)" v-if="submission">返回提交列表</el-button>
    <h1 class="mt-3">批改作业 (提交ID: {{ submissionId }})</h1>
    <div v-if="submission">
      <h2>学生提交内容:</h2>
      <p><strong>学生ID:</strong> {{ submission.studentId }}</p>
      <p><strong>实验ID:</strong> {{ submission.experimentId }}</p>
      <p><strong>提交时间:</strong> {{ submission.createTime }}</p>
      <p><strong>代码文本:</strong></p>
      <pre>{{ submission.codeText }}</pre>
      <!-- 如果有结果图片，显示图片和下载链接 -->
      <div v-if="submission.resultImg">
        <p><strong>结果图片:</strong></p>
        <img :src="backendBaseUrl + submission.resultImg" alt="结果图片" style="max-width: 500px;"/>
        <br>
        <a :href="backendBaseUrl + submission.resultImg" download>下载结果图片</a>
      </div>
      <!-- 如果有代码压缩包，提供下载链接 -->
      <div v-if="submission.codeZip">
         <p><strong>代码压缩包:</strong> <a :href="backendBaseUrl + submission.codeZip" download>下载代码压缩包</a></p>
      </div>
      
      <h2>批改区域:</h2>
      <!-- 批改表单将在这里添加 -->
      <div>
        <label for="grade">分数:</label>
        <input type="text" id="grade" v-model="grade">
      </div>
      <div>
        <label for="comment">评语:</label>
        <textarea id="comment" v-model="comment"></textarea>
      </div>
      <el-button type="success" @click="submitGrading">提交批改</el-button>

    </div>
    <div v-else>
      <p>正在加载提交详情...</p>
    </div>
  </div>
</template>

<script>
import { getSubmissionById } from '../api/submission';
import { addGrading } from '../api/grading';

export default {
  name: 'GradingView',
  data() {
    return {
      submissionId: null,
      submission: null,
      grade: '',
      comment: '',
      teacherId: 1, // TODO: Get actual teacher ID from auth or local storage
      backendBaseUrl: 'http://localhost:8080' // Add backend base URL
    };
  },
  created() {
    this.submissionId = this.$route.params.submissionId;
    console.log('GradingView created, submissionId:', this.submissionId);
    if (this.submissionId) {
      this.fetchSubmission(this.submissionId);
    }
  },
  methods: {
    fetchSubmission(submissionId) {
      getSubmissionById(submissionId)
        .then(response => {
          if (response.data) {
            this.submission = response.data;
          }
        })
        .catch(error => {
          console.error('Error fetching submission:', error);
        });
    },
    submitGrading() {
      const gradingData = {
        submissionId: this.submissionId,
        teacherId: this.teacherId,
        grade: this.grade,
        comment: this.comment
      };

      addGrading(gradingData)
        .then(response => {
          if (response.data.success) {
            alert('批改提交成功！');
            // Navigate back to the submission list for the current experiment
            this.$router.push(`/teacher/experiments/${this.submission.experimentId}/submissions`);
          } else {
            alert('批改提交失败: ' + response.data.message);
          }
        })
        .catch(error => {
          console.error('Error submitting grading:', error);
          alert('批改提交失败');
        });
    }
  }
}
</script>

<style scoped>
/* 组件的样式将在这里添加 */
</style> 