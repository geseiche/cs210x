
import java.util.HashSet;

public abstract class IMDBGraph implements Graph{
	
	protected static final String CHARSET = "ISO-8859-1";
	protected HashSet<NodeImpl> nodes;
	
	public HashSet<NodeImpl> getNodes() {
		return nodes;
	}

	public Node getNodeByName(String name) {
		for(NodeImpl node: nodes){
			if(node.getName().equals(name)){
				return node;
			}
		}
		return null;
	}
}
