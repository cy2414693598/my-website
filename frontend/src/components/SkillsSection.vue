<script setup>
// 数据驱动视图：HTML 里不写死四张卡片，而是 v-for 遍历数组。
// 以后加技能只改这个数组，模板一行不动——这就是"数据驱动"的含义
const skills = [
  {
    title: '嵌入式开发',
    tags: ['STM32', 'C', 'J-Link', 'OpenOCD', 'MQTT'],
  },
  {
    title: '硬件与结构',
    tags: ['Altium Designer', 'SolidWorks', 'Creo'],
  },
  {
    title: '后端开发',
    tags: ['Java', 'Spring Boot', 'MySQL'],
  },
  {
    title: 'Web 前端',
    tags: ['HTML', 'CSS', 'JavaScript', 'Vue 3'],
  },
]
</script>

<template>
  <section id="skills" class="section">
    <h2>技能</h2>
    <ul class="skill-grid">
      <li v-for="s in skills" :key="s.title" class="skill-card">
        <h3>{{ s.title }}</h3>
        <ul class="tags">
          <!-- :key 是 Vue 追踪列表项的身份标识，用稳定唯一值（不提倡用下标） -->
          <li v-for="t in s.tags" :key="t">{{ t }}</li>
        </ul>
      </li>
    </ul>
  </section>
</template>
