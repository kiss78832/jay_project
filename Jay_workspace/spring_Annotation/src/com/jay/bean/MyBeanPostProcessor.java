package com.jay.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/*
 *	��m�B�z����Bean (��l�ƫe��i��B�z�u�@)
 *	�N��m�B�z����J�bSpring�޲z�e���A�`��@Component
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("(MyBeanPostProcessor.java)�I�spostProcessBeforeInitialization(),"+"BeanName : "+beanName +"=>"+bean);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("(MyBeanPostProcessor.java)�I�spostProcessAfterInitialization(),"+"BeanName : "+beanName +"=>"+bean);
		return bean;
	}
	
}
