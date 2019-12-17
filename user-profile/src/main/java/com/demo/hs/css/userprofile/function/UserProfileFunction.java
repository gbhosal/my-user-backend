package com.demo.hs.css.userprofile.function;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.demo.hs.css.userprofile.function.router.FunctionRouter;

@Component("UserProfileFunction")
public class UserProfileFunction implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileFunction.class);
	@Autowired
	private FunctionRouter functionRouter;

	@Override
	public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent request) {
		LOGGER.info("APIGatewayProxyRequestEvent = {}", request);
		APIGatewayProxyResponseEvent response = functionRouter.route(request);
		LOGGER.info("APIGatewayProxyResponseEvent = {}", response);
		return response;
	}
}