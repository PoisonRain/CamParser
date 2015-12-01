
public class Node {
	public Node north;
	public Node south;
	public Node east;
	public Node west;
	
	public String label;
	
	public Node(String Label, Node North, Node South, Node East, Node West) {
		label = Label;
		north = North;
		south = South;
		east = East;
		west = West;
	}
	
	public void connect(Node other, String direction) throws Exception {
		direction = direction.toLowerCase();
		if(other==null) {
			connectToNull(direction);
			return;
		}
		switch(direction) {
		case "n":
			north = other;
			other.south = this;
		case "s":
			south = other;
			other.north = this;
		case "e":
			east = other;
			other.west = this;
		case "w":
			west = other;
			other.east = this;
		default:
			throw new Exception("Invalid direction.");
		}
	}
	
	public void connectToNull(String direction) throws Exception {
		switch(direction) {
		case "n":
			north = null;
		case "s":
			south = null;
		case "e":
			east = null;
		case "w":
			west = null;
		default:
			throw new Exception("Invalid direction.");
		}
	}
}