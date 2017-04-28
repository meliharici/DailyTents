

public class Tree extends BoardObject {
	private boolean isAttachtoTent;
	private int row;
	private int column;

	public Tree(int x, int y) {
		row = x;
		column = y;
	}

	public void attachToTent(Tent tent) {
		isAttachtoTent = true;
		tent.isAttachedtoTree = true;
	}

	public Tent attachedTent(int x, int y) {
		Tent tent = new Tent(x, y);
		return tent;
	}
}
