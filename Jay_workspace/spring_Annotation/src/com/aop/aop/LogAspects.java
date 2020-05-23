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
 *	@Aspect : �i�DSpring��e���O�O�@�Ӥ������O 
 */

@Aspect
public class LogAspects {

	/*
	 *	������@���J�I��F��
	 *		a).�����O�ޥΡC
	 *		b).��L�������ޥΡC
	 */
	@Pointcut("execution(public int com.aop.aop.MathCalculator.*(..))") //�@���I[.]�N��@�ӰѼ� 
	public void pointCut() {}
	
	
	/*
	 * 1.@Before�b�ؼФ�k���e���J : ���J�I��F��(���w�b���Ӥ�k���J) 
	 * 2.������ JoinPoint�@�w�n�Ʀb�ѼƦC��Ĥ@�� �A���~�ܽd:(public void logStart(Object obj,JoinPoint joinPoint))
	 * 3.execution("��F��")
	 */
	@Before("execution(public int com.aop.aop.MathCalculator.*(..))")//�Ĥ@�ؼg�k�����g
	public void logStart(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println("(LogAspects.java) "+joinPoint.getSignature().getName()+" ���k����...���o�Ѽ� : {"+Arrays.asList(args)+"}");
	}
	
	@After("pointCut()")//�ĤG�ؼg�k�A�g�b�ܼƸ̭��Q��@PointCut()�ޥ�
	public void logEnd(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println("(LogAspects.java) "+joinPoint.getSignature().getName()+" ���k����...���o�Ѽ� : {"+Arrays.asList(args)+"}");
	}
	
	/*
	 *	value = �Y���]�w@AfterReturning("pointCut()") �w�]�N�Ovalue
	 *	returning = obj -> �i�DSpring���^�ȫʸ˨�Ѽ�obj���W
	 */
	@AfterReturning(value = "pointCut()",returning = "obj") //
	public void logReturn(JoinPoint joinPoint,Object obj) {
		
		System.out.println("(LogAspects.java)���k���`��^...�Ѽƪ�^���G : {"+obj+"}");
	}
	
	/*
	 *	throwing = ex -> �i�DSpring���^���~�ʸ˨�Ѽ�ex���W
	 *	���սЧאּ athCalculatorBean.div(1, 0); 
	 */
	@AfterThrowing(value = "pointCut()", throwing = "ex")
	public void logException(JoinPoint joinPoint,Exception ex) {
		System.out.println("(LogAspects.java) "+joinPoint.getSignature().getName()+" ���k���`...��^Exception : {"+ex+"}");
	}

}
