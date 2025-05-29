<template>
    <div class="container" style="max-width: 400px;">
      <h2 class="mb-4">教师登录</h2>
      <el-form :model="form" @submit.prevent="onSubmit">
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-button type="primary" @click="onSubmit" class="w-100">登录</el-button>
      </el-form>
      <el-alert v-if="error" :title="error" type="error" class="mt-3" show-icon />
    </div>
  </template>
  <script setup>
  import { ref } from 'vue'
  import { teacherLogin } from '../api/teacher'
  import { useRouter } from 'vue-router'
  const router = useRouter()
  const form = ref({ username: '', password: '' })
  const error = ref('')
  const onSubmit = async () => {
    error.value = ''
    try {
      const { data } = await teacherLogin(form.value)
      if (data.success) {
        localStorage.setItem('teacher', JSON.stringify(data.teacher))
        await router.push('/teacher')
      } else {
        error.value = data.message
      }
    } catch (e) {
      error.value = '网络错误'
    }
  }
  </script>