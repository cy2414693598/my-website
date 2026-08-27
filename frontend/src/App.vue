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
    <!-- 注：这里刻意不加 <transition> 路由过渡。曾试过 out-in 模式，
         但它会等"旧页离场动画结束"才挂新页——在暂停 CSS 动画的环境
         （后台标签页/内嵌视图）里离场事件永远不触发，页面直接白屏。
         原则：动画只能是锦上添花，绝不能阻塞内容渲染 -->
    <router-view />
  </main>

  <footer class="site-footer">
    <!-- {{ }} 插值：Vue 把 JS 表达式的结果渲染进 HTML -->
    <p>© {{ new Date().getFullYear() }} SeeU · Vue 3 + Vite 构建 · GitHub Pages 托管</p>
  </footer>
</template>
