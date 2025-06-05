package com.liucc.aiagent.advisors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;

import reactor.core.publisher.Flux;

/**
 * Re2 Advisor 增强大模型推理能力
 */
public class ReReadingAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {


	private AdvisedRequest before(AdvisedRequest advisedRequest) {

		Map<String, Object> advisedUserParams = new HashMap<>(advisedRequest.userParams());
		advisedUserParams.put("re2_input_query", advisedRequest.userText());

        // 使用字符串模板，动态替换参数 re2_input_query
		return AdvisedRequest.from(advisedRequest)
			.userText("""
			    {re2_input_query}
			    Read the question again: {re2_input_query}
			    """)
			.userParams(advisedUserParams)
			.build();
	}

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        // 改写请求，让大模型多阅读一次用户prompt
		return chain.nextAroundCall(this.before(advisedRequest));
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        // 改写请求，让大模型多阅读一次用户prompt
		return chain.nextAroundStream(this.before(advisedRequest));
	}



    public String getName() { 
		return this.getClass().getSimpleName();
	}



    @Override
    public int getOrder() { 
		return 0;
	}
}
