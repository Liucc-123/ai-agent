package com.liucc.aiagent.chatmemory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liucc.aiagent.model.ChatMessage;
import com.liucc.aiagent.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InMySqlChatMemory implements ChatMemory {

    private final ChatMessageService chatMessageService;

    // public MySqlChatMemory(ChatMessageService chatMessageService) {
    //     this.chatMessageService = chatMessageService;
    // }
    @Override
    public void add(String conversationId, Message message) {
        add(conversationId, List.of(message));
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ChatMessage> chatMessages = messages.stream()
                .map(message ->  ChatMessage.builder()
                        .conversationId(conversationId)
                        .messageType(message.getMessageType().getValue())
                        .content(message.getText())
                        .build())
                .collect(Collectors.toList());
        chatMessageService.saveBatch(chatMessages);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversation_id", conversationId)
                .orderByDesc("id")
                .last("LIMIT " + lastN);
        List<ChatMessage> chatMessages = chatMessageService.list(queryWrapper);
        return chatMessages.stream()
                .map(chatMessage -> (Message) new UserMessage(chatMessage.getContent())) // Simplified for example
                .collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversation_id", conversationId);
        chatMessageService.remove(queryWrapper);
    }
}