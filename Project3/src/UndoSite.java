import java.util.ArrayList;

/**
 * 
 */

/**
 * @author zach7
 *
 */
public class UndoSite {

	private int add = 1;
	private int delete = -1;
	private int num;
	private Site site;
	private int index;
	private ArrayList undoList;

	public UndoSite(Site site, int index, int num) {

		this.site = site;
		this.index = index;
		this.num = num;

	}

	public int getAddOrDelete() {
		if (num == add) {
			return 1;
		} else if (num == delete) {
			return -1;
		}
		return 0;
	}

	public int getIndex() {
		return index;
	}
	
	public Site getSite() {
		return site;
	}

}
