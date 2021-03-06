import java.util.ArrayList;
import java.util.List;


public class State {
	public State north;
	public State south;
	public State east;
	public State west;
	
	public int distance=Integer.MAX_VALUE;
	
	public String label;
	
	public State(String Label, State North, State South, State East, State West) {
		label = Label;
		north = North;
		south = South;
		east = East;
		west = West;
	}
	
	public List<State> connectedStates() {
		List<State> connectedStates = new ArrayList<State>();
		if(north != null)
			connectedStates.add(north);
		if(south != null)
			connectedStates.add(south);
		if(east != null)
			connectedStates.add(east);
		if(west != null)
			connectedStates.add(west);
		return connectedStates;
	}
	
	public String connectedTo(State other) {
		if(north == other) return "n";
		if(south == other) return "s";
		if(east == other) return "e";
		if(west == other) return "w";
		return null; 
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("State:\n\t");
		if(north != null) {
			sb.append(north.label);
		}
		else {
			sb.append("0");
		}
		sb.append("\n");
		if(west != null) {
			sb.append(west.label);
		}
		else {
			sb.append("0");
		}
		sb.append("\t");
		sb.append(this.label);
		sb.append("\t");
		if(east!=null) {
			sb.append(east.label);
		}
		else {
			sb.append("0");
		}
		sb.append("\n\t");
		if(south!=null) {
			sb.append(south.label);
		}
		else {
			sb.append("0");
		}
		return sb.toString();
	}
	
	public void connect(State other, String direction) throws Exception {
		direction = direction.toLowerCase();
		if(other==null) {
			connectToNull(direction);
			return;
		}
		System.out.println(direction);
		if(direction.equals("n")) {
				north = other;
				other.south = this;
		}
		else if(direction.equals("s")) {
				south = other;
				other.north = this;
		}
		else if(direction.equals("e")) {
			east = other;
			other.west = this;
		}
		else if(direction.equals("w")) {
				west = other;
				other.east = this;
		}
		else {
			throw new Exception("Invalid direction.");
		}
	}
	
	public void connectToNull(String direction) throws Exception {
		if(direction.equals("n"))
			north = null;
		else if(direction.equals("s"))
			south = null;
		else if(direction.equals("e"))
			east = null;
		else if(direction.equals("w"))
			west = null;
		else
			throw new Exception("Invalid direction.");
	}
	
	public String directionTo(String otherLabel) {
		if(north != null && north.label.equals(otherLabel)) {
			return "n";
		}
		if(south != null && south.label.equals(otherLabel)) {
			return "s";
		}
		if(east != null && east.label.equals(otherLabel)) {
			return "e";
		}
		if(west != null && west.label.equals(otherLabel)) {
			return "w";
		}
		return null;
	}
}
