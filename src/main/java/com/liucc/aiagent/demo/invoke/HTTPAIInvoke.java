package com.liucc.aiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.liucc.aiagent.demo.TestApiKey.API_KEY;

/**
 * 通过HTTP方式调用AI
 */
@Slf4j
public class HTTPAIInvoke {
    public static void main(String[] args) {

        String result = callWithHutool();
        System.out.println("result:" + result);
    }

    public static String callWithHutool() {
        // 请求地址
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

        // 构建请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + API_KEY);
        headers.put("Content-Type", "application/json");

        // 构建消息列表
        Map<String, Object> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", "You are a helpful assistant.");

        Map<String, Object> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", "你是谁？");

        // 构建 input 部分
        Map<String, Object> input = new HashMap<>();
        input.put("messages", Arrays.asList(systemMsg, userMsg));

        // 构建 parameters 部分
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("result_format", "message");

        // 构建请求体
        Map<String, Object> body = new HashMap<>();
        body.put("model", "qwen-plus");
        body.put("input", input);
        body.put("parameters", parameters);

        // 发送 POST 请求
        HttpResponse response = HttpRequest.post(url)
                .addHeaders(headers)
                .body(JSONUtil.toJsonStr(body))
                .execute();

        // 返回响应结果
        return response.body();
    }

}
