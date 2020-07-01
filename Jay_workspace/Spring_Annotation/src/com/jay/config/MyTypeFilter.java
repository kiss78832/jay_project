package com.jay.config;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class MyTypeFilter implements TypeFilter {

	/*
	 * 回傳一個boolean，回傳true代表配對成功，false配對失敗
	 * 		(1).metadataReader:
	 * 		(2).metadataReaderFactory:
	 */
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		
		//獲取當前類別註解訊息
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		
		//或去當前正在掃描的類別訊息
		ClassMetadata classMetadata  = metadataReader.getClassMetadata();
		
		//獲取當前類別資源(類別的路徑)
		Resource resource= metadataReader.getResource();
		
		//獲取被過濾的類別名稱
		String className = classMetadata.getClassName();
		System.out.println("被過濾類別名稱 : "+className);
		
		//名稱中只有要"er"就會返回true，被包含在容器當中
		if(className.contains("er")) {
			return true;
		}
		return false;
	}

}
