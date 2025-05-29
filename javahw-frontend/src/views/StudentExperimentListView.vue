<template>
    <div class="container">
      <h2 class="mb-4">实验题目列表</h2>
      <div v-if="experiments.length > 0" class="row">
        <div class="col-md-6" v-for="exp in experimentsWithStatus" :key="exp.id">
          <ExperimentCard 
            :experiment="exp"
            :statusText="exp.statusText"
            :statusType="exp.statusType"
          >
            <router-link :to="`/experiment/${exp.id}`" class="btn btn-outline-primary btn-sm">查看/提交</router-link>
          </ExperimentCard>
        </div>
      </div>
      <div v-else>
          <p>暂无实验题目。</p>
      </div>
    </div>
  </template>
  <script setup>
  import { ref, onMounted, computed } from 'vue'
  import { getExperiments } from '../api/experiment'
  import ExperimentCard from '../components/ExperimentCard.vue'

  const experiments = ref([])

  const experimentsWithStatus = computed(() => {
      return experiments.value.map(exp => {
          const now = new Date();
          const deadline = new Date(exp.deadline);
          const isDeadlinePassed = exp.deadline && deadline < now;
          return {
              ...exp,
              statusText: isDeadlinePassed ? '已截止' : '进行中',
              statusType: isDeadlinePassed ? 'danger' : 'success'
          };
      });
  });

  onMounted(async () => {
    try {
      // Assuming getExperiments without teacherId fetches all experiments for student view
      const { data: experimentsData } = await getExperiments()
      experiments.value = experimentsData
    } catch (error) {
      console.error('Error fetching experiments:', error)
    }
  })
  </script> 
  <style scoped>
  /* Add any specific styles for this page if needed */
  </style> 