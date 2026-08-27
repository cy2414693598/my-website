-- ============================================================
-- workstation 库建表脚本（ER 图 §10 定稿 v1.0）
-- 幂等设计：IF NOT EXISTS，重复执行不报错不丢数据
-- 字段注释里的 D 编号 = 来源决策记录（docs/REQUIREMENTS.md §6）
-- ============================================================

-- 用户表：MVP 只有站长一个账号（D4：role 字段留缝，不用 RBAC）
CREATE TABLE IF NOT EXISTS `user` (
    `id`            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `username`      VARCHAR(50)  NOT NULL COMMENT '登录名',
    `password_hash` VARCHAR(100) NOT NULL COMMENT 'bcrypt 哈希，永不存明文',
    `role`          VARCHAR(20)  NOT NULL DEFAULT 'ADMIN' COMMENT 'D4：角色字段，演进缝',
    `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='用户';

-- 文章表：阶段 4 起被 ArticleService 真正使用
CREATE TABLE IF NOT EXISTS `article` (
    `id`         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `author_id`  BIGINT UNSIGNED NULL COMMENT 'D10 多用户预留，当前恒 NULL',
    `title`      VARCHAR(200)  NOT NULL COMMENT '标题',
    `slug`       VARCHAR(220)  NOT NULL COMMENT 'URL 标识',
    `content_md` MEDIUMTEXT    NOT NULL COMMENT 'Markdown 正文',
    `summary`    VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '摘要',
    `tags`       VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '逗号分隔，V2 升级标签表',
    `visibility` TINYINT       NOT NULL DEFAULT 0 COMMENT 'D9：0 公开 / 1 私密',
    `status`     TINYINT       NOT NULL DEFAULT 1 COMMENT '0 草稿 / 1 已发布 / 2 下架',
    `created_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY `idx_created_at` (`created_at`),
    KEY `idx_author` (`author_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='文章';

-- 训练记录表：每日训练营引擎（D7/D8），V2 启用
CREATE TABLE IF NOT EXISTS `drill` (
    `id`          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `user_id`     BIGINT UNSIGNED NULL COMMENT 'D10 多用户预留',
    `type`        TINYINT      NOT NULL COMMENT 'D8：0 演讲 / 1 八股 / 2 思维',
    `drill_date`  DATE         NOT NULL COMMENT '训练日期',
    `title`       VARCHAR(200) NOT NULL COMMENT '题面',
    `material`    TEXT         NULL COMMENT '素材或题目内容',
    `answer`      TEXT         NULL COMMENT '站长作答',
    `feedback`    TEXT         NULL COMMENT 'AI 点评',
    `score`       TINYINT      NULL COMMENT '得分',
    `next_review` DATE         NULL COMMENT '间隔重复队列：不达标时 1/3/7/21 天后重现',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY `idx_user_type` (`user_id`, `type`),
    KEY `idx_next_review` (`next_review`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='每日训练记录（D8 统一引擎）';

-- 评论表：访客评论（D6），V2 启用
CREATE TABLE IF NOT EXISTS `comment` (
    `id`         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `article_id` BIGINT UNSIGNED NOT NULL,
    `nickname`   VARCHAR(50)   NOT NULL COMMENT 'D6：访客昵称必填',
    `email`      VARCHAR(100) NULL COMMENT 'D6：选填，不公开',
    `content`    VARCHAR(2000) NOT NULL,
    `status`     TINYINT       NOT NULL DEFAULT 0 COMMENT 'D6：0 待审核 / 1 通过',
    `created_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY `idx_article` (`article_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='文章评论';
