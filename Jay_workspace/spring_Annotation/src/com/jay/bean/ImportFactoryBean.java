package com.jay.bean;

import org.springframework.beans.factory.FactoryBean;


/*
 *	返回一個Import02物件，這個物件會添加到容器中 
 */
public class ImportFactoryBean implements FactoryBean<Import02>{

	//設置返回Object
	@Override
	public Import02 getObject() throws Exception {
		System.out.println("ImportFactoryBean.getObject()被呼叫");
		return new Import02();
	}

	//設置返回Type
	@Override
	public Class<?> getObjectType() {
		System.out.println("ImportFactoryBean.getObjectType()被呼叫");
		return Import02.class;
	}

	//true:代表創建此Bean是 Singleton(單例) ; false:代表是 prototype(多例)
	@Override
	public boolean isSingleton() {
		System.out.println("ImportFactoryBean.isSingleton()被呼叫");
		return false;
	}

}
