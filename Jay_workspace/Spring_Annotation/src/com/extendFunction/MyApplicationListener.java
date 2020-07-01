package com.extendFunction;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent>{

	//當容器中發布此事件以後，方法會觸發
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("收到事件 : "+ event);
	}

}
