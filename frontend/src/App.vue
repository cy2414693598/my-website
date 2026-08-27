<script setup>
import ThemeToggle from './components/ThemeToggle.vue'
</script>

<template>
  <!-- 全站骨架：顶部导航 + 路由出口 + 页脚。
       router-view 是 Vue Router 的"占位符"，当前路由匹配到哪个页面组件，它就渲染谁 -->
  <header class="site-header">
    <router-link to="/" class="logo">SeeU</router-link>
    <nav class="site-nav">
      <router-link to="/">主页</router-link>
      <router-link to="/articles">文章</router-link>
      <ThemeToggle />
    </nav>
  </header>

  <main class="page">
    <router-view />
  </main>

  <footer class="site-footer">
    <!-- {{ }} 插值：Vue 把 JS 表达式的结果渲染进 HTML -->
    <p>© {{ new Date().getFullYear() }} SeeU · Vue 3 + Vite 构建 · GitHub Pages 托管</p>
  </footer>
</template>
