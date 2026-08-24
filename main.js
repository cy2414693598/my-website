// main.js — 阶段 1 的小交互
// 约定：script 标签放在 body 末尾，保证执行时上面的元素已加载完毕

// 1. 页脚年份自动更新，跨年不用手改
document.getElementById('year').textContent = new Date().getFullYear();

// 2. 根据访问时间生成问候语，页面有一点点"活"的感觉
const hour = new Date().getHours();
let greeting = '你好，我是';
if (hour < 6) {
  greeting = '夜深了，我是';
} else if (hour < 12) {
  greeting = '早上好，我是';
} else if (hour < 18) {
  greeting = '下午好，我是';
} else {
  greeting = '晚上好，我是';
}
document.getElementById('greeting').textContent = greeting;

// 3. 深浅色主题切换（借鉴 Josh Comeau 站上的深色模式开关）
// 思路：<html> 上的 data-theme 属性是总开关，CSS 里 [data-theme="dark"]
// 会覆盖亮色变量；localStorage 负责跨刷新记住选择
const themeBtn = document.getElementById('theme-toggle');

if (localStorage.getItem('theme') === 'dark') {
  document.documentElement.dataset.theme = 'dark';
  themeBtn.textContent = '☀️';
}

themeBtn.addEventListener('click', () => {
  const goingDark = document.documentElement.dataset.theme !== 'dark';
  if (goingDark) {
    document.documentElement.dataset.theme = 'dark';
    themeBtn.textContent = '☀️';
    localStorage.setItem('theme', 'dark');
  } else {
    delete document.documentElement.dataset.theme;
    themeBtn.textContent = '🌙';
    localStorage.setItem('theme', 'light');
  }
});
