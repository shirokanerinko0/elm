<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<back-button></back-button>
			<p>{{ food.foodName }}</p>
		</header>

		<!-- 食品logo部分 -->
		<div class="business-logo">
			<img :src="business.businessImg">
		</div>
		<!-- 食品信息部分 -->
		<div>
			<div>
				图片<input type="text" v-model="food.foodImg" style="font-size: 4vw; width: 20vw;height: 4vw;">{{
					food.foodImg }}</input>
			</div>
			<div>
				名称<input type="text" v-model="food.foodName" style="font-size: 4vw; width: 20vw;height: 4vw;">{{
					food.foodName }}</input>
			</div>
			<div>
				价格<input type="text" v-model="food.foodPrice" style="font-size: 4vw; width: 20vw;height: 4vw;">{{
					food.foodPrice }}</input>
			</div>
			<div>
				简介<input type="text" v-model="food.foodExplain" style="font-size: 4vw; width: 50vw;height: 20vw;">{{
					food.foodExplain }}</input>
			</div>
			<div>
				限量<input type="text" v-model="food.quantity" style="font-size: 4vw; width: 50vw;height: 20vw;">{{
					food.quantity }}</input>
			</div>
			<div>
				<button @click="submitChanges" class="addButton" style="font-size: 4vw;">保存更改</button>
			</div>
		</div>
		<div>
			<button @click="removeFood" class="addButton">移除食品</button>
		</div>
		<div class="blank" style="height: 17vw"></div>
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
		//根据foodId查询食品信息
		this.$axios.post('Food', this.$qs.stringify({
			foodId: this.foodId
		})).then(response => {
			this.food = response.data;
		}).catch(error => {
			console.error(error);
		});
	},
	components: {
		Footer, BackButton
	},
	methods: {
		submitChanges() {
			this.$axios.post('/Food', {
				food: this.food
			}).then(response => {
				if (response.data > 0) {
					alert("更新成功");
					this.$axios.post('Food', this.$qs.stringify({
						foodId: this.foodId
					})).then(response => {
						this.food = response.data;
					}).catch(error => {
						console.error(error);
					});
				}
				else alert("更新失败");
			})
				.catch(error => {
					console.error('失败:', error);
				});
		},
		removeFood(){
			this.$axios.post('/Food', this.$qs.stringify({
			foodId: this.foodId
		})).then(response => {
			if (response.data > 1){
				alert("更新成功");
			}
		}).catch(error => {
			console.error(error);
		});
		}

	},
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

/****************** 食品列表部分 ******************/
.wrapper .food {
	width: 100%;
	/*使用下外边距避开footer部分*/
	margin-bottom: 14vw;
}

.wrapper .food li {
	width: 100%;
	box-sizing: border-box;
	padding: 2.5vw;
	user-select: none;

	display: flex;
	justify-content: space-between;
	align-items: center;
}

.wrapper .food li .food-left {
	display: flex;
	align-items: center;
}

.wrapper .food li .food-left img {
	width: 20vw;
	height: 20vw;
}

.wrapper .food li .food-left .food-left-info {
	margin-left: 3vw;
}

.wrapper .food li .food-left .food-left-info h3 {
	font-size: 3.8vw;
	color: #555;
}

.wrapper .food li .food-left .food-left-info p {
	font-size: 3vw;
	color: #888;
	margin-top: 2vw;
}

.wrapper .food li .food-right {
	width: 16vw;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.wrapper .food li .food-right .fa-minus-circle {
	font-size: 5.5vw;
	color: #999;
	cursor: pointer;
}

.wrapper .food li .food-right p {
	font-size: 3.6vw;
	color: #333;
}

.wrapper .food li .food-right .fa-plus-circle {
	font-size: 5.5vw;
	color: #0097EF;
	cursor: pointer;
}

.wrapper .business-logo {
	width: 100%;
	height: 35vw;
	/*使用上外边距避开header部分*/
	margin-top: 12vw;

	display: flex;
	justify-content: center;
	align-items: center;
}

.wrapper .business-logo img {
	width: 40vw;
	height: 30vw;
	border-radius: 5px;
}

.wrapper .addButton {
	font-size: 4vw;
	display: flex;
	justify-content: center;
	/* 水平居中 */
	align-items: center;
	/* 垂直居中 */
	padding: 10px 20px;
	/* 按钮内边距 */
	margin-top: 2vw;
	height: 10vw;
	margin-left: auto;
	margin-right: auto;
	width: fit-content;
	/* 按钮宽度根据内容自适应 */
}
</style>
