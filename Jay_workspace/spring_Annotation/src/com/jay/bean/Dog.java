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
	
	//����Ыؽ�Ȥ���I�s
	@PostConstruct
	public void init() {
		System.out.println("(Dag.java)Dog...@PostConstruct Annotation");
	}
	
	//�e���������󤧫e�I�s
	@PreDestroy
	public void detory() {
		System.out.println("(Dag.java)Dog...@PreDestory Annotation");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	
	
}
