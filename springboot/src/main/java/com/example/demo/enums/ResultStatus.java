package com.example.demo.enums;

public enum ResultStatus {

	SUCCESS("200", "请求成功"),
	ERROR("500","服务器错误"),
	NO_STAFFNo("12001", "FAIL"),
	UNKNOWNERROR("12005", "FAIL");


	private String code;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	ResultStatus(String code, String msg) {
		setCode(code);
		setMsg(msg);
	}
}
