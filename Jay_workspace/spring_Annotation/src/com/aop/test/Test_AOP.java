package com.aop.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.aop.aop.MathCalculator;
import com.aop.config.MainConfigOfAOP;

public class Test_AOP {

		@Test
		public void test01() {
			AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
			
			// �� �`�N!��ʳЫطs������A�O���|�I�s��޲z�����Ӫ���A��©I�sMathCaculator.div(int i, int j)
			//MathCalculator mathCalculator = new MathCalculator();
			//mathCalculator.div(1, 1);
			
			//���T���o�覡�A�|��������Ҧ��\��A@Before�B@After�B@AfterReturn�B@AfterThrowing
			MathCalculator mathCalculatorBean = applicationContext.getBean(MathCalculator.class);
			mathCalculatorBean.div(1, 1);
			
			applicationContext.close();
			
		}
}
