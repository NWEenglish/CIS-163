package CampingReg;

public class Node {
	private Site data;
	private Node next;

	public Node(Site data, Node next) {
		this.data = data;
		this.next = next;
	}
	public Node() {
	}

	public Site getData() {
		return data;
	}
	
	public void setData(Site data) {
		this.data = data;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Node getNext() {
		return next;
	}

}
