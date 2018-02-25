/**
 * 
 */

/**
 * @author zach7
 *
 */
public class siteDate {

	private boolean[] sites;

	public siteDate() {

		sites = new boolean[5];
		for (int i = 0; i < 5; i++) {
			sites[i] = true;
		}

	}

	public boolean getIndex(int i){
		return sites[i - 1];
	}

	public void setIndex(int i){
		sites[i - 1] = false;
	}
	
	public void removeIndex(int i) {
		sites[i - 1] = true;
	}
	
}
