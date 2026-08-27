package com.seeu.workstation.common;

/**
 * 统一返回结构 —— 阶段 3 开工前定的 API 规范（ROADMAP 阶段 3）。
 * 所有接口的响应体都是 { code, message, data }：
 *   code = 0     成功
 *   code = 4xxxx 客户端错误（40001 参数错、40404 资源不存在……）
 *   code = 5xxxx 服务端错误
 *
 * record 是 Java 14+ 的"不可变数据载体"：一行声明顶替构造器/getter/equals/toString。
 * DTO、返回包装这类"纯搬运数据"的类，现代 Java 一律用 record。
 */
public record Result<T>(int code, String message, T data) {

    public static <T> Result<T> ok(T data) {
        return new Result<>(0, "ok", data);
    }

    public static Result<Void> ok() {
        return ok(null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
}
