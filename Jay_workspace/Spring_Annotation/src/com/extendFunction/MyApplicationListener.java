package com.extendFunction;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent>{

	//��e�����o�����ƥ�H��A��k�|Ĳ�o
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("����ƥ� : "+ event);
	}

}
