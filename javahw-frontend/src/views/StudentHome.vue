<template>
  <el-container style="height: calc(100vh - 60px); border: 1px solid #eee">
    <el-aside width="200px" style="background-color: #f8f8f8">
      <el-menu router default-active="/student/experiments">
         <!-- 使用 router 属性开启路由模式 -->
        <el-menu-item index="/student">
          <el-icon><HomeFilled /></el-icon>
          <span>主页</span>
        </el-menu-item>
        <el-menu-item index="/student/experiments">
          <el-icon><Reading /></el-icon>
          <span>Java实验</span>
        </el-menu-item>
        <el-menu-item index="/student/submissions">
          <el-icon><Tickets /></el-icon>
          <span>历史提交</span>
        </el-menu-item>
        <el-menu-item @click="logout">
          <el-icon><SwitchButton /></el-icon>
          <span>退出登录</span>
        </el-menu-item>
        <!-- 可以添加其他学生功能的菜单项 -->
      </el-menu>
    </el-aside>

    <el-container>
      <el-main>
        <!-- 学生主页的内容 -->
        <div class="student-home-content">
            <!-- 这里根据路由显示不同的内容 -->
           <router-view></router-view>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getExperiments } from '../api/experiment'
import { listMySubmissions, getSubmissionById } from '../api/submission'
import { getGradingBySubmission } from '../api/grading'
import ExperimentCard from '../components/ExperimentCard.vue'
import { ElDialog, ElButton, ElContainer, ElAside, ElMain, ElMenu, ElMenuItem, ElIcon } from 'element-plus' // Import necessary components
import { HomeFilled, Reading, Tickets, SwitchButton } from '@element-plus/icons-vue'; // Import necessary icons including SwitchButton
import 'element-plus/dist/index.css'
import { useRouter } from 'vue-router'; // Import useRouter

const router = useRouter(); // Get router instance

const experiments = ref([])
const submissions = ref([])

const dialogVisible = ref(false)
const currentSubmission = ref(null)
const currentGrading = ref(null)
const backendBaseUrl = 'http://localhost:8080'

// Note: Experiment list and historical submissions fetching logic will be moved to dedicated views later
// Keeping them here for now for reference if needed.

// onMounted(async () => {
//   try {
//     const { data: experimentsData } = await getExperiments()
//     experiments.value = experimentsData
//   } catch (error) {
//     console.error('Error fetching experiments:', error)
//   }

//   const student = JSON.parse(localStorage.getItem('student') || '{}');
//   if (student && student.id) {
//     try {
//       const { data: submissionsData } = await listMySubmissions(student.id)
//       submissions.value = submissionsData
//     } catch (error) {
//       console.error('Error fetching submissions:', error)
//     }
//   }
// })

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

const logout = () => {
  localStorage.removeItem('student'); // Clear student data from local storage
  router.push('/'); // Navigate to the welcome page
};

</script>
<style scoped>
/* 组件的样式将在这里添加 */
.el-container {
  height: 100%;
}

.el-aside {
  color: var(--el-text-color-primary);
  background: #fff !important; /* Ensure white background */
  box-sizing: border-box;
}

.el-menu {
  border-right: none; /* Remove default border */
}

.el-main {
  padding: 20px;
}

.student-home-content {
   max-width: 960px; /* Adjust max width */
   margin: 0 auto; /* Center the content */
}

pre {
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>