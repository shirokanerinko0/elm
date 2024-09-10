<template>
	<div class="wrapper">
		<header>
			<BackButton></BackButton>
			<p>我的商家</p>
		</header>
		<div class="user-info">
			<h3 >商家名称： {{ this.business.businessName }}</h3>
			<div class="avatar">
				<img :src="this.business.businessImg || defaultAvatar" alt="商家头像" />
				<input type="file" @change="handleFileChange" ref="fileInput" style="display: none;" />
				<button @click="triggerFileInput" style="font-size: 5vw;">上传头像</button>
			</div>
商家类型<select id="tShirtSize" v-model="orderTypeId" style="font-size: 4vw; width: 50vw;height: 6vw;" def>
                        <option value="1">美食</option>
                        <option value="2">早餐</option>
						<option value="3">跑腿代购</option>
						<option value="4">披萨汉堡</option>
						<option value="5">甜品饮料</option>
						<option value="6">速食简餐</option>
						<option value="7">地方小吃</option>
						<option value="8">米粉面馆</option>
						<option value="9">包子粥铺</option>
						<option value="10">炸鸡炸串</option>
                </select>
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
			business:{},
			foodArr:[],
			orderTypeId:1,
		};
	},
	created() {
		this.user = this.$getSessionStorage('user');
		this.business = this.$getSessionStorage('business');
		console.log('Business:', this.business);
        console.log('Type of business:', typeof this.business);
		this.orderTypeId = this.business.orderTypeId;
		console.log(this.business);
	},
	mounted() {
	  window.scrollTo(0, 0);  // 滚动到顶部
	},
	methods: {
		triggerFileInput() {
			this.$refs.fileInput.click();
		},
		handleFileChange(event) {
			const file = event.target.files[0];
			if (!file) return;

			const reader = new FileReader();
			reader.onloadend = () => {
				const base64Image = reader.result.split(',')[1];
				this.business.businessImg="data:image/png;base64,"+base64Image;
				this.uploadAvatar(base64Image);
			};
			reader.readAsDataURL(file);
		},
		uploadAvatar(base64Image) {
			this.$axios.post('/BusinessImgUpdate',{
			businessImg:"data:image/png;base64,"+base64Image,
			businessId:this.business.businessId
				}).then(response => {
					if(response.data==1)alert("更新成功");
					else alert("更新失败");
				})
				.catch(error => {
					console.error('上传失败:', error);
			});
		},
		
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
