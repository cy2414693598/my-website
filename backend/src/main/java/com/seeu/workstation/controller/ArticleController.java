package com.seeu.workstation.controller;

import com.seeu.workstation.common.PageResult;
import com.seeu.workstation.common.Result;
import com.seeu.workstation.model.Article;
import com.seeu.workstation.model.ArticleCreateRequest;
import com.seeu.workstation.model.ArticleUpdateRequest;
import com.seeu.workstation.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 文章接口 —— RESTful 语义对照表（本阶段的核心学习点）：
 *
 *   GET    /api/articles        200  查列表（分页）
 *   GET    /api/articles/{id}   200  查详情；不存在 → 404 + 业务码 40404
 *   POST   /api/articles        201  创建（RESTful 惯例：新建资源返回 201 Created）
 *   PUT    /api/articles/{id}   200  更新
 *   DELETE /api/articles/{id}   204  删除（204 No Content：成功但无内容可返回——
 *                                    删除就是把资源变没了，没有 body 可给）
 *
 * Controller 的纪律：只做"HTTP ↔ Java 的翻译"，业务逻辑一行不写（全在 Service）。
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    /** 构造器注入：Spring 自动把 ArticleService 实例传进来。
        相比字段注入的优点：依赖一目了然、字段可 final、单测可手动 new */
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /** 列表。@RequestParam 接 URL 问号参数，defaultValue 让前端不传也不报错 */
    @GetMapping
    public Result<PageResult<Article>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(articleService.list(page, size));
    }

    /** 详情。@PathVariable 接路径里的 {id} */
    @GetMapping("/{id}")
    public Result<Article> detail(@PathVariable Long id) {
        return Result.ok(articleService.getById(id));
    }

    /** 创建。@RequestBody 把 JSON 自动反序列化成 DTO；
        @Valid 触发 DTO 上的 @NotBlank，不过关 → 全局异常处理 → 400 */
    @PostMapping
    public ResponseEntity<Result<Article>> create(@Valid @RequestBody ArticleCreateRequest req) {
        Article created = articleService.create(req);
        // 201 + body：告诉调用方"新资源诞生了"，并把带 id 的完整对象还回去
        return ResponseEntity.status(201).body(Result.ok(created));
    }

    /** 更新（部分更新：传哪个字段改哪个，见 Service.update） */
    @PutMapping("/{id}")
    public Result<Article> update(@PathVariable Long id,
                                  @RequestBody ArticleUpdateRequest req) {
        return Result.ok(articleService.update(id, req));
    }

    /** 删除。204 = 成功且无内容——这就是为什么这个方法不返回 Result */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
