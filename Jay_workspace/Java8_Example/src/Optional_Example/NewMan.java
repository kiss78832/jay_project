package Optional_Example;

import java.util.Optional;

/*
 *	�s�g�k�d�� 
 */
public class NewMan {
	/*
	 * 	�쥻�ϥ� private Godness godness; ���  Optional<Godness> godness = Optional.empty();
	 * 		(a).�ϥ�Optional�ʸ����O�A���NullPointerException�C
	 * 		(b).�w�]�Ȥ@�w�n�]�A���M�|��^null�A�w�]�Ȥ]����]null�A�o�˽ե�NewMan���O�U����k�̼˷|�����A�ҥH�n�Τ@�ӪŪ�Optional��@�^�ǭȡC
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
