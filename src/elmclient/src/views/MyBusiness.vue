<template>
    <div class="wrapper">
        <header>
            <back-button></back-button>
            <p>商家注册</p>
        </header>
        <ul class="form-box">
            <li>
                <div class="title">
                    店铺名称：
                </div>
                <div class="content">
                    <input type="text" v-model="businessName" placeholder="输入你的店名">
                </div>
            </li>
            <li>
                <div class="title">
                    用户密码：
                </div>
                <div class="content">
                    <input type="password" v-model="confirmpassword" placeholder="输入你的密码">
                </div>
            </li>
        </ul>
        <div>
            <button class="button-login" @click="createBusiness">确认开店！</button>

        </div>
        <Footer></Footer>
    </div>

</template>
<script>
import Footer from '../components/Footer.vue';
import BackButton from '../components/BackButton.vue';
import CryptoJS from 'crypto-js';

export default {
    name: 'MyBusiness',
    data() {
        return {
            user:{},
            businessId: '',
            businessName:'',
            confirmpassword: '',
            business:{}
        }
    },
    created(){
        this.user = this.$getSessionStorage('user');
    },
    methods: {
        createBusiness() {
            if (this.businessName == '') {
                alert('店名不能为空！');
                return;
            }
            if (this.confirmpassword == '') {
                alert('密码不能为空！');
                return;
            }
            const encryptedPassword = CryptoJS.MD5(this.confirmpassword).toString();
            console.log("加密后的数据:", encryptedPassword);
            //登录请求
			console.log(this.user.userId);
			console.log(encryptedPassword);
			console.log(this.businessName);
            this.$axios.post('BusinessRegistration', null,
			{
				params:{
                    userId: this.user.userId,
                    password: encryptedPassword,
                    businessName: this.businessName,
				}
            }).then(response => {
                let businessId = response.data;
				console.log(businessId);
                if(!businessId){
					alert('密码错误');
				}
				else {
                    alert('恭喜成为商家！');
					if(businessId){
						this.$axios.post('Business',null,{
							params:{
							businessId:businessId,
							}
						}).then(response=>{
							this.business = response.data;
							console.log(response.data);
							console.log('Type of response.data:', typeof response.data);
							if(this.business==null){
								alert('没有这个商家：'+businessId);
							}else{
								this.$setSessionStorage('business',this.business);
							}
						}).catch(error=>{
							console.error(error);
						});
					}
                    this.$router.go(-1);
                }
            }).catch(error => {
                alert('something went wrony!');
                console.error(error);
            });
        }
    },
    components: {
        Footer, BackButton
    }
}

components: {
    Footer, BackButton
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

/****************** 表单部分 ******************/
.wrapper .form-box {
    width: 100%;
    margin-top: 12vw;
}

.wrapper .form-box li {
    box-sizing: border-box;
    padding: 4vw 3vw 0 3vw;
    display: flex;
    align-items: center;
}

.wrapper .form-box li .title {
    flex: 0 0 18vw;
    font-size: 3vw;
    font-weight: 700;
    color: #666;
}

.wrapper .form-box li .content {
    flex: 1;
}

.wrapper .form-box li .content input {
    border: none;
    outline: none;
    width: 100%;
    height: 4vw;
    font-size: 3vw;
}

.wrapper .button-login {
    width: 100%;
    box-sizing: border-box;
    padding: 4vw 3vw 0 3vw;
}

.wrapper .button-login button {
    width: 100%;
    height: 10vw;
    font-size: 3.8vw;
    font-weight: 700;
    color: #fff;
    background-color: #38CA73;
    border-radius: 4px;

    border: none;
    outline: none;
}

.wrapper .button-register {
    width: 100%;
    box-sizing: border-box;
    padding: 4vw 3vw 0 3vw;
}

.wrapper .button-register button {
    width: 100%;
    height: 10vw;
    font-size: 3.8vw;
    font-weight: 700;
    /*与上面登陆按钮不同的只有颜色、背景色、边框不同*/
    color: #666;
    background-color: #EEE;
    border: solid 1px #DDD;
    border-radius: 4px;

    border: none;
    outline: none;
}

/****************** 底部菜单部分 ******************/
.wrapper .footer {
    width: 100%;
    height: 14vw;
    border-top: solid 1px #DDD;
    background-color: #fff;

    position: fixed;
    left: 0;
    bottom: 0;

    display: flex;
    justify-content: space-around;
    align-items: center;
}

.wrapper .footer li {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    color: #999;
    user-select: none;
    cursor: pointer;
}

.wrapper .footer li p {
    font-size: 2.8vw;
}

.wrapper .footer li i {
    font-size: 5vw;
}
</style>