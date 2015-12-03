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
		// C D
		
		try {
			ANode.connect(BNode, "e");
			BNode.connect(DNode, "S");
			CNode.connect(DNode, "e");
			ANode.connect(CNode, "s");
			DNode.connect(ENode, "e");
			ENode.connect(FNode, "s");
			FNode.connect(GNode, "s");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		nodes.add(ANode);
		nodes.add(BNode);
		nodes.add(CNode);
		nodes.add(DNode);
		nodes.add(ENode);
		nodes.add(FNode);
		nodes.add(GNode);
		
		for(State s : nodes) {
			System.out.println(s);
		}
	}
	
	private List<Path> pathQueue;
	public Path findPath(State startNode, State endNode) {
		pathQueue = new ArrayList<Path>();
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
			System.out.println(wPath);
			for(State s : lastState.connectedStates()) {
				Path pathToS = wPath.getCopy();
				pathToS.add(s);
				if(s == endNode) {
					if(bestPath == null || pathToS.length() < bestPath.length()){
						bestPath = pathToS.getCopy();
						System.out.println("Changed best path to \n" + bestPath);
					}
				}
				if(pathToS.length() < s.distance) {
					s.distance = pathToS.length();
					pathQueue.add(pathToS);
				}
			}
		}
		return bestPath;
	}
}