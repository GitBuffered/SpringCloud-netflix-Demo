package org.springcloud.feign.with.hystrix;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class HystrixClientFallbackFactory implements FallbackFactory<TestClient> {
	@Override
	public TestClient create(Throwable cause) {
		return new TestClient() {

			@Override
			public String serviceUrl() {
				// TODO Auto-generated method stub

				return cause.getMessage();
			}

			@Override
			public String hi() {
				// TODO Auto-generated method stub
				return null;
			}

		};
	}
}
