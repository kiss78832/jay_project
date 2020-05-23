package com.aop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/*
 *	@Aspect : 告訴Spring當前類別是一個切面類別 
 */

@Aspect
public class LogAspects {

	/*
	 *	抽取公共切入點表達式
	 *		a).本類別引用。
	 *		b).其他的切面引用。
	 */
	@Pointcut("execution(public int com.aop.aop.MathCalculator.*(..))") //一個點[.]代表一個參數 
	public void pointCut() {}
	
	
	/*
	 * 1.@Before在目標方法之前切入 : 切入點表達式(指定在哪個方法切入) 
	 * 2.★★★ JoinPoint一定要排在參數列表第一位 ，錯誤示範:(public void logStart(Object obj,JoinPoint joinPoint))
	 * 3.execution("表達式")
	 */
	@Before("execution(public int com.aop.aop.MathCalculator.*(..))")//第一種寫法直接寫
	public void logStart(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println("(LogAspects.java) "+joinPoint.getSignature().getName()+" 除法執行...取得參數 : {"+Arrays.asList(args)+"}");
	}
	
	@After("pointCut()")//第二種寫法，寫在變數裡面利用@PointCut()引用
	public void logEnd(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println("(LogAspects.java) "+joinPoint.getSignature().getName()+" 除法結束...取得參數 : {"+Arrays.asList(args)+"}");
	}
	
	/*
	 *	value = 若未設定@AfterReturning("pointCut()") 預設就是value
	 *	returning = obj -> 告訴Spring把返回值封裝到參數obj身上
	 */
	@AfterReturning(value = "pointCut()",returning = "obj") //
	public void logReturn(JoinPoint joinPoint,Object obj) {
		
		System.out.println("(LogAspects.java)除法正常返回...參數返回結果 : {"+obj+"}");
	}
	
	/*
	 *	throwing = ex -> 告訴Spring把返回錯誤封裝到參數ex身上
	 *	測試請改為 athCalculatorBean.div(1, 0); 
	 */
	@AfterThrowing(value = "pointCut()", throwing = "ex")
	public void logException(JoinPoint joinPoint,Exception ex) {
		System.out.println("(LogAspects.java) "+joinPoint.getSignature().getName()+" 除法異常...返回Exception : {"+ex+"}");
	}

}
