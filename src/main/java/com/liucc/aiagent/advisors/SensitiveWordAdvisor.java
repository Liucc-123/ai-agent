package com.liucc.aiagent.advisors;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import reactor.core.publisher.Flux;

public class SensitiveWordAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordAdvisor.class);

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 11;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        logger.debug("SensitiveWordAdvisor - BEFORE call: {}", advisedRequest.userText());

        // 模拟敏感词检查
        if (containsSensitiveWord(advisedRequest.userText())) {
            logger.warn("SensitiveWordAdvisor - Detected sensitive word in request: {}", advisedRequest.userText());
            return new AdvisedResponse(ChatResponse.builder()
                    .generations(List.of(new Generation(new AssistantMessage("检测到违禁词，请修改您的输入。"))))
                    .build(),
                    advisedRequest.adviseContext());
        }
        return chain.nextAroundCall(advisedRequest);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        logger.debug("SensitiveWordAdvisor - BEFORE stream: {}", advisedRequest.userText());

        return chain.nextAroundStream(advisedRequest);
    }

    private boolean containsSensitiveWord(String text) {
        // 这是一个简化的敏感词检查逻辑，实际应用中会更复杂
        String[] sensitiveWords = { "死亡", "虚无", "人身攻击" };
        for (String word : sensitiveWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}