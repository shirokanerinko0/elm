<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<BackButton></BackButton>
			<p>我的菜品</p>
		</header>

		<!-- 食品列表部分 -->
		<ul class="food">
					<li v-for="(item,index) in foodArr" ">
						<div @click="toFoodInfo(item.foodId)" class="food-left">
							<img :src="item.foodImg">
							<div class="food-left-info">
								<h3>{{item.foodName}}</h3>
								<p>{{item.foodExplain}}</p>
								<p>&#165;{{item.foodPrice}}</p>
								<p>{{ item.quantity }}</p>
							</div>
						</div>
					</li>
				</ul>
		<div class="addbtn" @click="newFood(business.businessId)">
			<img src="../assets/more.png" alt="新增图标" class="icon-img">
			<i class="fa fa-plus-circle"></i>
			<p>新增菜品</p>
		</div>		
		<div class="blank" style="height: 5vw"></div>
		<!-- 底部菜单部分 -->
		<Footer> </Footer> 
	</div>
</template>


<script>
	import Footer from '../components/Footer.vue';
	import BackButton from '../components/BackButton.vue';
	
	export default{
		name:'FoodList',
		data(){
			return {
				foodArr:[],
				user:{},
				business:{},	
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
			this.business =this.$getSessionStorage('business');
			this.$axios.post('Food',this.$qs.stringify({				
				businessId:this.business.businessId
			})).then(response=>{
				this.foodArr = response.data;
			}).catch(error=>{
				console.error(error);
			});
		},
		components:{
			Footer,BackButton
		},
		mounted() {
		  window.scrollTo(0, 0);  // 滚动到顶部
		},
		methods:{
			toFoodInfo(foodId){
				this.$router.push({path:'/FoodInfo',query:{foodId:foodId}});
			},
			newFood(businessId){
				this.$router.push({path:'/NewFood',query:{businessId:businessId}});
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

	/****************** 商家列表部分 ******************/
	.wrapper .food {
		width: 100%;
		margin-top: 12vw;
		margin-bottom: 14vw;
	}

	.wrapper .food li {
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		border-bottom: solid 1px #DDD;
		user-select: none;
		cursor: pointer;

		display: flex;
		align-items: center;
	}

	.wrapper .food li .food-img {
		/*这里设置为相当定位，成为business-img-quantity元素的父元素*/
		position: relative;
	}

	.wrapper .food li .food-img img {
		width: 20vw;
		height: 20vw;
	}

	.wrapper .food li .food-img .food-img-quantity {
		width: 5vw;
		height: 5vw;
		background-color: red;
		color: #fff;
		font-size: 3.6vw;
		border-radius: 2.5vw;

		display: flex;
		justify-content: center;
		align-items: center;

		/*设置成绝对定位，不占文档流空间*/
		position: absolute;
		right: -1.5vw;
		top: -1.5vw;
	}

	.wrapper .food li .food-info {
		margin-left: 3vw;
	}

	.wrapper .food li .food-info h3 {
		font-size: 3.8vw;
		color: #555;
	}

	.wrapper .food li .food-info p {
		font-size: 3vw;
		color: #888;
		margin-top: 2vw;
	}


	.wrapper .addbtn {
		width: 100%;
		height: 20vw;
		border-top: solid 1px #DDD;
		border-bottom: solid 1px #DDD;
		background-color: #e8eef0;
		margin-top: 4vw;

		display: flex;
		justify-content: flex-start;
		align-items: center;

		font-size: 4.5vw;
		color: #0097FF;
		user-select: none;
		cursor: pointer;
	}

.wrapper .addbtn .icon-img {
    width: 20vw;  /* 控制图片的宽度 */
    height: 20vw;  /* 控制图片的高度 */
    margin-right: 20vw;  /* 图片和文字的间距 */
	margin-left: 0;
}
</style>
