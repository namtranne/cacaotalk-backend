package com.cacaotalk.user.entity;

public class Response {
	private String response;
	private boolean isSuccess;
	public Response(String a, boolean b) {
		this.response=a;
		isSuccess=b;
	}
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String a) {
		response=a;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public void setSuccess(boolean a) {
		isSuccess=a;
	}
}
