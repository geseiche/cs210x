import java.util.ArrayList;

public abstract class Friend extends Profile{
	
	protected ArrayList<Friend> friends;
	protected ArrayList<Moment> moments;
	
	public Friend(String name, Image image){
		super(name, image);
		this.friends = new ArrayList<Friend>();
		this.moments = new ArrayList<Moment>();
	}
	
	public void setFriends(ArrayList<Friend> friends){
		this.friends = friends;
	}
	
	public void setMoments(ArrayList<Moment> moments){
		this.moments = moments;
	}
	
	
}
