package com.heqing.demo.spring.struts2.util;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 返回数据
 */
public class ResultUtil<T> implements Serializable {

	/**
	 * 返回状态码
	 */
	private Integer code;

	/**
	 * 返回说明
	 */
	private String msg;

	/**
	 * 返回数据
	 */
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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

	public ResultUtil(int code, String errmsg, T data) {
		this.code = code;
		this.msg = errmsg;
		this.data = data;
	}

	public static ResultUtil build(int code, String msg){
		return build(code, msg, null);
	}

	public static ResultUtil build(Integer code, String msg, Object data){
		return new ResultUtil(code, msg, data);
	}

	public static ResultUtil buildSuccess(){
		return build(0, "OK", null);
	}

	public static ResultUtil buildSuccess(Object data){
		return build(0, "OK", data);
	}

	public static ResultUtil buildError(){
		return build(-1, "ERROR", null);
	}

	public static ResultUtil buildError(String msg){
		return build(-1, msg, null);
	}

	public static ResultUtil buildError(Integer code, String msg){
		return build(code, msg, null);
	}

	/**
	 * 是否响应成功
	 * @return
	 */
	public boolean isSuccessful(){
		return code == 0;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
