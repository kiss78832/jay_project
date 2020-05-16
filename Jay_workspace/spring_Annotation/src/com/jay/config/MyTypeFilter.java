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
	 * �^�Ǥ@��boolean�A�^��true�N��t�令�\�Afalse�t�異��
	 * 		(1).metadataReader:
	 * 		(2).metadataReaderFactory:
	 */
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		
		//�����e���O���ѰT��
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		
		//�Υh��e���b���y�����O�T��
		ClassMetadata classMetadata  = metadataReader.getClassMetadata();
		
		//�����e���O�귽(���O�����|)
		Resource resource= metadataReader.getResource();
		
		//����Q�L�o�����O�W��
		String className = classMetadata.getClassName();
		System.out.println("�Q�L�o���O�W�� : "+className);
		
		//�W�٤��u���n"er"�N�|��^true�A�Q�]�t�b�e����
		if(className.contains("er")) {
			return true;
		}
		return false;
	}

}
