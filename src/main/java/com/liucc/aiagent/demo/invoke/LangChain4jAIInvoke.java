package com.liucc.aiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 通过SpringAI调用AI
 */
@Component
public class SpringAIAIInvoke implements CommandLineRunner {

    @Resource
    private ChatModel dashScopeChatModel;
    @Override
    public void run(String... args) throws Exception {
        String response = dashScopeChatModel.call(new Prompt("将一个冷笑话")).getResult().getOutput().getText();
        System.out.println("response:" + response);
    }
}
