package Default_Example;

public interface MyFun_interface2 {
	
	default String getName() {
		return "MyFun_interface2��getName()";
	}
	
	public static void show() {
		System.out.println("MyFun_interface2 ���R�A��k");
	}
}
