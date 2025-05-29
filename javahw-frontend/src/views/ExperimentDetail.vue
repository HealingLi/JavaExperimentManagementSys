<template>
    <div class="container">
      <el-button type="info" @click="$router.push('/student/experiments')">返回实验列表</el-button>
      <h2 class="mt-3">{{ experiment.name }}</h2>
      <p>{{ experiment.description }}</p>
      <p class="text-muted">截止时间：{{ experiment.deadline }}</p>
      <h4>提交代码</h4>
      <CodeEditor v-model="code" />
      <el-upload
        class="mt-2"
        :http-request="uploadZip"
        :show-file-list="false"
        accept=".zip"
      >
        <el-button type="primary">上传zip代码包</el-button>
      </el-upload>
      <h4 class="mt-4">上传运行结果</h4>
      <ResultUploader @uploaded="resultPath = $event" />
      <el-button class="mt-3" type="success" @click="submit" :disabled="isSubmittingDisabled">提交</el-button>
      <h4 class="mt-4">我的历史提交</h4>
      <div v-if="historicalSubmissions.length > 0">
        <div v-for="(submission, index) in historicalSubmissions" :key="submission.id" class="card mb-3">
          <div class="card-header">提交记录 {{ index + 1 }} (ID: {{ submission.id }}) - 提交时间: {{ submission.createTime }}</div>
          <div class="card-body">
            <h5>学生提交内容:</h5>
            <p><strong>代码文本:</strong></p>
            <pre>{{ submission.codeText }}</pre>
            <div v-if="submission.resultImg">
              <p><strong>结果图片:</strong></p>
              <img :src="backendBaseUrl + submission.resultImg" alt="结果图片" style="max-width: 400px;"/>
              <br>
              <a :href="backendBaseUrl + submission.resultImg" download>下载结果图片</a>
            </div>
             <div v-if="submission.codeZip">
               <p><strong>代码压缩包:</strong> <a :href="backendBaseUrl + submission.codeZip" download>下载代码压缩包</a></p>
            </div>

            <h5 class="mt-3">教师批改:</h5>
            <div v-if="submission.grading">
              <p><strong>分数:</strong> {{ submission.grading.grade }}</p>
              <p><strong>评语:</strong> {{ submission.grading.comment }}</p>
              <p class="text-muted">批改时间: {{ submission.grading.createTime }}</p>
            </div>
            <div v-else>
              <p>教师暂未批改。</p>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <p>您还没有针对此实验提交过作业。</p>
      </div>
    </div>
  </template>
  <script setup>
  import { ref, onMounted, computed } from 'vue'
  import { getExperimentById } from '../api/experiment'
  import { uploadCode } from '../api/upload'
  import CodeEditor from '../components/CodeEditor.vue'
  import ResultUploader from '../components/ResultUploader.vue'
  import { useRoute, useRouter } from 'vue-router'
  import { submitHomework, listMySubmissions } from '../api/submission'
  import { getGradingBySubmission } from '../api/grading'
  const route = useRoute()
  const router = useRouter()
  const experiment = ref({})
  const code = ref('')
  const resultPath = ref('')
  const zipPath = ref('')
  const isSubmittingDisabled = ref(true);
  const isZipUploadComplete = ref(false);
  const isResultUploadComplete = ref(false);
  const historicalSubmissions = ref([]);
  const backendBaseUrl = 'http://localhost:8080';

  const isSubmitEnabled = computed(() => {
      const zipNeeded = zipPath.value !== '';
      const resultNeeded = resultPath.value !== '';

      if (!zipNeeded && !resultNeeded) {
          return true;
      }
      if (zipNeeded && !resultNeeded) {
          return isZipUploadComplete.value;
      }
      if (!zipNeeded && resultNeeded) {
          return isResultUploadComplete.value;
      }
      if (zipNeeded && resultNeeded) {
          return isZipUploadComplete.value && isResultUploadComplete.value;
      }
      return false;
  });

  onMounted(async () => {
    const experimentId = route.params.id;
    
    try {
      const { data } = await getExperimentById(experimentId)
      experiment.value = data
    } catch (error) {
      console.error('Error fetching experiment details:', error)
    }

    const student = JSON.parse(localStorage.getItem('student') || '{}');
    if (student && student.id) {
      try {
        const { data: allSubmissions } = await listMySubmissions(student.id);
        const experimentSubmissions = allSubmissions.filter(sub => sub.experimentId == experimentId);
        
        historicalSubmissions.value = await Promise.all(experimentSubmissions.map(async sub => {
          try {
            const { data: gradingData } = await getGradingBySubmission(sub.id);
            if (gradingData && gradingData.length > 0) {
              sub.grading = gradingData[0];
            } else {
              sub.grading = null;
            }
          } catch (error) {
            console.error(`Error fetching grading for submission ${sub.id}:`, error);
            sub.grading = { error: '无法加载批改信息' };
          }
          return sub;
        }));

      } catch (error) {
        console.error('Error fetching historical submissions:', error)
      }
    }
  })
  const uploadZip = async ({ file }) => {
    isSubmittingDisabled.value = true;
    isZipUploadComplete.value = false;
    const { data } = await uploadCode(file)
    if (data.success) {
      alert('上传成功')
      zipPath.value = data.path
      isZipUploadComplete.value = true;
    } else {
      alert(data.message)
      isZipUploadComplete.value = false;
      zipPath.value = '';
    }
     isSubmittingDisabled.value = !isSubmitEnabled.value;
  }
  const submit = async () => {
    console.log('Submitting with zipPath:', zipPath.value);
    console.log('Submitting with resultPath:', resultPath.value);

    const currentCodeText = code.value;
    const currentCodeZip = zipPath.value;
    const currentResultImg = resultPath.value;

    const submissionData = {
      experimentId: experiment.value.id,
      codeText: currentCodeText,
      codeZip: currentCodeZip,
      resultImg: currentResultImg
    };
    console.log('Data sent to submitHomework:', submissionData);

    const res = await submitHomework(submissionData);
    if (res.data.success) {
      alert('提交成功')
      const student = JSON.parse(localStorage.getItem('student') || '{}');
       if (student && student.id) {
         const experimentId = route.params.id;
         try {
           const { data: allSubmissions } = await listMySubmissions(student.id);
           const experimentSubmissions = allSubmissions.filter(sub => sub.experimentId == experimentId);
           historicalSubmissions.value = await Promise.all(experimentSubmissions.map(async sub => {
             try {
               const { data: gradingData } = await getGradingBySubmission(sub.id);
                if (gradingData && gradingData.length > 0) {
                  sub.grading = gradingData[0];
                } else {
                  sub.grading = null;
                }
             } catch (error) {
               console.error(`Error fetching grading for submission ${sub.id}:`, error);
                sub.grading = { error: '无法加载批改信息' };
             }
             return sub;
           }));
         } catch (error) {
           console.error('Error refreshing historical submissions:', error);
         }
       }
    } else {
      alert(res.data.message)
    }
     isSubmittingDisabled.value = false;
  }
  const handleResultUploaded = (path) => {
      resultPath.value = path;
      isResultUploadComplete.value = true;
      isSubmittingDisabled.value = !isSubmitEnabled.value;
  };
  </script>
  <style scoped>
  pre {
    white-space: pre-wrap;
    word-wrap: break-word;
  }
  </style>