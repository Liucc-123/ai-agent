package com.liucc.aiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "test.pdf";
        String content = "This is a test PDF content.";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}