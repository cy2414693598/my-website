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
    <!-- 主行：版权 + 本站开源（能力证明型网站的关键链接） -->
    <p class="footer-copy">
      © {{ new Date().getFullYear() }} SeeU ·
      <a href="https://github.com/cy2414693598/my-website" target="_blank" rel="noopener">本站开源</a>
    </p>
    <!-- 次行：弱化小字。技术署名（colophon）放配角位；
         阶段 7 若用国内服务器，ICP 备案号按法规要加在这一行 -->
    <p class="footer-meta">嵌入式工程师的全栈学习笔记 · Powered by Vue 3 &amp; GitHub Pages</p>
  </footer>
</template>
