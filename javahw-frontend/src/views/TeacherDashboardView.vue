<template>
  <div class="teacher-dashboard-container">
    <h1 class="welcome-text">欢迎，{{ teacherName || '教师' }}！</h1>
    
    <el-row :gutter="20" class="mt-4">
      <el-col :span="12">
        <el-card>
          <template #header>待批改作业概览</template>
          <div>
            <!-- Placeholder for pending grading summary -->
            <p>您有 [数量] 份作业待批改。</p>
            <el-button type="primary" size="small" @click="$router.push('/teacher/submissions-to-grade')">查看待批改列表</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>实验统计</template>
          <div>
            <!-- Placeholder for experiment statistics -->
            <p>您已创建 [数量] 个实验。</p>
            <el-button type="primary" size="small" @click="$router.push('/teacher/experiments')">管理实验</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- More dashboard content can be added here -->

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElRow, ElCol, ElCard, ElButton } from 'element-plus';

const teacherName = ref('');

onMounted(() => {
  const teacher = JSON.parse(localStorage.getItem('teacher') || '{}');
  if (teacher && teacher.name) {
    teacherName.value = teacher.name;
  }
});

</script>

<style scoped>
.teacher-dashboard-container {
  max-width: 800px; /* 设置最大宽度 */
  margin: 0 auto; /* 居中 */
  padding: 20px; /* 添加内边距 */
}

.welcome-text {
  font-size: 2em; /* 字体放大 */
  font-weight: bold; /* 字体加粗 */
  color: #2c3e50; /* 深色字体 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3); /* 添加文本阴影 */
}
</style> 