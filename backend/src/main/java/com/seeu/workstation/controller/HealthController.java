package com.seeu.workstation.controller;

import com.seeu.workstation.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 健康检查接口 —— 后端版的 "Hello World"。
 * 用途：探活（服务器还活着吗）、阶段 7 的监控端点就长这样。
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Result<Map<String, String>> health() {
        return Result.ok(Map.of("status", "up", "app", "workstation"));
    }
}
