<template>
  <div class="experiment-management-container">
    <el-button type="info" @click="$router.push('/teacher/dashboard')">返回教师主页</el-button>
    <h1 class="mt-3">实验管理</h1>
    
    <el-button type="primary" @click="showAddDialog = true">新增题目</el-button>

    <!-- 实验列表将在这里显示 -->
    <div class="experiment-list row">
      <div class="col-md-6" v-for="experiment in experimentsWithStatus" :key="experiment.id">
        <!-- 使用 ExperimentCard 组件 -->
        <ExperimentCard 
          :experiment="experiment"
          :statusText="experiment.statusText"
          :statusType="experiment.statusType"
        >
          <!-- 将教师端操作按钮放入 slot -->
          <el-button size="small" @click="editExperiment(experiment)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteExperiment(experiment.id)">删除</el-button>
          <el-button size="small" @click="viewSubmissions(experiment.id)">查看提交</el-button>
        </ExperimentCard>
      </div>
    </div>

    <!-- 新增题目对话框 -->
    <el-dialog v-model="showAddDialog" title="新增题目">
      <el-form :model="newExperimentForm">
        <el-form-item label="题目名称">
          <el-input v-model="newExperimentForm.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="newExperimentForm.description" type="textarea" />
        </el-form-item>
        <el-form-item label="截止时间">
          <el-date-picker v-model="newExperimentForm.deadline" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="addExperiment">确定</el-button>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import { getExperiments, deleteExperiment, updateExperiment, addExperiment as apiAddExperiment } from '../api/experiment';
import { ElButton, ElTable, ElTableColumn, ElDialog, ElForm, ElFormItem, ElInput, ElDatePicker, ElMessage } from 'element-plus';
import 'element-plus/dist/index.css';
import ExperimentCard from '../components/ExperimentCard.vue';
import { computed } from 'vue'; // Import computed

export default {
  name: 'ExperimentManagementView',
  components: {
    ElButton,
    ElTable,
    ElTableColumn,
    ElDialog,
    ElForm,
    ElFormItem,
    ElInput,
    ElDatePicker,
    ExperimentCard,
    ElMessage,
  },
  data() {
    return {
      experiments: [],
      editingExperimentId: null,
      currentExperiment: {},
      showAddDialog: false,
      newExperimentForm: { name: '', description: '', deadline: '' },
      teacherId: null,
    };
  },
  created() {
    const teacher = JSON.parse(localStorage.getItem('teacher') || '{}');
    if (teacher && teacher.id) {
        this.teacherId = teacher.id;
        this.fetchExperiments();
    } else {
        console.error('Teacher not logged in or ID missing');
    }
  },
   computed: { // Add computed property for experiments with status
      experimentsWithStatus() {
          return this.experiments.map(exp => {
              const now = new Date();
              const deadline = new Date(exp.deadline);
              const isDeadlinePassed = exp.deadline && deadline < now;
              return {
                  ...exp,
                  statusText: isDeadlinePassed ? '已截止' : '进行中',
                  statusType: isDeadlinePassed ? 'danger' : 'success'
              };
          });
      }
  },
  methods: {
    fetchExperiments() {
      getExperiments({ teacherId: this.teacherId })
        .then(response => {
          if (response.data) {
            this.experiments = response.data.map(exp => ({
                ...exp,
                // Adjust date formatting if necessary
                deadline: exp.deadline ? exp.deadline.replace('Z', '') : '' // Example adjustment
            }));
          }
        })
        .catch(error => {
          console.error('Error fetching experiments:', error);
          ElMessage.error('加载实验列表失败！');
        });
    },
    editExperiment(experiment) {
      this.$router.push(`/teacher/experiments/${experiment.id}/edit`);
    },
    deleteExperiment(id) {
      if (confirm('确定删除该实验吗？')) {
        deleteExperiment(id)
          .then(() => {
            ElMessage.success('实验删除成功！');
            this.fetchExperiments();
          })
          .catch(error => {
            console.error('Error deleting experiment:', error);
            ElMessage.error('删除失败！');
          });
      }
    },
    viewSubmissions(experimentId) {
      this.$router.push(`/teacher/experiments/${experimentId}/submissions`);
    },
    addExperiment() {
        const dataToAdd = {
            ...this.newExperimentForm,
            teacherId: this.teacherId,
            deadline: this.newExperimentForm.deadline ? new Date(this.newExperimentForm.deadline).toISOString() : null
        };
        apiAddExperiment(dataToAdd)
            .then(response => {
                if (response.data.success) {
                    ElMessage.success('实验新增成功！');
                    this.showAddDialog = false;
                    this.newExperimentForm = { name: '', description: '', deadline: '' };
                    this.fetchExperiments();
                } else {
                    ElMessage.error('实验新增失败: ' + response.data.message);
                }
            })
            .catch(error => {
                console.error('Error adding experiment:', error);
                ElMessage.error('实验新增失败！');
            });
    }
  }
}
</script>

<style scoped>
/* 组件的样式将在这里添加 */
.experiment-management-container {
  max-width: 800px; /* 设置最大宽度 */
  margin: 0 auto; /* 居中 */
  padding: 20px; /* 添加内边距 */
}

/* 移除 ExperimentCard 旁边的编辑表单的样式 */
.card.mb-3.shadow-sm.p-3 {
  display: none;
}
</style> 