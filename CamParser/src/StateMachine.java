import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class StateMachine {
	public List<State> nodes;
	
	public StateMachine() {
		nodes = new ArrayList<State>();
		State ANode = new State("A", null, null, null, null);
		State BNode = new State("B", null, null, null, null);
		State CNode = new State("C", null, null, null, null);
		State DNode = new State("D", null, null, null, null);
		State ENode = new State("E", null, null, null, null);
		State FNode = new State("F", null, null, null, null);
		State GNode = new State("G", null, null, null, null);
		State HNode = new State("H", null, null, null, null);
		// A B
		// C D E
		//     F
		//     G
		
		try {
			ANode.connect(BNode, "e");
			BNode.connect(DNode, "s");
			CNode.connect(DNode, "e");
			ANode.connect(CNode, "s");
			DNode.connect(ENode, "e");
			ENode.connect(FNode, "s");
			FNode.connect(GNode, "s");
			HNode.connect(FNode, "e");
			HNode.connect(DNode, "n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		nodes.add(ANode);
		nodes.add(BNode);
		nodes.add(CNode);
		nodes.add(DNode);
		nodes.add(ENode);
		nodes.add(FNode);
		nodes.add(HNode);
		nodes.add(GNode);
		
		for(State s : nodes) {
			//System.out.println(s);
		}
	}
	
	public boolean containsLabel(String label) {
		for(State s : nodes) {
			if(s.label.equals(label)) return true;
		}
		return false;
	}
	
	public State nodeWithLabel(String label) {
		for(State s : nodes) {
			if(s.label.equals(label)) return s;
		}
		return null;
	}
	
	public String getDirection(String label) {
		if(currentPath == null || !currentPath.containsLabel(label)) {
			if(this.containsLabel(label)) {
				currentPath = findPath(nodeWithLabel(label), nodes.get(nodes.size()-1));
			}
			else {
				return null;
			}
		}
		System.out.println(currentPath);
		return currentPath.getDirection(label);
	}
	
	public Path currentPath = null;
	private List<Path> pathQueue;
	public Path findPath(State startNode, State endNode) {
		pathQueue = new ArrayList<Path>();
		for(State s : nodes) s.distance = Integer.MAX_VALUE;
		startNode.distance = 0;
		Path p = new Path(startNode);
		if(startNode.connectedTo(endNode) != null) {
			p.states.add(endNode);
			return p;
		}
		pathQueue.add(p);
		Path bestPath = null;
		while(pathQueue.size() > 0) {
			Path wPath = pathQueue.remove(0);
			State lastState = wPath.states.get(wPath.states.size()-1);
			//System.out.println(wPath);
			for(State s : lastState.connectedStates()) {
				Path pathToS = wPath.getCopy();
				pathToS.add(s);
				if(s == endNode) {
					if(bestPath == null || pathToS.length() < bestPath.length()){
						bestPath = pathToS.getCopy();
						//System.out.println("Changed best path to \n" + bestPath);
					}
				}
				if(pathToS.length() < s.distance) {
					s.distance = pathToS.length();
					pathQueue.add(pathToS);
				}
			}
		}
		currentPath = bestPath;
		return bestPath;
	}
}
