<template>
  <div class="container mt-4">
    <el-button type="info" @click="$router.back()">返回实验管理</el-button>
    
    <h1 class="mt-3">编辑题目</h1>

    <div v-if="loading">
      <p>加载中...</p>
    </div>
    <div v-else-if="currentExperiment.id">
      <el-form :model="currentExperiment" label-width="100px">
        <el-form-item label="题目名称">
          <el-input v-model="currentExperiment.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="currentExperiment.description" type="textarea" />
        </el-form-item>
        <el-form-item label="截止时间">
           <el-date-picker v-model="currentExperiment.deadline" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" />
        </el-form-item>

        <el-form-item>
          <el-button type="success" @click="saveExperiment">保存</el-button>
          <el-button type="info" @click="cancelEditing">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-else>
      <p>加载实验信息失败或实验不存在。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getExperimentById, updateExperiment } from '../api/experiment';
import { ElButton, ElForm, ElFormItem, ElInput, ElDatePicker, ElMessage } from 'element-plus';
import 'element-plus/dist/index.css';

const route = useRoute();
const router = useRouter();
const currentExperiment = ref({});
const loading = ref(true);

onMounted(async () => {
  const experimentId = route.params.id;
  if (!experimentId) {
    loading.value = false;
    ElMessage.error('未提供实验ID。');
    return;
  }

  try {
    const { data } = await getExperimentById(experimentId);
    if (data) {
       // Format deadline for date picker if necessary (assuming ISO string from backend)
       currentExperiment.value = { 
         ...data, 
         deadline: data.deadline ? new Date(data.deadline) : null
      }; 
    } else {
        ElMessage.error('未找到实验信息。');
    }
  } catch (error) {
    console.error('Error fetching experiment:', error);
    ElMessage.error('加载实验信息失败！');
  } finally {
    loading.value = false;
  }
});

const saveExperiment = async () => {
   // Format deadline back to ISO string for backend
   const dataToSave = { 
       ...currentExperiment.value, 
       deadline: currentExperiment.value.deadline ? new Date(currentExperiment.value.deadline).toISOString() : null 
   };

  try {
    const response = await updateExperiment(dataToSave);
    if (response.data.success) {
      ElMessage.success('实验更新成功！');
      router.push('/teacher/experiments');  
    } else {
      ElMessage.error('实验更新失败: ' + response.data.message);
    }
  } catch (error) {
    console.error('Error updating experiment:', error);
    ElMessage.error('实验更新失败！');
  }
};

const cancelEditing = () => {
  router.push('/teacher/experiments');
};
</script>

<style scoped>
/* Add specific styles if needed */
</style> 