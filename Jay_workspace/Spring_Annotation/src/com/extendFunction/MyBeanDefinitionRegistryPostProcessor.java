package com.extendFunction;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import com.jay.bean.Car;
import com.jay.bean.Cat;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBeanDefinitionRegistryPostProcessor.class postProcessBeanFactory()[Bean�ƶq] = "+beanFactory.getBeanDefinitionCount());
	}

	//BeanDefinitionRegistry Bean�w�q�T�����O�s���ߡA�H��BeanFactory�N�O����BeanDefinitionRegistry
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("MyBeanDefinitionRegistryPostProcessor.class postProcessBeanDefinitionRegistry()[Bean�ƶq] = "+registry.getBeanDefinitionCount());
		
		//��ؤ覡�i�H�w�qBean���ӷ�
		RootBeanDefinition beanDefinition = new RootBeanDefinition(Car.class);
		AbstractBeanDefinition abstractBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Cat.class).getBeanDefinition();
		
		//registerBeanDefinition �i�H���U�s��Bean�A�õ���Bean�R�W
		registry.registerBeanDefinition("Car_test", beanDefinition);
		registry.registerBeanDefinition("Cat_test", abstractBeanDefinition);
	}
}
