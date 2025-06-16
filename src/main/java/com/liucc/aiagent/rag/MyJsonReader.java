package com.liucc.aiagent.rag;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

// 从 classpath 下的 JSON 文件中读取文档
@Component
class MyJsonReader {
    private final Resource resource;

    MyJsonReader(@Value("classpath:complex_data.json") Resource resource) {
        this.resource = resource;
    }

    // 基本用法
    List<Document> loadBasicJsonDocuments() {
        JsonReader jsonReader = new JsonReader(this.resource);
        return jsonReader.get();
    }

    // 指定使用哪些 JSON 字段作为文档内容
    List<Document> loadJsonWithSpecificFields() {
        JsonReader jsonReader = new JsonReader(this.resource, "description", "features");
        return jsonReader.get();
    }

    // 使用 JSON 指针精确提取文档内容
    List<Document> loadJsonWithPointer(String pointer) {
        JsonReader jsonReader = new JsonReader(this.resource);
        return jsonReader.get(pointer); // 提取 items 数组内的内容
    }
}

