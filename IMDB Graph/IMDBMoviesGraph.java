import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class IMDBMoviesGraph extends IMDBGraph {

	HashMap<String, LinkedList<NodeImpl>> actors;
	HashMap<String, NodeImpl> movies;
	
	public IMDBMoviesGraph (String actorsFilename, String actressesFilename) throws IOException {
		actors = new HashMap<String, LinkedList<NodeImpl>>();
		movies = new HashMap<String, NodeImpl>();
		
		loadDataFromFile(actorsFilename);
		loadDataFromFile(actressesFilename);
		
		nodes = new HashSet<NodeImpl>(movies.values());
		
	}
	private void parseMovie(String line, String currentActor){
		
		final int lastTab = line.lastIndexOf('\t');
		line = line.substring(lastTab + 1);
		if(line.charAt(0) == '"' || line.contains("(TV)")){
			// Is a TV show
			return;
		}
		final int closeParen = line.indexOf(')');
		final String movieName = line.substring(0, closeParen + 1);
		NodeImpl movie;
		if(!movies.containsKey(movieName)){
			movie = new NodeImpl(movieName);
			movies.put(movieName, movie);
		} else {
			movie = movies.get(movieName);
			movie.addNeighbors(actors.get(currentActor));
			for(NodeImpl neighbor: actors.get(currentActor)){
				neighbor.addNeighbor(movie);
			}
		}
		actors.get(currentActor).add(movie);
		
	}
	private void loadDataFromFile(String filename) throws IOException{
		
		Scanner scanner = new Scanner(new File(filename), CHARSET);
		boolean scanning = false;
		
		String currentActor = "";
		
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
					currentActor = line.substring(0, tabIndex);
					actors.put(currentActor, new LinkedList<NodeImpl>());
					parseMovie(line.substring(tabIndex), currentActor);
				}

			}
		}
		scanner.close();
	}
	
	
}
