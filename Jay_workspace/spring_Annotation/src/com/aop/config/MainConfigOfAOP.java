package com.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.aop.aop.LogAspects;
import com.aop.aop.MathCalculator;


/*
 *	AOP[�ʺA�N�z] : ���b�{���B������ʺA���N�Y�q�N�X���J����w��k���w��m�i��B�檺�s���覡
 * 		1).�ɤJAOP�Ҷ��A Spring AOP (aspectjweaver-1.8.13) JDK�O1.8�N��1.8�}�Y��
 * 		2).�w�q�@�ӷ~���޿���(MathCaluclator):�b�~���޿�B�檺�ɭԱN��x�i��L�X(��k���e�B��k�B��)
 * 		3).�w�q�@���x�������O(LogAspects):�������O�̭�����k�ݭn�ʺA�P��MathCalculator.div�B�����̵M�����
 * 				�q����k:
 * 					�e�m�q��[@Before]:�b�ؼФ�k(div)���椧�e�B�@(EX:logStart)
 * 					��m�q��[@After]:�b�ؼФ�k(div)���椧��B�@�A���װʧ@���`�����٬O�X�{���`���|Ĳ�o(EX:logdEnd)
 * 					��^�q��[@AfterReturning]:�b�ؼФ�k(div)���`��^����B�@(EX:logReturn)
 * 					���`�q��[@AfterThrowing]:�b�ؼФ�k(div)�X�{���`�H��B�@(EX:logdException)
 * 					��¶�q��[@Around]:�ʺA�N�z�A��ʱ��i�ؼФ�k�B��(joinPoint.procced())
 * 		4).���������O���ؼФ�k�е���ɦ�a���B��(�q������)
 * 		5).�N�������O�M�~���޿���(�ؼФ�k�Ҧb��)���[�J�ˮe����
 * 		6).�����i�DSpring�������O�O������(���������W�[�J�@�ӵ���:@Aspect)
 * 		7).���t�m�����[�W@EnableAspectJAutoProxy [�}�Ұ����Ѫ�AOP�Ҧ�] �����I!���[ �ASpring�����ܦh@EnalbeXXXX�����O
 * 
 * 
 * 	�T�B:
 * 		�Ĥ@�B : �N�~���޿褸��M�������O���[�J��e�����A�i�DSpring���Ӧ�������(@Aspect)
 * 		�ĤG�B : �b�������W���C�@�ӳq����k�W�е��q�����ѡA�i�DSpring��ɦ�a�B��(���J�I��F��)
 * 		�ĤT�B : �}�Ұ�����AOP�Ҧ� : @EnableAspectJAutoProxy
 * 
 * 	@EnableAspectJAutoProxy :
 * 		a).@Import(AspectJAutoProxyRegistrar.class)
 *	
 */

@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

	//�N�~���޿�[�J��e����
	@Bean
	public MathCalculator calculator() {
		return new MathCalculator();
	}
	
	//�N�������O�[�J��e����
	@Bean
	public LogAspects logAspects() {
		return new LogAspects();
	}
}
