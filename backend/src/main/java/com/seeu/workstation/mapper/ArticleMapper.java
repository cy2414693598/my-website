package com.seeu.workstation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seeu.workstation.model.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章 Mapper —— 数据访问层（三层结构的第三层到齐：Controller → Service → Mapper）。
 *
 * 注意：这个接口一个方法都没写，却已经拥有 insert/deleteById/selectById/selectPage
 * 等全套 CRUD——继承 BaseMapper<Article> 白送的。这就是 ORM 的意义：
 * "单表 CRUD"这种毫无营养的代码，交给框架生成。
 *
 * 遇到复杂查询时才在这里加自定义方法（配 SQL 注解或 XML）。
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
