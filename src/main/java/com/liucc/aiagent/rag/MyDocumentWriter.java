package com.liucc.aiagent.rag;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.writer.FileDocumentWriter;
import org.springframework.stereotype.Component;

import cn.hutool.core.io.FileUtil;

@Component
class MyDocumentWriter {

    /**
     * 将文档列表写入到指定文件中
     * @param filename 文件路径
     * @param documents 切片文档列表
     */
    public void writeDocuments(String filename, List<Document> documents) {
        if (!FileUtil.exist(filename)) {
            FileUtil.touch(filename);
        }
        FileDocumentWriter writer = new FileDocumentWriter(filename, true, MetadataMode.ALL, false);
        writer.accept(documents);
    }
}
