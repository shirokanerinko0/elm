<template>
	<div class="wrapper">
	  <!-- header部分，页面顶部的导航栏，包含返回按钮和标题 -->
	  <header>
		<BackButton></BackButton> <!-- 引入了返回按钮组件 -->
		<p>商家列表</p> <!-- 标题显示为“商家列表” -->
	  </header>
  
	  <!-- 商家列表部分，使用ul显示商家列表 -->
	  <ul class="business">
		<!-- 使用v-for指令循环遍历businessArr数组，并渲染商家列表 -->
		<!-- 点击每个商家列表项时，调用toBusinessInfo方法并传递businessId -->
		<li v-for="item in businessArr" @click="toBusinessInfo(item.businessId)">
		  <!-- 商家图片部分 -->
		  <div class="business-img">
			<img :src="item.businessImg"> <!-- 绑定商家的图片地址 -->
			<!-- 如果该商家的quantity大于0，则显示一个红色的小圆圈，表示有多少商品 -->
			<div class="business-img-quantity" v-show="item.quantity > 0">{{item.quantity}}</div>
		  </div>
		  <!-- 商家信息部分，显示商家的名称、起送价格、配送价格和说明 -->
		  <div class="business-info">
			<h3>{{item.businessName}}</h3> <!-- 商家名称 -->
			<p>&#165;{{item.starPrice}}起送 | &#165;{{item.deliveryPrice}}配送</p> <!-- 起送价和配送费 -->
			<p>{{item.businessExplain}}</p> <!-- 商家说明 -->
		  </div>
		</li>
	  </ul>
	  <!-- 用于占位的空白区域，保证底部菜单部分显示正确 -->
	  <div class="blank" style="height: 5vw"></div>
  
	  <!-- 底部菜单部分，引入了Footer组件 -->
	  <Footer></Footer>
	</div>
  </template>
  
  <script>
	// 导入Footer和BackButton组件，用于页面中
	import Footer from '../components/Footer.vue';
	import BackButton from '../components/BackButton.vue';
  
	export default {
	  name: 'BusinessList', // 组件名为BusinessList
	  data() {
		return {
		  orderTypeId: this.$route.query.orderTypeId, // 从URL的query参数中获取orderTypeId
		  businessArr: [], // 存储商家信息的数组
		  user: {} // 存储用户信息
		};
	  },
	  created() {
		// 在组件创建时从sessionStorage中获取存储的用户信息
		this.user = this.$getSessionStorage('user');
  
		// 向服务器发送POST请求，根据orderTypeId获取商家信息
		this.$axios.post('Businesses', this.$qs.stringify({
		  orderTypeId: this.orderTypeId // 将orderTypeId作为请求参数发送给后端
		})).then(response => {
		  this.businessArr = response.data; // 将后端返回的数据赋值给businessArr
		  // 如果用户已登录，调用listCart方法获取购物车信息
		  if (this.user != null) {
			this.listCart();
		  }
		}).catch(error => {
		  console.error(error); // 捕获并打印请求错误
		});
	  },
	  components: {
		Footer, // 注册Footer组件
		BackButton // 注册BackButton组件
	  },
	  methods: {
		// listCart方法，用于获取购物车信息，并更新每个商家的商品数量
		listCart() {
		  // 向服务器发送POST请求，根据用户ID获取购物车信息
		  this.$axios.post('Carts', this.$qs.stringify({
			userId: this.user.userId // 将userId作为请求参数发送给后端
		  })).then(response => {
			let cartArr = response.data; // 获取购物车数据
			// 遍历商家列表，初始化每个商家的商品数量为0
			for (let businessItem of this.businessArr) {
			  businessItem.quantity = 0;
			  // 遍历购物车数据，如果商家ID匹配，则累加商品数量
			  for (let cartItem of cartArr) {
				if (cartItem.businessId == businessItem.businessId) {
				  businessItem.quantity += cartItem.quantity;
				}
			  }
			}
			// 对商家数组进行排序
			this.businessArr.sort();
		  }).catch(error => {
			console.error(error); // 捕获并打印请求错误
		  });
		},
		// toBusinessInfo方法，用于跳转到商家详情页，并传递商家ID作为query参数
		toBusinessInfo(businessId) {
		  this.$router.push({ path: '/businessInfo', query: { businessId: businessId } });
		}
	  }
	}
  </script>
  
  <style scoped>
	/****************** 总容器 ******************/
	.wrapper {
	  width: 100%;
	  height: 100%;
	}
  
	/****************** header部分 ******************/
	.wrapper header {
	  width: 100%;
	  height: 12vw;
	  background-color: #0097FF; /* 设置背景色为蓝色 */
	  color: #fff; /* 设置文字颜色为白色 */
	  font-size: 4.8vw; /* 设置字体大小 */
	  position: fixed; /* 固定在页面顶部 */
	  left: 0;
	  top: 0;
	  z-index: 1000; /* 设置z-index保证其显示在最上层 */
	  display: flex;
	  justify-content: center; /* 水平居中 */
	  align-items: center; /* 垂直居中 */
	}
  
	/****************** 商家列表部分 ******************/
	.wrapper .business {
	  width: 100%;
	  margin-top: 12vw; /* 商家列表距离页面顶部12vw */
	  margin-bottom: 14vw; /* 商家列表距离底部14vw */
	}
  
	.wrapper .business li {
	  width: 100%;
	  box-sizing: border-box; /* 使用box-sizing保证padding不影响元素大小 */
	  padding: 2.5vw; /* 设置内边距 */
	  border-bottom: solid 1px #DDD; /* 设置下边框颜色为浅灰色 */
	  user-select: none; /* 禁止用户选中列表内容 */
	  cursor: pointer; /* 鼠标悬浮时显示为手型光标 */
	  display: flex; /* 使用flexbox布局 */
	  align-items: center; /* 垂直居中 */
	}
  
	.wrapper .business li .business-img {
	  position: relative; /* 设置相对定位，便于定位数量小圆圈 */
	}
  
	.wrapper .business li .business-img img {
	  width: 20vw; /* 设置图片宽度 */
	  height: 20vw; /* 设置图片高度 */
	}
  
	.wrapper .business li .business-img .business-img-quantity {
	  width: 5vw;
	  height: 5vw;
	  background-color: red; /* 设置小圆圈的背景色为红色 */
	  color: #fff; /* 设置文字颜色为白色 */
	  font-size: 3.6vw; /* 设置字体大小 */
	  border-radius: 2.5vw; /* 设置边框半径，形成圆形 */
	  display: flex;
	  justify-content: center;
	  align-items: center; /* 垂直和水平居中 */
	  position: absolute; /* 设置绝对定位 */
	  right: -1.5vw; /* 距离右边1.5vw */
	  top: -1.5vw; /* 距离顶部1.5vw */
	}
  
	.wrapper .business li .business-info {
	  margin-left: 3vw; /* 设置商家信息距离图片3vw */
	}
  
	.wrapper .business li .business-info h3 {
	  font-size: 3.8vw; /* 设置商家名称的字体大小 */
	  color: #555; /* 设置文字颜色为深灰色 */
	}
  
	.wrapper .business li .business-info p {
	  font-size: 3vw; /* 设置描述文字的字体大小 */
	  color: #888; /* 设置文字颜色为浅灰色 */
	  margin-top: 2vw; /* 设置段落与标题之间的距离 */
	}
  </style>
  