package com.liucc.aiagent.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liucc.aiagent.mapper.ChatMessageMapper;
import com.liucc.aiagent.model.ChatMessage;

import org.springframework.stereotype.Service;

@Service
public class ChatMessageService extends ServiceImpl<ChatMessageMapper, ChatMessage> {
}