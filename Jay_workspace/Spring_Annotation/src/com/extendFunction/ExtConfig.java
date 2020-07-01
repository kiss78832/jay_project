package com.extendFunction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jay.bean.Car;


/*
 *	�X�i��z :
 *	�� BeanFactoryPostProcessor
 *	�� BeanDefinitionRegistryPostProcessor
 *	�� ApplicationListener
 *
 *	BeanPostProcessor:bean��m�B�z���Abean�Ыت����l�ƫe��i���d�I�u�@���C
 *	1.�bBeanFactory�зǪ�l�Ƥ���եΡA�Ө�w�M�ק�BeanFactory�����e�C
 *	2.�Ҧ�Bean�w�q�w�g�O�s�[����beanFactory�A���Obean������٬��ЫءC
 *	
 *	
 *	�� BeanFactoryPostProcessor:beanFactory����m�B�z��
 *	��z:
 *	1.IOC�e���Ыت���
 *	2.invokeBeanFactoryPostProcessors(beanFactory) ����[BeanFactoryPostProcessor]
 *	     �p��Ө�Ҧ�BeanFactoryPostProcessor�ð���L�̪���k
 *	  	1).�����bBeanFactory�����Ҧ������OBeanFactoryPostProcessor������A�ð���L�̪���k
 * 	  	2).�b��l�ƳЫب�L����e������
 * 
 *	�� BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *	-> postProcessBeanDefinitionRegistry():�b�Ҧ���Bean�w�q�T���N�n�Q�[���ABean����٥��Ы�
 *	1.��@BeanDefinitionRegistryPostProcessor�A�ݹ�@ postProcessBeanDefinitionRegistry()�B�~������postProcessBeanFactory()
 *	2.�u����BeanFactoryPostProcessor�A������BeanDefinitionRegistryPostProcessor�A�Ӱ�BeanFactoryPostProcessor�C
 *	3.�i�H�Q��BeanDefinitionRegistryPostProcessor�h���e���K�[�@�Ǥ���BBean�C
 *	��z:
 *		1.IOC�Ыت���
 *		2.refresh() -> invokeBeanFactoryPostProcessors(beanFactory);
 *		3.�q�e���������Ҧ���BeanDefinitionRegistryPostProcessor����
 *			1).�̦�Ĳ�o�Ҧ���postProcessBeanDefinitionRegistry()�C
 *			2).�AĲ�opostProcessBeanFactory()��BeanFactoryPostProcessor
 *		4.�A�q�e�������BeanFactoryPostProcessor����A�M��̦�Ĳ�opostProcessBeanFactory()��k
 *
 *	�� ApplicationListener :��ť�e�����o�����ƥ�A�ƥ��X�ʼҫ��}�o�C
 *		public interface ApplicationListener<E extends ApplicationEvent> -> ��ťApplicationEvent�Ψ�U�����l�ƥ�:
 *	1. ��ť�|�Өƥ��~��ApplicationContextEvent and ApplicationEvent
 *		1).ContextRefreshedEvent �e����s�N�|�o���ƥ�
 *		2).ContextClosedEvent �e�������N�|�o���ƥ�
 *		3).ContextStartedEvent �e���}�l�N�|�o���ƥ�
 *		4).ContextStoppedEvent �e������N�|�o���ƥ�
 *		.......extends ApplicationContextEvent extends ApplicationEvent
 *	2.�B�J :
 *		1).�g�@�Ӻ�ť��(ApplicationListener��{��)�Ӻ�ť�Y�ƥ�(ApplicationEvent�Ψ�l��)
 *		2).���ť���[�J��e��
 *		3).�u�n�e�����������ƥ󪺵o���A�ڭ̴N���ť��o�Өƥ�C[��s�B�����B�}�l�B����]
 *		4).�o���@�Өƥ�:
 *			applicationContext.publishEvent()
 *	
 *	��z: [ContextRefreshedEvent�BConfigTest$1[source=�ڵo�����ƥ�]�BContextClosedEvent]
 *		1.ContextRefreshedEvent�ƥ�
 *			A.�e���Ыت���:refresh()�C
 *			B.finishRefresh(); �e����s����
 *			C.publishEvent(new ContextRefreshedEvent(this));
 *		2.�ۤv�o���ƥ�
 *
 *		�����o�����ƥ��ۤv�o�����ƥ󳣷|�q�L[�ƥ�o���y�{]�C
 *				�i�ƥ�o���y�{�j:
 *					a).��ƥ�o�e��U��ť���A����ƥ󪺦h����(���o��):getApplicationEventMulticaster()
 *					b).multicastEvent���o�ƥ�
 *					c).�����Ҧ���ApplicationListener:
 *							for(final ApplicationListener<?> listener : getApplicationListeners(event,type))
 *								1.�p�G��Executor�A�i�H����ϥ�Executor�i�沧�B���o;
 *									Executor executor = getTaskExecutor();
 *								2.�_�h�A�P�B���覡��������listener��k�AinvokeListener(listener,event);
 *								    ����listener�^��onApplicationEvent��k
 * 
 */
@ComponentScan("com.extendFunction")
@Configuration
public class ExtConfig {

	@Bean
	public Car car() {
		return new Car();
	}
}
