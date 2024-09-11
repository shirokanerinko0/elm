<template>
	<div class="wrapper">
		<header>
			<BackButton></BackButton>
			<p>我的信息</p>
		</header>

		<div class="user-info">
			<h3>用户名： {{ this.user.userName }}</h3>
			<div class="avatar">
				<img :src="this.user.userImg || defaultAvatar" alt="用户头像" />
				<input type="file" @change="handleFileChange" ref="fileInput" style="display: none;" />
				<button class="upload-btn" @click="triggerFileInput">上传头像</button>
			</div>
			<button class="logout-btn" @click="logout">退出登录</button>

			<div>
				<button class="address-btn" @click="toAddress">管理配送地址</button>
			</div>
			<div>
				<button class="business-btn" @click="becomeBussiness">我的商家</button>
			</div>
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
	},
	mounted() {
		window.scrollTo(0, 0); // 滚动到顶部
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
			if (file.size > 2 * 1024 * 1024) {
				alert(`图片大小超过2MB，请选择更小的图片`);
				return;
			}
			const reader = new FileReader();
			reader.onloadend = () => {
				const base64Image = reader.result.split(',')[1];
				this.user.userImg = "data:image/png;base64," + base64Image;
				this.uploadAvatar(base64Image);
			};
			reader.readAsDataURL(file);
		},
		uploadAvatar(base64Image) {
			this.$axios
				.post('/UserImg', {
					userImg: "data:image/png;base64," + base64Image,
					userId: this.user.userId,
				})
				.then((response) => {
					this.user.userImg = "data:image/png;base64," + base64Image;
					this.$setSessionStorage('user', this.user);
					localStorage.setItem('userAvatar', "data:image/png;base64," + base64Image);
				})
				.catch((error) => {
					console.error('上传失败:', error);
				});
		},
		toAddress() {
			this.$router.push('/userAddress');
		},
		becomeBussiness() {
			this.$router.push('/businessFrom');
		},
	},
	components: {
		Footer,
		BackButton,
	},
};
</script>

<style scoped>
/* 页面布局与样式 */
.wrapper {
	width: 100%;
	height: 100%;
	background-color: #f5f5f5;
}

header {
	width: 100%;
	height: 12vw;
	background-color: #007bff;
	color: white;
	font-size: 4.8vw;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 1000;
	display: flex;
	align-items: center;
	justify-content: center;
}

.user-info {
	margin-top: 14vw;
	padding: 4vw;
	font-size: 4vw;
	color: #333;
	background-color: white;
	border-radius: 12px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	text-align: center;
}

.avatar {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.avatar img {
	width: 22vw;
	height: 22vw;
	border-radius: 50%;
	border: 3px solid #007bff;
	margin-bottom: 12px;
}

.upload-btn {
	font-size: 3.8vw;
	color: white;
	background-color: #007bff;
	border: none;
	border-radius: 8px;
	padding: 1.2vw 4vw;
	margin-top: 8px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.upload-btn:hover {
	background-color: #0056b3;
}

.logout-btn {
	font-size: 4vw;
	background-color: #dc3545;
	color: white;
	border: none;
	border-radius: 8px;
	padding: 1.5vw 5vw;
	margin-top: 12px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.logout-btn:hover {
	background-color: #c82333;
}

/* 按钮样式 */
.address-btn, .business-btn {
	font-size: 4vw;
	width: 80%;
	background-color: #28a745;
	color: white;
	border: none;
	border-radius: 8px;
	padding: 1.5vw 0;
	margin-top: 12px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.address-btn:hover, .business-btn:hover {
	background-color: #218838;
}
</style>
