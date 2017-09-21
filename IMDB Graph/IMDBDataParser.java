
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IMDBDataParser {

	private static final String CHARSET = "ISO-8859-1";
	private String actorFile;
	private String actressFile;
	private HashMap<String, HashSet<String>> actors;
	private HashMap<String, HashSet<String>> movies;

	 
	
	public IMDBDataParser(String actorFile, String actressFile){
		this.actorFile = actorFile;
		this.actressFile = actressFile;
		actors = new HashMap<String, HashSet<String>>();
		movies = new HashMap<String, HashSet<String>>();
	}
	
	private String parseMovie(String line){
		
		final int lastTab = line.lastIndexOf('\t');
		line = line.substring(lastTab + 1);
		if(line.charAt(0) == '"' || line.contains("(TV)")){
			return "";
		}
		final int closeParen = line.indexOf(')');
		return line.substring(0, closeParen + 1);
		
	}
	private void loadDataFromFile(String filename) throws IOException{
		
		final Scanner scanner = new Scanner(new File(filename), CHARSET);
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
					final String movie = parseMovie(line);
					if(movie != ""){
						actors.get(currentActor).add(movie);
						if(!movies.containsKey(movie)){
							movies.put(movie, new HashSet<String>());
						}
						movies.get(movie).add(currentActor);
					}
				} else if (line.isEmpty()){
					// Empty line
				} else {
					if(actors.get(currentActor) == null || actors.get(currentActor).isEmpty() ){
						//remove previous actor if they do not have any movies;
						actors.remove(currentActor);
					}
					final int tabIndex = line.indexOf('\t');
					if(tabIndex == -1){
						// File is messed up, skip actor
						continue;
					}
					currentActor = line.substring(0, tabIndex);
					actors.put(currentActor, new HashSet<String>());
					final String movie = parseMovie(line.substring(tabIndex));
					if(movie != ""){
						actors.get(currentActor).add(movie);
						if(!movies.containsKey(movie)){
							movies.put(movie, new HashSet<String>());
						}
						movies.get(movie).add(currentActor);
					}
				}

			}
		}
		scanner.close();
	}
	
	public void loadData() throws IOException{
		loadDataFromFile(actorFile);
		System.out.println("Done loading actors");
		loadDataFromFile(actressFile);
		System.out.println("Done loading actresses");
	}
	public HashMap<String, HashSet<String>> getMovies(){
		return movies;
	}
	public HashMap<String, HashSet<String>> getActors(){
		return actors;
	}
}
