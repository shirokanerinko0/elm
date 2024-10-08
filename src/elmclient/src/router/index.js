import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/Index.vue'
import BusinessList from '../views/BusinessList.vue'
import BusinessInfo from '../views/BusinessInfo.vue'
import Login from '../views/Login.vue'
import Orders from '../views/Orders.vue'
import UserAddress from '../views/UserAddress.vue'
import Payment from '../views/Payment.vue'
import OrderList from '../views/OrderList.vue'
import AddUserAddress from '../views/AddUserAddress.vue'
import EditUserAddress from '../views/EditUserAddress.vue'
import Register from '../views/Register.vue'
import MyUser from '../views/MyUser.vue'
import Search from '../views/Search.vue'
import MyBusiness from '../views/MyBusiness.vue'
import BusinessFrom from '../views/BusinessFrom.vue'
import FoodInfo from '../views/FoodInfo.vue'
import FoodList from '../views/FoodList.vue'
import NewFood from '../views/NewFood.vue'
const routes = [{
		path: '/',
		name: 'Home',
		component: Index
	}, {
		path: '/index',
		name: 'Index',
		component: Index
	}, {
		path: '/businessList',
		name: 'BusinessList',
		component: BusinessList
	}, {
		path: '/businessInfo',
		name: 'BusinessInfo',
		component: BusinessInfo
	}, {
		path: '/login',
		name: 'Login',
		component: Login
	}, {
		path: '/orders',
		name: 'Orders',
		component: Orders
	}, {
		path: '/userAddress',
		name: 'UserAddress',
		component: UserAddress
	}, {
		path: '/payment',
		name: 'Payment',
		component: Payment
	}, {
		path: '/orderList',
		name: 'OrderList',
		component: OrderList
	}, {
		path: '/addUserAddress',
		name: 'AddUserAddress',
		component: AddUserAddress
	}, {
		path: '/editUserAddress',
		name: 'EditUserAddress',
		component: EditUserAddress
	}, {
		path: '/register',
		name: 'Register',
		component: Register
	}, {
		path: '/myUser',
		name: 'MyUser',
		component: MyUser
	}, {
		path: '/search',
		name: 'Search',
		component: Search
	},{
		path:'/myBusiness',
		name:'MyBusiness',
		component: MyBusiness
	},{
		path:'/businessFrom',
		name:'BusinessFrom',
		component: BusinessFrom
	},{
		path:'/foodInfo',
		name:'FoodInfo',
		component: FoodInfo
	},{
		path:'/foodList',
		name:'FoodList',
		component: FoodList
	},{
		path:'/newFood',
		name:'NewFood',
		component: NewFood
	}

]


// //解决重复路由报异常问题
// const originalPush = VueRouter.prototype.push;
// VueRouter.prototype.push = function push(location) {
// 	return originalPush.call(this, location).catch(err => err)
// }

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
