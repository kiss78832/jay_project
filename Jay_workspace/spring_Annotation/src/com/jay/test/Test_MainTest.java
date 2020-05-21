package com.jay.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.jay.bean.Import01;
import com.jay.bean.ImportFactoryBean;
import com.jay.bean.Person;
import com.jay.config.MainConfig;
import com.jay.config.MainConfig2;

public class Test_MainTest {

	public static void main(String[] args) {
		/*
		 * �ϥ�XML���U�g�k ApplicationContext applicationContext = new
		 * ClassPathXmlApplicationContext("beans.xml"); Person bean =
		 * (Person)applicationContext.getBean("person"); System.out.println(bean);
		 */

		// �ϥ�annotation�覡�g�k
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig.class);
		Person bean = applicationContext.getBean(Person.class);
		System.out.println(bean);

		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : namesForType) {
			System.out.println(name);
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();

		for (String name : definitionNames) {
			// �L�X�QSpring����ե�
			System.out.println("�QSpring������� : " + name);
		}
	}

	@Test
	public void test02() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();

		for (String name : definitionNames) {
			// �L�X�QSpring����ե�
			System.out.println("�QSpring������� : " + name);
		}

		//���ռХ� @Bean �����O�q�{���O"���"
		Object bean01 = applicationContext.getBean("person");
		Object bean02 = applicationContext.getBean("person");
		System.out.println("�O�_������ : "+ bean01.hashCode()+ " , "+ bean02.hashCode() );
		
	}
	
	/*
	 *	����@Bean��singleton(���)�� : 
	 *		(1).IOC�e���Ұʮɷ|�եΤ�k�Ыت���(Person)��iIOC�e�����A�H��C������N�O"�q�e������"(�N�Omap.get()����)�C
	 *
	 *	����@Bean�� prototype(�h��) �ɡA��ϥΨ��Bean���ɭԤ~�|�Ыظӹ�ҡC
	 *		(1).IOC�e���Ұʨä��|�h�եΤ�k�Ыت���b�e�����A�C��������ɭԤ~�|�եΤ�k�Ыت���C
	 */
	@Test
	public void test03() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		System.out.println("���� - IOC�e���Ы�");
		//prototype�|�s��Ыب��Person�A��singleton�u�|�Ыؤ@�ӡC
		Object bean01 = applicationContext.getBean("linus"); //�Ыخe��
		Object bean02 = applicationContext.getBean("person"); //�Ыخe��
		}
	
	/*
	 *	@Lazy�i�[�� :
	 * 		(1)."Lazy" �u�|�f�t "singleton" �C
	 * 		(2)."Lazy" -> �����O�N�z�ưȳB�z�A�ҥH��ݭnBean���ɭԤ~�|�ЫءC  singleton -> ���ƨϥβĤ@���Ыت�����C
	 */
	@Test
	public void test04() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		System.out.println("���� - IOC�e���Ы�");
		Object bean01 = applicationContext.getBean("person"); //�Ыخe��
		Object bean02 = applicationContext.getBean("person"); //�Ыخe��
	}
	
	/*
	 *	applicationContext.getBeanNamesForType() -> ���oPerson���O��@Bean���W��
	 *	applicationContext.getBeansOfType() -> ���oPerson���O����A��^Map���X(key=ID,value=Person����)
	 *	applicationContext.getEnvironment() -> �i�H�d�������ܶq���ȡC
	 *
	 */
	@Test
	public void test05() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : namesForType) {
			System.out.println(name);
		}
		
		Map<String,Person> persons = applicationContext.getBeansOfType(Person.class);
		System.out.println(persons);
		
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String property = environment.getProperty("os.name");
		System.out.println("�d�ݧ@�~�t�� : "+property);
	}
	
	
	/*
	 *	Import�d�Ҵ��ե�
	 *	���ժ`�ѦbMainConfig2���O��@Import�O�_�����\
	 */
	@Test
	public void test06() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		Import01 import01 = applicationContext.getBean(Import01.class);
		System.out.println("�T�{����O�_�s�b : "+import01);
		
		//�I�sImportFactoryBean �d��
		Object bean01 = applicationContext.getBean("importFactoryBean");//����ID���Bean
		Object bean02 = applicationContext.getBean("importFactoryBean");
		System.out.println("Bean������ : " + bean01.getClass()); //FactoryBean������O�I�sgetObject()�Ыت���
		System.out.println("Bean�O�_�P�@�� ? "+bean01.hashCode() +","+ bean02.hashCode());
		//�I�sImportFactoryBean �d��02
		Object bean03 = applicationContext.getBean("&importFactoryBean"); //�Y+"&"�e��űo��ImportFactoryBean�����O�A�ä��OgetObject�A�O�u�tBean
		System.out.println("Bean03������"+bean03.getClass());
		
		String[] applicationName = applicationContext.getBeanDefinitionNames();
		for (String name : applicationName) {
			System.out.println("�QSpring���ޤ��� : "+name);
		}
		
	}
	
}
