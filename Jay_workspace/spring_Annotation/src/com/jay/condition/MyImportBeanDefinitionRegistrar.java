package com.jay.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.jay.bean.Import03;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{

	
	/*
	 *	AnnotationMetadata : 當前類注解的訊息 
	 * 	BeanDefinitionRegistry : BeanDefinition 註冊類別
	 * 		把所有需要添加到容器中的Bean ; 調用
	 * 		BeanDefinitionRegistry.registerBeanDefinition手工註冊進來
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		boolean definition = registry.containsBeanDefinition("com.jay.bean.Import01");
		boolean definition2 = registry.containsBeanDefinition("com.jay.bean.Import02");
		
		//當 definition 跟 definition2 都有被註冊時，report03會被註冊到Bean裡面
		if(definition && definition2) {
			
			//指定Bean定義訊息，(Bean的類型,Bean...)
			RootBeanDefinition beanDefinition = new RootBeanDefinition(Import03.class); 
			//註冊一個Bean指名bean名稱
			registry.registerBeanDefinition("report03", beanDefinition);
		}
	}
	
	
}
