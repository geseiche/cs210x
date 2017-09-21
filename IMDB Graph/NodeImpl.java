import java.util.*;

public class NodeImpl implements Node{

	private String name;
	private LinkedList<NodeImpl> neighbors;
	
	public NodeImpl(String name){
		this.name = name;
		this.neighbors = new LinkedList<NodeImpl>();
	}
	
	public String getName() {
		return name;
	}

	public LinkedList<NodeImpl> getNeighbors() {
		return neighbors;
	}

	public void addNeighbor(NodeImpl n){
		neighbors.add(n);
	}
	
	public void removeNeighbor(NodeImpl n){
		neighbors.remove(n);
	}
	
	public void setNeighbors(LinkedList<NodeImpl> neighbors){
		this.neighbors = neighbors;
	}
	
	public void addNeighbors(LinkedList<NodeImpl> neighbors){
		this.neighbors.addAll(neighbors);
	}
}
