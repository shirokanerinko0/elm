<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<back-button></back-button>
			<p>食品管理</p>
		</header>

		<!-- 食品logo部分 -->
		<div class="business-logo">
			<img :src="food.foodImg" alt="Business Logo" style="height: 40vw;width: 40vw;">
		</div>

		<!-- 食品信息部分 -->
		<div class="food-info-container">
			<div class="food-input-group">
				<label>图片</label>
				<input type="file" @change="handleFileChange" ref="fileInput" style="display: none;" />
				<button class="upload-btn" @click="triggerFileInput" style="font-size: 4vw;">上传图片</button>
			</div>
			<div class="food-input-group">
				<label>名称</label>
				<input type="text" v-model="food.foodName">
			</div>
			<div class="food-input-group">
				<label>价格</label>
				<input type="number" v-model="food.foodPrice" @input="validatePrice">
			</div>
			<div class="food-input-group">
				<label>简介</label>
				<textarea v-model="food.foodExplain"></textarea>
			</div>
			<div class="food-input-group">
				<label>数量</label>
				<input type="number" v-model="food.quantity" @input="validateQuantity">
			</div>
			<div class="button-group">
				<button @click="submitChanges" class="save-button">保存更改</button>
				<button @click="removeFood" class="delete-button">移除食品</button>
			</div>
		</div>

		<!-- 底部空白和Footer -->
		<div class="blank"></div>
		<Footer></Footer>
	</div>
</template>

<script>
import Footer from '../components/Footer.vue';
import BackButton from '../components/BackButton.vue';

export default {
	name: 'FoodInfo',
	data() {
		return {
			foodId: this.$route.query.foodId,
			food: {},
			business: {},
			user: {},
		}
	},
	created() {
		this.user = this.$getSessionStorage('user');
		this.business = this.$getSessionStorage('business');
		// 根据foodId查询食品信息
		console.log(this.foodId);
		this.$axios.get('GetOneFood',{
			params:{
				foodId: this.foodId
			}
		}).then(response => {
			this.food = response.data;
			if(this.food.businessId!=this.business.businessId){
				alert("当前食品不属于你");
				this.$router.go(-1);
			}
		}).catch(error => {
			console.error(error);
		});
		
	},
	components: {
		Footer, BackButton
	},
	methods: {
		submitChanges() {
			if (!this.food.foodName) {
			    alert('食品名称不能为空');
			    return;
			}
			if(this.food.foodExplain&&this.food.foodExplain.length>40){
				alert("食品简介太长");
				return;
			}
			if(this.food.foodName&&this.food.foodName.length>40){
				alert("食品名称太长");
				return;
			}			
			console.log(this.food);
			this.$axios.put('Food', 
				this.food
			).then(response => {
				if (response.data > 0) {
					alert("更新成功");
					this.$router.go(-1);
				} else {
					alert("更新失败");
				}
			}).catch(error => {
				console.error('失败:', error);
			});
		},
		removeFood() {
			this.$axios.delete('Food',{
				params: { foodId: this.foodId }
			}).then(response => {
				if (response.data > 0) {
					alert("删除食品成功");
					this.$router.go(-1);
				}
			}).catch(error => {
				console.error(error);
			});
		},triggerFileInput() {
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
				this.food.foodImg = "data:image/png;base64," + base64Image;
			};
			reader.readAsDataURL(file);
		},
		validatePrice() {
		    if (this.food.foodPrice < 0) {
				alert("价格不能为负数");
		      this.food.foodPrice = 0;
		    }
			if (this.food.foodPrice > 99999) {
				alert("价格太高");
			  this.food.foodPrice = 99999;
			}
		},
		validateQuantity() {
		    if (this.food.quantity < 0) {
				alert("数量不能为负数");
		      this.food.quantity = 0;
		    }
			this.food.quantity=Math.floor(this.food.quantity);
		}
	}
}
</script>

<style scoped>
/****************** 总容器 ******************/
.wrapper {
	width: 100%;
	min-height: 100vh;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	background-color: #f9f9f9;
}

/****************** header部分 ******************/
.wrapper header {
	width: 100%;
	height: 12vw;
	background-color: #0097FF;
	color: white;
	font-size: 4.8vw;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 1000;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/****************** 食品logo部分 ******************/
.business-logo {
	width: 100%;
	height: 40vw;
	margin-top: 12vw; /* 留出header的空间 */
	display: flex;
	justify-content: center;
	align-items: center;
}

.business-logo img {
	width: 40vw;
	height: auto;
	border-radius: 10px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

/****************** 食品信息部分 ******************/
.food-info-container {
	padding: 4vw;
	margin-top: 4vw;
}

.food-input-group {
	margin-bottom: 4vw;
}

.food-input-group label {
	font-size: 4vw;
	color: #333;
	display: block;
	margin-bottom: 2vw;
}

.food-input-group input, .food-input-group textarea {
	width: 100%;
	font-size: 4vw;
	padding: 2vw;
	border: 1px solid #ddd;
	border-radius: 5px;
	box-sizing: border-box;
}

.food-input-group textarea {
	height: 20vw;
	resize: none;
}

.button-group {
	display: flex;
	justify-content: space-between;
	margin-top: 6vw;
}

.save-button {
	background-color: #0097FF;
	color: white;
	border: none;
	border-radius: 5px;
	padding: 3vw 6vw;
	font-size: 4vw;
	cursor: pointer;
}

.delete-button {
	background-color: #FF4C4C;
	color: white;
	border: none;
	border-radius: 5px;
	padding: 3vw 6vw;
	font-size: 4vw;
	cursor: pointer;
}

.blank {
	height: 17vw;
}

/****************** 底部Footer ******************/
.footer {
	position: fixed;
	bottom: 0;
	width: 100%;
}
</style>
