<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<back-button></back-button>
			<p>用户登陆</p>
		</header>

		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="title">
					手机号码：
				</div>
				<div class="content">
					<input type="text" v-model="userId" placeholder="手机号码">
				</div>
			</li>
			<li>
				<div class="title">
					密码：
				</div>
				<div class="content">
					<input type="password" v-model="password" placeholder="密码">
				</div>
			</li>
		</ul>

		<div class="button-login">
			<button @click="login">登陆</button>
		</div>
		<div class="button-register">
			<button @click="register">去注册</button>
		</div>

		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	import BackButton from '../components/BackButton.vue';	
	import CryptoJS from 'crypto-js';
	export default{
		name:'Login',
		data(){
			return {
				userId:'',
				password:'',
				userType:0,
				businessId:null
			}
		},
		mounted() {
  		window.scrollTo(0, 0);
		},
		methods:{
			login(){
				if(this.userId==''){
					alert('手机号码不能为空！');
					return;
				}
				if(this.password==''){
					alert('密码不能为空！');
					return;
				}
				const encryptedPassword =CryptoJS.MD5(this.password).toString();
				console.log("加密后的数据:", encryptedPassword);
				//登录请求
				this.$axios.post('UserLogin',this.$qs.stringify({
					userId:this.userId,
					password:encryptedPassword
				})).then(response=>{
					let user = response.data;
					console.log(user);
					if(!user){
						alert('用户名或密码不正确！');
						return;
					}else{
						//sessionstorage有容量限制，为了防止数据溢出，所以不将userImg数据放入session中
						localStorage.setItem('userAvatar', user.userImg);
						//user.userImg = '';
						console.log("userId："+user.userId);
						this.$setSessionStorage('user',user);
						this.businessId=user.userType;
						if(this.businessId!=null){
							this.$axios.post('Business',null,{
								params:{
								businessId:this.businessId,
								}
							}).then(response=>{
								let business = response.data;
								console.log(business);
								if(!business){
									alert('没有这个商家：'+this.businessId);
								}else{
									this.$setSessionStorage('business',business);
								}
							}).catch(error=>{
								console.error(error);
							});
						}
						this.$router.go(-1);
					}
				}).catch(error=>{
					console.error(error);
				});
				
			},
			register(){
				this.$router.push({path:'/register'});
			}
		},
		
		components:{
			Footer,BackButton
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

	/****************** 表单部分 ******************/
	.wrapper .form-box {
		width: 100%;
		margin-top: 12vw;
	}

	.wrapper .form-box li {
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;
		display: flex;
		align-items: center;
	}

	.wrapper .form-box li .title {
		flex: 0 0 18vw;
		font-size: 3vw;
		font-weight: 700;
		color: #666;
	}

	.wrapper .form-box li .content {
		flex: 1;
	}

	.wrapper .form-box li .content input {
		border: none;
		outline: none;
		width: 100%;
		height: 4vw;
		font-size: 3vw;
	}

	.wrapper .button-login {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;
	}

	.wrapper .button-login button {
		width: 100%;
		height: 10vw;
		font-size: 3.8vw;
		font-weight: 700;
		color: #fff;
		background-color: #38CA73;
		border-radius: 4px;

		border: none;
		outline: none;
	}

	.wrapper .button-register {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;
	}

	.wrapper .button-register button {
		width: 100%;
		height: 10vw;
		font-size: 3.8vw;
		font-weight: 700;
		/*与上面登陆按钮不同的只有颜色、背景色、边框不同*/
		color: #666;
		background-color: #EEE;
		border: solid 1px #DDD;
		border-radius: 4px;

		border: none;
		outline: none;
	}

	/****************** 底部菜单部分 ******************/
	.wrapper .footer {
		width: 100%;
		height: 14vw;
		border-top: solid 1px #DDD;
		background-color: #fff;

		position: fixed;
		left: 0;
		bottom: 0;

		display: flex;
		justify-content: space-around;
		align-items: center;
	}

	.wrapper .footer li {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;

		color: #999;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .footer li p {
		font-size: 2.8vw;
	}

	.wrapper .footer li i {
		font-size: 5vw;
	}
</style>
