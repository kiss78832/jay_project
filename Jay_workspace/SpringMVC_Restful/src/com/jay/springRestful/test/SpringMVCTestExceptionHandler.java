package com.jay.springRestful.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//這不是一個異常類別，沒有繼承Exception
//@ControllerAdvice 會造成springmvc.xml掃描出問題，但不影響操作，先註解(未查證)
//@ControllerAdvice
public class SpringMVCTestExceptionHandler {
	
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleArithmeticException2(Exception ex) {
		System.out.println("HandleException.java頁面: "+ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception",ex);
		return mv;
	}
	
}
