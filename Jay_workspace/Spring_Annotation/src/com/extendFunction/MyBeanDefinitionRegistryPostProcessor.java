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
		System.out.println("MyBeanDefinitionRegistryPostProcessor.class postProcessBeanFactory()[Bean數量] = "+beanFactory.getBeanDefinitionCount());
	}

	//BeanDefinitionRegistry Bean定義訊息的保存中心，以後BeanFactory就是按照BeanDefinitionRegistry
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("MyBeanDefinitionRegistryPostProcessor.class postProcessBeanDefinitionRegistry()[Bean數量] = "+registry.getBeanDefinitionCount());
		
		//兩種方式可以定義Bean的來源
		RootBeanDefinition beanDefinition = new RootBeanDefinition(Car.class);
		AbstractBeanDefinition abstractBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Cat.class).getBeanDefinition();
		
		//registerBeanDefinition 可以註冊新的Bean，並給該Bean命名
		registry.registerBeanDefinition("Car_test", beanDefinition);
		registry.registerBeanDefinition("Cat_test", abstractBeanDefinition);
	}
}
