package com.liucc.aiagent.advisors;

import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.model.MessageAggregator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

	@Override
	public String getName() {
		return "这是我的自定义顾问";
	}

	@Override
	public int getOrder() {
		return 100;
	}

	private AdvisedRequest before(AdvisedRequest request) {
		log.info("request: {}", request.userText());
		return request;
	}

	private void observeAfter(AdvisedResponse advisedResponse) {
		log.info("response: {}", advisedResponse.response().getResult().getOutput().getText());
	}

    /**
     * 环绕通知
     */
	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {

        /**
         * 前置处理
         */
		advisedRequest = before(advisedRequest);

		AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

        /**
         * 后置处理
         */
		observeAfter(advisedResponse);

		return advisedResponse;
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        // 前置处理
		advisedRequest = before(advisedRequest);

		Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);

        // 通过MessageAggregator等模型回复全部完成后聚合起来，避免回复碎片化
		return new MessageAggregator().aggregateAdvisedResponse(advisedResponses, this::observeAfter);
	}

}

