package com.liucc.aiagent.rag;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MyJsonReaderTest {

    @Resource
    private MyJsonReader myJsonReader;
    @Resource
    private MyDocumentWriter myDocumentWriter;

    @Test
    public void loadJsonWithPointerTest() {
        List<Document> documents = myJsonReader.loadJsonWithPointer("/company/headquarters");
        // List<Document> documents = myJsonReader.loadBasicJsonDocuments();
        log.info("documents:{}", documents);
        // 将切片文档写入到文件系统
        myDocumentWriter.writeDocuments("chunks.txt", documents);
    }
}
