import { ref, watch } from 'vue'

// 主题管理：和阶段 1 的 main.js 逻辑完全相同，只是搬进了 Vue 的响应式世界。
// 模块级 state 是"单例"——所有组件共享同一个 theme，一处切换全站生效
const theme = ref(localStorage.getItem('theme') === 'dark' ? 'dark' : 'light')

function applyTheme(t) {
  if (t === 'dark') {
    document.documentElement.dataset.theme = 'dark'
  } else {
    delete document.documentElement.dataset.theme
  }
  localStorage.setItem('theme', t)
}

applyTheme(theme.value)
watch(theme, applyTheme)

export function useTheme() {
  const toggle = () => {
    theme.value = theme.value === 'dark' ? 'light' : 'dark'
  }
  return { theme, toggle }
}
