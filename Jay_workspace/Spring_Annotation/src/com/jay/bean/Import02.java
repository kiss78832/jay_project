package com.jay.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/*
 *	測試各Aware作用 
 */
@Component
public class Import02 implements ApplicationContextAware,BeanNameAware,EmbeddedValueResolverAware{
	
	//暫存用，純測試
	private ApplicationContext applicationCOntext;

	//ApplicationContextAware實作方法  [當前的applicationContext， 這也可以調用容器的服務]
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("(import02.java)傳入的IOC : "+applicationContext);
		this.applicationCOntext = applicationContext;
	}

	//BeanNameAware實作方法
	@Override
	public void setBeanName(String name) {
		System.out.println("(import02.java)獲取當前Bean名字 : "+name);
	}

	//EmbeddedValueResolverAware實作方法  [用來解析佔位字符 #{}  ${} ]
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		String resolveStringValue = resolver.resolveStringValue("解析佔位符號 : 作業系統 = ${os.name} , 計算100*20 = #{100*20}");
		System.out.println("(import02.java)解析的字串 : "+resolveStringValue);
	}
	
}
