package com.jay.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/springmvc")
@Controller
public class SpringMVC_Test {
	
	private static final String SUCCESS = "success";
	
	/**
	 * 1.@RequestMapping 可修飾方法與類別
	 * 2.類別定義處:提供初步請求映射訊息，當對於WEB跟目錄。所以記得連結也要打上/springmvc
	 * 3.方法處:提供進一步的細分映射訊息，也就是/sprimgmvc可當作一個資料夾，/testRequestMapping可當作檔案來看待。
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		System.out.println("testRequestMapping()");
		return SUCCESS;
	}
	
	/**
	 * 常用:使用method屬性來指定請求方式
	 * */
	@RequestMapping(value="/testMethod" , method=RequestMethod.POST)
	public String testMethod() {
		System.out.println("testMethod()");
		return SUCCESS;
	}
	
	/**
	 * 常用:限定Get後面?出現在參數，進行比對。
	 * */
	@RequestMapping(value="testParamsAnHeaders",params= {"username","age!=0"})
	public String testParamsAnHeaders() {
		System.out.println("Test 網頁的params的參數");
		return SUCCESS;
	}
	
	/**
	 * 依照網頁請求頭的資料進行比對，但目前測不出來。
	 * */
	@RequestMapping(value="testParamsAnHeaders02",headers = {"Accept-Language=zh-TW,zh;q=0.9"})
	public String testParamsAnHeaders02() {
		System.out.println("Test 網頁的Headers的參數");
		return SUCCESS;
	}
	
	/**
	 * @RequestMapping 支持ant風格  1.* 通配符   2.? 單一字元   3.**匹配多層路徑 
	 * ex:
	 * 		/springmvc/(*任意單一字串)/abc
	 * 		/springmvc/abc??? ->/springmvc/abcQQQ 後面三個字元可隨機
	 * 		/springmvc/(**多層)/abc  -> /springmvc/ddd/eee/abc 中間多少層都沒關係
	 * */
	@RequestMapping("/antStyle/*/abc")
	public String antStyle() {
		System.out.println("antStyle()");
		return SUCCESS;
	}
	
	/**
	 * @PathVariable 可以映射URL中的占位符到目標方法參數中
	 * 支持Rest風格的URL
	 * */
	@RequestMapping("/pathVariable/{id}")
	public String pathVariable(@PathVariable("id") String id) {
		System.out.println("Test @PathVariable 占位符");
		return SUCCESS;
	}
	
	/**
	 * REST風格，Get方法
	 * */
	@RequestMapping(value="/rest01/{id}",method=RequestMethod.GET)
	public String rest01(@PathVariable String id) {
		System.out.println("Test Rest風格 Get()方法: "+id);
		return SUCCESS;
	}
	
	
	
	
	/**
	 * REST風格，delete方法
	 * */
	@RequestMapping(value="/rest02/{id}",method=RequestMethod.DELETE)
	public String rest02(@PathVariable String id) {
		System.out.println("Test Rest風格 Delete()方法: "+id);
		return SUCCESS;
	}
	
	/**
	 * REST風格，put方法
	 * */
	@RequestMapping(value="/rest03/{id}",method=RequestMethod.PUT)
	public String rest03(@PathVariable String id) {
		System.out.println("Test Rest風格 Put()方法"+id);
		return SUCCESS;
	}
}
