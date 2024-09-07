<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<BackButton></BackButton>
			<p>我的信息</p>
		</header>
		<h3>用户名： {{this.user.userId}}</h3>
	<button class="layoutButton" @click="logout">退出登录</button>
	<!-- 底部菜单部分 -->
	<Footer></Footer>
	</div> 
</template>

<script>
	import Footer from '../components/Footer.vue';
	import BackButton from '../components/BackButton.vue';		
	export default{
		name:'MyUser',
		data(){
			return {
				user:{},
				userId:{}
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
		},
		methods:{
			logout(){
				sessionStorage.removeItem('user');
				this.$router.go(-1);
			}
		},
		components:{
			Footer,BackButton
		}
	}	
</script>

<style scoped>
	.layoutButton {
	  font-size: 5vw;
	  position: absolute;
	  bottom: 16vw;       /* 距离底部 14vw */
	  left: 50%;          /* 将按钮水平居中 */
	  transform: translateX(-50%);  /* 精确居中 */
	  display: flex;       /* 使按钮内的内容居中 */
	  justify-content: center;
	  align-items: center;
	}
	/****************** 总容器 ******************/
	.wrapper {
		width: 100%;
		height: 100%;
	}
	/****************** header部分 ******************/
	.wrapper header {
		width: 100%;
		height: 12vw;
		background-color: #0097FF;
		color: #fff;
		font-size: 4.8vw;

		position: fixed;
		left: 0;
		top: 0;
		z-index: 1000;

		display: flex;
		justify-content: center;
		align-items: center;
	}
	.wrapper h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw;
		font-size: 4vw;
		font-weight: 300;
		color: black;
	}
</style>