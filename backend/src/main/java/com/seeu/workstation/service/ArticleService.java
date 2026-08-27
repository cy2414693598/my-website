package com.seeu.workstation.service;

import com.seeu.workstation.common.BizException;
import com.seeu.workstation.common.PageResult;
import com.seeu.workstation.model.Article;
import com.seeu.workstation.model.ArticleCreateRequest;
import com.seeu.workstation.model.ArticleUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 文章业务层 —— Controller 只管"翻译 HTTP"，业务规则全部在 Service。
 *
 * 阶段 3 的存储方案：ConcurrentHashMap（内存）。
 *   线程安全：多个请求并发读写同一张 Map 不炸（Web 是多线程的）
 *   AtomicLong：无并发冲突的自增 id 发号器
 * 阶段 4 把 store 换成 MyBatis-Plus 的 Mapper，方法签名不变——
 * 这就是分层的意义：换实现不换接口。
 */
@Service
public class ArticleService {

    /** 业务错误码约定：40404 = 文章不存在（404 + 04 序号），前端凭 code 做精确提示 */
    private static final int CODE_NOT_FOUND = 40404;

    private final Map<Long, Article> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    public ArticleService() {
        // 种子数据：和前端 frontend/src/data/articles.js 的三篇一致，
        // 阶段 5 前端改为调这些接口时，看到的内容就是熟的
        create(new ArticleCreateRequest(
                "STM32 串口接收的三种姿势：轮询、中断、DMA + 空闲中断",
                "## 一、轮询\n\n```c\nwhile (!(USART1->SR & USART_SR_RXNE));\n```\n\n（完整内容见前端文章，此处为接口演示正文）",
                "从死等标志位到 DMA 配空闲中断，串口接收的演进史。",
                "STM32,嵌入式,C"));
        create(new ArticleCreateRequest(
                "三天上线第一个网站：一个嵌入式工程师的 Web 入门实录",
                "## 第一关：环境\n\n从 Node.js 到 Git push，工具链思维全是加分项。",
                "从「Node.js 是什么」到个人主页跑在 https 上。",
                "全栈,建站,Git"));
        create(new ArticleCreateRequest(
                "嵌入式工程师为什么要学 Web 全栈：技术栈拓展笔记",
                "不是转型，是拓展。嵌入式 + Web 的交叉地带正在变成高价值区。",
                "设备看板、OTA 管理、数据可视化——交叉地带的机会。",
                "全栈,思考"));
    }

    /** 分页列表：按 id 倒序（新的在前），跳过前 (page-1)*size 条 */
    public PageResult<Article> list(int page, int size) {
        List<Article> all = store.values().stream()
                .sorted(Comparator.comparing(Article::getId).reversed())
                .toList();
        int from = (page - 1) * size;
        List<Article> pageList = all.stream()
                .skip(from)
                .limit(size)
                .toList();
        return new PageResult<>(pageList, all.size(), page, size);
    }

    public Article getById(Long id) {
        Article a = store.get(id);
        if (a == null) {
            throw new BizException(CODE_NOT_FOUND, "文章不存在，id=" + id, HttpStatus.NOT_FOUND);
        }
        return a;
    }

    public Article create(ArticleCreateRequest req) {
        Article a = new Article();
        a.setId(idGen.incrementAndGet());
        a.setTitle(req.title());
        a.setSlug(slugify(req.title()));
        a.setContentMd(req.contentMd());
        a.setSummary(req.summary() == null ? "" : req.summary());
        a.setTags(req.tags() == null ? "" : req.tags());
        a.setVisibility(0);   // 默认公开（D9）
        a.setStatus(1);       // 默认已发布：草稿流程在阶段 6 后台上线后启用
        a.setCreatedAt(LocalDateTime.now());
        a.setUpdatedAt(LocalDateTime.now());
        store.put(a.getId(), a);
        return a;
    }

    /** 部分更新：请求里传了哪个字段就改哪个，updatedAt 总是刷新 */
    public Article update(Long id, ArticleUpdateRequest req) {
        Article a = getById(id); // 不存在会抛 40404
        if (req.title() != null) a.setTitle(req.title());
        if (req.contentMd() != null) a.setContentMd(req.contentMd());
        if (req.summary() != null) a.setSummary(req.summary());
        if (req.tags() != null) a.setTags(req.tags());
        a.setUpdatedAt(LocalDateTime.now());
        return a;
    }

    public void delete(Long id) {
        getById(id); // 先确认存在，不存在同样 404
        store.remove(id);
    }

    /** 标题 → URL 友好的 slug：中文原样保留，空白转连字符 */
    private String slugify(String title) {
        String s = title.trim().replaceAll("\\s+", "-");
        return s.length() > 80 ? s.substring(0, 80) : s;
    }
}
