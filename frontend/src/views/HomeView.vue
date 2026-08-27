<script setup>
import AboutSection from '../components/AboutSection.vue'
import SkillsSection from '../components/SkillsSection.vue'
import ContactSection from '../components/ContactSection.vue'

// 问候语：原阶段 1 的 JS 逻辑，现在是一个普通函数 + 模板插值
function greeting() {
  const h = new Date().getHours()
  if (h < 6) return '夜深了，我是'
  if (h < 12) return '早上好，我是'
  if (h < 18) return '下午好，我是'
  return '晚上好，我是'
}
</script>

<template>
  <div>
    <!-- 首屏 hero -->
    <section class="hero">
      <p class="hero-greeting">{{ greeting() }}</p>
      <h1>SeeU</h1>
      <p class="hero-tagline">嵌入式工程师 · 正在拓展全栈 Web 技术栈</p>
    </section>

    <!-- 页面 = 组件的组合。HomeView 不写具体内容，只负责"编排" -->
    <AboutSection />
    <SkillsSection />
    <ContactSection />
  </div>
</template>
