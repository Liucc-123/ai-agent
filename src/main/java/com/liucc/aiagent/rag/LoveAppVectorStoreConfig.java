package com.liucc.aiagent.rag;

import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;

import jakarta.annotation.Resource;

/**
 * 向量存储配置类
 */
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    public VectorStore loveAppVectorStore() {
        
         
}
