package Default_Example;

public class MyClass /*extends MyExtendsClass*/ implements MyFun_interface,MyFun_interface2{

	
	/*
	 *	(1)	�Ĭ�d��  MyClass extends �uMyExtendsClass�v    implements �uMyFun_interface�v �A��̳���getName()�C
	 *		���D : �аݷ|�����~�Ӫ�getName()�A�٬O������getName()? 
	 *				Ans:�Y�����O���Ѩ����{��k�A�o�˷|�H�����O���u���A������getName()�|�����C
	 *
	 *	(2)	�Ĭ�d��  MyClass implements �uMyFun_interface�v , �uMyFun_interface2�v �A��̳���getName()�C
	 *		���D : �аݷ|����MyFun_interface��getName()�A�٬OMyFun_interface2��getName()? 
	 *				Ans:���״N�O��MyClass�h��@���Ӥ�k�A�p�U��
	 */
	
	@Override
	public String getName() {
		return MyFun_interface.super.getName();
	}
	
}
