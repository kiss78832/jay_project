package Optional_Example;

import java.util.Optional;

import org.junit.Test;

/*
 *	Optional �d���D�� 
 */
public class Optional02 {

	@Test
	public void Test01() {
		//	�¼g�k�d��
		Man man = new Man();
		String getGodessName = getGodnessName(man);
		System.out.println(getGodessName);
		
		//	�s�g�k�d��
		Optional<Godness> gn = Optional.ofNullable(new Godness("�ڦ��ȽЩI�s��"));
		Optional<NewMan> op = Optional.ofNullable(new NewMan(gn));
		String getGodessName2 = getGodnessName2(op);
		System.out.println(getGodessName2);
	}
	
	
	
	//	�ݨD: ����k�H���ߤ����k��
   /*	�s�g�k
	*	�y�{ : �p�GgetGodnessName()	->	�O�ŭȴNorElse(new NewMan()) -> ���O�ŭȴN��getGodnessName()����man
	*	-> ��NewMan���ogetGodness() -> �@�˧P�_�O�_�ŭȦb���w�]��	-> .getName() �C
	*/		
	public String getGodnessName2(Optional<NewMan> man) {
		return man.orElse(new NewMan()) //	�w�]��
				  .getGodness()
				  .orElse(new Godness("�s�g�k�w�]�Ȥk��"))
				  .getName();
	}
	
	//	�¼g�k�A�B�z�ŭȤ�k
	public String getGodnessName(Man man) {

		if(man != null) {
			Godness gn = man.getGodness();
			if(gn != null) {
				return gn.getName();
			}
		}
		return "�¼g�k�w�]�Ȥk��";
	}
}
