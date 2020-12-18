package com.jay.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jay.web.exception.UserNotExistException;

@ControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(UserNotExistException.class)
	public String handleException(Exception e,HttpServletRequest request) {

		Map<String,Object> map = new HashMap<>();

		map.put("code", "Hi! here is MyExceptionHandler");
		map.put("message", "攔截錯誤 ="+e.getMessage());

		request.setAttribute("javax.servlet.error.status_code", 500);
		request.setAttribute("handlerMap", map);

		return "forward:/error";
	}


//	此寫法會導致【瀏覽器】跟【客戶端】請求都會是同一種處裡方式，不會去分辯【瀏覽器接收.html格式】跟【客戶端接收 JSON格式】
//	處理方式 : 要分成兩段寫
//	@ResponseBody
//	@ExceptionHandler(UserNotExistException.class)
//	public Map<String,Object> handleException(Exception e) {
//		System.out.println("進入handleException");
//
//		Map<String,Object> map = new HashMap<>();
//		map.put("code", "user.noteException");
//		map.put("message","修改Message");
//		return map;
//	}
}
