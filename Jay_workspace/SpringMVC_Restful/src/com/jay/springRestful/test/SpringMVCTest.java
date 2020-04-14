package com.jay.springRestful.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jay.springRestful.crud.DAO.EmployeeDao;
import com.jay.springRestful.crud.entities.Employee;

@Controller
public class SpringMVCTest {

	@Autowired
	private EmployeeDao employeeDao;
	
	
	//	新增員工，利用轉換器來新增。
	@RequestMapping("/testCoversionServiceConerter")
	public String testConverter(@RequestParam("employee") Employee employee) {
		System.out.println("svae: " + employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}
	
	/**
	 * @RequestBody:
	 *  Content-Type的值有以下幾種:
	 * 		(1).application/x-www-form-urlencoded:可選（即非必須，因為這種情況的數據@RequestParam, @ModelAttribute也可以處理，當然@RequestBody也能處理
	 * 		(2).multipart/form-data:不能處理（即使用@RequestBody不能處理這種格式的數據）
	 * 		(3).其他格式（其他格式包括application/json, application/xml等），這些格式的數據，必須使用@RequestBody來處理
	 * 
	 * 	這裡的@RequestBody用於讀取Http請求的body部分數據——就是我們的請求數據。
	 * 		比如json或者xml。然後把數據綁定到controller中方法的參數上，這裡就是String body這個入參。
	 * 	注意 : [不能用@RequestBody把request請求的數據解析，並賦給我們定義的參數上是由請求頭的Content-Type的值來決定的。]
	 * 
	 * 
	 * @ReponseBody:
	 * 	用法：
	 * 		放在controller層的方法上，將Controller的方法返回的對象，通過適當的HttpMessageConverter轉換為指定格式後，寫入到Response對象的body數據區。
	 *	使用時機：
	 *		當我們想讓頁面知道我們返回的數據不是按照html標籤的頁面來解析，而是其他某種格式的數據解析時（如json、xml等）使用。
	 * 	解析 : 
	 * 	 	當你不加上@ResponseBody，返回到頁面的是String類型的數據。加上這個註解，在頁面通過data.studentInfo獲取到的就是json格式的，可​​以取這個json內部的屬性值直接。
	 * */
	@ResponseBody
	@RequestMapping("/testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String body) {
		System.out.println("@RequestBody: "+body);
		return "Hello World!" + new Date();
	}
	
	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson() {
		return employeeDao.getAll();
	}

	/**
	 * HttpEntity<T> 與 ResponseEntity<T> 方式差不多，自行研究。
	 * 
	 * */
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
		byte[] body = null;
		ServletContext servletContext = session.getServletContext();
		InputStream in = servletContext.getResourceAsStream("/files/abc.txt");
		body = new byte[in.available()];
		in.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition","attachment;filename=abc.txt");
		
		HttpStatus statusCode = HttpStatus.OK;
		
		ResponseEntity<byte[]> reponse = new ResponseEntity<byte[]>(body,headers,statusCode);
		return reponse;
	}
	
	/**
	 * 國際化轉換源代碼流程:
	 * 		獲取name=locale請求的參數 -> 把第一步的locale請求參數解析為Locale對象 -> 獲取LocaleResolver對象          [LocaleChangeInterceptor]
	 * 			-> 把Locale對象設置為Session的屬性 -> 從Session中獲取Locale對象      						[SessionLocaleResolver]
	 * 				-> 使用該Locale對象															[應用程式]
	 * */
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@RequestMapping("/i18n")
	public String testI18n(Locale locale) {
		String val = messageSource.getMessage("i18n.user", null,locale);
		System.out.println("國際化測試: "+val);
		return "i18n";
	}
	
	/**
	 * 上傳功能 :
	 * 		Jakarta Commons FileUpload技術實作一個MultipartResolver實現類:CommonsMultipartResovler。
	 *			a.SpringMVC需配置MultipartResovler才具有上傳功能
	 * 
	 * */
	@RequestMapping("/testFileUpload")
	public String testFileUpload(@RequestParam("desc") String desc,@RequestParam("file") MultipartFile file) throws IOException{
		System.out.println("desc: "+desc);
		System.out.println("OriginalFilename: "+file.getOriginalFilename());
		System.out.println("InputStream: "+file.getInputStream());
		return "success";
	}
	
	/**
	 * 錯誤導入的頁面:
	 * 		(1).在@ExceptionHandler 方法中的參數可以加入Exception類型的參數，該參數對應發生錯誤的對象。(範例1)
	 * 		(2).@ExceptionHandler 方法的參數不能傳入Map，若想把異常訊息印在頁面上，需要使用ModelAndView作為返回值。(範例2)
	 * 		(3).@ExceptionHandler 方法標記異常有優先級，例如RuntimeException VS ArithmeticException(優先)
	 * 		(4).@ControllerAdvice 如果當前Handler找不到@ExceptionHandler方法來處理異常:
	 * 				a.先到@Controller看是否有@ExceptionHandler。
	 * 				b.去查找@ControllerAdvice是否有@ExceptionHandler，若沒有就直接跳500頁面。
	 * 
	 * */
	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("i") int i) {
		System.out.println("運算結果: "+(10/i));
		return "success";
	}

	//一般跳轉到錯誤頁面{ArithmeticException.class}不能重複 (範例1)
//	@ExceptionHandler({ArithmeticException.class})
	public String handleArithmeticException(Exception ex) {
		System.out.println("出現異常: "+ex);
		return "error";
	}
	
	//跳轉到錯誤頁面+印出Exception(範例2)
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleArithmeticException2(Exception ex) {
		System.out.println("出現異常(ArithmeticException): "+ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception",ex);
		return mv;
	}
	
	/*
	 * 錯誤優先級，此範例與(範例2)比較，會優先ArithmeticException，若ArithmeticException註解才會換RuntimeException(看console)
	 * 但他的錯誤永遠都是ArithmeticException的錯誤。
	 */
//	@ExceptionHandler({RuntimeException.class})
	public ModelAndView handleArithmeticException3(Exception ex) {
		System.out.println("出現異常(RuntimeException): "+ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception",ex);
		return mv;
	}
	
	//記得將RuntimeException範例註解
	//當輸入13時會自動跳到UserNameNotMatchPasswordException()自定義錯誤
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("i") int i) {
		if(i == 13) {
			//自定義的Exception
			throw new UserNameNotMatchPasswordException();
		}
		System.out.println("我跳轉成功，我是沒有錯誤訊息");
		return "success";
	}
	

	//記得將RuntimeException範例註解
	//方法還是會被執行，但是返回是404頁面，@ResponseStatus(測試註解在『方法』的作用)的效果
	@ResponseStatus(reason = "方法還是會被執行，但是返回是404頁面，@ResponseStatus(測試註解在『方法』的作用)的效果",value = HttpStatus.NOT_FOUND)
	@RequestMapping("/testResponseStatusExceptionResolver2")
	public String testResponseStatusExceptionResolver2(@RequestParam("i") int i) {
		if(i == 13) {
			//自定義的Exception
			throw new UserNameNotMatchPasswordException();
		}
		System.out.println("方法還是會被執行，但是返回是404頁面，@ResponseStatus(測試註解在『方法』的作用)的效果");
		return "success";
	}
	
	//針對Http的特殊錯誤進行處理，這邊故意設置POST請求(from表單)，而我們從URL進來(GET請求)。
	@RequestMapping(value = "/testDefaultHandlerExceptionResolver",method = RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver() {
		System.out.println("testDefaultHandlerExceptionResolver");
		return "success";
	}
}
