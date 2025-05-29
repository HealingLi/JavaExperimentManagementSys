<template>
  <div class="welcome-container">
    <h1>欢迎使用 Java 实验习题系统</h1>
    <p>请选择您的身份：</p>
    <div class="role-selection">
      <el-button type="primary" size="large" @click="selectRole('student')">我是学生</el-button>
      <el-button type="success" size="large" @click="selectRole('teacher')">我是教师</el-button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const selectRole = (role) => {
  router.push(`/auth/${role}`);
};
</script>

<style scoped>
.welcome-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 60px); /* Adjust based on navbar height */
  text-align: center;
  padding: 20px;
}

h1 {
  color: #2c3e50;
  margin-bottom: 20px;
}

.role-selection button {
  margin: 10px;
}
</style> 