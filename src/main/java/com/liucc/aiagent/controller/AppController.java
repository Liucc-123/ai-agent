package com.liucc.aiagent.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liucc.aiagent.app.LoveApp;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


/**
 * 应用接口
 */
@RestController
@Slf4j
public class AppController {

    @Resource
    private LoveApp loveApp;
    
    @GetMapping(value = "doChatWithStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithStream(String author) {
        return loveApp.doChatWithStream(author);
    }
}
