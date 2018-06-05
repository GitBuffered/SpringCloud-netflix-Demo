package org.springcloud.feign.manually;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;

import feign.Client;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

@Import(FeignClientsConfiguration.class)
public class FooController {

	private TestClient fooClient;

	private TestClient adminClient;

    	@Autowired
	public FooController(
			Decoder decoder, Encoder encoder, Client client) {
		this.fooClient = (TestClient) Feign.builder().client(client)
				.encoder(encoder)
				.decoder(decoder)
				.requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
				.target(TestClient.class, "http://PROD-SVC");
		this.adminClient = (TestClient) Feign.builder().client(client)
				.encoder(encoder)
				.decoder(decoder)
				.requestInterceptor(new BasicAuthRequestInterceptor("admin", "admin"))
				.target(TestClient.class, "http://PROD-SVC");
    }
}