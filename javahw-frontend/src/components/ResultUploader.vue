<template>
    <el-upload
      class="upload-demo"
      action=""
      :http-request="upload"
      :show-file-list="false"
      accept="image/*"
    >
      <el-button type="primary">上传运行结果图片</el-button>
    </el-upload>
  </template>
  <script setup>
  import { uploadResult } from '../api/upload'
  const emit = defineEmits(['uploaded'])
  const upload = async ({ file }) => {
    const { data } = await uploadResult(file)
    if (data.success) {
      emit('uploaded', data.path)
    } else {
      alert(data.message)
    }
  }
  </script>