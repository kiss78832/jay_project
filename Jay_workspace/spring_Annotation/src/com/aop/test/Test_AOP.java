package com.aop.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.aop.aop.MathCalculator;
import com.aop.config.MainConfigOfAOP;

public class Test_AOP {

		@Test
		public void test01() {
			AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
			
			// ★ 注意!手動創建新的物件，是不會呼叫到管理的那個物件，單純呼叫MathCaculator.div(int i, int j)
			//MathCalculator mathCalculator = new MathCalculator();
			//mathCalculator.div(1, 1);
			
			//正確取得方式，會執行切面所有功能，@Before、@After、@AfterReturn、@AfterThrowing
			MathCalculator mathCalculatorBean = applicationContext.getBean(MathCalculator.class);
			mathCalculatorBean.div(1, 1);
			
			applicationContext.close();
			
		}
}
