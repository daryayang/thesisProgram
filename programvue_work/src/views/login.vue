<template>
	<div class>
	 <div class=header>
	  </div>
	  <div class="background"></div>
	  <div class="container">
			<div class="item">
				<h2 class="logo"><i class='bx bxl-xing'></i>NeuroFinance Hub</h2>
				<div class="text-item">
					<h2>基于轩辕-70b的金融智能应用平台</h2>
		
				</div>
			</div>
			<div class="login-section">
				<div class="form-box login">
					<form action="">
						<h2>用户登录</h2>
						<el-form ref="form" :model="loginForm" :rules="rules" label-position="left" label-width="0">
						<el-form-item prop="username">
							<el-input class="input-box" v-model="loginForm.username" placeholder="请输入用户名" clearable></el-input>
						</el-form-item>
						<el-form-item prop="password">
							<el-input class="input-box" type="password" v-model="loginForm.password" placeholder="请输入密码" show-password></el-input>
						</el-form-item>
						<div class="space" ></div>
	
						<div class="remember-password">
							<a href="#">忘记密码？</a>
						</div>
						<el-form-item>
						<el-button class="btn" type="primary" @click="do_login" :loading="loading">登录</el-button>
						</el-form-item>
						</el-form>
	
					</form>
				</div>
			</div>
		</div>
	</div>
	</template>
	
	<script>
	import {login} from '../api/login.js'
	import {setToken} from '../utils/auth'
	import { ElMessage } from 'element-plus';

	
	export default {
	  data() {
		return {
		  loginForm: {
			username: 'admin',
			password: '123123'
		  },
		  rules: {
			username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
			password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
		  },
		  loading: false
		}
	  },
	  methods: {
		async do_login() {
    try {
        this.loading = true;
        const response = await login(this.loginForm.username, this.loginForm.password);

        if (response.data && response.data.token) {
            const token = response.data.token;
            setToken(token);
			this.$router.push({ path: '/dashboard' });
			console.log(token);
			console.log('token');
			ElMessage.success('登录成功');

        } else {
            console.error('Login failed: Token not found in response', response);
            this.$message.error('登录失败：无法获取令牌');
        }
    } catch (error) {
        console.error('Login failed:', error);
        this.$message.error('用户名或密码错误');
    } finally {
        this.loading = false;
    }
}
	},
	}
	</script>
	
	
	<style scoped>
	@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,300;0,400;0,500;1,500&display=swap');
	*{
		margin: 0;
		padding: 0;
		box-sizing: border-box;
		font-family: "poppins",sans-serif;
	}
	.space {
	  height: 10px;
	}
	
	body{
		height: 100vh;
		width: 100%;
		background: #000;
	}
	.background{
		background: url(../assets/img/background.jpeg) no-repeat;
		background-position: center;
		background-size:cover ;
		height: 100vh;
		width: 100%;
		filter: blur(10px);
	}
	
	.header{
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		padding: 25px 13%;
		background: transparent;
		display: flex;
		justify-content: space-between;
		align-items: center;
		z-index: 100;
	
	}
	.navbar a{
		position: relative;
		font-size: 16px;
		color: #fff;
		margin-right: 30px;
		text-decoration: none;
	}
	.navbar a::after{
		content: "";
		position: absolute;
		left: 0;
		width: 100%;
		height: 2px;
		background: #fff;
		bottom: -5px;
		border-radius: 5px;
		transform: translateY(10px);
		opacity: 0;
		transition: .5s ease;
	}
	.navbar a:hover:after{
		transform: translateY(0);
		opacity: 1;
	}
	.search-bar{
		width: 250px;
		height: 45px;
		background-color: transparent;
		border: 2px solid #fff;
		border-radius: 6px;
		display: flex;
		align-items: center;
	}
	.search-bar input{
		width: 100%;
		background-color: transparent;
		border: none;
		outline: none;
		color: #fff;
		font-size: 16px;
		padding-left: 10px;
	}
	.search-bar button{
		width: 40px;
		height: 100%;
		background: transparent;
		outline: none;
		border: none;
		color: #fff;
		cursor: pointer;
	}
	.search-bar input::placeholder{
		color: #fff;
	}
	.search-bar button i{
		font-size: 22px;
	}
	.container{
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%,-50%);
		width: 75%;
		height: 550px;
		margin-top: 20px;
		background: url(../assets/img/background.jpeg) no-repeat;
		background-position: center;
		background-size:cover ;
		border-radius: 20px;
		overflow: hidden;
		
		
	}
	.item{
		position: absolute;
		top: 0;
		left: 0;
		width: 58%;
		height: 100%;
		color: #fff;
		background: transparent;
		padding: 80px;
		display: flex;
		justify-content: space-between;
		flex-direction: column;
		
	
	
	}
	.item .logo{
		color: rgb(46, 213, 239);
		font-size: 40px;
		margin: 20px ;
	
	}
	.text-item h2{
		font-size: 20px;
		line-height: 1;
	}
	.text-item p{
		font-size: 16px;
		margin: 20px 0;
	}
	.social-icon a i{
		color: #fff;
		font-size: 24px;
		margin-left: 10px;
		cursor: pointer;
		transition: .5s ease;
	}
	.social-icon a:hover i{
		transform: scale(1.2);
	}
	.container .login-section{
		position: absolute;
		top: 0;
		right: 0;
		width: calc(100% - 58%);
		height: 100%;
		color: #fff;
		backdrop-filter: blur(10px);
	}
	
	.login-section .form-box{
		position: absolute;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 100%;
		
	}
	.login-section .form-box.register{
		transform: translateX(430px);
		transition: transform .6s ease;
		transition-delay: 0s;
	}
	.login-section.active .form-box.register{
		transform: translateX(0px);
		transition-delay: .7s;
	}
	
	.login-section .form-box.login{
		transform: translateX(0px);
		transition: transform .6s ease;
		transition-delay: 0.7s;
	}
	.login-section.active .form-box.login{
		transform: translateX(430px);
		transition-delay: 0s;
	}
	
	
	
	.login-section .form-box h2{
		text-align: center;
		font-size: 25px;
	}
	
	.form-box .input-box{
		width: 340px;
		height: 50px;
		border-bottom: 2px solid#fff;
		margin: 20px 0;
		position: relative;
	}
	.input-box input{
		width: 100%;
		height: 100%;
		background: transparent;
		border: none;
		outline: none;
		font-size: 16px;
		padding-right: 28px;
		color:black;
	}
	.input-box label{
		position: absolute;
		top: 50%;
		left: 0;
		transform: translateY(-50%);
		font-size: 16px;
		font-weight: 600px;
		pointer-events: none;
		transition: .5s ease;
	}
	.input-box .icon{
		position: absolute;
		top: 13px;
		right: 0;
		font-size: 19px;
	}
	.input-box input:focus~ label,
	.input-box input:valid~ label{
		top: -5px;
	}
	.remember-password{
		font-size: 14px;
		font-weight: 500;
		margin-top: -1px ;
		display: flex;
		justify-content: flex-end;
	}
	.remember-password label input{
		accent-color: #fff;
		margin-right: 3px;
	
	}
	.remember-password a{
		color: #fff;
		text-decoration: none;
	}
	.remember-password a:hover{
		text-decoration: underline;
	}
	.btn{
		background: #fff;
		width: 100%;
		height: 45px;
		outline: none;
		border: none;
		border-radius: 4px;
		cursor: pointer;
		background: #f72d7a;
		font-size: 16px;
		color: #fff;
		box-shadow: rgba(0,0,0,0.4);
		margin-top: 30px ;
	}
	.create-account{
		font-size: 14.5px;
		text-align: center;
		margin: 25px;
	}
	.create-account p a{
		color: #fff;
		font-weight: 600px;
		text-decoration: none;
	}
	.create-account p a:hover{
		text-decoration: underline;
	}
	</style>
	