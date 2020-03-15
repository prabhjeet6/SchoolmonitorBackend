package com.schoolmonitor.model;

import java.util.Map;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
import org.springframework.stereotype.Component;

@Component
public class ApiError {
	private int httpStatusValue;
	private String httpStatusCode;
	private Map<String, String[]> parameterMap;

	private String webRequest;
	private String exceptionMessage;
	private StackTraceElement [] stacktrace;
	private Throwable cause;

	

	public StackTraceElement[] getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(StackTraceElement[] stacktrace) {
		this.stacktrace = stacktrace;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public int getHttpStatusValue() {
		return httpStatusValue;
	}

	public void setHttpStatusValue(int httpStatusValue) {
		this.httpStatusValue = httpStatusValue;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public String getWebRequest() {
		return webRequest;
	}

	public void setWebRequest(String webRequest) {
		this.webRequest = webRequest;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
