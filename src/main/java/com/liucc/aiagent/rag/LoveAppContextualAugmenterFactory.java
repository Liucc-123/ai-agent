package com.liucc.aiagent.rag;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;

/**
 * 自定义上下文增强器工厂类
 */
public class LoveAppContextualAugmenterFactory {

    public static ContextualQueryAugmenter createInstance(){
        PromptTemplate emptyContextPromptTemplate = new PromptTemplate("""
                你应该输出下面的内容：
                抱歉，我只能回答恋爱相关的问题，别的没办法帮到您哦，
                有问题可以联系编程导航客服 水冠
                """);
        return ContextualQueryAugmenter.builder()
                .emptyContextPromptTemplate(emptyContextPromptTemplate)
                .allowEmptyContext(false)
                .build();
    }
}
