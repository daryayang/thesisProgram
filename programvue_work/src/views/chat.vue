<template>
   <div class="chat-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
                        <h1 style="color: #274cd1;text-decoration: none">基于轩辕-70B的聊天机器人</h1>
                        <br>

                        <b style="font-size: 16px;color: rgb(50,58,88);margin-right: 30px;"><i>&nbsp;-by: 杨洋</i>
                          <el-button @click="goToDashboard">返回主页</el-button></b>
                    
          </div>
      </template>
      <div class="message-container">
        <div v-for="(item, index) in messageArray" :key="index">
          <!-- 用户输入行 -->
          <div v-if="item.type === 'user-input'" class="user-input">
            <div class="user-text"><strong>你：</strong> {{ item.content }}</div>
          </div>
          <!-- 消息行 -->
          <div v-else class="message">
            <div class="bot-text"><strong>轩辕：</strong>{{ item.content }}</div>
          </div>
        </div>
        <div class="message-spacing"></div>
      </div>

      <div class="input-container">
        <textarea v-model="userInput" placeholder="请输入问题"></textarea>
        <button @click="sendQuestion">发送问题</button>
      </div>
    </el-card>
  </div>


  </template>
  
  <script>
  import axios from 'axios';
  import service from '../utils/request';
  export default {
    data() {
      return {
        message:'',
        messageArray: [],
        userInput: '',  // 新增用户输入的问题
      };
    },
    created() {
      this.createSseConnect();
    },
    methods: {
      // 建立连接
      createSseConnect() {
        let clientId = 1;
        if (window.EventSource) {
          const eventSource = new EventSource(
            `${service.defaults.baseURL}/xuanyuan/sse/connect?clientId=` + clientId
          );
          console.log(eventSource);

          eventSource.onmessage = (event) => {
            console.log('onmessage: ' + event.data);
            this.message = this.message + event.data;
            this.messageArray.push({ type: 'message', content: event.data });

            try {
              const parsedMessage = JSON.parse(event.data);
              console.log('Parsed Message:', parsedMessage);

              // 继续处理消息
              this.message = this.message + event.data;
            } catch (error) {
              console.error('Error parsing JSON:', error);
            }
          };
  
          eventSource.onopen = (event) => {
            console.log('onopen:' + event);
          };
  
          eventSource.onerror = (event) => {
            console.log('onerror:' + event);
          };
  
          eventSource.close = (event) => {
            console.log('close :' + event);
          
            eventSource.onerror = (event) => {
          console.error('SSE连接发生错误:', event);
          };
          };
        } else {
          console.log('你的浏览器不支持SSE~');
        }
        console.log(' 测试 打印');
      },
      sendQuestion() {
      const clientId = 1;
      const question = this.userInput.trim();

      if (question !== '') {
        console.log('你好')
        // 向后端发送请求
        const apiUrl = `${service.defaults.baseURL}/xuanyuan/sse/chat`;

        axios.post(apiUrl + `?clientId=${clientId}&question=${question}`)
          .then(response => {
            console.log(apiUrl)
            // 根据需要处理响应
            const responseData = response.data;

            // 在前端的消息历史记录中添加后端返回的消息
            this.messageArray.push({ type: 'user-input', content: this.userInput });
          

          // 清空用户输入字段
          this.userInput = '';

          })
          .catch(error => {
            console.error('发送问题时出错:', error);
            // 清空用户输入字段
          this.userInput = '';

          });

      } else {
        // 用户输入为空的情况
        alert('请先输入问题');
      }
    },
    goToDashboard() {
        // 使用 Vue Router 跳转到主页
        this.$router.push('/dashboard');
    }
  },
};
  </script>
  
  <style scoped>
  /* Add any scoped styles here if needed */
  .chat-container {
  display: flex;
   flex-direction: column;
   height: 100vh;
 }
 .card-header b {
    margin-top: 40px; /* 调整间距大小 */
}
.card-header h1 {
    margin-top: 20px;
    margin-bottom: 40px; /* 调整间距大小 */
}
 .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
 .card-header button {
  background-color: #fbd4db; /* 浅红色 */
    color: #fff;
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-left: 30px;

}

  .message-container {
  flex-grow: 1;
  overflow-y: auto;
  padding: 20px;
  
}
.user-text {
  margin-bottom: 10px;
  padding: 10px;
  background-color: #b3e0f2; /* 浅蓝色 */
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
  transition: box-shadow 0.3s; /* 添加过渡效果 */
}

.bot-text {
  margin-bottom: 10px;
  padding: 10px;
  background-color: #fbd4db; /* 浅粉色 */
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
  transition: box-shadow 0.3s; /* 添加过渡效果 */
}
.user-text:hover, .bot-text:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 悬停时更明显的 box-shadow */
}
  .input-container {
    position: fixed;
    bottom: 10px;
    left: 10px;
    right: 10px;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 10px;
    background-color: #f5f5f5;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  textarea {
    flex-grow: 1;
    height: 50px; /* Adjust the height as needed */
    margin-right: 10px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    resize: none;
  }

  button {
    background-color: #3498db; /* Blue color */
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
  }

  button:hover {
    background-color: #2980b9; /* Darker blue on hover */
  }
  .message-spacing {
  height: 0px; /* 设置间距的高度，根据需要调整 */
}

  </style>