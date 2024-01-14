<template>
  <div>
  <Transition>
    <div class="container">
      <div class="welcome-message">系统消息</div>
      <div class="line"></div>
      <div class = 'space1'>
      </div>
      <el-tabs v-model="message">
			<el-tab-pane :label="`未读消息(${state.unread.length})`" name="first">
				<el-table :data="state.unread" :show-header="false" style="width: 100%">
					<el-table-column>
						<template #default="scope">
							<span class="message-title">{{ scope.row.title }}</span>
						</template>
					</el-table-column>
					<el-table-column prop="date" width="180"></el-table-column>
					<el-table-column width="120">
						<template #default="scope">
							<el-button size="small" @click="handleRead(scope.$index)">标为已读</el-button>
						</template>
					</el-table-column>
				</el-table>
				<div class="handle-row">
					<el-button type="primary">全部标为已读</el-button>
				</div>
			</el-tab-pane>
			<el-tab-pane :label="`已读消息(${state.read.length})`" name="second">
				<template v-if="message === 'second'">
					<el-table :data="state.read" :show-header="false" style="width: 100%">
						<el-table-column>
							<template #default="scope">
								<span class="message-title">{{ scope.row.title }}</span>
							</template>
						</el-table-column>
						<el-table-column prop="date" width="150"></el-table-column>
						<el-table-column width="120">
							<template #default="scope">
								<el-button type="danger" @click="handleDel(scope.$index)">删除</el-button>
							</template>
						</el-table-column>
					</el-table>
					<div class="handle-row">
						<el-button type="danger">删除全部</el-button>
					</div>
				</template>
			</el-tab-pane>
			<el-tab-pane :label="`回收站(${state.recycle.length})`" name="third">
				<template v-if="message === 'third'">
					<el-table :data="state.recycle" :show-header="false" style="width: 100%">
						<el-table-column>
							<template #default="scope">
								<span class="message-title">{{ scope.row.title }}</span>
							</template>
						</el-table-column>
						<el-table-column prop="date" width="150"></el-table-column>
						<el-table-column width="120">
							<template #default="scope">
								<el-button @click="handleRestore(scope.$index)">还原</el-button>
							</template>
						</el-table-column>
					</el-table>
					<div class="handle-row">
						<el-button type="danger">清空回收站</el-button>
					</div>
				</template>
			</el-tab-pane>
		</el-tabs>

    </div>
  </Transition>
  </div>
</template>




  
  <script setup lang="ts" name="tabs">

import { ref, reactive } from 'vue';
import imgurl from '../assets/img/img.jpg';

const userID = ref('');
const studentID = ref('');
const phoneNumber = ref('');
const email = ref('');
const username = ref(localStorage.getItem('ms_username') || '');

const message = ref('first');
const state = reactive({
	unread: [
		{
			date: '2023-12-19 20:00:00',
			title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
		},
		{
			date: '2023-12-17 21:00:00',
			title: '今晚12点整发大红包，先到先得'
		}
	],
	read: [
		{
			date: '2023-11-30 20:00:00',
			title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
		}
	],
	recycle: [
		{
			date: '2023-11-31 20:00:00',
			title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
		}
	]
});

const handleRead = (index: number) => {
	const item = state.unread.splice(index, 1);
	state.read = item.concat(state.read);
};
const handleDel = (index: number) => {
	const item = state.read.splice(index, 1);
	state.recycle = item.concat(state.recycle);
};
const handleRestore = (index: number) => {
	const item = state.recycle.splice(index, 1);
	state.read = item.concat(state.read);
};

</script>
  
<style scoped>


.welcome-message {
  text-align: center;
  font-size: 40px;
  color: #378ee6;
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

.message-title {
	cursor: pointer;
}
.handle-row {
	margin-top: 30px;
}
</style>