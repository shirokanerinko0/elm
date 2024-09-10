<template>
	<div class="wrapper">
		<header>
			<BackButton></BackButton>
			<p>我的商家</p>
		</header>
		<div class="user-info">
			<h3>商家名称： {{ this.business.businessName }}</h3>
			<div class="avatar">
				<img :src="this.business.businessImg || defaultAvatar" alt="商家头像" />
				<input type="file" @change="handleFileChange" ref="fileInput" style="display: none;" />
				<button @click="triggerFileInput" style="font-size: 5vw;">上传头像</button>
			</div>
			<div style="margin-top: 2vw;">商家类型<select id="tShirtSize" v-model="business.orderTypeId" class="biaodan"
					def>
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
			<div>
				商家地址<input type="text" v-model="business.businessAddress"
					style="font-size: 4vw; width: 50vw;height: 4vw;">{{ business.businessAddress }}</input>

			</div>
			<div>起送价格<input type="text" v-model="business.starPrice"
					style="font-size: 4vw; width: 20vw;height: 4vw;">{{ business.businessAddress }}</input>

			</div>
			<div>
				配送价格<input type="text" v-model="business.deliveryPrice"
					style="font-size: 4vw; width: 20vw;height: 4vw;">{{ business.businessAddress }}</input>

			</div>
			<div>
				商家简介<input type="text" v-model="business.businessExplain"
					style="font-size: 4vw; width: 50vw;height: 20vw;">{{ business.businessAddress }}</input>
			</div>
			<div>
				<button @click="submitChanges" class="addButton" style="font-size: 4vw;">保存更改</button>
			</div>
		</div>
		<div class="newArea">
			<p @click="toFoodList">---我的菜品---</p>
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
			business: {},
			foodArr: [],
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
			if (file.size > 2 * 1024 * 1024) {
				alert(`图片大小超过2MB，请选择更小的图片`);
				return;
			}
			const reader = new FileReader();
			reader.onloadend = () => {
				const base64Image = reader.result.split(',')[1];
				this.business.businessImg = "data:image/png;base64," + base64Image;
				this.uploadAvatar(base64Image);
			};
			reader.readAsDataURL(file);
		},
		uploadAvatar(base64Image) {
			this.$axios.post('/BusinessImgUpdate', {
				businessImg: "data:image/png;base64," + base64Image,
				businessId: this.business.businessId
			}).then(response => {
				// this.user.userImg = "data:image/png;base64,"+base64Image;
				// this.$setSessionStorage('business', this.business);
				// localStorage.setItem('businessAvatar', "data:image/png;base64,"+base64Image);
				if (response.data == 1) alert("更新成功");
				else alert("更新失败");
			})
				.catch(error => {
					console.error('上传失败:', error);
				});
		},
		submitChanges() {
			this.$axios.post('/BusinessRegistrationMore', {
				//更新更多的商家信息
				businessId: this.business.businessId,
				orderTypeId: this.business.orderTypeId,
				businessAddress: this.business.businessAddress,
				businessExplain: this.business.businessExplain,
				deliveryPrice: this.business.deliveryPrice,
				starPrice: this.business.starPrice
			}).then(response => {
				// this.$setSessionStorage('business', this.business);
				if (response.data == 1) alert("更新成功");
				else alert("更新失败");
			})
				.catch(error => {
					console.error('失败:', error);
				});
		},
		toFoodList() {
			this.$router.push('/foodList');
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
	display: flex;
	flex-direction: column;
	/* 将内容垂直排列 */
	justify-content: center;
	/* 垂直居中 */
	align-items: center;
	/* 水平居中 */
	text-align: center;
	margin-bottom: 10px;
	/* 可根据需要调整与下方内容的距离 */
}

.wrapper .user-info .avatar img {
	width: 20vw;
	height: 20vw;
	border-radius: 50%;
}

.wrapper .user-info .biaodan {
	font-size: 4vw;
	width: 50vw;
	height: 6vw;

}

.wrapper .user-info .addButton {
    font-size: 4vw;
    display: flex;
    justify-content: center;  /* 水平居中 */
    align-items: center;  /* 垂直居中 */
    padding: 10px 20px;  /* 按钮内边距 */
    margin-top: 2vw;
    height: 10vw;
    margin-left: auto;
    margin-right: auto;
    width: fit-content;  /* 按钮宽度根据内容自适应 */
}
/****************** 我的菜品 ******************/
.wrapper .newArea {
		/**
		 * 设置容器宽度95%，然后水平居中，这样两边留白; 
		 * 这里不能用padding，因为背景图片也会覆盖padding
		 */
		width: 95%;
		margin: 0 auto;
		height: 29vw;

		/*此三个样式组合，可以保证背景图片充满整个容器*/
		background-image: url(../assets/index_banner.png);
		background-repeat: no-repeat;
		background-size: cover;

		box-sizing: border-box;
		padding: 2vw 6vw;
	}

	.wrapper .newArea h3 {
		font-size: 4.2vw;
		margin-bottom: 1.2vw;
	}

	.wrapper .newArea p {
		font-size: 6vw;
		color: #ac1515;
		margin-top: 8vw;
		margin-left: 7vw;
	}

	.wrapper .newArea a {
		font-size: 3vw;
		color: #C79060;
		font-weight: 700;
	}
</style>
