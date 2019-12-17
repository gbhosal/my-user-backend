package com.demo.hs.css.userprofile.utils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public class ThreadLocalContainer {
	private static ThreadLocal<APIGatewayProxyRequestEvent> apiGatewayRequestThreadLocal = ThreadLocal
			.withInitial(() -> null);

	public static APIGatewayProxyRequestEvent getAPIGatewayProxyRequestEvent() {
		return apiGatewayRequestThreadLocal.get();
	}

	public static void setAPIGatewayProxyRequestEvent(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
		apiGatewayRequestThreadLocal.set(apiGatewayProxyRequestEvent);
	}

	public static void removeAPIGatewayProxyRequestEvent() {
		apiGatewayRequestThreadLocal.remove();
	}
}