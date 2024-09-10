<template>
	<div class="wrapper">
		<header>
			<BackButton></BackButton>
			<p>我的信息</p>
		</header>
		

		<div class="user-info">
			<h3 >用户名： {{ this.user.userName }}</h3>
			<div class="avatar">
				<img :src="this.user.userImg || defaultAvatar" alt="用户头像" />
				<input type="file" @change="handleFileChange" ref="fileInput" style="display: none;" />
				<button @click="triggerFileInput" style="font-size: 5vw;">上传头像</button>
			</div>
			<button class="layoutButton" @click="logout">退出登录</button>

			<div>
				<button class="addButton" @click="toAddress">管理配送地址</button>	
			</div>
			<div><button class="addButton2" @click="becomeBussiness">我的商家</button></div>
		</div>
		

		<Footer></Footer>
	</div>
</template>

<script>
import Footer from '../components/Footer.vue';
import BackButton from '../components/BackButton.vue';
import defaultAvatarPath from '../assets/dcfl01.png'; // 引入默认头像

export default {
	name: 'MyUser',
	data() {
		return {
			user: {},
			defaultAvatar: defaultAvatarPath,
		};
	},
	created() {
		this.user = this.$getSessionStorage('user');
		this.user.userImg = localStorage.getItem('userAvatar') ||this.user.userImg||'';
	},
	mounted() {
	  window.scrollTo(0, 0);  // 滚动到顶部
	},
	methods: {
		logout() {
			sessionStorage.removeItem('user');
			sessionStorage.removeItem('business');
			this.$router.go(-1);
		},
		triggerFileInput() {
			this.$refs.fileInput.click();
		},
		handleFileChange(event) {
			const file = event.target.files[0];
			if (!file) return;

			const reader = new FileReader();
			reader.onloadend = () => {
				const base64Image = reader.result.split(',')[1];
				this.user.userImg="data:image/png;base64,"+base64Image;
				this.uploadAvatar(base64Image);
			};
			reader.readAsDataURL(file);
		},
		uploadAvatar(base64Image) {
			this.$axios.post('/UserImgUpdate',{ 
			userImg:"data:image/png;base64,"+base64Image,
			userId:this.user.userId
				}).then(response => {
					this.user.userImg = response.data.userImg;
					this.$setSessionStorage('user', this.user);
					localStorage.setItem('userAvatar', response.data.userImg);
				})
				.catch(error => {
					console.error('上传失败:', error);
				});
		},
		toAddress() {
    		// 使用 Vue Router 导航到新的页面
    		this.$router.push('/userAddress'); 
        },
		becomeBussiness(){
			this.$router.push('/businessFrom');
		}
		
	},
	components: {
		Footer, BackButton
	}
}
</script>

<style scoped>
.layoutButton {
	font-size: 5vw;
	position: absolute;
	bottom: 16vw;
	left: 50%;
	transform: translateX(-50%);
	display: flex;
	justify-content: center;
	align-items: center;
}
.wrapper {

	width: 100%;
	height: 100%;
}
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
.wrapper .user-info {
	margin-top: 12vw;
	padding: 4vw;
	font-size: 4vw;
	font-weight: 300;
	color: black;
}
.wrapper .user-info .avatar {
	text-align: center;
}
.wrapper .user-info .avatar img {
	width: 20vw;
	height: 20vw;
	border-radius: 50%;
}
/****************** 管理地址按钮 ******************/
.addButton, .addButton2 {
    font-size: 4vw;
    position: absolute;  /* 确保按钮根据父容器定位 */
    left: 50%;           /* 将按钮水平居中 */
    transform: translateX(-50%);  /* 精确居中 */
    display: flex;      /* 使按钮内的内容居中 */
    justify-content: center;
    align-items: center;
    padding: 10px 20px; /* 按钮内边距 */
}
.addButton{
	top: 100vw;
}
.addButton2{
	top: 60vw;
}
</style>
