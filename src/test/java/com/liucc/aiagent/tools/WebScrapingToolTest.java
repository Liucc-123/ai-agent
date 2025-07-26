package com.liucc.aiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebScrapingToolTest {

    @Test
    void scrapeWebPage() {
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        String url = "https://www.bilibili.com/";
        String result = webScrapingTool.scrapeWebPage(url);
        assertNotNull(result);
    }
}