<script setup>
import { useTheme } from '../composables/useTheme'

const { theme, toggle } = useTheme()
</script>

<template>
  <!-- @click 是 v-on:click 的缩写；:aria-label 是 v-bind 的缩写，属性值跟着状态变 -->
  <button
    class="theme-btn"
    type="button"
    @click="toggle"
    :aria-label="theme === 'dark' ? '切换到浅色主题' : '切换到深色主题'"
  >
    {{ theme === 'dark' ? '☀️' : '🌙' }}
  </button>
</template>
