package com.jay.web.customizeServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("MyListener ------ contextInitialized  Web 應用啟動");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("MyListener ----- contextDestroyed() Web 項目銷毀");
	}
}
