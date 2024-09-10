import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import VueRouter from 'vue-router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'font-awesome/css/font-awesome.min.css'
import axios from 'axios'
import qs from 'qs'
import {
	getCurDate,
	setSessionStorage,
	getSessionStorage,
	removeSessionStorage,
	setLocalStorage,
	getLocalStorage,
	removeLocalStorage
} from './common.js'

axios.defaults.baseURL = 'http://localhost:8080/elm/';

const app = createApp(App)

// 将 axios、qs 和其他工具函数挂载到全局属性上
app.config.globalProperties.$axios = axios
app.config.globalProperties.$qs = qs

app.config.globalProperties.$getCurDate = getCurDate
app.config.globalProperties.$setSessionStorage = setSessionStorage
app.config.globalProperties.$getSessionStorage = getSessionStorage
app.config.globalProperties.$removeSessionStorage = removeSessionStorage
app.config.globalProperties.$setLocalStorage = setLocalStorage
app.config.globalProperties.$getLocalStorage = getLocalStorage
app.config.globalProperties.$removeLocalStorage = removeLocalStorage
// 路由守卫
router.beforeEach((to, from) => {
  let user = sessionStorage.getItem('user');
  // 除了登录、注册、首页、商家列表、商家信息之外，都需要判断是否登录
  if (!(to.path === '/' || to.path === '/index' || to.path === '/businessList' || to.path === '/businessInfo' || to.path === '/login' || to.path === '/register')) {
    if (user === null) {
    return '/login';
    }
  }
  if(to.path=='/businessFrom'&&user.userType==null){
	  return 'MyBusiness';
  }
  
  // 继续导航
  return true;
});
app.use(ElementPlus, { size: 'small', zIndex: 3000 })
app.use(router)
app.mount('#app')