package com.example.demo.enums;

public enum ResultStatus {

	SUCCESS("200", "请求成功"), NO_STAFFNo("12001", "FAIL"), UNKNOWNERROR("12005", "FAIL");
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

	ResultStatus(String code, String des) {
		setCode(code);
		setDescription(des);
	}
}
