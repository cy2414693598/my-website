package com.seeu.workstation.model;

import jakarta.validation.constraints.NotBlank;

/**
 * 创建文章的请求体（DTO：Data Transfer Object，只负责搬运数据）。
 *
 * 与实体的区别：DTO 只含"客户端该提供的字段"——id、createdAt 由服务端生成，
 * 绝不让前端传（否则等于把主键和时间的决定权交了出去）。
 * @NotBlank 触发后由 GlobalExceptionHandler 统一转成 400。
 */
public record ArticleCreateRequest(
        @NotBlank(message = "标题不能为空") String title,
        @NotBlank(message = "正文不能为空") String contentMd,
        String summary,
        String tags) {
}
