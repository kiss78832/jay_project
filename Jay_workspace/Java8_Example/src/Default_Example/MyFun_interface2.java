package Default_Example;

public interface MyFun_interface2 {
	
	default String getName() {
		return "MyFun_interface2的getName()";
	}
	
	public static void show() {
		System.out.println("MyFun_interface2 的靜態方法");
	}
}
