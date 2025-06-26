package com.liucc.aiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyKeywordEnricher {

    @Resource
    private ChatModel dashscopeChatModel;
    List<Document> enrichDocuments(List<Document> documents){
        // 生成 3 个关键词的元数据
        KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(dashscopeChatModel, 3);
        return keywordMetadataEnricher.apply(documents);
    }
}
