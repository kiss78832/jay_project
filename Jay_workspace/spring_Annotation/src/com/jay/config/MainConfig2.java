package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.jay.bean.Import01;
import com.jay.bean.Import02;
import com.jay.bean.ImportFactoryBean;
import com.jay.bean.Person;
import com.jay.condition.LinuxCondition;
import com.jay.condition.MyImportBeanDefinitionRegistrar;
import com.jay.condition.MyImportSelector;
import com.jay.condition.WindowsCondition;

/*
 *	 �e�����U����覡 :
 *		(1).Package���y + ����Хܪ`��(@Controller/@Service/@Repository/@Component)
 * 		(2).@Bean[�ɤJ�ĤT�褸��]�A�ĤT�褸�󤣷|���A�`��@Component����...
 * 		(3).@Import[�ֳt���e�����ɤJ����]
 * 			 (�@).@Import(�n�ɤJ��e����������) : �e�����N�|�۰ʵ��U�o�Ӥ���AID�q�{�������W���|
 * 			 (�G).ImportSelector:��^�ݭn�ɤJ�����󪺧������W�}�C(�Ѧ�MyImportSelector.java)
 * 			 (�T).ImportBeanDefinitionRegistrar : ��ʵ��UBean��e���� (�Ѧ�MyImportBeanDefinitionRegistrar.java)
 * 		(4).�ϥ�Spring���Ѫ�FactoryBean(�u�tBean)
 * 			 (�@).�q�{�����O�u�tBean�I�sgetObject()�Ыت�����
 * 			 (�G).�n����u�tBean�����A�ڭ̻ݭn��ID�e���@�ӫe���"&"�A�N�|��o�u�tBean����
 */
@Import({Import01.class,Import02.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class}) //�Ĥ@��Import�覡
@Conditional({WindowsCondition.class})
@Configuration
public class MainConfig2 {
	
	/*
	 *	@Scope(�վ�@�ΰ�) :
	 * 		(1).prototype : �h��ҡA�C�����|�Ыؤ@�ӷs��Bean (�`��)
	 * 		(2).singleton : ���ҡA�u�|���Ʈ��P�@��Bean (�`��)(�q�{��)
	 * 		(3).request : �P�@���ШD�Ыؤ@�ӹ��(���`��)
	 * 		(4).session : �P�@��session�Ыؤ@�ӹ��(���`��)
	 * 
	 *	@Lazy(�i�[��) :
	 *		(1)."Lazy"�u�|�f�t "singleton�C
	 */
	
	@Scope
	@Lazy
	@Bean(name = "person")
	public Person person00() {
		//����
		System.out.println("(MainConfig2.java)���e�����K�[Person...");
		return new Person("�i�T",25);
	}
	
	/*
	 *	@Conditional(����) ���Ӥ@�w������i��P�_�A�������󵹮e�������UBean
	 *		(1).�i�ۤv�g�@�����O�����ҡA�����O�O�o�n extends Conditional �A�A�Q��@Conditional�N��i�����ҡC
	 *		(2).�y�{:
	 *				�ϥΨ�Bean���ɭ� 	-> �X�ݸ�@Bean	-> �z�L@Conditional�Y�^��true�N�Ыظ�Bean�B�Yfalse�N���Ыظ�Bean�C
	 *		(3).�]�i�H�t�b���O�W(MainConfig2)�A�N�����S�L�N���|�Ыظ����O��Bean
	 */
	@Conditional({WindowsCondition.class}) 
	@Bean(name = "bill")
	public Person person01() {
		System.out.println("(MainConfig2.java)�Ы�bill_Bean�e��");
		return new Person("Bill Gates",62);
	}
	
	@Conditional(LinuxCondition.class)
	@Bean(name = "linus")
	public Person person02() {
		System.out.println("(MainConfig2.java)�Ы�linus_Bean�e��");
		return new Person("linus",48);
	}
	
	//FactoryBean
	@Bean
	public ImportFactoryBean importFactoryBean() {
		return new ImportFactoryBean();
		
	}
}
