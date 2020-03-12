package Default_Example;

public class MyClass /*extends MyExtendsClass*/ implements MyFun_interface,MyFun_interface2{

	
	/*
	 *	(1)	衝突範例  MyClass extends 「MyExtendsClass」    implements 「MyFun_interface」 ，兩者都有getName()。
	 *		問題 : 請問會先取繼承的getName()，還是介面的getName()? 
	 *				Ans:若父類別提供具體實現方法，這樣會以父類別為優先，介面的getName()會忽略。
	 *
	 *	(2)	衝突範例  MyClass implements 「MyFun_interface」 , 「MyFun_interface2」 ，兩者都有getName()。
	 *		問題 : 請問會先取MyFun_interface的getName()，還是MyFun_interface2的getName()? 
	 *				Ans:答案就是看MyClass去實作哪個方法，如下↓
	 */
	
	@Override
	public String getName() {
		return MyFun_interface.super.getName();
	}
	
}
