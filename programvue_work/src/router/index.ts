import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
import { usePermissStore } from '../store/permiss';
import Home from '../views/home.vue';

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/dashboard',
                name: 'dashboard',
                meta: {
                    title: '系统首页',
                    permiss: '1',
                },
                component: () => import(/* webpackChunkName: "dashboard" */ '../views/dashboard.vue'),
            }, {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心',
                    permiss: '1',
                },
                component: () => import(/* webpackChunkName: "dashboard" */ '../views/user.vue'),
            },
            {
                path: '/sse/connect',
                name: 'chat',
                meta: {
                    title: '智慧问答',
                    permiss: '2',
                },
                component: () => import(/* webpackChunkName: "403" */ '../views/chat.vue'),
            }, 
            {
                path: '/news',
                name: 'news',
                meta: {
                    title: '热点新闻',
                },
                component: () => import(/* webpackChunkName: "login" */ '../views/news.vue'),
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        meta: {
            title: '登录',
        },
        component: () => import(/* webpackChunkName: "login" */ '../views/login.vue'),
    },
    {
        path: '/404',
        name: '404',
        meta: {
            title: '无法访问',
        },
        component: () => import(/* webpackChunkName: "403" */ '../views/404.vue'),
    }, 
    {
        path: '/:catchAll(.*)', // This is a catch-all route
        name: 'NotFound',
        meta: {
            title: '无法访问',
        },
        component: () => import(/* webpackChunkName: "403" */ '../views/404.vue'),
      },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;

    if (to.path !== '/login' && to.path !== '/dashboard' && to.path !== '/user' && to.path !== '/sse/connect'&& to.path !== '/404' && to.path !== '/news') {
        next('/404');
    } else {
        next();
    }    
});

export default router;
