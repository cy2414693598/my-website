import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/main.css'
// highlight.js 的代码配色（深色代码块，两种主题下都成立）
import 'highlight.js/styles/github-dark.css'

createApp(App).use(router).mount('#app')
