import java.util.ArrayList;
import java.util.List;


public class Graph {
	public List<Node> nodes;
	
	public Graph() {
		nodes = new ArrayList<Node>();
		Node ANode = new Node("A", null, null, null, null);
		Node BNode = new Node("B", null, null, null, null);
		Node CNode = new Node("C", null, null, null, null);
		Node DNode = new Node("D", null, null, null, null);
		
		try {
			ANode.connect(BNode, "e");
			BNode.connect(DNode, "S");
			CNode.connect(DNode, "e");
			ANode.connect(CNode, "s");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		nodes.add(ANode);
		nodes.add(BNode);
		nodes.add(CNode);
		nodes.add(DNode);
	}
	
	public String findPath(Node startNode, String omitDirection) {
		
		return "";
	}
}