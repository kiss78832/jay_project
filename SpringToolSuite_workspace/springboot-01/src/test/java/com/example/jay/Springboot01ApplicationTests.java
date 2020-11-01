package com.example.jay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.jay.bean.Person;

/**
 * SpringBoot 單元測試
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01ApplicationTests {

	@Autowired
	Person person;

	@Autowired
	ApplicationContext ioc;

	@Test
	public void contextLoads() {
		System.out.println("測試 = "+person);
	}

	//測試取得beans.xml所設置的Bean
	@Test
	public void testHelloService() {
		boolean checkBean = ioc.containsBean("helloService"); //對應xml的bean id
		System.out.println("測試Bean = "+checkBean);
	}

	@Test
	public void testLogging() {
		Logger Log = LoggerFactory.getLogger(getClass());

		Log.trace("這是trace日誌...");
		Log.debug("這是debug日誌...");
		Log.info("這是info日誌...");
		Log.warn("這是warn日誌...");
		Log.error("這是error日誌...");
	}

}
