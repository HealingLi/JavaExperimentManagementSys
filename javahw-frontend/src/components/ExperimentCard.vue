<template>
  <el-card class="experiment-card">
    <template #header>
      <div class="card-header-flex">
        <div class="status-tag">
          <el-tag :type="statusType">{{ statusText }}</el-tag>
        </div>
        <h3 class="card-title">{{ experiment.name }}</h3>
      </div>
    </template>
    <div class="card-content">
      <p class="card-text">{{ experiment.description }}</p>
      <div class="deadline">
        <el-icon><Clock /></el-icon>
        <span>截止时间：{{ formattedDeadline }}</span>
      </div>
    </div>
    <div class="card-actions">
      <slot></slot>
    </div>
  </el-card>
</template>

<script setup>
import { defineProps, computed } from 'vue';
import { Clock } from '@element-plus/icons-vue'; // Import the Clock icon
import { ElCard, ElTag, ElIcon } from 'element-plus'; // Import necessary components
import 'element-plus/dist/index.css';

const props = defineProps({
  experiment: Object,
  statusText: String,
  statusType: { // e.g., 'success', 'warning', 'danger', 'info'
    type: String,
    default: 'info'
  }
});

const formattedDeadline = computed(() => {
  if (!props.experiment || !props.experiment.deadline) return '无截止时间';
  // Assuming deadline is a valid date string
  const date = new Date(props.experiment.deadline);
  return date.toLocaleString(); // Or format as needed
});
</script>

<style scoped>
.experiment-card {
  margin-bottom: 20px;
}
.card-header-flex {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.card-title {
  font-size: 1.2em;
  font-weight: bold;
  margin: 0;
}
.card-content p {
  margin-bottom: 10px;
}
.deadline {
  display: flex;
  align-items: center;
  color: #606266;
  font-size: 0.9em;
}
.deadline .el-icon {
  margin-right: 5px;
}
.card-actions {
  margin-top: 15px;
  text-align: right; /* Align actions to the right */
}
</style>