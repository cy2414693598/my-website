package com.seeu.workstation.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seeu.workstation.common.BizException;
import com.seeu.workstation.common.PageResult;
import com.seeu.workstation.mapper.ArticleMapper;
import com.seeu.workstation.model.Article;
import com.seeu.workstation.model.ArticleCreateRequest;
import com.seeu.workstation.model.ArticleUpdateRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 文章业务层 —— Controller 只管"翻译 HTTP"，业务规则全部在 Service。
 *
 * 阶段 4：存储从 ConcurrentHashMap 换成 MySQL（经 ArticleMapper）。
 * 注意 Controller 一个字都没改——方法签名不变，实现随便换，
 * 这就是分层的第一笔红利兑现。
 */
@Service
public class ArticleService {

    /** 业务错误码约定：40404 = 文章不存在（404 + 04 序号），前端凭 code 做精确提示 */
    private static final int CODE_NOT_FOUND = 40404;

    private final ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    /**
     * 首次启动播种三篇文章。幂等：只在空表时执行——
     * 重启不会重复插入（这正是"数据持久化"的意义所在）。
     */
    @PostConstruct
    public void seedIfEmpty() {
        Long count = articleMapper.selectCount(null);
        if (count != null && count > 0) {
            return;
        }
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

    /** 分页列表：新的在前。selectPage 需要分页插件（见 MybatisPlusConfig） */
    public PageResult<Article> list(int page, int size) {
        Page<Article> result = articleMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<Article>().orderByDesc(Article::getId));
        return new PageResult<>(result.getRecords(), result.getTotal(), page, size);
    }

    public Article getById(Long id) {
        Article a = articleMapper.selectById(id);
        if (a == null) {
            throw new BizException(CODE_NOT_FOUND, "文章不存在，id=" + id, HttpStatus.NOT_FOUND);
        }
        return a;
    }

    public Article create(ArticleCreateRequest req) {
        Article a = new Article();
        a.setTitle(req.title());
        a.setSlug(slugify(req.title()));
        a.setContentMd(req.contentMd());
        a.setSummary(req.summary() == null ? "" : req.summary());
        a.setTags(req.tags() == null ? "" : req.tags());
        a.setVisibility(0);   // 默认公开（D9）
        a.setStatus(1);       // 默认已发布：草稿流程在阶段 6 后台上线后启用
        a.setCreatedAt(LocalDateTime.now());
        a.setUpdatedAt(LocalDateTime.now());
        // insert 后主键自动回填进实体（@TableId(IdType.AUTO) 的功劳），直接返回即可
        articleMapper.insert(a);
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
        articleMapper.updateById(a);
        return a;
    }

    public void delete(Long id) {
        getById(id); // 先确认存在，不存在同样 404
        articleMapper.deleteById(id);
    }

    /** 标题 → URL 友好的 slug：中文原样保留，空白转连字符 */
    private String slugify(String title) {
        String s = title.trim().replaceAll("\\s+", "-");
        return s.length() > 80 ? s.substring(0, 80) : s;
    }
}
