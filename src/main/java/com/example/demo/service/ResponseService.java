package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.response.CommonResult;
import com.example.demo.model.response.ListResult;
import com.example.demo.model.response.SingleResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
public class ResponseService {

	@Getter
	@AllArgsConstructor
	public enum CommonResponse {
		SUCCESS(0, "성공하였습니다."),
		FAIL(-1, "실패하였습니다.");
		
		int code;
		String msg;
		
	}
	
	// 단일건 결과를 처리하는 메소드
	public <T> SingleResult<T> getSingResult(T data) {
		SingleResult<T> result = new SingleResult<T>();
		result.setData(data);
		setSuccessResult(result);
		return result;
	}

	// 다중건 결과를 처리하는 메소드
	public <T> ListResult<T> getListResult(List<T> list) {
		ListResult<T> result = new ListResult<T>();
		result.setList(list);
		setSuccessResult(result);
		return result;
	}
	
	private void setSuccessResult(CommonResult result) {
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMsg(CommonResponse.SUCCESS.getMsg());
	}

	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		return result;
	}

	public CommonResult getFailResult(int code, String msg) {
		CommonResult result = new CommonResult();
		result.setSuccess(false);
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
	
	
}
