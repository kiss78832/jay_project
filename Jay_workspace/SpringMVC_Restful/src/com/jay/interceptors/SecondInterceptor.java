package com.jay.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定義攔截器作法:
 * 		(1).實作HandlerInterceptor
 * 		(2).在springmvc.xml配置<bean class="com.jay.interceptors.FirstIntercetor"></bean>(注意:要包在<mvc:interceptors>裡面，bean指向自己路徑。)
 *		(3).使用場合:
 *				a.攔截器適合做權限使用，若沒有此權限就不會給你調用目標方法。
 *				b.日誌
 *				c.事務
 *		(4).執行流程:
 *				FirstInterceptor[#preHandle()] -> HandlerAdapter[#handle] -> FirstInterceptor[#postHandle()] 
 *					DispatcherServlet[#render] -> FirstInterceptor[#afterCompletion()]
 *
 *
 *
 *
 * 加入SecondIntercetor.java特定攔截器:
 * 		(1).當FirstIntercetor[#preHandle()]返回值設false，結果會只有執行FirstIntercetor[#preHandle()]。
 * 		(2).當FirstIntercetor[#preHandle()]返回值設true，SecondInterceptor[#preHandle()]返回值設false : 請參考流程圖參考二
 * 		(3).執行流程:
 * 				FirstIntercetor[#preHandle()] → SecondInterceptor[#preHandle()] 
 * 															↓
 * 											      HandlerAdapter[#handle]
 *  														↓
 *  		   FirstIntercetor[#postHandle()] ← SecondIntercetor[#postHandle()]
 *  						|__ __ __ __ __ __ __ __ __ __ _
 *  														↓
 *  											  DispatcherServlet[#render]
 *  														↓
 *		 FirstInterceptor[#afterCompletion()] ← SecondInterceptor[#afterCompletion()]
 *
 *
 * 		(4).當FirstIntercetor[#preHandle()]返回值設true，SecondInterceptor[#preHandle()]返回值設false。
 * 			執行流程:
 * 						(return:true)				   (return:false)
 * 				FirstIntercetor[#preHandle()] → SecondInterceptor[#preHandle()] 
 * 							 _ __ __ __ __ __ __ __ __ __ __|
 *  						↓								
 *		 	FirstInterceptor[#afterCompletion()]
 *			
 *			結論:
 *				(1).#afterCompletion()這個方法是釋放資源，因為FirstIntercetor有執行過所以需要被釋放，
 *						再#preHandle()回傳true才會執行，false則是不會。
 *				(2).ConversionServiceExposingInterceptor才是第一個攔截器，之後才會運行到我們自定義的攔截器。
 * */
public class SecondInterceptor implements HandlerInterceptor{
	
	/*
	 * 預先處理:
	 * 	返回值為true: 則繼續調用後續的攔截器和目標方法。(順利執行preHandle、postHandle、afterCompletion。)
	 *  返回值為false: 則不會再調用後續的攔截器和目標方法。(執行到preHandle就跳出結束。)
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println("[SecondInterceptor] preHandle");
		return true;
	}
	
	/*
	 * 後處理(調用目標方法之後，processDispatchResult之前):
	 * 	(1).可以對請求域(HttpServletRequest)中的屬性或是視圖(ModelAndView)做出修改。
	 * 
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("[SecondInterceptor] postHandle");		
	}
	
	/*
	 * 完成後(processDispatchResult之後):
	 * 	(1).釋放資源。
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("[SecondInterceptor] afterCompletion");
	}



}
