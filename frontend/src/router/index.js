import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ArticlesView from '../views/ArticlesView.vue'
import ArticleDetailView from '../views/ArticleDetailView.vue'

// 用 hash 模式（URL 带 #）是权衡的结果：
// GitHub Pages 是纯静态托管，刷新 /articles 这种路径会直接 404（没有服务器帮你回退到 index.html）。
// 阶段 7 有了自己的 Nginx 后，再切换到 createWebHistory() 换回干净的 URL。
const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/articles', name: 'articles', component: ArticlesView },
    // 动态路由：:id 会作为 props 传给页面组件
    { path: '/articles/:id', name: 'article-detail', component: ArticleDetailView, props: true },
  ],
  // 切页回到顶部（前进时），浏览器后退时保留原位置
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 }
  },
})

export default router
