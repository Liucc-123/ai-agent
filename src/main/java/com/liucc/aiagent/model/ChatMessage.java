package com.liucc.aiagent.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_message")
public class ChatMessage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String conversationId;
    private String messageType;
    private String content;
}