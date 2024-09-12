<template>
    <div class="wrapper">
        <!-- header部分 -->
        <header>
            <BackButton></BackButton>
            <p>新增菜品</p>
        </header>

        <!-- 主内容区域 -->
        <div class="content">
            <div class="upload">
                <img v-if="foodImg" :src="foodImg" alt="菜品图片" class="preview-img"/>
                <input type="file" @change="handleFileChange" ref="fileInput" style="display: none;" />
                <button @click="triggerFileInput" class="upload-button">上传图片</button>
            </div>

            <!-- 裁剪区域 -->
            <div v-if="imageToCrop" class="cropper-container">
                <img ref="image" :src="imageToCrop" alt="待裁剪图片" />
                <button @click="cropImage" class="crop-button">裁剪图片</button>
            </div>

            <div class="input-group">
                <label>名称</label>
                <input type="text" v-model="foodName" class="input-field">
            </div>

            <div class="input-group">
                <label>价格</label>
                <input type="number" v-model="foodPrice" step="0.01" class="input-field" min="0" @input="validatePrice">
            </div>

            <div class="input-group">
                <label>简介</label>
                <textarea v-model="foodExplain" class="input-field textarea"></textarea>
            </div>

            <div class="input-group">
                <label>数量</label>
                <input type="number" v-model="quantity" class="input-field" min="0" @input="validateQuantity">
            </div>
            <div>
                <button @click="submitChanges" class="submit-button">确认添加</button>
            </div>
        </div>
		<div class="blank" style="height: 10vw"></div>
        <Footer></Footer>
    </div>
</template>

<script>
import Footer from '../components/Footer.vue';
import BackButton from '../components/BackButton.vue';
import Cropper from 'cropperjs';
import 'cropperjs/dist/cropper.css'; // 引入裁剪样式

export default {
    name: 'NewFood',
    data() {
        return {
            foodName: '',
            foodExplain: '',
            foodPrice: 0,
            quantity: 100,
            foodImg: '',
            imageToCrop: '', // 记录待裁剪的图片
            cropper: null, // Cropper 实例
            businessId: this.$route.query.businessId,
            user: {},
        }
    },
    components: {
        Footer, BackButton
    },
    methods: {
        triggerFileInput() {
            this.$refs.fileInput.click();
        },
        handleFileChange(event) {
            const file = event.target.files[0];
            if (!file) return;
            if (file.size > 2 * 1024 * 1024) {
                this.errorMessage = "文件大小不能超过 2MB";
                event.target.value = ''; // 清空文件选择
                return;
            }
            // 创建一个URL并显示图片在裁剪区域
            this.imageToCrop = URL.createObjectURL(file);

            // 在图片加载完成后初始化 cropper
            this.$nextTick(() => {
                if (this.cropper) {
                    this.cropper.destroy(); // 如果已有裁剪实例，先销毁
                }
                const imageElement = this.$refs.image;
                this.cropper = new Cropper(imageElement, {
                    aspectRatio: 1, // 例如：正方形裁剪
                    viewMode: 1
                });
            });
        },
        cropImage() {
            if (this.cropper) {
                // 裁剪并转换为 base64
                const croppedCanvas = this.cropper.getCroppedCanvas();
                this.foodImg = croppedCanvas.toDataURL('image/png');
                this.cropper.destroy(); // 裁剪完成后销毁实例
                this.imageToCrop = ''; // 清除裁剪区域
            }
        },
        submitChanges() {
			console.log(this.foodName);
			console.log(this.foodExplain);
			console.log(this.foodImg);
			console.log(this.foodPrice);
			console.log(this.businessId);
			console.log(this.quantity);
			if(!this.foodName){
				alter("食品名称不能为空");
				return;
			}
			if(this.foodExplain&&this.foodExplain.length>40){
				alert("食品简介太长");
				return;
			}
			if(this.foodName&&this.foodName.length>40){
				alert("食品名称太长");
				return;
			}			
            this.$axios.post('/OneFood',{
                foodName: this.foodName,
                foodExplain: this.foodExplain,
                foodImg: this.foodImg,
				foodPrice: parseFloat(this.foodPrice),  // 使用 parseFloat 确保转换为浮点数
				businessId: Number(this.businessId),  // 确保 businessId 是数字类型
				quantity: Number(this.quantity)  // 确保 quantity 是数字类型
            }).then(response => {
                if (response.data > 0) {
					alert("更新成功");
					this.$router.go(-1);
				}
                else alert("更新失败");
            }).catch(error => {
                console.error('失败:', error);
            });
        },
		validatePrice() {
		    if (this.foodPrice < 0) {
				alert("价格不能为负数");
		      this.foodPrice = 0;
		    }
			if (this.foodPrice > 99999) {
				alert("价格太高");
			  this.foodPrice = 99999;
			}
		},
		validateQuantity() {
		    if (this.quantity < 0) {
				alert("数量不能为负数");
		      this.quantity = 0;
		    }
		}
    }
}
</script>

<style scoped>
/* 设置页面整体的样式 */
.wrapper {
    width: 100%;
    height: 100%;
}

/* header 样式 */
header {
    width: 100%;
    height: 12vw;
    background-color: #0097FF;
    color: white;
    font-size: 5vw;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    display: flex;
    justify-content: center;
    align-items: center;
}

/* 主内容区域，确保不被header遮挡 */
.content {
    margin-top: 14vw;
    padding: 4vw;
    font-size: 4vw;
}

/* 上传按钮样式 */
.upload-button {
    font-size: 4vw;
    padding: 2vw;
    background-color: #0097FF;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

/* 裁剪按钮样式 */
.crop-button {
    font-size: 4vw;
    padding: 2vw;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 2vw;
}

/* 输入框和文本域的样式 */
.input-group {
    margin-bottom: 4vw;
}

.input-group label {
    display: block;
    margin-bottom: 1vw;
    font-size: 4vw;
    color: #555;
}

.input-field {
    width: 100%;
    font-size: 4vw;
    padding: 2vw;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-sizing: border-box;
}

.textarea {
    height: 12vw;
    resize: none;
}

/* 提交按钮样式 */
.submit-button {
    width: 100%;
    padding: 3vw;
    background-color: #28a745;
    color: white;
    font-size: 4vw;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

/* 裁剪区域样式 */
.cropper-container {
    margin-top: 4vw;
    text-align: center;
}

.preview-img {
    max-width: 100%;
    margin-bottom: 2vw;
}
</style>
