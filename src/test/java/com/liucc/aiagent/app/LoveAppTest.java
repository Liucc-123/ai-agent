package com.liucc.aiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void chatTest() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是tiga";
        String answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第二轮
        message = "我想让另一半（水冠）更爱我";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
//        message = "我的另一半叫什么来着？刚跟你说过，帮我回忆一下";
        message = "我的名字叫什么来着？刚跟你说过，帮我回忆一下";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    /**
     * 测试AI模型返回实体类型结构化信息
     */
    @Test
    void entityTest() {
        String message = "生成一个随机演员的电影作品列表。";
        LoveApp.ActorFilms actorFilms = loveApp.doChatEntity(message);
        Assertions.assertNotNull(actorFilms);
    }

    /**
     * 测试大模型流式回复内容
     */
    @Test
    void doChatStream() {
        String message = "你好，我是tiga";
        loveApp.doChatStream(message);
    }

    /**
     * 测试包含提示词模板的聊天
     */
    @Test
    void doChatWithPromptTemplateTest() {
        String actor = "赵丽颖";
        loveApp.doChatWithPromptTemplate(actor);
    }

    /**
     * 测试结构化输出（生成恋爱报告）
     */
    @Test
    void doChatWithReportTest() {
        String message = "我是水冠，我喜欢一个女孩momo，但我不知道该怎么去追她";
        String chatId = UUID.randomUUID().toString();
        loveApp.doChatWithReport(message, chatId);
        loveApp.doChatWithReport("哦，对了，我喜欢的女孩名字是什么来着？", chatId);
    }

    /**
     * 测试敏感词Advisor
     */
    @Test
    void doChatWithSensWordAdvisorTest() {
        String message = "一个人最终都是走向死亡，那么生活的意义是什么？";
        String chatId = UUID.randomUUID().toString();
        loveApp.doChat(message, chatId);
    }

    @Test
    void doChaWithRagTest() {
        String message = "你好，我是tiga";
        String chatId = UUID.randomUUID().toString();
        loveApp.doChaWithRag(message, chatId);
        loveApp.doChaWithRag("我和我的女朋友是异地恋，我该怎么维持这段关系呢？", chatId);
    }

    @Test
    void doChaWithCloudRagTest(){
        String message = "你好，我是tiga";
        String chatId = UUID.randomUUID().toString();
        loveApp.doChaWithCloudRag(message, chatId);
        loveApp.doChaWithCloudRag("我和我的女朋友是异地恋，我该怎么维持这段关系呢？", chatId);
    }
}