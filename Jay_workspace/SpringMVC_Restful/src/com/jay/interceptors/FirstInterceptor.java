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
 *				FirstIntercetor[#preHandle()] -> HandlerAdapter[#handle] -> FirstIntercetor[#postHandle()] 
 *					DispatcherServlet[#render] -> FirstIntercetor[#afterCompletion()]
 *
 * */
public class FirstInterceptor implements HandlerInterceptor{
	
	/*
	 * 預先處理:
	 * 	返回值為true: 則繼續調用後續的攔截器和目標方法。(順利執行preHandle、postHandle、afterCompletion。)
	 *  返回值為false: 則不會再調用後續的攔截器和目標方法。(執行到preHandle就跳出結束。)
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println("[FirstIntercetor] preHandle");
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
		System.out.println("[FirstIntercetor] postHandle");		
	}
	
	/*
	 * 完成後(processDispatchResult之後):
	 * 	(1).釋放資源。
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("[FirstIntercetor] afterCompletion");
	}

}
