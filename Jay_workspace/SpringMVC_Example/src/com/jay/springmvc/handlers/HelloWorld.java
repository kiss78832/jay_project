package com.jay.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	
	/**
	 * 1.使用@RenderMapping 來映射請求
	 * 2.返回值會通過視圖解析為實際的物理視圖，對於InternalResourceViewResolver 視圖解析器，會做如下的解析:
	 *   通過prefix + returnVal + 後綴這樣的方式得到實際的物理視圖，然後會做轉發操作。(/WEB-INF/views/sucess.jsp)
	 * 
	 * 
	 */
	@RequestMapping("/helloworld")
	public String hello() {
		System.out.println("hello world");
		//注意這回傳值是指頁面的名稱
		return "success";
	}
}
