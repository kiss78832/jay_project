package com.jay.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/*
 *	���զUAware�@�� 
 */
@Component
public class Import02 implements ApplicationContextAware,BeanNameAware,EmbeddedValueResolverAware{
	
	//�Ȧs�ΡA�´���
	private ApplicationContext applicationCOntext;

	//ApplicationContextAware��@��k  [��e��applicationContext�A �o�]�i�H�եήe�����A��]
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("(import02.java)�ǤJ��IOC : "+applicationContext);
		this.applicationCOntext = applicationContext;
	}

	//BeanNameAware��@��k
	@Override
	public void setBeanName(String name) {
		System.out.println("(import02.java)�����eBean�W�r : "+name);
	}

	//EmbeddedValueResolverAware��@��k  [�ΨӸѪR����r�� #{}  ${} ]
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		String resolveStringValue = resolver.resolveStringValue("�ѪR����Ÿ� : �@�~�t�� = ${os.name} , �p��100*20 = #{100*20}");
		System.out.println("(import02.java)�ѪR���r�� : "+resolveStringValue);
	}
	
}
