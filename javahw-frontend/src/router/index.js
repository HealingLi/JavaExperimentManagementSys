import { createRouter, createWebHistory } from 'vue-router'
import StudentHome from '../views/StudentHome.vue'
import TeacherHome from '../views/TeacherHome.vue'
import ExperimentDetail from '../views/ExperimentDetail.vue'
import NotFound from '../views/NotFound.vue'
import ExperimentManagementView from '../views/ExperimentManagementView.vue'
import SubmissionListView from '../views/SubmissionListView.vue'
import GradingView from '../views/GradingView.vue'
import ExperimentEditView from '../views/ExperimentEditView.vue'
import WelcomeView from '../views/WelcomeView.vue'
import StudentExperimentListView from '../views/StudentExperimentListView.vue'
import StudentSubmissionHistoryView from '../views/StudentSubmissionHistoryView.vue'
import SubmissionsToGradeView from '../views/SubmissionsToGradeView.vue'
import TeacherDashboardView from '../views/TeacherDashboardView.vue'
import AuthPage from '../views/AuthPage.vue'

const routes = [
  { path: '/', component: WelcomeView },
  { path: '/auth/:role', component: AuthPage },
  {
    path: '/student',
    component: StudentHome,
    children: [
      { path: '', redirect: '/student/experiments' },
      { path: 'experiments', component: StudentExperimentListView },
      { path: 'submissions', component: StudentSubmissionHistoryView }
    ]
  },
  {
    path: '/teacher',
    component: TeacherHome,
    children: [
      { path: '', redirect: '/teacher/dashboard' },
      { path: 'dashboard', component: TeacherDashboardView },
      { path: 'experiments', component: ExperimentManagementView },
      { path: 'submissions-to-grade', component: SubmissionsToGradeView },
    ]
  },
  { path: '/experiment/:id', component: ExperimentDetail },
  { path: '/teacher/experiments/:id/edit', component: ExperimentEditView },
  { path: '/teacher/experiments/:experimentId/submissions', component: SubmissionListView },
  { path: '/teacher/submissions/:submissionId/grade', component: GradingView },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isTeacherPath = to.path.startsWith('/teacher');
  const isStudentPath = to.path.startsWith('/student');
  const isAuthPath = to.path.startsWith('/auth');
  const isWelcomePath = to.path === '/';

  const teacherLoggedIn = localStorage.getItem('teacher');
  const studentLoggedIn = localStorage.getItem('student');

  if (isTeacherPath && !teacherLoggedIn) {
    // Redirect teacher to teacher login if not logged in
    next({ path: '/auth/teacher', query: { redirect: to.fullPath } });
  } else if (isStudentPath && !studentLoggedIn) {
    // Redirect student to student login if not logged in
    next({ path: '/auth/student', query: { redirect: to.fullPath } });
  } else if (isAuthPath && (teacherLoggedIn || studentLoggedIn)) {
    // If on an auth page and already logged in, redirect to respective home page
    if (teacherLoggedIn) {
      next('/teacher');
    } else if (studentLoggedIn) {
      next('/student');
    } else {
        // Should not happen based on the condition, but as a fallback
        next('/');
    }
  } else if (isWelcomePath && (teacherLoggedIn || studentLoggedIn)) {
      // If on welcome page and already logged in, redirect to respective home page
       if (teacherLoggedIn) {
        next('/teacher');
      } else if (studentLoggedIn) {
        next('/student');
      }
  } else {
    // Allow access to other routes (welcome, auth pages when not logged in, or protected pages when logged in)
    next();
  }
});

export default router