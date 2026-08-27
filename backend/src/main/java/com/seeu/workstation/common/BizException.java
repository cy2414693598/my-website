package com.seeu.workstation.common;

import org.springframework.http.HttpStatus;

/**
 * 业务异常 —— Service 层发现"业务上不对"时抛出（如文章不存在），
 * 由 GlobalExceptionHandler 统一转成 HTTP 状态码 + Result 结构。
 *
 * 设计要点：业务码 code（前端用）与 HTTP 状态 httpStatus（传输层用）分开携带，
 * 比如"文章不存在"= 业务码 40404 + HTTP 404。
 */
public class BizException extends RuntimeException {

    private final int code;
    private final HttpStatus httpStatus;

    public BizException(int code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
