package com.jay.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


/*
 *	第二種Import方式 : 自定義邏輯返回需要導入的元件 
 */
public class MyImportSelector<K> implements ImportSelector {

	//(1).返回值，就是導入到容器中的元件完整類別名稱
	//(2).AnnotationMetadata : 當前標註@Import注解的類別所有注釋訊息
	//(3).return 不要是null，會出異常(NullPointException)
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		
//		MultiValueMap<String, Object> map = importingClassMetadata.getAllAnnotationAttributes("mainConfig2",true);
//		System.out.println("測試 : "+map);
		
		//返回的名稱就是要import的名稱，但提前是你必須先在 "MainConfig2.class" 注解 "MyImortSelector" 
		return new String[] {"com.jay.bean.Import01","com.jay.bean.Import02"};
	}

		
}
