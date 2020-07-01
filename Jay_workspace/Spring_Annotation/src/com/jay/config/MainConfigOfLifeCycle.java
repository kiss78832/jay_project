package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jay.bean.Car;

/*
 *	Bean���ͩR�g�� : ( bean�Ы� ------> ��l�� ------> �P���L�{ )
 *		
 *	�e���޲zBean���ͩR�g��  :  (�ͩR�g���X�G���i�H����)
 *		(1).�ڭ̥i�H�۩w�q��l�ƩM�P����k�A�e���bBean�i����e�ͩR�g�����ɭԽեΧڭ̦۩w�q����l�ƩM�P��
 *		(2).�غc�l�Ы�:
 *				singleton:�b�e���Ұʪ��ɭԳЫءC
 *				prototype:�b�I�s��Bean�ɳЫءC
 *
 *		��BeanPostProcessor.PostProcessBeforeInitialization() [��l�Ƥ��eĲ��]
 *
 *		(3).��l��:(A~C�T�ؤ覡)
 *				�غc�l�Ыا����A�ê��Ȧn  ------> �I�s��l�Ƥ�k ...
 *
 *		��BeanPostProcessor.postProcessAfterInitialization() [�P�����eĲ��]
 *
 *		(4).�P��:
 *				singleton:�e���������ɭ�
 *				prototype:�e�����|�޲z�o��Bean�A�e�����|�ե� destory() method
 *		
 *
 *	�w�q��l�ƻP�P���覡:
 * 		(A).��k�@ :���w��l�ƩM�P����k
 * 			  (a).<bean id="person" class="com.jay.bean.Person" init-method="" destroy-method=""> XML�g�k
 * 			  (b).@Bean(initMethod = "init" , destroyMethod = "destory")  annotation�g�k
 * 		(B).��k�G :�q�LBean��@  
 * 				initializingBean (�w�q��l���޿�)
 * 				DisposableBean (�w�q�P���޿�)
 * 		(C).��k�T :�i�H�ϥ�JSR250	[������˪��覡�A²��M��]
 * 				@PostConstruct:�bBean����k�`��@PostConstruct�A���Pinit-method�A�����l�Ʈɷ|�I�s�Ӥ�k�C
 * 				@PreDestroy:�bBean����k�`��@PreDestroy�A���Pdestroy-method�A����P���ɷ|�I�s�Ӥ�k�C 
 * 				�`�N : �O�o����Bean�浹Spring�޲z�A���U@Component
 *  	(D).BeanPostProcessor[interface],bean����m�B�z��     
 * 				     	�bbean��l�ƫe��i��@�ǳB�z�u�@ :	
 *						PostProcessBeforeInitialization :�b��l�Ƥ��e���u�@�C 
 *						postProcessAfterInitialization :�b��l�Ƥ��᪺�u�@�C
 *		
 *		(E).BeanPosstProcessor��z(SouceCode):
 *			populateBean(beanName, mbd, instanceWrapper); //��Bean�i����
 *
 *			//�i���l�ơA�T�B�J
 *			exposedObject = initializeBean(beanName, exposedObject, mbd){
 *				
 *				//initializeBean����
 *				wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *				invokeInitMethods(beanName, wrappedBean, mbd); //�۩w�q��l�Ƥ�k
 *				wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *			}
 *			
 *		(F).Spring���h��BeanPostProcessor���ϥ�(�j�q�ϥ�) : 
 *				(1).Bean�����
 *				(2).�`�J��L����(@Component)
 *				(3).@AutoWired
 *				(4).�ͩR�g���`�ѥ\��
 *				(5).@Async(���P�B�B�z)
 *						......����
 */

//�ܽd���P�覡�޶iBean�A����@Bean
@Configuration
@ComponentScan("com.jay.bean")
public class MainConfigOfLifeCycle {
	
	@Bean(initMethod = "init" , destroyMethod = "destory") //���w ��l�Ƥ�kID=init, �P����kID=destory
	public Car car() {
		return new Car();
	}
	
}
