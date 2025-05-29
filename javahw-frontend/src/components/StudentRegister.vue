<template>
    <div class="container" style="max-width: 400px;">
      <h2 class="mb-4">学生注册</h2>
      <el-form :model="form" @submit.prevent="onSubmit">
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="form.sno" />
        </el-form-item>
        <el-button type="primary" @click="onSubmit" class="w-100">注册</el-button>
      </el-form>
      <el-alert v-if="error" :title="error" type="error" class="mt-3" show-icon />
    </div>
  </template>
  <script setup>
  import { ref } from 'vue'
  import { studentRegister } from '../api/student'
  import { useRouter } from 'vue-router'
  const router = useRouter()
  const form = ref({ username: '', password: '', name: '', sno: '' })
  const error = ref('')
  const onSubmit = async () => {
    error.value = ''
    try {
      const { data } = await studentRegister(form.value)
      if (data.success) {
        await router.push('/login/student')
      } else {
        error.value = data.message
      }
    } catch (e) {
      error.value = '网络错误'
    }
  }
  </script>