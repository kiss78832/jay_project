package com.jay.bean;

import org.springframework.beans.factory.FactoryBean;


/*
 *	��^�@��Import02����A�o�Ӫ���|�K�[��e���� 
 */
public class ImportFactoryBean implements FactoryBean<Import02>{

	//�]�m��^Object
	@Override
	public Import02 getObject() throws Exception {
		System.out.println("ImportFactoryBean.getObject()�Q�I�s");
		return new Import02();
	}

	//�]�m��^Type
	@Override
	public Class<?> getObjectType() {
		System.out.println("ImportFactoryBean.getObjectType()�Q�I�s");
		return Import02.class;
	}

	//true:�N��Ыئ�Bean�O Singleton(���) ; false:�N��O prototype(�h��)
	@Override
	public boolean isSingleton() {
		System.out.println("ImportFactoryBean.isSingleton()�Q�I�s");
		return false;
	}

}
