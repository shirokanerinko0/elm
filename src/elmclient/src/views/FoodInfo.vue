<template>
	<div class="wrapper">
				
				<!-- header部分 -->
				<header>
					<back-button></back-button>
					<p>{{food.foodName}}</p>
				</header>
				
				<!-- 食品logo部分 -->
				<div class="food-logo">
					<img :src="food.foodImg">
				</div>
				
				<!-- 食品信息部分 -->
				<div class="food-info">
					<h1>{{food.foodName}}</h1>
					<p>价格：&#165;{{food.Price}}</p>
					<p>{{food.foodExplain}}</p>
				</div>
                <div>
                    <button>编辑食品信息</button>
                    <button>移除食品</button>
                </div>
		<div class="blank" style="height: 17vw"></div>
		<!--<Footer></Footer>-->
		</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	import BackButton from '../components/BackButton.vue';		
	export default{
		name:'FoodInfo',
		data(){
			return {
                foodId: this.$route.query.foodId,
				food:{},
				business:{},
				user:{},
				isShowDetailet:false,
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
			//根据foodId查询食品信息
			this.$axios.post('Food',this.$qs.stringify({				
				foodId:this.foodId
			})).then(response=>{
				this.food = response.data;
			}).catch(error=>{
				console.error(error);
			});
			
			//根据businessId查询所属食品信息
			this.$axios.post('Food',this.$qs.stringify({
				foodId:this.foodId
			})).then(response=>{
				this.food = response.data;				
				//如果已登录，那么需要去查询购物车中是否已经选购了某个食品
			}).catch(error=>{
				console.error(error);
			});
		},
		components:{
			Footer,BackButton
		},
		methods:{			
			detailetShow(){
				this.isShowDetailet = !this.isShowDetailet;
			}
		},
	}
</script>

<style scoped>
	/****************** 总容器 ******************/
	.wrapper{
		width: 100%;
		height: 100%;
	}
	
	/****************** header部分 ******************/
	.wrapper header{
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
	
	/****************** 商家logo部分 ******************/
	.wrapper .business-logo{
		width: 100%;
		height: 35vw;
		/*使用上外边距避开header部分*/
		margin-top: 12vw;
		
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.wrapper .business-logo img{
		width: 40vw;
		height: 30vw;
		border-radius: 5px;
	}
	
	/****************** 商家信息部分 ******************/
	.wrapper .business-info{
		width: 100%;
		height: 20vw;
		
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	.wrapper .business-info h1{
		font-size: 5vw;
	}
	.wrapper .business-info p{
		font-size: 3vw;
		color: #666;
		margin-top: 1vw;
	}
	
	/****************** 食品列表部分 ******************/
	.wrapper .food{
		width: 100%;
		/*使用下外边距避开footer部分*/
		margin-bottom: 14vw;
	}
	.wrapper .food li{
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		user-select: none;
		
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.wrapper .food li .food-left{
		display: flex;
		align-items: center;
	}
	.wrapper .food li .food-left img{
		width: 20vw;
		height: 20vw;
	}
	.wrapper .food li .food-left .food-left-info{
		margin-left: 3vw;
	}
	.wrapper .food li .food-left .food-left-info h3{
		font-size: 3.8vw;
		color: #555;
	}
	.wrapper .food li .food-left .food-left-info p{
		font-size: 3vw;
		color: #888;
		margin-top: 2vw;
	}
	.wrapper .food li .food-right{
		width: 16vw;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.wrapper .food li .food-right .fa-minus-circle{
		font-size: 5.5vw;
		color: #999;
		cursor: pointer;
	}
	.wrapper .food li .food-right p{
		font-size: 3.6vw;
		color: #333;
	}
	.wrapper .food li .food-right .fa-plus-circle{
		font-size: 5.5vw;
		color: #0097EF;
		cursor: pointer;
	}
	
	/****************** 购物车部分 ******************/
	.wrapper .cart{
		width: 100%;
		height: 14vw;
		
		position: fixed;
		left: 0;
		bottom: 0;
		
		display: flex;
	}
	.wrapper .cart .cart-left{
		flex: 2;
		background-color: #505051;
		display: flex;
	}
	.wrapper .cart .cart-left .cart-left-icon{
		width: 16vw;
		height: 16vw;
		box-sizing: border-box;
		border: solid 1.6vw #444;
		border-radius: 8vw;
		background-color: #3190E8;
		font-size: 7vw;
		color: #fff;
		
		display: flex;
		justify-content: center;
		align-items: center;
		
		margin-top: -4vw;
		margin-left: 3vw;
		
		position: relative;
	}
	.wrapper .cart .cart-left .cart-left-icon-quantity{
		width: 5vw;
		height: 5vw;
		border-radius: 2.5vw;
		background-color: red;
		color: #fff;
		font-size: 3.6vw;
		
		display: flex;
		justify-content: center;
		align-items: center;
		
		position: absolute;
		right: -1.5vw;
		top: -1.5vw;
	}
	.wrapper .cart .cart-left .cart-left-info p:first-child{
		font-size: 4.5vw;
		color: #fff;
		margin-top: 1vw;
	}
	.wrapper .cart .cart-left .cart-left-info p:last-child{
		font-size: 2.8vw;
		color: #AAA;
	}
	
	.wrapper .cart .cart-right{
		flex: 1;
	}
	/*达到起送费时的样式*/
	.wrapper .cart .cart-right .cart-right-item{
		width: 100%;
		height: 100%;
		background-color: #38CA73;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;
		cursor: pointer;
		
		display: flex;
		justify-content: center;
		align-items: center;
	}

	/*不够起送费时的样式（只有背景色和鼠标样式的区别）*/
	/*
	.wrapper .cart .cart-right .cart-right-item{
		width: 100%;
		height: 100%;
		background-color: #535356;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;
		
		display: flex;
		justify-content: center;
		align-items: center;
	}
	*/
</style>
