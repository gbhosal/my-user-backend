package com.demo.hs.css.userprofile.function.router;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.demo.hs.css.exception.HSException;
import com.demo.hs.css.userprofile.service.UserProfileService;
import com.demo.hs.css.userprofile.utils.ReturnCode;
import com.demo.hs.css.userprofile.utils.ThreadLocalContainer;
import com.demo.hs.css.userprofile.vo.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component("FunctionRequestRouter")
public class FunctionRouter {
	private static final Logger LOGGER = LoggerFactory.getLogger(FunctionRouter.class);
	
	@Autowired
	private UserProfileService userProfileService;
	private ObjectMapper mapper = new ObjectMapper();;
	private Map<String, String> headers = new HashMap<>();

	{
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		headers.put("Content-Type", "application/json");
	}
	
	public APIGatewayProxyResponseEvent route(APIGatewayProxyRequestEvent request) {
		HttpMethod httpMethod = HttpMethod.valueOf(request.getHttpMethod());
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		ThreadLocalContainer.setAPIGatewayProxyRequestEvent(request);
		
		if (httpMethod == HttpMethod.GET && request.getPath().endsWith("health")) {
			return build(userProfileService.health());
		} else if (httpMethod == HttpMethod.GET && request.getPath().matches("/user/profile/[A-Za-z0-9]+")) {
			return build(userProfileService.getUserProfile(request.getPathParameters().get("username")));
		} else {
			response.setBody(
					"{\"returnCode\":\"08\",\"returnMessage\":\"DATA VALIDATION\",\"messages\":[\"Unsuppported method.\"]}");
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json");
			response.setHeaders(headers);
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}
		
		ThreadLocalContainer.removeAPIGatewayProxyRequestEvent();
		return response;
	}
	
	public String buildBody(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
			throw new HSException(e.getMessage(), e);
		}
	}
	
	private APIGatewayProxyResponseEvent build(Object response) {
		BaseResponse baseResponse = (BaseResponse) response;
		ReturnCode returnCode = ReturnCode.getReturnCode(baseResponse.getReturnCode());
		
		if (returnCode == ReturnCode.SUCCESS) {
			return build(HttpStatus.OK.value(), buildBody(response));
		} else if (returnCode == ReturnCode.DATA_VALIDATION) {
			return build(HttpStatus.BAD_REQUEST.value(), buildBody(response));
		} else if (returnCode == ReturnCode.SYSTEM_ERROR) {
			return build(HttpStatus.INTERNAL_SERVER_ERROR.value(), buildBody(response));
		} else {
			throw new HSException("Unknown returnCode");
		}
	}
	
	private APIGatewayProxyResponseEvent build(int statusCode, String body) {	
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setStatusCode(statusCode);		
		response.setHeaders(headers);
		response.setBody(body);
		return response;
	}
}