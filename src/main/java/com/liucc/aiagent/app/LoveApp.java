package com.liucc.aiagent.app;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

import java.io.IOException;
import java.util.List;

import com.liucc.aiagent.rag.LoveAppContextualAugmenterFactory;
import com.liucc.aiagent.rag.LoveAppRagCustomAdvisorFactory;
import com.liucc.aiagent.rag.QueryRewriter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.liucc.aiagent.advisors.MyLoggerAdvisor;
import com.liucc.aiagent.advisors.SensitiveWordAdvisor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * 恋爱助手
 */
@Component
@Slf4j
public class LoveApp {

    record ActorFilms(String actor, List<String> movies) {
    }

    // 恋爱报告 记录
    record LoveReport(String title, List<String> suggestions) {
    }

    // 系统提示词
    private static final String SYSTEM_PROMPT = """
            扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。
            围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；
            恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。
            引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。
            """;
    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    private final ChatClient chatClient;

    @jakarta.annotation.Resource
    private VectorStore loveAppVectorStore;

    @jakarta.annotation.Resource
    private VectorStore pgVectorVectorStore;

    @jakarta.annotation.Resource
    private Advisor loveAppRagCloudAdvisor;

    @jakarta.annotation.Resource
    private ChatClient.Builder chatClientBuilder;

    /**
     * 构造函数注入ChatClient。默认使用的大模型是 DashScopeChatModel
     *
     * @param builder 通过ChatClient.Builder构造ChatClient
     * @throws IOException
     */
    // private final InMySqlChatMemory mySqlChatMemory;
    // public LoveApp(ChatClient.Builder builder, InMySqlChatMemory mySqlChatMemory) {
    //         this.mySqlChatMemory = mySqlChatMemory;
    //         ChatMemory chatMemory = this.mySqlChatMemory;
    //         chatClient = builder.defaultSystem(systemResource, Charset.forName("UTF-8"))
    //                         .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory),
    //                                         new SensitiveWordAdvisor(),
    //                                         new MyLoggerAdvisor())
    //                         .build();
    // }
    public LoveApp(ChatClient.Builder builder) throws IOException {
        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    /**
     * 与大模型聊天
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    /**
     * 结构化输出
     *
     * @param message
     * @return
     */
    public ActorFilms doChatEntity(String message) {
        ActorFilms entity = chatClient.prompt()
                .user(message)
                .call()
                .entity(ActorFilms.class);
        log.info("entity: {}", entity);
        return entity;
    }

    public void doChatStream(String message) {
        log.info("大模型流式回复");
        Flux<String> output = chatClient.prompt()
                .user(message)
                .stream()
                .content();

        // 订阅 Flux 流
        output.subscribe(
                // 正常输出
                content -> log.info("content: {}", content),
                // 异常情况
                error -> log.error("Error occurred: {}", error.getMessage()),
                () -> log.info("Stream completed"));

        // 这里阻塞主线程，是因为SpringAI使用的是非阻塞式的响应式编程，大模型还没开始回复内容呢，程序主线程就已经结束，因此就拿不到模型的流式回复内容。
        try {
            // 阻塞主线程，等待流完成
            output.blockLast();
        } catch (Exception e) {
            log.error("等待流完成时出错", e);
        }
    }

    /**
     * 包含提示词模板的chat 方法
     *
     * @param actor 演员名称
     */
    public void doChatWithPromptTemplate(String actor) {
        // 默认情况下，SpringAI 使用StTemplateRenderer来动态替换提示词模板中的参数。默认的语法解析规则是解析 {} 中的内容
        String modelResponse = chatClient.prompt()
                .user(u -> u.text("告诉我关于演员{actor}的5个作品")
                        .param("actor", actor))
                .call()
                .content();
        System.out.println("sout方式，modelResponse: " + modelResponse);
        log.info("modelResponse: {}", modelResponse);
    }

    public Flux<String> doChatWithStream(String author) {
        Flux<String> content = chatClient.prompt()
                .user(u -> u.text("请用200字以内的方式介绍{author}的作品")
                        .param("author", author))
                .stream()
                .content();
        log.info("没事儿，就是想记录一下");
        return content;
    }

    /**
     * 结构化输出实战（生成恋爱报告）
     *
     * @param message 用户prompt
     * @param chatId  会话id
     * @return
     */
    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient.prompt()
                // 覆盖默认的系统提示词
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        // 指定记忆的大小 10条记录
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoveReport.class);
        return loveReport;
    }

    @jakarta.annotation.Resource
    private QueryRewriter queryRewriter;

    /**
     * 基于 RAG 的聊天
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChaWithRag(String message, String chatId) {
        // 改写后的 prompt
        String rewriteMessage = queryRewriter.doQueryWrite(message);
        String content = chatClient.prompt()
                .user(rewriteMessage)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志记录顾问、QA顾问
                .advisors(new MyLoggerAdvisor())
//                                .advisors(new QuestionAnswerAdvisor(pgVectorVectorStore))
//                                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                .advisors(LoveAppRagCustomAdvisorFactory.createLoveAppRagCustomAdvisor(pgVectorVectorStore, "已婚"))
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
        return content;
    }

    /**
     * 基于云知识库的知识问答
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChaWithCloudRag(String message, String chatId) {
        String content = chatClient.prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志记录顾问
                .advisors(new MyLoggerAdvisor())
                // 基于云知识库的增强检索
                .advisors(loveAppRagCloudAdvisor)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
        return content;
    }
}
