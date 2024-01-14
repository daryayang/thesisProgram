<template>
	<div>
	  <Transition>
		<div class="container">
		  <div class="welcome-message">热点新闻</div>
		  <div class="line"></div>
		  <div class="space1"></div>
		  <el-row :gutter="20">
			<!-- 遍历新闻列表并显示每一条新闻 -->
			<el-col :span="24" v-for="newsItem in newsList" :key="newsItem.id" class="news-col">
			  <el-card shadow="hover">
				<template #header>
				  <div class="clearfix">
					<span>{{ newsItem.title }}</span>
				  </div>
				</template>
				<div class="info">
				  <div class="info-desc"><strong>新闻简介：</strong>{{ newsItem.summary }}</div>
				  <div class="info-time"><strong>发布时间：</strong>{{ formatPublishTime(newsItem.publishTime) }}</div>
				  <div class="info-source"><strong>新闻来源：</strong>{{ newsItem.source }}</div>
				  <div class="info-link">
					<!-- 使用 el-link 显示新闻链接 -->
					<el-link :href="newsItem.link" target="_blank"><strong>新闻链接：</strong>{{ newsItem.link }}</el-link>
				  </div>
				</div>
			  </el-card>
			</el-col>
		  </el-row>
		</div>
	  </Transition>
	</div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import { getNews } from '../api/login.js';
  

  const newsList = ref<Array<{ id: number; title: string; summary: string; publishTime: string; source: string; link: string }>>([]);

  
  // 页面加载后调用获取新闻的函数
  onMounted(() => {
	fetchNews();
  });
  
  // 获取新闻并更新 newsList
  const fetchNews = () => {
	getNews()
	  .then(response => {
		newsList.value = response.data;
	  })
	  .catch(error => {
		console.error('获取新闻失败', error);
	  });
  };
  
  // 格式化发布时间，你可以根据需要自定义
  const formatPublishTime = (time: string | undefined) => {
	if (time) {
	  return new Date(time).toLocaleString();
	} else {
	  return 'N/A';
	}
  };
  </script>

  
<style scoped>

.news-col {
  margin-bottom: 20px; /* Adjust the value based on your preference */
}
.welcome-message {
  text-align: center;
  font-size: 40px;
  color: #d81616;
  margin-top: 30px;
}

.line {
  width: calc(100% - 20px);
  height: 2px;
  background-color: #f2f2f2;
  margin-top: 10px;
  margin-bottom: 10px;
}

.message {
  display: flex;
  justify-content: center;
  width: 100%;

  margin-bottom: 20px;
}
.element-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin: 0 1%; /* 调整元素之间的距离 */
}

.image-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  margin-bottom: 20px;
}
.space1{
  height: 27px;
}

.space2{
  height: 200px;
}
.message-title {
	cursor: pointer;
}
.handle-row {
	margin-top: 30px;
}

.image-wrapper {
  width: 100%; /* 图片宽度与页面相同 */
  max-width: 1200px;
  margin-right: 10px;
  overflow: hidden;
}

.compressed-image {
  width: 100%;
  height: auto;
  object-fit: contain;
}
.clearfix span {
  font-size: 22px; /* 你可以根据需要调整字体大小 */
  color: #257dd6;
}

.message-title {
	cursor: pointer;
}
.handle-row {
	margin-top: 30px;
}
</style>