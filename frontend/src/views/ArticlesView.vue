<script setup>
import { articles } from '../data/articles'
</script>

<template>
  <section class="articles-page">
    <h1>文章</h1>
    <p class="page-desc">嵌入式与全栈双线笔记 · 共 {{ articles.length }} 篇</p>

    <div class="article-list">
      <!-- 整张卡片可点：router-link 渲染成 <a>，但由 Vue Router 接管，不整页刷新 -->
      <router-link
        v-for="a in articles"
        :key="a.id"
        :to="`/articles/${a.id}`"
        class="article-card"
      >
        <h2>{{ a.title }}</h2>
        <p class="meta">{{ a.date }} · {{ a.tags.join(' / ') }}</p>
        <p class="summary">{{ a.summary }}</p>
      </router-link>
    </div>
  </section>
</template>
