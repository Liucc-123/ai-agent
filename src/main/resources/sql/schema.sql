-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `ai-agent` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE `ai-agent`;

-- 如果存在则删除表（可选，便于开发）
-- DROP TABLE IF EXISTS `chat_message`;

-- 创建表 chat_message
CREATE TABLE IF NOT EXISTS `chat_message` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `conversation_id` VARCHAR(255) NULL COMMENT '会话ID',
    `message_type` VARCHAR(255) NULL COMMENT '消息类型',
    `content` TEXT NULL COMMENT '消息内容',
    INDEX `idx_conversation_id` (`conversation_id`) -- 为 conversation_id 添加索引以加快查询速度
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天消息表';