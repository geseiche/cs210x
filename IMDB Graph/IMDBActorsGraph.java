import java.io.File;
import java.io.IOException;
import java.util.*;

public class IMDBActorsGraph extends IMDBGraph {
	
	static final String CHARSET = "ISO-8859-1";
	HashMap<String, LinkedList<NodeImpl>> movies;
	
	
	public IMDBActorsGraph (String actorsFilename, String actressesFilename) throws IOException {
		movies = new HashMap<String, LinkedList<NodeImpl>>();
		
		nodes= new HashSet<NodeImpl>();
		
		loadDataFromFile(actorsFilename);
		loadDataFromFile(actressesFilename);
	}
	
	
	
	private void parseMovie(String line, NodeImpl currentActor){
		
		final int lastTab = line.lastIndexOf('\t');
		line = line.substring(lastTab + 1);
		if(line.charAt(0) == '"' || line.contains("(TV)")){
			// Is a TV show
			return;
		}
		final int closeParen = line.indexOf(')');
		final String movie = line.substring(0, closeParen + 1);
		if(!movies.containsKey(movie)){
			movies.put(movie, new LinkedList<NodeImpl>());
		} else {
			currentActor.addNeighbors(movies.get(movie));
			for(NodeImpl neighbor: movies.get(movie)){
				neighbor.addNeighbor(currentActor);
			}
		}
		movies.get(movie).add(currentActor);
		
	}
	
	private void loadDataFromFile(String filename) throws IOException{
		
		Scanner scanner = new Scanner(new File(filename), CHARSET);
		boolean scanning = false;
		
		NodeImpl currentActor = new NodeImpl("");
		
		while (scanner.hasNextLine()){
			final String line = scanner.nextLine();
			if (line.equals("THE ACTORS LIST") || line.equals("THE ACTRESSES LIST")){
				for(int i = 0; i < 4; i++){
					scanner.nextLine();
				}
				scanning = true;
			} else if(scanning){
				if(line.startsWith("\t")){
					parseMovie(line, currentActor);
				} else if (line.isEmpty()){
					// Empty line
				} else {
					final int tabIndex = line.indexOf('\t');
					if(tabIndex == -1){
						// File is messed up, skip actor
						continue;
					}
					currentActor = new NodeImpl(line.substring(0, tabIndex));
					nodes.add(currentActor);
					parseMovie(line.substring(tabIndex), currentActor);
				}

			}
		}
		scanner.close();
	}
}
