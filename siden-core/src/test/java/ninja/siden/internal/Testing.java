/*
 * Copyright 2014 SATO taichi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package ninja.siden.internal;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import mockit.Mock;
import mockit.MockUp;
import ninja.siden.util.Io;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author taichi
 */
public interface Testing {

	static CloseableHttpClient client() {
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(200)
				.build());
		builder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
		return builder.build();
	}

	@FunctionalInterface
	interface ResponseConsumer {
		void accept(HttpResponse response) throws Exception;
	}

	static void request(HttpUriRequest request, ResponseConsumer fn)
			throws Exception {
		Io.using(Testing::client, c -> {
			fn.accept(c.execute(request));
			return null;
		});
	}

	static String read(HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return "";
		}
		try (Scanner scanner = new Scanner(entity.getContent(),
				StandardCharsets.UTF_8.name())) {
			return scanner.useDelimiter("\\A").next();
		}
	}

	static HttpHandler mustCall() {
		return new MockUp<HttpHandler>() {
			@Mock(invocations = 1)
			public void handleRequest(HttpServerExchange exchange)
					throws Exception {
			}
		}.getMockInstance();
	}

	static HttpHandler empty() {
		return exc -> {
			throw new AssertionError();
		};
	}
}
