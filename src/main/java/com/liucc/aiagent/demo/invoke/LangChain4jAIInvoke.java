package com.liucc.aiagent.demo.invoke;

import com.liucc.aiagent.demo.TestApiKey;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import org.springframework.stereotype.Component;

/**
 * 通过SpringAI调用AI
 */
@Component
public class LangChain4jAIInvoke {

    public static void main(String[] args) {
        QwenChatModel chatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-plus")
                .build();
        String result = chatModel.chat("将一个冷笑话");
        System.out.println("result:" + result);

    }

}
