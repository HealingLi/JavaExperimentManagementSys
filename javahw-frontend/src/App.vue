<template>
  <div>
    <!-- Removed Navbar -->
    <router-view />
  </div>
</template>
<script setup>
// Removed import Navbar from './components/Navbar.vue'
</script>
<style>
body {
  background-image: url('/background.png'); /* 引用 public 目录下的背景图片 */
  background-size: cover; /* 覆盖整个屏幕 */
  background-repeat: no-repeat; /* 不重复 */
  background-attachment: fixed; /* 固定背景，不随滚动条移动 */
  font-family: 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Arial', sans-serif;
  margin: 0; /* 移除默认的body外边距 */
  padding: 0; /* 移除默认的body内边距 */
}
</style>