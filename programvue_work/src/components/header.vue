<template>
	<div class="header">
		<!-- 折叠按钮 -->
		<div class="collapse-btn" @click="collapseChange">
			<el-icon v-if="sidebar.collapse"><Expand /></el-icon>
			<el-icon v-else><Fold /></el-icon>
		</div>
		<div class="logo">NeuroFinance Hub</div>
		<div class="header-right">
			<div class="header-user-con">
				<!-- 消息中心 -->

				<!-- 用户头像 -->
				<el-avatar class="user-avator" :size="30" :src="imgurl" />
				<!-- 用户名下拉菜单 -->
				<el-dropdown class="user-name" trigger="click" @command="handleCommand">
					<span class="el-dropdown-link">
						{{ username}}
						<el-icon class="el-icon--right">
							<arrow-down />
						</el-icon>
					</span>
					<template #dropdown>
						<el-dropdown-menu>
							<el-dropdown-item command="user">个人中心</el-dropdown-item>
							<el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
						</el-dropdown-menu>
					</template>
				</el-dropdown>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useSidebarStore } from '../store/sidebar'
import { useRouter } from 'vue-router'
import imgurl from '../assets/img/img.jpg'
// @ts-ignore
import { getAccessToken } from '../utils/auth'
import axios from 'axios'; // Import Axios library
import service from '../utils/request';

let username = ref<string | null>(null);
const message: number = 2;

const sidebar = useSidebarStore();

// 侧边栏折叠
const collapseChange = () => {
  sidebar.handleCollapse();
};

onMounted(async () => {
  if (document.body.clientWidth < 1500) {
    collapseChange();
  }

  // 在组件挂载时，获取用户名
  await fetchUsername();
});

// 用户名下拉菜单选择事件
const router = useRouter();
const apiUrl = `${service.defaults.baseURL}/user/info`;

const fetchUsername = async () => {
  try {
    const token = getAccessToken(); // 获取保存在 localStorage 中的 token
    console.log(token);
    if (!token) {
      console.error('未找到有效的 token');
      return;
    }

    const response = await axios.get(apiUrl + `?token=${token}`, {
      headers: {
        Authorization: `Bearer ${token}`, // 在请求头中添加 token
      },
    });

    const contentType = response.headers['content-type'];

    if (contentType && contentType.includes('application/json')) {
      const data = response.data;
      console.log('Data:', data);
      if (data.code === 20000) {
        username.value = data.data.name;
 		// 注意这里修改为正确的属性名
		console.log(username.value);
		console.log(data.data.name);
        console.log("nihao");
      } else {

        console.error('获取用户名失败:', data.message);
      }
    } else {
      // 处理非 JSON 响应
      const textData = response.data;
      console.error('获取用户名时发生错误，非 JSON 响应:', response.statusText);
    }
  } catch (error) {
    console.error('获取用户名时发生错误:', error);
  }
};

const handleCommand = (command: string) => {
  if (command === 'loginout') {
    localStorage.removeItem('ms_username');
    router.push('/login');
  } else if (command === 'user') {
    router.push('/user');
  }
};
</script>

<style scoped>
.header {
	position: relative;
	box-sizing: border-box;
	width: 100%;
	height: 70px;
	font-size: 22px;
	color: #fff;
}
.collapse-btn {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100%;
	float: left;
	padding: 0 21px;
	cursor: pointer;
}
.header .logo {
	float: left;
	width: 250px;
	line-height: 70px;
}
.header-right {
	float: right;
	padding-right: 50px;
}
.header-user-con {
	display: flex;
	height: 70px;
	align-items: center;
}
.btn-fullscreen {
	transform: rotate(45deg);
	margin-right: 5px;
	font-size: 24px;
}

.user-name {
	margin-left: 10px;
}
.user-avator {
	margin-left: 20px;
}
.el-dropdown-link {
	color: #fff;
	cursor: pointer;
	display: flex;
	align-items: center;
}
.el-dropdown-menu__item {
	text-align: center;
}
</style>
