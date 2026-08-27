package com.seeu.workstation.model;

/**
 * 更新文章的请求体。与创建不同：全部字段可空 —— "传了哪个字段就改哪个"（部分更新），
 * 这也是 PUT 语义在本项目的约定。
 */
public record ArticleUpdateRequest(
        String title,
        String contentMd,
        String summary,
        String tags) {
}
