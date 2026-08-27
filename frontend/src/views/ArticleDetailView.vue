<script setup>
import { ref, computed, onMounted, watch, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { marked } from 'marked'
// 只导入常用语言包，比全量导入 highlight.js 小很多——打包体积意识
import hljs from 'highlight.js/lib/common'
import { articles } from '../data/articles'

const route = useRoute()
// computed：依赖变了自动重算。id 变了，article 自动跟着变
const article = computed(() => articles.find((a) => a.id === route.params.id))
const html = ref('')

async function render() {
  if (!article.value) return
  // Markdown → HTML（marked 只负责转换）
  html.value = marked.parse(article.value.content)
  // nextTick：等 Vue 把 v-html 真正写进 DOM 之后再做 DOM 操作。
  // 不能用 requestAnimationFrame——页面在后台标签页时 rAF 会被浏览器挂起，
  // 高亮就永远不会执行（真实踩坑：不可见的 webview 里测试高亮失效）
  await nextTick()
  document.querySelectorAll('pre code').forEach((el) => {
    hljs.highlightElement(el)
  })
}

onMounted(render)
// 同一个组件实例被复用（文章A→文章B）时，watch 路由参数重新渲染
watch(() => route.params.id, render)
</script>

<template>
  <article v-if="article" class="article-detail">
    <router-link to="/articles" class="back">← 返回文章列表</router-link>
    <h1>{{ article.title }}</h1>
    <p class="meta">{{ article.date }} · {{ article.tags.join(' / ') }}</p>

    <!-- v-html：把 HTML 字符串渲染进页面。
         注意：这里的内容是自己写的 Markdown，没有 XSS 风险；
         将来接入用户提交的内容时，必须换成带白名单过滤的渲染方案 -->
    <div class="markdown-body" v-html="html"></div>
  </article>

  <div v-else class="article-detail">
    <p>文章不存在，可能已被删除。</p>
    <router-link to="/articles">← 返回文章列表</router-link>
  </div>
</template>
