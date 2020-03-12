package Optional_Example;

import java.util.Optional;

/*
 *	新寫法範例 
 */
public class NewMan {
	/*
	 * 	原本使用 private Godness godness; 更改  Optional<Godness> godness = Optional.empty();
	 * 		(a).使用Optional封裝類別，減少NullPointerException。
	 * 		(b).預設值一定要設，不然會返回null，預設值也不能設null，這樣調用NewMan類別下的方法依樣會報錯，所以要用一個空的Optional當作回傳值。
	 */
	private Optional<Godness> godness = Optional.empty();

	public Optional<Godness> getGodness() {
		return godness;
	}

	public void setGodness(Optional<Godness> godness) {
		this.godness = godness;
	}

	public NewMan(Optional<Godness> godness) {
		this.godness = godness;
	}

	public NewMan() {
		super();
	}

	@Override
	public String toString() {
		return "NewMan [godness=" + godness + "]";
	}
	
	
}
