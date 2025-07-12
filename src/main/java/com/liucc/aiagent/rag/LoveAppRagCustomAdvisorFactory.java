package com.liucc.aiagent.rag;

import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;

/**
 * 自定义顾问工厂类
 */
public class LoveAppRagCustomAdvisorFactory {

    /**
     * 获取自定义顾问
     * @param vectorStore 向量存储
     * @param status 标签
     * @return
     */
    public static Advisor createLoveAppRagCustomAdvisor(VectorStore vectorStore, String status){
        Filter.Expression expression = new FilterExpressionBuilder()
                .eq("status", status)
                .build();
        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .topK(2)
                .filterExpression(expression)
                .similarityThreshold(0.7)
                .vectorStore(vectorStore)
                .build();
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                // 自定义查询增强器
                .queryAugmenter(LoveAppContextualAugmenterFactory.createInstance())
                .build();
    }
}
