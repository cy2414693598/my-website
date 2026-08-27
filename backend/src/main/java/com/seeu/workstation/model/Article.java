package com.seeu.workstation.model;

import java.time.LocalDateTime;

/**
 * 文章实体 —— 字段与 docs/REQUIREMENTS.md §10 ER 图一一对应。
 * 阶段 3 数据存内存（Service 里的 Map）；阶段 4 这个类直接映射成 MySQL 的 article 表。
 *
 * 为什么不用 record：实体有"生命周期中被修改"的需求（更新时改字段），
 * record 是不可变的，适合 DTO 不适合实体——两种形态各有正当用途。
 */
public class Article {

    private Long id;
    /** D10 多用户预留：阶段 6 之前恒为 null */
    private Long authorId;
    private String title;
    /** URL 标识，如 stm32-usart-receive（前端路由用） */
    private String slug;
    /** Markdown 正文 */
    private String contentMd;
    private String summary;
    /** 逗号分隔标签，V2 升级为独立标签表 */
    private String tags;
    /** D9 可见性：0 公开 / 1 私密（登录后可见） */
    private Integer visibility;
    /** 0 草稿 / 1 已发布 / 2 下架（与 §7 发布流程图的状态一一对应） */
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getContentMd() { return contentMd; }
    public void setContentMd(String contentMd) { this.contentMd = contentMd; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public Integer getVisibility() { return visibility; }
    public void setVisibility(Integer visibility) { this.visibility = visibility; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
