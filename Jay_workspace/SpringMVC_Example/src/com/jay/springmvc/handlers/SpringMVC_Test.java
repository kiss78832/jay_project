package com.jay.springmvc.handlers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jay.springmvc.pojo.User;

@RequestMapping("/springmvc")
@Controller
public class SpringMVC_Test {
	
	private static final String SUCCESS = "success";
	
	
	/**************@RequestMapping範例，params and headers 後面有@annotation版本**************/
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
	
	
	
	/********************************Rest風格範例***********************************/

	/**
	 * Rest 風格的URL 
	 * 以CRUD為例 :                 早期寫法:
	 * 新增: /order POST                  一樣 
	 * 修改: /order/{id} PUT              update?id={id}
	 * 查詢: /order/{id} GET				 get?id={id}
	 * 刪除: /order/{id} DELETE			 delete?id={id}
	 * 
	 * 如何發送PUT 請求和 DELETE 請求呢?
	 * 1.需要配置HiddenHttpMethodFilter
	 * 2.需要發送POST請求
	 * 3.需要在發送POST請求攜帶一個 name=_method 的隱藏，值為DELETE或PUT
	 * 
	 * 在SpringMVC 的目標方法中如何得到id呢?
	 * 使用@PathVariable註解
	 * 
	 * */

	/**
	 * @PathVariable 可以映射URL中的占位符到目標方法參數中
	 * 	支持Rest風格的URL
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
	@ResponseBody
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


	/*********************@RequestParam範例 :請求映射參數	***********************/

	//defaultValue 默認值 ，required此值是否為必須 預設為true，若int改Integer就不用預設(回傳null)
	@RequestMapping(value="/requestParam")
	public String requestParam(@RequestParam(value="username") String username,
			@RequestParam(value="age" ,defaultValue = "0",required = false) int age) {
		System.out.println("@RequestParam參數 -> username: "+username+" age: "+age);
		return SUCCESS;
	}

	/*********************@RequestHeader範例 :請求映射網頁Cookie內容	***********************/

	@RequestMapping(value="/cookieValue")
	public String cookieValue(@CookieValue("JSESSIONID") String jsessionId) {
		System.out.println("@CookieValue參數 -> JSESSIONID: "+jsessionId);
		return SUCCESS;
	}

	/*********************@CookieValue範例 :請求映射參數	***********************/

	@RequestMapping(value="/requestHeader")
	public String requestHeader(@RequestHeader(value="Accept-Language") String language) {
		System.out.println("@RequestHeader參數 -> Accept_Language: "+language);
		return SUCCESS;
	}
	
	/*********************POJO範例 :請求POJO參數	***********************/
	/*
	 * SpringMVC 會按照請求參數名的POJO屬姓名進行自動配對。 
	 * 自動為該對象填充屬性值。支持級聯屬性。如:address.city、address.province...等等
	 */
	@RequestMapping(value="/pojoParams")
	public String pojoParams(User user) {
		System.out.println("POJO參數 -> User: "+user);
		return SUCCESS;
	}
	
	/*********************Servlet原生API範例	 ***********************/
	/*
	 * 支持Servlet原生API
	 * 1.HttpServletRequest
	 * 2.HttpservletResponse
	 * 3.HttpSession
	 * 4.java.security.Principal
	 * 5.Local InputStream
	 * 6.OutputStream
	 * 7.Reader
	 * 8.Writer
	 */
	@RequestMapping(value="/servletAPI")
	public String servletAPI(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("Request參數 -> "+request+" Response參數 -> "+response);
		return SUCCESS;
	}
	
	
}