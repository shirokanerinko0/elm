<template>
<button @click="$router.go(-1)" class="back-button">返回</button>
</template>

<script>
	export default{
		name:'BackButton',
	}
</script>

<style>
	.back-button {
	    background-color: #007bff;
	    color: white;
	    border: none;
		font-size: 4vw;
		width: 20vw;
		height: 10vw;
	    padding: 2vw 4vw;
	    cursor: pointer;
	    border-radius: 5vw;
		position: absolute; /* 将按钮从文档流中移除 */
		left: 0vw; 
	}
</style>