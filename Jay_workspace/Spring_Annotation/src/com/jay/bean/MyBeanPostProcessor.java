package com.jay.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/*
 *	後置處理器的Bean (初始化前後進行處理工作)
 *	將後置處理器放入在Spring管理容器，注解@Component
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("(MyBeanPostProcessor.java)呼叫postProcessBeforeInitialization(),"+"BeanName : "+beanName +"=>"+bean);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("(MyBeanPostProcessor.java)呼叫postProcessAfterInitialization(),"+"BeanName : "+beanName +"=>"+bean);
		return bean;
	}
	
}
