<template>
    <div class="sidebar">
      <el-menu
        class="sidebar-el-menu"
        :default-active="onRoutes"
        :collapse="sidebar.collapse"
        background-color="#324157"
        text-color="#bfcbd9"
        active-text-color="#20a0ff"
        unique-opened
        router
      >
        <el-menu-item
          v-for="item in items"
          :index="item.index"
          :key="item.index"
          
        >
          <el-icon>
            <component :is="item.icon"></component>
          </el-icon>
          <span>{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </div>
  </template>
  
  
  <script setup lang="ts">
  import { computed } from 'vue';
  import { useSidebarStore } from '../store/sidebar';
  import { useRoute } from 'vue-router';
  
  
  const items = [
    {
        icon: 'Calendar',
        index: '/dashboard',
        title: '系统首页',
        permiss: '2',
  
    },
    {
        icon: 'Odometer',
        index: '/news',
        title: '热点新闻',
        permiss: '1',
    },
    {
        icon: 'Calendar',
        index: '/sse/connect',
        title: '智慧问答',
        permiss: '2',
  
    },
    {
        icon: 'Calendar',
        index: '/404',
        title: '智慧分析',
        permiss: '2',
  
    },
   
    
  ];
  
  const route = useRoute();
  const onRoutes = computed(() => {
    return route.path;
  });
  
  const sidebar = useSidebarStore();
  </script>
  
  <style scoped>
  .sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
  }
  .sidebar::-webkit-scrollbar {
    width: 0;
  }
  .sidebar-el-menu:not(.el-menu--collapse) {
    width: 200px;
  }
  .sidebar > ul {
    height: 100%;
  }
  
  </style>