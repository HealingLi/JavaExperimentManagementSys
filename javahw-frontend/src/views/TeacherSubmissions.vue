<template>
    <div class="container">
      <h2>实验提交列表</h2>
      <div v-if="experiment">
        <h3>实验: {{ experiment.name }}</h3>
        <p>描述: {{ experiment.description }}</p>
        <p>截止时间: {{ experiment.deadline }}</p>
      </div>
  
      <el-table :data="submissions" style="width: 100%">
        <el-table-column prop="id" label="提交ID" width="80"></el-table-column>
        <!-- 这里可能需要根据 studentId 调用后端接口获取学生姓名等信息 -->
        <el-table-column prop="studentId" label="学生ID"></el-table-column>
        <el-table-column prop="createTime" label="提交时间"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <!-- TODO: 添加查看提交详情、下载代码、查看结果图片、批改等操作 -->
            <el-button size="small">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <el-alert v-if="error" :title="error" type="error" class="mt-3" show-icon />
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { listSubmissionsByExperiment } from '../api/submission' // 假设你有这个API调用函数
  import { getExperimentById } from '../api/experiment' // 假设你有获取实验详情的API
  
  const route = useRoute()
  const experimentId = route.params.experimentId // 从路由参数获取实验ID
  const submissions = ref([])
  const experiment = ref(null)
  const error = ref('')
  
  const fetchSubmissions = async () => {
    if (!experimentId) {
      error.value = '未提供实验ID'
      return
    }
    try {
      // 调用后端接口获取提交列表
      const { data } = await listSubmissionsByExperiment(experimentId)
      if (data) { // 假设后端直接返回List<Submission>或者data字段包含列表
        submissions.value = data; // 根据后端实际返回结构调整
      } else {
         // 假设后端返回标准JSON格式，例如 { success: true, data: [...] }
         if (data.success) {
           submissions.value = data.data;
         } else {
           error.value = data.message || '获取提交列表失败';
         }
      }
  
    } catch (e) {
      console.error(e);
      error.value = '获取提交列表网络错误'
    }
  }
  
  const fetchExperimentDetails = async () => {
     if (!experimentId) {
       return
     }
     try {
       const { data } = await getExperimentById(experimentId);
       if (data) { // 假设后端直接返回Experiment对象或data字段包含对象
         experiment.value = data; // 根据后端实际返回结构调整
       }
     } catch (e) {
       console.error(e);
       // 错误处理
     }
  }
  
  
  onMounted(() => {
    fetchSubmissions()
    fetchExperimentDetails()
  })
  
  </script>
  
  <style scoped>
  /* 添加你的样式 */
  </style>