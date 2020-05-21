package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jay.bean.Person;

//�ϥ�@PropertySourceŪ���~���t�m��󤤪�key/value�A�O�s��B�������ܶq���C   @PropertySource���U��k�� String[] value();
@PropertySource(value= {"classpath:/person.properties"})
@Configuration
public class MainConfigOfPropertyValues {
	
	@Bean
	public Person person() {
		return new Person();
	}
}
