# SeeU 的个人成长工作站

> 建站的过程练技术，用站的过程练自己。

一个从零手写的全栈个人平台：学习笔记、每日训练（演讲 / 八股 / 思维体操）、创作与生活，集成一处。首要用户是站长本人——先打磨自己和网站，暂不宣传。

**线上地址**：https://cy2414693598.github.io/my-website/

## 技术栈

| 层 | 选型 |
|---|---|
| 前端 | Vue 3 + Vite + Vue Router（`frontend/`） |
| 后端 | Java 21 + Spring Boot 3 + MyBatis-Plus（阶段 3 起） |
| 数据库 | MySQL 8 |
| 部署 | GitHub Pages + GitHub Actions（push 即自动上线）；阶段 7 起后端本地 / 云混合 |

## 仓库结构

```
frontend/               Vue 3 前端工程
docs/REQUIREMENTS.md    需求基线：定位、决策记录 D1~D9、用例图、AI 功能阶梯
ROADMAP.md              8 阶段学习路线图 + 完成记录
.github/workflows/      CI/CD：自动构建部署到 GitHub Pages
```

## 本地开发

```bash
cd frontend
npm install
npm run dev      # 开发服务器（热更新，改代码即时可见）
npm run build    # 生产构建（部署由 Actions 自动完成，无需手动发布）
```

## 关于这个仓库

这是我的全栈学习项目：每个功能对应一项技术，网站与站长共同成长。完整路线见 [ROADMAP.md](ROADMAP.md)，需求与架构决策见 [docs/REQUIREMENTS.md](docs/REQUIREMENTS.md)——包括为什么手搓而不用脚手架（D5）、训练类功能为什么是统一引擎（D8）。
