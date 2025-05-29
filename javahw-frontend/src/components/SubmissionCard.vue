<template>
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h6 class="card-title">提交人：{{ submission.studentName }}</h6>
        <p class="card-text">代码：<a :href="submission.codePath" target="_blank">下载</a></p>
        <p class="card-text">运行结果：<img :src="submission.resultPath" alt="运行结果" style="max-width:200px;" v-if="submission.resultPath" /></p>
        <slot></slot>
      </div>
    </div>
  </template>
  <script setup>
  defineProps(['submission'])
  </script>