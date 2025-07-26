package com.liucc.aiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        String url = "https://cdn.pixabay.com/photo/2024/01/15/04/29/woman-8509279_1280.jpg"; // Replace with a valid URL for testing
        String fileName = "女生.jpg";
        String result = resourceDownloadTool.downloadResource(url, fileName);
        assertNotNull(result, "The download result should not be null");
    }
}