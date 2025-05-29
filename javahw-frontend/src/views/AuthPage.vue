<template>
  <div class="auth-container">
    <el-button type="info" @click="$router.push('/')" class="back-button">返回欢迎页</el-button>
    <!-- 动态标题 -->
    <h2 class="text-center mb-4">{{ isLogin ? '欢迎回来' : '新用户注册' }} {{ roleLabel }}</h2>
    
    <!-- 状态切换 -->
    <div class="mode-switch text-center mb-4">
      <el-button-group>
        <el-button :type="isLogin ? 'primary' : ''" 
                   @click="toggleMode(true)">登录</el-button>
        <el-button :type="!isLogin ? 'primary' : ''"
                   @click="toggleMode(false)">注册</el-button>
      </el-button-group>
    </div>

    <!-- 动态表单 -->
    <el-form :model="form" @submit.prevent="onSubmit">
      <!-- 公共字段 -->
      <el-form-item label="用户名">
        <el-input v-model="form.username" />
      </el-form-item>
       <el-form-item label="密码">
        <el-input v-model="form.password" type="password" />
      </el-form-item>

      <!-- 注册扩展字段 -->
      <template v-if="!isLogin">
        <el-form-item v-if="role === 'student'" label="学号">
          <el-input v-model="form.studentId"/>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="form.realname"/>
        </el-form-item>
      </template>
       <el-alert v-if="error" :title="error" type="error" class="mt-3" show-icon />
         <el-form-item class="mt-4">
            <el-button type="primary" @click="onSubmit" class="w-100">{{ isLogin ? '登录' : '注册' }}</el-button>
         </el-form-item>
    </el-form>
   
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { studentLogin, studentRegister } from '../api/student'; // Assume these exist
import { teacherLogin, teacherRegister } from '../api/teacher'; // Assume these exist
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();

const role = computed(() => route.params.role);
const isLogin = ref(true);
const form = ref({ username: '', password: '', studentId: '', realname: '' });
const error = ref('');

const roleLabel = computed(() => role.value === 'student' ? '学生' : '教师');

const toggleMode = (mode) => {
  isLogin.value = mode;
  // Reset form and error when toggling mode
  form.value = { username: '', password: '', studentId: '', realname: '' };
  error.value = '';
};

const onSubmit = async () => {
  error.value = '';
  try {
    let res;
    if (role.value === 'student') {
      if (isLogin.value) {
        res = await studentLogin({ username: form.value.username, password: form.value.password });
      } else {
        res = await studentRegister(form.value); // Send all form data for registration
      }
    } else if (role.value === 'teacher') {
      if (isLogin.value) {
         res = await teacherLogin({ username: form.value.username, password: form.value.password });
      } else {
         res = await teacherRegister(form.value); // Send all form data for registration
      }
    }

    if (res && res.data.success) {
      ElMessage.success(`${roleLabel.value}${isLogin.value ? '登录' : '注册'}成功！`);
      // Store user info and navigate to respective home page
      const userData = res.data[role.value]; // Get student or teacher object
      localStorage.setItem(role.value, JSON.stringify(userData));
      router.push(`/${role.value}`); // Navigate to /student or /teacher
    } else {
      error.value = res ? res.data.message : '请求失败';
      ElMessage.error(`${roleLabel.value}${isLogin.value ? '登录' : '注册'}失败: ${error.value}`);
    }
  } catch (e) {
    console.error('Authentication error:', e);
    error.value = '网络或服务器错误';
    ElMessage.error('网络或服务器错误');
  }
};

// Watch for role changes in the route and reset state
watch(() => route.params.role, (newRole, oldRole) => {
  if (newRole !== oldRole) {
    isLogin.value = true; // Default to login mode on role change
    form.value = { username: '', password: '', studentId: '', realname: '' };
    error.value = '';
  }
});

</script>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 40px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.mode-switch {
  margin-bottom: 20px;
}

.back-button {
    margin-bottom: 20px;
}

</style> 