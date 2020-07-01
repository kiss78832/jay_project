package com.jay.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//�P�_Linux�t��
public class LinuxCondition implements Condition {

	/*
	 *	ConditionContext �P�_�����ϥΤW�U��(����)
	 *	AnnotatedTypeMetadata �`���T��	  
	 * 
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// TODO �O�_Linux�t��
		// 1.�������IOC�ϥΪ�beanfactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		
		// 2.������O�[����
		ClassLoader classLoader = context.getClassLoader();
		// 3.�����e���ҰT��
		Environment environment = context.getEnvironment();
		// 4.���bean�w�q���U���O
		BeanDefinitionRegistry registry = context.getRegistry();
		
		String property = environment.getProperty("os.name");
		if (property.contains("Linux")) {
			return true;
			
		}
		return false;
		
	}

}
