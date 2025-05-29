<template>
  <div class="container">
    <h2>待批改作业列表</h2>
    <div v-if="loading">
      <p>加载中...</p>
    </div>
    <div v-else-if="submissionsToGrade.length > 0">
      <el-row :gutter="20">
        <el-col :span="12" v-for="submission in submissionsToGrade" :key="submission.id">
          <el-card class="submission-card">
            <template #header>
              <div class="card-header">
                <span>实验: {{ submission.experimentTitle || '未知实验' }}</span>
              </div>
            </template>
            <div>
              <p><strong>学生:</strong> {{ submission.studentName || '未知学生' }}</p>
              <p><strong>提交时间:</strong> {{ submission.createTime }}</p>
            </div>
            <div class="mt-3">
              <el-button type="primary" size="small" @click="gradeSubmission(submission.id)">去批改</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div v-else>
      <p>没有待批改的作业。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getExperiments } from '../api/experiment' // Using existing getExperiments
import { listSubmissionsByExperiment } from '../api/submission'
import { getGradingBySubmission } from '../api/grading'
import { getStudentById } from '../api/student' // Hypothetical API function
import { getExperimentById } from '../api/experiment' // Import getExperimentById
import { useRouter } from 'vue-router'
import { ElButton, ElCard, ElRow, ElCol } from 'element-plus' // Import ElCard, ElRow, ElCol

const submissionsToGrade = ref([])
const loading = ref(true)
const router = useRouter()

onMounted(async () => {
  const teacher = JSON.parse(localStorage.getItem('teacher') || '{}');
  if (!teacher || !teacher.id) {
    loading.value = false;
    return;
  }

  try {
    console.log('Fetching experiments for teacher ID:', teacher.id);
    const { data: experiments } = await getExperiments({ teacherId: teacher.id });
    console.log('Received experiments data:', experiments);

    const allSubmissions = [];
    if (experiments && experiments.length > 0) {
      for (const experiment of experiments) {
        console.log(`Processing experiment ${experiment.id} with title: ${experiment.title}`);
        const { data: submissions } = await listSubmissionsByExperiment(experiment.id);
        for (const submission of submissions) {
          const { data: grading } = await getGradingBySubmission(submission.id);
          if (!grading || grading.length === 0) {
            // This submission has not been graded
            const relatedExperiment = experiments.find(exp => exp.id === submission.experimentId);
            submission.experimentTitle = relatedExperiment ? relatedExperiment.title : '未知实验 (无匹配)';

            // Fetch experiment title by ID
            console.log(`Fetching experiment for ID: ${submission.experimentId}`);
            try {
              const { data: experiment } = await getExperimentById(submission.experimentId);
              console.log(`Received experiment data for ID ${submission.experimentId}:`, experiment);
              if (experiment && experiment.name) {
                submission.experimentTitle = experiment.name;
              } else {
                submission.experimentTitle = '未知实验 (无数据)';
              }
            } catch (experimentError) {
              console.error(`Error fetching experiment ${submission.experimentId}:`, experimentError);
              submission.experimentTitle = '未知实验 (API错误)';
            }

            // Fetch student name
            console.log(`Fetching student for ID: ${submission.studentId}`);
            try {
              const { data: student } = await getStudentById(submission.studentId);
              console.log(`Received student data for ID ${submission.studentId}:`, student);
              if (student && student.name) {
                submission.studentName = student.name;
              } else {
                submission.studentName = '未知学生 (无数据)';
              }
            } catch (studentError) {
              console.error(`Error fetching student ${submission.studentId}:`, studentError);
              submission.studentName = '未知学生 (API错误)';
            }
            allSubmissions.push(submission);
          }
        }
      }
    } else {
      console.log('No experiments found for this teacher.');
    }

    submissionsToGrade.value = allSubmissions;
  } catch (error) {
    console.error('Error fetching submissions to grade:', error);
  } finally {
    loading.value = false;
  }
});

const gradeSubmission = (submissionId) => {
  router.push(`/teacher/submissions/${submissionId}/grade`);
};

</script>

<style scoped>
/* Component-specific styles */
.submission-card {
  margin-bottom: 20px;
}
.card-header {
  font-weight: bold;
}
</style> 