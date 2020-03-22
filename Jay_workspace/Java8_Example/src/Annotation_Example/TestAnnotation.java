package Annotation_Example;

import java.lang.reflect.Method;

import org.junit.Test;

/*
 *	���Ƶ��ѽd�� 
 */
public class TestAnnotation {
	
	@Test
	public void test01() throws Exception {
		
		//TestAnnotation���U��Method show() 
		Class<TestAnnotation> classType =TestAnnotation.class; 
		Method m1 = classType.getMethod("show");
		
		//	���oAnnotation����@MyAnnotation
		MyAnnotation[] myA01 = m1.getAnnotationsByType(MyAnnotation.class);
		
		//	�w�����@MyAnnotation �� show()Method ���o�L���Ѫ����e
		for(MyAnnotation myAnnotation : myA01) {
			System.out.println(myAnnotation.value());
		}
		
	}
	
	@MyAnnotation("Hello")
	@MyAnnotation("World")
	public void show(@MyAnnotation("abc") String str) {
		
	}
}