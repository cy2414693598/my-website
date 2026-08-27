import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// base 很关键：GitHub Pages 把网站挂在 /my-website/ 子路径下，
// 不配这个，打包后的 JS/CSS 引用会全部 404 —— 这是 GH Pages + SPA 的经典天坑
export default defineConfig({
  base: '/my-website/',
  plugins: [vue()],
})
