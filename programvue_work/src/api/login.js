import request from '../utils/request.js'

// 用户登录
export function login(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/user/login',
    method: 'post',
    data: data
  })
}

//获取信息
export function getInfo(token){
  return request({
    url:'/user/info',
    method:'get',
    params:{token}
  })
}

//登出
export function logout(){
  return request({
    url:'/user/logout',
    method:'post'
  })
}

//获取新闻
export function getNews() {
  return request({
    url: '/news/all',
    method: 'get',
  })
}
