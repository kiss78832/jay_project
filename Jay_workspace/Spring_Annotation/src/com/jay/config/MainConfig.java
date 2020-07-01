package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

import com.jay.bean.Person;
import com.jay.service.BookService;

//�t�m���O(.java) = �t�m���(xml)

@Configuration //�����ѧi�DSpring�o�O�@�Ӱt�m���O
@ComponentScan(value = "com.jay",includeFilters = {
		/*
		 * �b�t�m���O�����U�@�ӱ��y���A@ComponentScan(value = ���w�n���y��package,[excludeFilters(�ư����w��package),includeFilters(�]�t���wpackage)]
		 * 		(1).��l�X:Filter[] excludeFilters() default {} -> �ҥH�O�@��Filter[]�}�C�A�i�H�Τj�A��{}�B�z�h���޿�C
		 * 		(2).@Filter(type = FilterType.(�A�n�ư�������),classes = {���������W��})
		 * 		(3).�Y�n��"includeFilters"�A�O�o��@ComponentScan���UuseDefaultFilters�w�]"true"��"false"�C
		 * 		(4).Java8 �H��i�H�����ϥΦh��@ComponentScan�h���]�w�AJava8�H�e�Х�@ComponentScans(���U�i�H�g�h��@ComponentScan)�C
		 * 		(5).FilterType�`������:
		 * 				ANNOTATION = ���ӵ��� (�`��)
		 * 				ASSIGNABLE_TYPE = �̷ӫ��w�����O (�`��)
		 * 				ASPECTJ = �ϥ�ASPECTJ��F��
		 * 				REGEX = ���h��F��
		 * 				CUSTOM = �۩w�q�W�h (�����gFilterType��@���O)
		 */
		@Filter(type = FilterType.ANNOTATION,classes = {Controller.class}),
		@Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookService.class})
		
		//@Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class}) //���զ۩w�qFilter
},useDefaultFilters = false) 
public class MainConfig {
	
	/*
	 *	@Bean :
	 *		(1).���e�����U�@��Bean�A����(Person)����^�������Aid�q�{�O��k�W��(person)�A��Bean���O��Ҫ��A���ީI�s�h�֦����O�P�@�ӹ���C
	 *		(2).�Y�ݭn��@Bean�R�W�A@Bean(name = "�۩w�q�W��")
	 */
	@Bean 
	public Person person() {
		return new Person("lisi",20);
	}
}
