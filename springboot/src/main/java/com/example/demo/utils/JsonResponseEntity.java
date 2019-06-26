package com.example.demo.utils;


import com.example.demo.enums.ResultStatus;

public class JsonResponseEntity<T>{
		private String code;
		private String msg;
		private T data;

		public static JsonResponseEntity<Object> newJsonResult(ResultStatus result,Object data){
	        return new JsonResponseEntity<>(result, data);
	    }
		public JsonResponseEntity(ResultStatus result, T data) {
	        super();
	        this.code = result.getCode();
	        this.msg = result.getMsg();
	        this.data = data;
	        
	    }
		public JsonResponseEntity() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		
		
}

