package Annotation_Example;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/*
 *	重複註解範例 
 */
public class TestAnnotation {
	
	@Test
	public void test01() throws Exception {
		
		//TestAnnotation底下的Method show() 
		Class<TestAnnotation> classType =TestAnnotation.class; 
		Method m1 = classType.getMethod("show");
		
		//	取得Annotation類型@MyAnnotation
		MyAnnotation[] myA01 = m1.getAnnotationsByType(MyAnnotation.class);
		
		//	針對註解@MyAnnotation 的 show()Method 取得他註解的內容
		for(MyAnnotation myAnnotation : myA01) {
			System.out.println(myAnnotation.value());
		}
		
	}
	
	@MyAnnotation("Hello")
	@MyAnnotation("World")
	public void show(@MyAnnotation("abc") String str) {
		
	}
	
	@Test
	public void test02() {
		List features = Arrays.asList("Lambdas", "Default Method", "Stream API",
				 "Date and Time API");
				features.forEach(System.out::println);
	}
}
