package com.liucc.aiagent.rag;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 向量存储配置类
 */
@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Bean
    public VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        // 加载文档
        List<Document> markdowns = loveAppDocumentLoader.loadMarkdowns();
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        // 分段文档存储到向量数据库中
        simpleVectorStore.add(markdowns);
        return simpleVectorStore;
    }
         
}
