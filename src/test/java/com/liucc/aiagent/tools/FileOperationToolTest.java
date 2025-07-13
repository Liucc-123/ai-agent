package com.liucc.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileOperationToolTest {
    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "文件工具测试.txt";
        String content = fileOperationTool.readFile(fileName);
        Assertions.assertNotNull(content);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "文件工具测试.txt";
        String content = "这是一个文件操作工具测试内容。";
        String s = fileOperationTool.writeFile(fileName, content);
        Assertions.assertNotNull(s);
    }
}