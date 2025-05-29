<template>
  <el-input
    type="textarea"
    :rows="12"
    :model-value="modelValue"
    @update:modelValue="val => $emit('update:modelValue', val)"
    placeholder="在此粘贴你的Java代码"
    style="font-family: 'Fira Mono', 'Consolas', monospace;"
  />
</template>
<script setup>
defineProps(['modelValue'])
defineEmits(['update:modelValue'])
</script>