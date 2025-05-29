<template>
  <el-container style="height: calc(100vh - 60px); border: 1px solid #eee">
    <el-aside width="200px" style="background-color: #f8f8f8">
      <el-menu router default-active="/teacher/dashboard">
        <!-- 使用 router 属性开启路由模式 -->
        <el-menu-item index="/teacher/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>主页</span>
        </el-menu-item>
        <el-menu-item index="/teacher/experiments">
          <el-icon><Setting /></el-icon>
          <span>实验管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher/submissions-to-grade">
          <el-icon><Tickets /></el-icon>
          <span>批改作业</span>
        </el-menu-item>
        <!-- 可以添加其他教师功能的菜单项 -->

        <el-menu-item @click="logout">
           <el-icon><SwitchButton /></el-icon>
           <span>退出登录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-main>
        <!-- 教师主页的内容 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { HomeFilled, Setting, Tickets, SwitchButton } from '@element-plus/icons-vue'; // Import necessary icons
import { useRouter } from 'vue-router'; // Import useRouter

const router = useRouter(); // Get router instance

// Removed teacherName and onMounted logic

const logout = () => {
  localStorage.removeItem('teacher'); // Clear teacher data from local storage
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

/* Removed teacher-home-content and welcome-text styles as they are now in TeacherDashboardView.vue */
</style>