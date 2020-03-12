package Optional_Example;

/*
 *	ÂÂ¼gªk½d¨Ò 
 */
public class Man {

	private Godness godness;

	public Godness getGodness() {
		return godness;
	}

	public void setGodness(Godness godness) {
		this.godness = godness;
	}

	public Man() {
	}

	public Man(Godness godness) {
		this.godness = godness;
	}

	@Override
	public String toString() {
		return "Man [godness=" + godness + "]";
	}
	
	
}
