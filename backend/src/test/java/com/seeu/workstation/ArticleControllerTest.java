package com.seeu.workstation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 项目的第一批自动化测试（阶段 3 起步，补"六块"里最薄的测试块）。
 *
 * MockMvc：不启动真实 HTTP 盏听，在内存里模拟请求 → 响应全流程，
 * 毫秒级跑完，适合接口回归——以后改代码跑一遍，防"改 A 坏 B"。
 *
 * 这三个测试就是接口的"活文档"：看断言就知道接口承诺了什么。
 */
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void healthShouldReturnUp() throws Exception {
        mvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.status").value("up"));
    }

    @Test
    @org.junit.jupiter.api.Disabled("作业：写完 ArticleController 后删掉这行注解，测试转绿即通关")
    void articleCrudFlowShouldWork() throws Exception {
        // ① 创建 → 201 + 返回完整对象（含服务端生成的 id）
        String body = """
                {"title":"测试文章","contentMd":"# 正文","tags":"测试"}
                """;
        String location = mvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("测试文章"))
                .andExpect(jsonPath("$.data.id").exists())
                .andReturn().getResponse().getContentAsString();
        long id = com.jayway.jsonpath.JsonPath.read(location, "$.data.id");

        // ② 查详情 → 拿得到刚创建的
        mvc.perform(get("/api/articles/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("测试文章"));

        // ③ 更新 → 字段变化
        mvc.perform(put("/api/articles/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"改过的标题\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("改过的标题"));

        // ④ 删除 → 204 无 Body
        mvc.perform(delete("/api/articles/" + id))
                .andExpect(status().isNoContent());

        // ⑤ 再查 → 404 + 业务码 40404
        mvc.perform(get("/api/articles/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(40404));
    }

    @Test
    @org.junit.jupiter.api.Disabled("作业：同上，写完 Controller 后启用")
    void blankTitleShouldBeRejected() throws Exception {
        mvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"contentMd\":\"正文\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(40001));
    }
}
