package com.jay.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Dog implements ApplicationContextAware{

	private ApplicationContext applicationContext; 
	
	
	public Dog() {
		System.out.println("(Dag.java)dog constructor...");
	}
	
	//物件創建賦值之後呼叫
	@PostConstruct
	public void init() {
		System.out.println("(Dag.java)Dog...@PostConstruct Annotation");
	}
	
	//容器移除物件之前呼叫
	@PreDestroy
	public void detory() {
		System.out.println("(Dag.java)Dog...@PreDestory Annotation");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	
	
}
