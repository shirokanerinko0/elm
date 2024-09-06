<template>
  <div class="cart">
    <h2>购物车</h2>
    <ul v-if="items.length > 0">
      <li v-for="(item, index) in items" :key="index" >
		<li v-if="item.quantity>0" class="cart-item">
        <p>{{ item.foodName }} x {{ item.quantity }}</p>
        <p>¥{{ (item.foodPrice * item.quantity).toFixed(2) }}</p>
		</li>
      </li>
	 <div class="blank" style="height: 20vw"></div>
    </ul>
    <p v-else>购物车为空</p>
  </div>
</template>

<script>
export default {
  name:"Cart",
  props: {
    items: Array, // 父组件传递的购物车商品列表
  },
};
</script>

<style scoped>
.cart {
	border: 0.3vw solid #ccc;
	position: fixed;
	padding-bottom: 30vw;
	width: 100%;
	height: 50vh;
	background-color: white;
	color: black;
	font-size: 3vw;
	overflow-y: auto;
}
.cart-item{
	width: 100%;
	box-sizing: border-box;	
	display: flex;
	justify-content: space-between;
	align-items: center;
}
</style>
