package com.demo.hs.css.userprofile.utils;

import java.util.stream.Stream;

public enum ReturnCode {
	SUCCESS("00", "SUCCESS"), 
	NO_DATA_FOUND("04", "NO DATA FOUND"), 
	DATA_VALIDATION("08", "DATA VALIDATION"), 
	SYSTEM_ERROR("12", "SYSTEM ERROR");

	private String returnCode;
	private String returnMessage;

	private ReturnCode(String returnCode, String returnMessage) {
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}
	
	public static ReturnCode getReturnCode(String returnCode) {
		ReturnCode[] returnCodes = values();
		return Stream.of(returnCodes).filter(predicate -> predicate.getReturnCode().equals(returnCode)).findFirst()
				.orElse(null);
	}
}
