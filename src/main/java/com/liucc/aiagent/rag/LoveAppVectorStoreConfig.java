package com.liucc.aiagent.rag;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

/**
 * 向量存储配置类
 */
@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeywordEnricher myKeywordEnricher;

    @Bean
    public VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {

        // 加载文档
        List<Document> markdowns = loveAppDocumentLoader.loadMarkdowns();
        // 生成关键词元数据
        myKeywordEnricher.enrichDocuments(markdowns);
        // chunk
        List<Document> chunks = myTokenTextSplitter.splitCustomized(markdowns);
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();

        // 分段文档存储到向量数据库中
        simpleVectorStore.add(chunks);
        return simpleVectorStore;
    }
         
}
