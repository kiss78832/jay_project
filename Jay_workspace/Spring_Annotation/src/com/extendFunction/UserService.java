package com.extendFunction;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	//自訂監聽器
	@EventListener(classes= {ApplicationEvent.class})
	public void listen(ApplicationEvent event) {
		System.out.println("UserService.java UserService監聽事件"+event);
	}
}
