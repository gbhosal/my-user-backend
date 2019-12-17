package com.demo.hs.css.userprofile.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
	@JsonProperty("returnCode")
	protected String returnCode;
	@JsonProperty("returnMessage")
	protected String returnMessage;
	@JsonProperty("messages")
	protected List<String> messages;
}