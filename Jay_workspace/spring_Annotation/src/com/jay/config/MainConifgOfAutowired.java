package com.jay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.jay.bean.Car;
import com.jay.bean.Import01;
import com.jay.dao.BookDao;

/*
 *	�۰ʸ˰t(Autowired) -> Spring�Q�Ψ̿�`�J(DI)�A������IOC�e�����U�Ӥ��󪺨̿����Y��ȡC
 *		�@�B@Autowired �۰ʪ`�J :
 *				a).�q�{�u�����������h�e��������������� : applicationContext.getBean(BookDao.class)
 *	
 *				b).�p�G�Ө�h�ӬۦP��������A�|�̷��ݩʦW�٧@������ID�h�e�����d��C
 *					EX:Serivce.@Autowired.private BookDao "bookDao" <- [bookDao�N�O�ݩʦW��ID];
 *	
 *				c).@Qualifier("���wBean�W")�A�ϥ�@Qualifier���w�ݭn�˰t����ID�A�Ӥ��O�q�L�ݩʦW�١C
 *	
 *				d).Bean���y�{ :
 *						����Class(�p�G�o�{���O�ߤ@)	 -> �ݩm�W��(�����Ƥ��󪺸��u���̷�"�ݩʦW��ID")
 *						->�Y��@Qualifier�N�ҥ~�A�̷�@Qualifier���w���W��
 * 	
 * 				e).�q�{�@�w�n�N�ݩʵ���Ȧn�A�]�N�O@Autowired��Bean�̩w���n������ȡA�Y�S���|�����C
 * 						���D�ABean�W���s�W @Autowired(required = false) -> ������ȷ|��null�A���|�����AEX:bookService���� :BookService [bookDao=null]
 * 
 * 				f).@Primary�A��Spring�i��۰ʸ˰t���ɭԡA�q�{�ϥέ��諸bean�]�i�H�~��ϥ�@Qualifier���w�ݭn�˰tBean���W�l�C
 * 						@Qualifier > @Primary > �q�{�ݩʦW��ID�C
 * 
 * 		�G�BSpring�٤���ϥ�@Resource(JSR250)�M@Inject(JSR330) [Java�W�d�A�Ӥ��OSpring]
 * 				a).@Resource: 
 * 						�i�H�M@Autowired�@�˹�{�۰ʸ˰t�A�q�{�O���Ӥ����ݩʦW�ٶi��˰t�C
 * 							���I 	: ������@Primary�\��S����� @Autowired(required = false) 
 * 							�u�I	: �SSpring�ج[�̵M�i�H���A�]���OJava�W�d
 * 				
 * 				b).@Inject
 * 						�i�H�M@Autowired�@�˹�{�۰ʸ˰t�C�������@Primary�\��
 * 							���I : �S�����@Autowired(required = false)�B�ݭn�ɤJjavax.inject��Jar�]
 * 							�u�I : �SSpring�ج[�̵M�i�H���A�]���OJava�W�d
 * 
 * 				c).AutowiredAnnotationBeanPostProcessor : �ѪR�����۰ʸ˰t�\��C(@Resource�B@Inject�B@Autowired)
 * 
 * 		�T�B@Autowired:�غc�l�B�ѼơB��k�B�ݩʡA���O�q�e��������ѼƤ��󪺭�
 * 				a).�е��b��k�W (MainConfigOfAutowired.java)
 * 					�`�N:																				@Bean
 * 						1.@Bean + ��k�Ѽ�(Car car) �A�ѼƱq�e��������A�q�{���g@Autowired+@Component�ĪG�]�O�@�˪��A����۰ʸ˰t�CEX: public Import01 import01(Car car)
 * 				b).�е��b�غc�l (Boss.java)
 * 					�`�N:
 * 						1.@Autowired �q�{�u�����o�L�ѫغc�l(���Υ[@Autowired�A�|�۰��q�{)�A�Y�L�Ѹ򦳰ѳ��s�b�A��@Autowired��b���Ѽƫغc�l�A�|�u�����o���Ѽƫغc�l�C
 * 						2.@Autowired �Y�u���@�Ӧ��Ѽƫغc�l�A�N�|�u�����o�ӫغc�l�C(���Υ[@Autowired�A�|�۰��q�{)
 * 				c).��b�ѼƦ�m�C
 * 
 * 		�|�B�۩w�q����Q�n�ϥ�Spring�e�����h���@�Ǥ���(ApplicationContext,BeanFactory,XXX)�A
 * 					�۩w�q�����@(implements)  XXXAware�A�b�Ыت��󪺮ɭԡA�|�եΤ����W�w����k�`�J��������Aware�A
 * 						��Spring���h�@�Ǥ���`�J��ۤvBean���A�Q��XXXAware,�\��ϥ�XXXProcessor:
 * 									EX:ApplicationContextAware ==> ApplicationContextAwareProcessor
 * 
 * 			��@Aware���� :
 *					BeanNameAware �G�i�H����e����bean���W��
 *					BeanFactoryAware:�����ebean factory�o�]�i�H�եήe�����A��
 *					ApplicationContextAware�G ��e��applicationContext�A �o�]�i�H�եήe�����A��
 *					MessageSourceAware�G��omessage source�A�o�]�i�H��o�奻�H��
 *					applicationEventPulisherAware�G���Ψƥ�o�G���A�i�H�o���ƥ�A
 *					ResourceLoaderAware�G ��o�귽�[�����A�i�H��o�~���귽��󪺤��e
 *					...����
 *			
 *			Bean�ͩR�g��:
 * 			������  ��Ҥ�(new Object) -> ��R�ݩ�(�`�Jbean���̿�Ϊ̵��ݩʽ��[setXXX]) -> ����Aware���f(����Ҧ���@Aware���������O)
 * 						 -> ��l��(��l�ƪ��覡���T��) -> �i�Ϊ��A(bean�i�H�Q���ε{���ϥ�) -> �P��(�P�����覡���T��)
 * 
 * 			��l�ƪ��覡���T��:(1).��{InitializingBean����  (2).@PostConstruct����  (3).xml���q�Linit-method�ݩʫ��w��l�Ƥ�k
 * 			�P�����覡���T��:(1).��{DisposableBean����  (2).@PreDestroy����  (3).xml���q�Ldestroy-method�ݩʫ��w�P����k
 * 
 * 
 * 
 * 
 * 			������ ��@Configuration��@ComponentScan({})���������package�A����b�[��Spring�t�m���ɡA�|�۰ʽեι�@Aware���f�����C
 * 			
 */

@Configuration
@ComponentScan({"com.jay.service","com.jay.dao","com.jay.controller","com.jay.bean"})
public class MainConifgOfAutowired {

	@Primary
	@Bean(name = "bookDao2")
	public BookDao bookDao() {
		BookDao bookDao = new BookDao();
		bookDao.setLable("2");
		return bookDao;
	}
	
	//@Bean �е�����k�Ыت��󪺮ɭԡA��k�Ѽƪ��ȱq�e�������
	@Bean
	public Import01 import01(Car car) {
		Import01 import01 = new Import01();
		import01.setCar(car);
		return import01;
	}
	
}
