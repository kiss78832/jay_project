package com.jay.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


/*
 *	�ĤG��Import�覡 : �۩w�q�޿��^�ݭn�ɤJ������ 
 */
public class MyImportSelector<K> implements ImportSelector {

	//(1).��^�ȡA�N�O�ɤJ��e���������󧹾����O�W��
	//(2).AnnotationMetadata : ��e�е�@Import�`�Ѫ����O�Ҧ��`���T��
	//(3).return ���n�Onull�A�|�X���`(NullPointException)
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		
//		MultiValueMap<String, Object> map = importingClassMetadata.getAllAnnotationAttributes("mainConfig2",true);
//		System.out.println("���� : "+map);
		
		//��^���W�ٴN�O�nimport���W�١A�����e�O�A�������b "MainConfig2.class" �`�� "MyImortSelector" 
		return new String[] {"com.jay.bean.Import01","com.jay.bean.Import02"};
	}

		
}
