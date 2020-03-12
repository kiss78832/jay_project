package Optional_Example;

import java.util.Optional;

import org.junit.Test;

/*
 *	Optional 範例題目 
 */
public class Optional02 {

	@Test
	public void Test01() {
		//	舊寫法範例
		Man man = new Man();
		String getGodessName = getGodnessName(man);
		System.out.println(getGodessName);
		
		//	新寫法範例
		Optional<Godness> gn = Optional.ofNullable(new Godness("我有值請呼叫我"));
		Optional<NewMan> op = Optional.ofNullable(new NewMan(gn));
		String getGodessName2 = getGodnessName2(op);
		System.out.println(getGodessName2);
	}
	
	
	
	//	需求: 獲取男人中心中的女神
   /*	新寫法
	*	流程 : 如果getGodnessName()	->	是空值就orElse(new NewMan()) -> 不是空值就拿getGodnessName()內的man
	*	-> 到NewMan取得getGodness() -> 一樣判斷是否空值在取預設值	-> .getName() 。
	*/		
	public String getGodnessName2(Optional<NewMan> man) {
		return man.orElse(new NewMan()) //	預設值
				  .getGodness()
				  .orElse(new Godness("新寫法預設值女神"))
				  .getName();
	}
	
	//	舊寫法，處理空值方法
	public String getGodnessName(Man man) {

		if(man != null) {
			Godness gn = man.getGodness();
			if(gn != null) {
				return gn.getName();
			}
		}
		return "舊寫法預設值女神";
	}
}
