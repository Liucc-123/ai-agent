package com.liucc.aiagent.springai;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 文档过滤和检索 测试类
 */
@SpringBootTest
@Slf4j
public class DocumentRetrieverTest {

    @Resource
    private ChatClient.Builder chatClientBuilder;


    @Test
    public void queryRewrite(){
        Query query = new Query("啥是程序员鱼皮啊啊啊啊？");

        QueryTransformer queryTransformer = RewriteQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder)
                .build();

        Query transformedQuery = queryTransformer.transform(query);
        log.info("原始查询: {}", query.text());
        log.info("转换后的查询: {}", transformedQuery.text());
    }

    @Test
    public void MultiQueryExpanderTest(){
        MultiQueryExpander queryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder)
                .numberOfQueries(3)
                .build();
        List<Query> queries = queryExpander.expand(new Query("How to run a Spring Boot app?"));
        log.info("queries:{}", queries);
    }
}
