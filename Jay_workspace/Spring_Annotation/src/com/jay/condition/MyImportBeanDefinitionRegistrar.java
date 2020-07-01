package com.jay.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.jay.bean.Import03;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{

	
	/*
	 *	AnnotationMetadata : ��e���`�Ѫ��T�� 
	 * 	BeanDefinitionRegistry : BeanDefinition ���U���O
	 * 		��Ҧ��ݭn�K�[��e������Bean ; �ե�
	 * 		BeanDefinitionRegistry.registerBeanDefinition��u���U�i��
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		boolean definition = registry.containsBeanDefinition("com.jay.bean.Import01");
		boolean definition2 = registry.containsBeanDefinition("com.jay.bean.Import02");
		
		//�� definition �� definition2 �����Q���U�ɡAreport03�|�Q���U��Bean�̭�
		if(definition && definition2) {
			
			//���wBean�w�q�T���A(Bean������,Bean...)
			RootBeanDefinition beanDefinition = new RootBeanDefinition(Import03.class); 
			//���U�@��Bean���Wbean�W��
			registry.registerBeanDefinition("report03", beanDefinition);
		}
	}
	
	
}
