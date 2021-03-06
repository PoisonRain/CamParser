import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Path {
	public List<State> states;
	
	public Path() {
		states = new ArrayList<State>();
	}
	
	public Path(State startState) {
		states = new ArrayList<State>();
		states.add(startState);
	}
	
	public Path(List<State> currentPath, State nextState) {
		states = currentPath;
		states.add(nextState);
	}
	
	public Path(List<State> currentPath) {
		states = currentPath;
	}
	
	public boolean containsState(State s) {
		return states.contains(s);
	}
	
	public void add(State s) {
		states.add(s);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Path:");
		for(State s : states) {
			sb.append("\t");
			sb.append(s.label);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public State last() {
		return states.get(states.size() - 1);
	}
	
	public int length() {
		return states.size();
	}
	
	public Path getCopy() {
		List<State> newStates = new ArrayList<State>();
		for(State s : states) {
			newStates.add(s);
		}
		return new Path(newStates);
	}
	
	public boolean containsLabel(String label) {
		for(State s : states) {
			if(s.label.equals(label)) return true;
		}
		return false;
	}
	
	public String getDirection(String label) {
		State currentState = null;
		int Index = -1;
		for(int i=0; i<states.size(); i++) {
			if(states.get(i).label.equals(label)) {
				currentState = states.get(i);
				Index = i;
				break;
			}
		}
		if(currentState == null) return null;
		if(Index == states.size()-1) return "f";
		State nextState = states.get(Index+1);
		return currentState.directionTo(nextState.label);
	}
}
