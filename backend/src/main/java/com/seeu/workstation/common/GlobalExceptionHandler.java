package com.seeu.workstation.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理器 —— 所有 Controller 抛出的异常在这里"收口"。
 *
 * @RestControllerAdvice = AOP 思想：不用在每个接口里 try-catch，
 * 异常处理逻辑只写一次，全站生效。这是"关键路径收口"设计（D4 同款思想）。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 排查现场靠它：吞异常不打日志 = 排查时两眼一抹黑（真实踩坑后的补救） */
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** 业务异常：按异常自带的状态码返回 */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result<Void>> handleBiz(BizException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(Result.error(e.getCode(), e.getMessage()));
    }

    /** 参数校验失败（@NotBlank 等被触发）：统一 400，把第一个字段的错误说清楚 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + "：" + err.getDefaultMessage())
                .orElse("参数错误");
        return ResponseEntity.badRequest().body(Result.error(40001, msg));
    }

    /**
     * 访问了不存在的路径。Spring Boot 3.2+ 会抛 NoResourceFoundException——
     * 如果没有这个专门处理，它会落进下面的兜底 handler 变成 500。
     * （真实踩坑：接口还没写时访问 /api/articles 返回了 500 而不是 404）
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Result<Void>> handleNoResource(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Result.error(40401, "接口不存在：" + e.getResourcePath()));
    }

    /** 兜底：没预料到的异常一律 500，且不把内部堆栈泄漏给调用方——但自己必须留全案底 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleUnknown(Exception e) {
        log.error("未预期异常：", e);
        return ResponseEntity.internalServerError()
                .body(Result.error(50000, "服务器开小差了，请稍后再试"));
    }
}
