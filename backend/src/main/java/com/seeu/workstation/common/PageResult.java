package com.seeu.workstation.common;

import java.util.List;

/**
 * 分页返回的统一包装（ROADMAP 阶段 3 定的分页约定）。
 * 前端拿到 list 渲染列表，拿 total 画页码。
 */
public record PageResult<T>(List<T> list, long total, int page, int size) {
}
