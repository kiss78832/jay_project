package com.jay.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//判斷Linux系統
public class LinuxCondition implements Condition {

	/*
	 *	ConditionContext 判斷條件能使用上下文(環境)
	 *	AnnotatedTypeMetadata 注釋訊息	  
	 * 
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// TODO 是否Linux系統
		// 1.能獲取到IOC使用的beanfactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		
		// 2.獲取類別加載器
		ClassLoader classLoader = context.getClassLoader();
		// 3.獲取當前環境訊息
		Environment environment = context.getEnvironment();
		// 4.獲取bean定義註冊類別
		BeanDefinitionRegistry registry = context.getRegistry();
		
		String property = environment.getProperty("os.name");
		if (property.contains("Linux")) {
			return true;
			
		}
		return false;
		
	}

}
