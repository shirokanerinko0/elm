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
					style="font-size: 4vw; width: 50vw;height: 4vw;"/>

			</div>
			<div>起送价格<input type="text" v-model="business.starPrice"
					style="font-size: 4vw; width: 20vw;height: 4vw;"/>

			</div>
			<div>
				配送价格<input type="text" v-model="business.deliveryPrice"
					style="font-size: 4vw; width: 20vw;height: 4vw;"/>

			</div>
			<div>
				商家简介<input type="text" v-model="business.businessExplain"
					style="font-size: 4vw; width: 50vw;height: 20vw;"/>
			</div>
			<div>
				<button @click="submitChanges" class="addButton" style="font-size: 4vw;">保存更改</button>
			</div>
		</div>
		<div class="newArea">
			<p @click="toFoodList">---我的菜品---</p>
		</div>
		<div class="blank" style="height: 15vw"></div>	
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
			this.$axios.post('BusinessImg', {
				businessImg: "data:image/png;base64," + base64Image,
				businessId: this.business.businessId
			}).then(response => {
				// this.user.userImg = "data:image/png;base64,"+base64Image;
				// this.$setSessionStorage('business', this.business);
				// localStorage.setItem('businessAvatar', "data:image/png;base64,"+base64Image);
				if (response.data == 1) {alert("更新成功");
				this.business.businessImg= "data:image/png;base64," + base64Image;
				this.$setSessionStorage('business',this.business);
				}
				else alert("更新失败");
			})
				.catch(error => {
					console.error('上传失败:', error);
				});
		},
		submitChanges() {
			this.$axios.put('Business', this.$qs.stringify({
					//更新更多的商家信息
				businessId:this.business.businessId,
				orderTypeId:this.business.orderTypeId,
				businessAddress:this.business.businessAddress,
				businessExplain:this.business.businessExplain,
				deliveryPrice:this.business.deliveryPrice,
				starPrice:this.business.starPrice
			})).then(response => {
				// this.$setSessionStorage('business', this.business);
				if (response.data == 1) {alert("更新成功");
				this.$setSessionStorage('business',this.business);
				}
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
    background-color: #f7f8fa; /* 更柔和的背景色 */
}

.wrapper header {
    width: 100%;
    height: 12vw;
    background-color: #007bff; /* 调整为深蓝色 */
    color: #fff;
    font-size: 4.8vw;
    position: fixed;
    left: 0;
    top: 0;
    z-index: 1000;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 添加阴影 */
}

.wrapper .user-info {
    margin-top: 16vw; /* 加大上边距 */
    padding: 6vw;
    font-size: 4vw;
    font-weight: 400; /* 调整字体厚度 */
    color: #333; /* 更深的文字颜色，提升可读性 */
    background-color: #fff; /* 设置为白色背景 */
    border-radius: 10px; /* 添加圆角 */
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加阴影 */
}

.wrapper .user-info h3 {
    font-size: 5vw;
    font-weight: bold;
    margin-bottom: 4vw;
}

.wrapper .user-info .avatar {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    margin-bottom: 10px;
}

.wrapper .user-info .avatar img {
    width: 20vw;
    height: 20vw;
    border-radius: 50%;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 为头像添加阴影 */
    object-fit: cover; /* 确保图片不会被拉伸 */
}

.wrapper .user-info .avatar button {
    font-size: 4vw;
    background-color: #007bff;
    color: white;
    padding: 1.5vw 3vw;
    border-radius: 5px;
    margin-top: 2vw;
    cursor: pointer;
    border: none;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: background-color 0.3s ease;
}

.wrapper .user-info .avatar button:hover {
    background-color: #0056b3; /* 悬停效果 */
}

.wrapper .user-info .biaodan {
    font-size: 4vw;
    width: 100%;
    height: 6vw;
    margin-top: 2vw;
    padding: 0.5vw;
    border-radius: 5px;
    border: 1px solid #ccc;
    background-color: #f9f9f9;
}

.wrapper .user-info input[type="text"] {
    font-size: 4vw;
    width: 100%;
    height: 6vw;
    padding: 1vw;
    margin-top: 2vw;
    border-radius: 5px;
    border: 1px solid #ddd;
    background-color: #f9f9f9;
}

.wrapper .user-info .addButton {
    font-size: 4vw;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 2vw 4vw;
    margin-top: 4vw;
    background-color: #28a745;
    color: white;
    border-radius: 5px;
    cursor: pointer;
    border: none;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: background-color 0.3s ease;
}

.wrapper .user-info .addButton:hover {
    background-color: #218838; /* 悬停时的效果 */
}

/****************** 我的菜品 ******************/
.wrapper .newArea {
    width: 100%;
    margin: 0 auto;
    height: 29vw;
    background-image: url(../assets/index_banner.png);
    background-repeat: no-repeat;
    background-size: cover;
    box-sizing: border-box;
    padding: 2vw 6vw;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加阴影 */
}

.wrapper .newArea p {
    font-size: 6vw;
    color: #e74c3c; /* 调整为更亮的红色 */
    margin-top: 8vw;
    margin-left: 7vw;
    cursor: pointer;
    transition: color 0.3s ease;
}

.wrapper .newArea p:hover {
    color: #c0392b; /* 悬停时的颜色变化 */
}

</style>
