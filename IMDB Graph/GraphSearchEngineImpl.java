import java.util.*;

public class GraphSearchEngineImpl implements GraphSearchEngine {

	
	public List<Node> findShortestPath(Node s, Node t) {
		final Map<Node, Integer> distancesFromS = findDistances(s, t);
		
		if(distancesFromS.get(t) == null){
			return null;
		}
		return findShortestPathRec(s, t, distancesFromS);
	}
	
	
	private List<Node> findShortestPathRec(Node s, Node t, Map<Node, Integer> distancesFromS){
		int distance = distancesFromS.get(t);
		List<Node> path;
		
		for(Node neighbor: t.getNeighbors()){
			if(distancesFromS.get(neighbor) == distance -1){
				path = findShortestPathRec(s, neighbor, distancesFromS);
				path.add(t);
				return path;
			}
		}
		path = new LinkedList<Node>();
		path.add(s);
		return path;
		
	}
	
	
	private Map<Node, Integer> findDistances(Node s, Node t){
		List<Node> visitedNodes = new ArrayList<Node>();
		Queue<Node> nodesToVisit = new LinkedList<Node>();
		Map<Node, Integer> distances = new HashMap<Node,Integer>();
		
		nodesToVisit.add(s);
		distances.put(s, 0);
		
		while(nodesToVisit.size() > 0) {
			Node n = nodesToVisit.remove();
			visitedNodes.add(n);
			if(n.getName().equals(t.getName())){
				return distances;
			}
			
			for(Node neighbor: n.getNeighbors()){
				if(!nodesToVisit.contains(neighbor) && !visitedNodes.contains(neighbor)){
					distances.put(neighbor, distances.get(n) + 1);
					nodesToVisit.add(neighbor);
				}
			}
		}
		return distances;
		
	}

}
